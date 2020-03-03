/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.services;

import com.google.common.base.Strings;
import com.stately.openlis.astm.AstmMessage;
import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.astm.ResultRecord;
import com.stately.openlis.entities.StatusCode;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.entities.TestRequestImpl;
import com.stately.openlis.parsers.LogManager;
import com.stately.common.model.DateRange;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.openlis.model.TestElement;
import com.stately.openlis.model.TestOrder;
import com.stately.openlis.model.TestRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Edwin
 */
public class TestOrderService
{

    private CrudService crudService;

    public String genId()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public TestOrderService(CrudService crudService)
    {
        this.crudService = crudService;
    }

    public TestOrder loadTestOrder(String sampleId)
    {

        TestOrderImpl testOrderImpl = findTestOrderImplBySampleId(sampleId);

        if (testOrderImpl == null)
        {
            return null;
        }

//        return getTestOrder(sampleId);
        return getTestOrder(testOrderImpl.getOrderId());
    }

    public TestOrderImpl findTextByCarewexOrderId(String orderID)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestOrderImpl.class);
        qryBuilder.addObjectParam(TestOrderImpl._orderId, orderID);

        List<TestOrderImpl> testOrderImplsList = qryBuilder.buildQry().getResultList();

        if (testOrderImplsList == null || testOrderImplsList.isEmpty())
        {
            return null;
        }

        return testOrderImplsList.get(0);

    }

    public TestOrderImpl findTestOrderImplBySampleId(String sampleId)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestOrderImpl.class);
        qryBuilder.addStringQryParam(TestOrderImpl._orderCode, sampleId, QryBuilder.ComparismCriteria.LIKE);
        
        TestOrderImpl testOrderImpl = qryBuilder.getSingleResult2(TestOrderImpl.class);
        return testOrderImpl;
    }

    public TestOrder getTestOrder(String orderId)
    {
        try
        {
            TestOrderImpl testOrderImpl = findTextByCarewexOrderId(orderId);

            if (testOrderImpl == null)
            {
                return null;
            }

            TestOrder testOrder = new TestOrder();
            testOrder.setOrderId(testOrderImpl.getOrderId());
            testOrder.setPatientName(testOrderImpl.getPatientName());
            testOrder.setPatientId(testOrderImpl.getPatientId());
            testOrder.setFacilityId(testOrderImpl.getFacilityId());
            testOrder.setFacilityName(testOrderImpl.getFacilityName());
            testOrder.setOrderDate(testOrderImpl.getOrderDate());

            QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestRequestImpl.class);
            qryBuilder.addObjectParam(TestRequestImpl._testOrder, testOrderImpl);

            List<TestRequest> testRequestsList = new LinkedList<>();

            List<TestRequestImpl> testRequestImplsList = qryBuilder.buildQry().getResultList();
            for (TestRequestImpl testRequestImpl : testRequestImplsList)
            {
                TestRequest testRequest = new TestRequest();
                testRequest.setConceptCode(testRequestImpl.getConceptCode());
                testRequest.setSpecimenId(testRequestImpl.getSpecimenId());
                testRequest.setTestId(testRequestImpl.getTestId());
                testRequest.setTestName(testRequestImpl.getTestName());

                testRequest.setValidatedBy(testRequestImpl.getValidatedBy());
                testRequest.setValidatedOn(testRequestImpl.getValidatedOn());

                testRequest.setOrder(testOrder);

                qryBuilder = new QryBuilder(crudService.getEm(), TestElementImpl.class);
                qryBuilder.addObjectParam(TestElementImpl._testRequest, testRequestImpl);

                List<TestElement> testElementsList = new LinkedList<>();

                List<TestElementImpl> elementImplsList = qryBuilder.buildQry().getResultList();

                for (TestElementImpl testElementImpl : elementImplsList)
                {
                    TestElement testElement = new TestElement();
                    testElement.setTest(testRequest);

                    testElement.setAnalyteCode(testElementImpl.getAnalyteCode());
                    testElement.setConceptCode(testElementImpl.getConceptCode());
                    testElement.setDateTimeTestCompleted(testElementImpl.getDateTimeTestCompleted());
                    testElement.setDateTimeTestStarted(testElementImpl.getDateTimeTestStarted());
                    testElement.setInstrumentIdentification(testElementImpl.getInstrumentIdentification());
                    testElement.setMeasurementData(testElementImpl.getMeasurementData());
                    testElement.setName(testElementImpl.getTestName());
                    testElement.setOperationIdentification(testElementImpl.getOperationIdentification());
                    testElement.setResultStatus(testElementImpl.getResultStatus());
                    testElement.setResultsAbnormalFlags(testElementImpl.getResultsAbnormalFlags());

                    testElement.setUnitsOfMeasureValue(testElementImpl.getUnitsOfMeasureValue());

                    testElementsList.add(testElement);

                }

                testRequest.setElements(testElementsList);
                testRequestsList.add(testRequest);

            }

            testOrder.setRequests(testRequestsList);

            return testOrder;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public TestOrderImpl saveOrder(TestOrder testOrder)
    {
        try
        {

            TestOrderImpl previous = findTextByCarewexOrderId(testOrder.getOrderId());

            if (previous != null)
            {
                System.out.println("Test Order already exists in database");
                return previous;
            }

            TestOrderImpl testOrderImpl = new TestOrderImpl();
            testOrderImpl.setId(genId());

            testOrderImpl.setOrderId(testOrder.getOrderId());
            testOrderImpl.setPatientName(testOrder.getPatientName());
            testOrderImpl.setPatientId(testOrder.getPatientId());
            testOrderImpl.setFacilityId(testOrder.getFacilityId());
            testOrderImpl.setFacilityName(testOrder.getFacilityName());

            if (testOrder.getOrderDate() != null)
            {
                testOrderImpl.setOrderDate(testOrder.getOrderDate());
            } else
            {
                testOrderImpl.setOrderDate(new Date());
            }

            String orderCode = LogManager.instance().createOrderFile(testOrder);
            testOrderImpl.setOrderCode(orderCode);

            testOrderImpl.setCreatedOn(new Date());
            testOrderImpl.setRecievedTime(new Date());

            crudService.save(testOrderImpl);

            List<TestRequest> testRequestsList = testOrder.getRequests();
            for (TestRequest testRequest : testRequestsList)
            {
                TestRequestImpl testRequestImpl = StaticConverters.toTestRequestImpl(testRequest);

                testRequestImpl.setId(genId());

                testRequestImpl.setTestOrder(testOrderImpl);

                crudService.save(testRequestImpl);

                List<TestElement> elementsList = testRequest.getElements();
                for (TestElement testElement : elementsList)
                {
                    TestElementImpl testElementImpl = StaticConverters.toTestElementImpl(testElement);
                    testElementImpl.setTestRequest(testRequestImpl);

                    testElementImpl.setId(genId());

                    crudService.save(testElementImpl);
                }
            }

            return testOrderImpl;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    public List<TestOrderImpl> getTestOrderImplsList(DateRange dateRange, String sampleID)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestOrderImpl.class);
        qryBuilder.addStringQryParam(TestOrderImpl._orderCode, sampleID, QryBuilder.ComparismCriteria.LIKE);
        qryBuilder.addDateRange(dateRange, TestOrderImpl._createdOn);
        System.out.println(qryBuilder.getQryInfo());

        return qryBuilder.buildQry().getResultList();
    }

    public List<TestOrderImpl> getTestOrderImplsList(DateRange dateRange, StatusCode statusCode)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestOrderImpl.class);
        qryBuilder.addObjectParam(TestOrderImpl._orderStatus, statusCode);
        qryBuilder.addDateRange(dateRange, TestOrderImpl._createdOn);
        System.out.println(qryBuilder.getQryInfo());

        return qryBuilder.buildQry().getResultList();
    }

    public String stripCode(String sampleId)
    {
        if(sampleId.contains("-"))
        {
            sampleId = sampleId.substring(9, sampleId.length());
        }
        
        if(sampleId.startsWith("00"))
        {
            sampleId = sampleId.replaceFirst("00", "");
        }
        
        if(sampleId.startsWith("0"))
        {
            sampleId = sampleId.replaceFirst("0", "");
        }
        
        return sampleId;
    }
    
    public List<AstmMessage> getAstmMessages(String sampleId)
    {
        
        System.out.println("Original : " + sampleId);
        
        sampleId = stripCode(sampleId);
        
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), AstmMessage.class);
//        qryBuilder.addObjectParam(AstmMessage._sampleId, sampleId);
        qryBuilder.addStringQryParam(AstmMessage._sampleId, sampleId, QryBuilder.ComparismCriteria.LIKE);
        System.out.println(qryBuilder.getQryInfo());
        return qryBuilder.buildQry().getResultList();
    }
    
    public List<AstmMessage> getAstmMessages(StatusCode statusCode)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), AstmMessage.class);
        qryBuilder.addObjectParam(AstmMessage._statusCode, statusCode);
        
//        qryBuilder.addStringQryParam(AstmMessage._sampleId, sampleId, QryBuilder.ComparismCriteria.LIKE);
        System.out.println(qryBuilder.getQryInfo());
        
        qryBuilder.orderByDesc(AstmMessage._createdOn);
        return qryBuilder.buildQry().setFirstResult(0).setMaxResults(1000).getResultList();
    }

    public List<AstmMessage> getAstmMessages(DateRange dateRange, String sampleID)
    {
        sampleID = stripCode(sampleID);
        
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), AstmMessage.class);
        qryBuilder.addStringQryParam(AstmMessage._sampleId, sampleID, QryBuilder.ComparismCriteria.LIKE);
        qryBuilder.addDateRange(dateRange, AstmMessage._createdOn);
        qryBuilder.orderByDesc(AstmMessage._recievedTime);
        
        System.out.println(qryBuilder.getQryInfo());

        return qryBuilder.buildQry().getResultList();
    }

    public AstmMessage saveLabMessage(LabMessage labMessage)
    {
        try
        {
            Date valueDate = new Date();
            AstmMessage astmMessage = new AstmMessage();
            astmMessage.setId(Store.get().getId());
            astmMessage.setSampleId(labMessage.getSampleId());
            astmMessage.setDeviceId(labMessage.getDeviceId());
            astmMessage.setPatientName(labMessage.getPatientName());
            astmMessage.setMessageText(labMessage.getRawMessage());
            astmMessage.setCreatedOn(valueDate);
            astmMessage.setRecievedTime(valueDate);

            AstmMessage savedMessage = Store.get().crudService().save(astmMessage);
            if(savedMessage == null)
            {
                System.out.println("Unable save labMessage " + labMessage);
                return null;
            }

            List<ResultRecord> resultRecordsList = labMessage.getResultRecordsList();
            for (ResultRecord resultRecord : resultRecordsList)
            {
                String id = genId();
                
                while (crudService.find(ResultRecord.class, id) != null)
                {                    
                     id = genId();
                }
                
                resultRecord.setId(id);
                resultRecord.setAstmMessage(astmMessage);
                Store.get().crudService().save(resultRecord);
                System.out.println("saved ... " + resultRecord);
            }

            return astmMessage;

        } catch (Exception e)
        {
            System.out.println("Error saving new lab message ... " + labMessage);
            e.printStackTrace();
        }

        return null;

    }

    public List<ResultRecord> getResultRecord(AstmMessage astmMessage)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), ResultRecord.class);
        qryBuilder.addObjectParam(ResultRecord._astmMessage, astmMessage);
        qryBuilder.orderByAsc(ResultRecord._assayName);
        qryBuilder.orderByDesc(ResultRecord._dateTimeTestCompleted);

        return qryBuilder.buildQry().getResultList();
    }

    public List<ResultRecord> getResultRecord(String sampleId)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), ResultRecord.class);
        qryBuilder.addObjectParam(ResultRecord._astmMessage_sampleId, sampleId);
        qryBuilder.orderByAsc(ResultRecord._assayName);
        qryBuilder.orderByDesc(ResultRecord._dateTimeTestCompleted);

        return qryBuilder.buildQry().getResultList();
    }

    public List<TestElementImpl> getTestElement(TestOrderImpl testOrderImpl)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestElementImpl.class);
        qryBuilder.addObjectParam(TestElementImpl._testRequest_testOrder, testOrderImpl);

        return qryBuilder.buildQry().getResultList();
    }

    public List<TestElementImpl> getTestElementByRequest(TestRequestImpl testOrderImpl)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestElementImpl.class);
        qryBuilder.addObjectParam(TestElementImpl._testRequest, testOrderImpl);

        return qryBuilder.buildQry().getResultList();
    }

    public List<TestRequestImpl> getTestRequestList(TestOrderImpl testOrderImpl)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestRequestImpl.class);
        qryBuilder.addObjectParam(TestRequestImpl._testOrder, testOrderImpl);

        return qryBuilder.buildQry().getResultList();
    }

    public List<TestRequestImpl> getTestRequestList(String sampleId)
    {
        sampleId = stripCode(sampleId);
        
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestRequestImpl.class);
        qryBuilder.addStringQryParam(TestRequestImpl._testOrder_orderCode, sampleId, QryBuilder.ComparismCriteria.LIKE);
//        qryBuilder.addObjectParam(TestRequestImpl._testOrder_orderCode, sampleId);
        System.out.println(qryBuilder.getQryInfo());

        return qryBuilder.buildQry().getResultList();
    }

    public TestRequestImpl getTestRequestByTestId(String testId)
    {
        QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), TestRequestImpl.class);
        qryBuilder.addObjectParam(TestRequestImpl._testId, testId);

        return qryBuilder.getSingleResult2(TestRequestImpl.class);
    }

    public TestOrder getPackageTestOrder(String sampleId)
    {

        if (Strings.isNullOrEmpty(sampleId))
        {
            System.out.println("Cannot load TestOrder with empty sample id");
            return null;
        }

        TestOrder testOrder = Store.get().testOrderService().loadTestOrder(sampleId);

        if (testOrder == null)
        {
            System.out.println("Cannot find saved test order with a sample id : " + sampleId);
            return null;
        }

        sampleId = sampleId.replaceAll("_", "");
        List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(sampleId);

        System.out.println("similar ASTM Mesages = " + astmMessagesList.size());

        System.out.println("clearling list ...");
        LabMessage labMessage = new LabMessage();

        for (AstmMessage astmMessage : astmMessagesList)
        {
            List<ResultRecord> resultRecordsList = Store.get().testOrderService().getResultRecord(astmMessage);
            for (ResultRecord resultRecord : resultRecordsList)
            {
                labMessage.addResult(resultRecord);
            }
        }

        System.out.println("initiatsing sending of new mesaage ... to JMS ..");
        Map<String, ResultRecord> map = labMessage.createResultMap();

        System.out.println("retriving data ... " + testOrder);
        System.out.println("test order .. " + testOrder);
        //retrive all TestElement for fetched TestOrder
        List<TestElement> testElementsList = new LinkedList<>();
        for (TestRequest request : testOrder.getRequests())
        {

            //moved to main testOrder retriveval
//            TestRequestImpl testRequestImpl = Store.get().testOrderService().getTestRequestByTestId(request.getTestId());
//            
//            request.setValidatedBy(testRequestImpl.getValidatedBy());
//            request.setValidatedOn(testRequestImpl.getValidatedOn());
            testElementsList.addAll(request.getElements());
        }

        //retrive result records and transform them/map to TestElement
        for (TestElement testElement : testElementsList)
        {
            ResultRecord resultRecord = map.get(testElement.getAnalyteCode());

            if (resultRecord != null)
            {
                testElement.setDateTimeTestStarted(resultRecord.getDateTimeTestStarted());
                testElement.setDateTimeTestCompleted(resultRecord.getDateTimeTestCompleted());

                testElement.setInstrumentIdentification(resultRecord.getInstrumentIdentification());
                testElement.setMeasurementData(resultRecord.getMeasurementData());
                testElement.setOperationIdentification(resultRecord.getOperationIdentification());
                testElement.setResultStatus(resultRecord.getResultStatus());
                testElement.setResultsAbnormalFlags(resultRecord.getResultsAbnormalFlags());
                testElement.setUnitsOfMeasureValue(resultRecord.getUnitsOfMeasureValue());
            }
        }

        return testOrder;

    }

    public LabMessage toLabMessage(TestOrderImpl testOrderImpl)
    {
        LabMessage labMessage = new LabMessage();

        if (testOrderImpl == null)
        {
            String msg = "Cannot find TestOrder with a sample id matching " + labMessage.getSampleId();
            System.out.println(":::: " + msg);

            return labMessage;
        }

        labMessage.setPatientId(testOrderImpl.getPatientId());
        labMessage.setPatientName(testOrderImpl.getPatientName());
        labMessage.setSampleId(testOrderImpl.getOrderCode());
        
        List<TestElementImpl> testElementImplsList = Store.get().testOrderService().getTestElement(testOrderImpl);

        String orderCode = testOrderImpl.getOrderCode().replaceAll("_", "");
        
        //temporary messaure for filterint out date part
//        if(orderCode.contains("-"))
//        {
////            orderCode = orderCode.substring(0, 6);
//            orderCode = orderCode.substring(7, orderCode.length());
//        }
        
        System.out.println("Order Code is ... " + orderCode);

        List<ResultRecord> orderResultRecordsList = new LinkedList<>();

        for (TestElementImpl testElementImpl : testElementImplsList)
        {
            System.out.println(testElementImpl.getId() + " -- " + testElementImpl.getAnalyteCode() + " ... " + testElementImpl.getTestName());

            ResultRecord resultRecord = new ResultRecord();
            resultRecord.setUniversalTestId(testElementImpl.getAnalyteCode());
            resultRecord.setAssayName(testElementImpl.getTestName());
            resultRecord.setMeasurementData(testElementImpl.getMeasurementData());
            resultRecord.setUnitsOfMeasureValue(testElementImpl.getUnitsOfMeasureValue());

            orderResultRecordsList.add(resultRecord);
        }

        List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(orderCode);

        System.out.println("similar ASTM Mesages = " + astmMessagesList.size());

        for (AstmMessage astmMessage : astmMessagesList)
        {
            List<ResultRecord> resultRecordsList = Store.get().testOrderService().getResultRecord(astmMessage);
            for (ResultRecord resultRecord : resultRecordsList)
            {
                labMessage.addResult(resultRecord);
            }
        }

        Map<String, ResultRecord> map = labMessage.createResultMap();

        System.out.println("TestOrder Retrived from DB = " + testOrderImpl.getOrderCode() + " --- " + testOrderImpl);

        for (ResultRecord orderTestElement : orderResultRecordsList)
        {
            
            ResultRecord resultRecord = map.get(orderTestElement.getUniversalTestId());

            if (resultRecord != null)
            {
                orderTestElement.setDateTimeTestStarted(resultRecord.getDateTimeTestStarted());
                orderTestElement.setDateTimeTestCompleted(resultRecord.getDateTimeTestCompleted());

                orderTestElement.setInstrumentIdentification(resultRecord.getInstrumentIdentification());
                orderTestElement.setMeasurementData(resultRecord.getMeasurementData());
                orderTestElement.setOperationIdentification(resultRecord.getOperationIdentification());
                orderTestElement.setResultStatus(resultRecord.getResultStatus());
                orderTestElement.setResultsAbnormalFlags(resultRecord.getResultsAbnormalFlags());
                orderTestElement.setUnitsOfMeasureValue(resultRecord.getUnitsOfMeasureValue());
                
            } else
            {
//                System.out.println("cannot loadTestOrder mapped pair for " + orderTestElement.getUniversalTestId());
            }
        }

        labMessage.setResultRecordsList(orderResultRecordsList);

        return labMessage;

    }

}
