/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.entities.TestRequestImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Edwin
 */
public class LabMessage extends LabReportDetail
{
    public static String _deviceId = "deviceId";
    private String deviceId;
    
    public AstmMessage astmMessage;
    
    private HeaderRecord headerRecord = new HeaderRecord();
    private PatientRecord patientRecord = new PatientRecord();
    private OrderRecord orderRecord = new OrderRecord();
    private TerminatorRecord terminatorRecord = new TerminatorRecord();
    
    
    private ExtendedResultRecord extendedResultRecord = new ExtendedResultRecord();
    
    private List<OrderRecord> orderRecordsList = new LinkedList<>();
    private List<ResultRecord> resultRecordsList = new LinkedList<>();
    
    private List<TestRequestImpl> testRequestImplsList = new LinkedList<>();
    
    private String rawMessage;
    private String validatorName;
    private TestOrderImpl testOrderImpl;
    
    public List<TestElementImpl> getAllTestElementList()
    {
        List<TestElementImpl> elementImplsList = new LinkedList<>();
        for (TestRequestImpl testRequest : testRequestImplsList)
        {
            elementImplsList.addAll(testRequest.getElements());
        }
        
        return elementImplsList;
    }

    public AstmMessage getAstmMessage()
    {
        return astmMessage;
    }

    public void setAstmMessage(AstmMessage astmMessage)
    {
        this.astmMessage = astmMessage;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }
    
    public void addOrder(OrderRecord orderRecord)
    {
        orderRecordsList.add(orderRecord);
    }
    
    public void addResult(ResultRecord resultRecord)
    {
        resultRecordsList.add(resultRecord);
    }

    public HeaderRecord getHeaderRecord()
    {
        return headerRecord;
    }

    public void setHeaderRecord(HeaderRecord headerRecord)
    {
        this.headerRecord = headerRecord;
    }

    public PatientRecord getPatientRecord()
    {
        return patientRecord;
    }

    public void setPatientRecord(PatientRecord patientRecord)
    {
        this.patientRecord = patientRecord;
    }

    
    public TerminatorRecord getTerminatorRecord()
    {
        return terminatorRecord;
    }

    public void setTerminatorRecord(TerminatorRecord terminatorRecord)
    {
        this.terminatorRecord = terminatorRecord;
    }
    
    public ExtendedResultRecord getExtendedResultRecord()
    {
        return extendedResultRecord;
    }

    public void setExtendedResultRecord(ExtendedResultRecord extendedResultRecord)
    {
        this.extendedResultRecord = extendedResultRecord;
    }

    public String getRawMessage()
    {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage)
    {
        this.rawMessage = rawMessage;
    }
        
    public OrderRecord getOrderRecord()
    {
        return orderRecord;
    }

    public void setOrderRecord(OrderRecord orderRecord)
    {
        this.orderRecord = orderRecord;
    }

    public List<OrderRecord> getOrderRecordsList()
    {
        return orderRecordsList;
    }

    public void setOrderRecordsList(List<OrderRecord> orderRecordsList)
    {
        this.orderRecordsList = orderRecordsList;
    }

    public List<ResultRecord> getResultRecordsList()
    {
        return resultRecordsList;
    }

    public void setResultRecordsList(List<ResultRecord> resultRecordsList)
    {
        this.resultRecordsList = resultRecordsList;
    }

    public TestOrderImpl getTestOrderImpl()
    {
        return testOrderImpl;
    }

    public void setTestOrderImpl(TestOrderImpl testOrderImpl)
    {
        this.testOrderImpl = testOrderImpl;
    }
    
    

    @Override
    public String toString()
    {
        return "LabMessage{" + "deviceId=" + deviceId + ", astmMessage=" + astmMessage + ", headerRecord=" + headerRecord + ", patientRecord=" + patientRecord + ", orderRecord=" + orderRecord + ", terminatorRecord=" + terminatorRecord + '}';
    }

    
    
    
    
    public List<LabReportDetail> labReports()
    {
        List<LabReportDetail> labReportsList = new LinkedList<>();
        
//        System.out.println("transforming .... " + resultRecordsList);
        
        int counter = 0;
        
        for (ResultRecord result : resultRecordsList)
        {
            LabReportDetail labReportDetail = new LabMessage();
            labReportDetail.setPatientName(getPatientName());
            labReportDetail.setSampleId(getOrderRecord().getSpecimenId());
            labReportDetail.setNo(++counter);
            
            labReportDetail.setAnalyteCode(result.getUniversalTestId());
            labReportDetail.setTestName(result.getAssayName());
            labReportDetail.setResultValue(result.getMeasurementData());
            
            if(!Strings.isNullOrEmpty(result.getUnitsOfMeasureValue()))
            {
                labReportDetail.setResultUnit(result.getUnitsOfMeasureValue());
            }
            
            labReportsList.add(labReportDetail);
        }
        
        
        return labReportsList;
    }
    
    
    public Map<String,ResultRecord> createResultMap()
    {
        System.out.println("creating result map out of .. " + resultRecordsList.size());
        
        System.out.println("Printing out initial list ...");
        for (ResultRecord resultRecord : resultRecordsList)
        {
//            System.out.println(resultRecord);
        }
        System.out.println("****** Proceeding to create the map");
        
        Map<String,ResultRecord> finalMap = new LinkedHashMap<>();
        
        Multimap<String, ResultRecord> myMultimap = ArrayListMultimap.create();
        
        for (ResultRecord resultRecord : resultRecordsList)
        {            
            //filter out no result
            if(resultRecord.getMeasurementData().equalsIgnoreCase(ResultRecord.NO_RESULT))
            {
                continue;
            }
            
            myMultimap.put(resultRecord.getUniversalTestId(), resultRecord);
        }
        
       Map<String,Collection<ResultRecord>> toMap = myMultimap.asMap();
       
        for (Map.Entry<String, Collection<ResultRecord>> entry : toMap.entrySet())
        {
//            System.out.println("distinct pocessing ..");
            
            String key = entry.getKey();
            Collection<ResultRecord> value = entry.getValue();
            
//            System.out.println(key + " : " + value);
            
            List<ResultRecord> myList = new LinkedList<>(value);
            
            //scan throug and search if some have been validated, use those ones for the basis of filtering
            
            List<ResultRecord> validatedList = myList.stream()
                    .filter(ResultRecord::isValidated)
                    .collect(Collectors.toList());
            
            if(!validatedList.isEmpty())
            {
                myList = validatedList;
            }
            
            
            
            Collections.sort(myList, new Comparator<ResultRecord>()
            {
                @Override
                public int compare(ResultRecord o1, ResultRecord o2)
                {
                   return o1.getDateTimeTestCompleted().compareTo(o2.getDateTimeTestCompleted());
                }
            });
            
            if(!myList.isEmpty())
            {
                ResultRecord aResultRecord = myList.get(myList.size() -1);
                
                finalMap.put(aResultRecord.getUniversalTestId(), aResultRecord);
//                System.out.println("final accepted : " + aResultRecord);
            }
            
        }
        
        
        
        
//        for (ResultRecord resultRecord : resultRecordsList)
//        {
//            finalMap.put(resultRecord.getUniversalTestId(), resultRecord);
//        }

        System.out.println("final .... " );
        for (Map.Entry<String, ResultRecord> entry : finalMap.entrySet())
        {
            String key = entry.getKey();
            ResultRecord value = entry.getValue();
            
//            System.out.println(key + " : " + value);
            
        }

//        System.out.println("final map prepared .. " + finalMap);
        System.out.println(" finalMap.size() : final map prepared .. " + finalMap.size());

        return finalMap;
    }

    public List<TestRequestImpl> getTestRequestImplsList()
    {
        return testRequestImplsList;
    }

    public void setTestRequestImplsList(List<TestRequestImpl> testRequestImplsList)
    {
        this.testRequestImplsList = testRequestImplsList;
    }

    public String getValidatorName()
    {
        return validatorName;
    }

    public void setValidatorName(String validatorName)
    {
        this.validatorName = validatorName;
    }
    
    
    
    
    
}
