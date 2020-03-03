/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.parsers;

import com.google.common.base.Strings;
import com.stately.openlis.astm.Delimiter;
import com.stately.openlis.astm.HeaderRecord;
import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.astm.RecordType;
import com.stately.openlis.astm.OrderRecord;
import com.stately.openlis.astm.PatientRecord;
import com.stately.openlis.astm.ResultRecord;
import com.stately.openlis.astm.TerminatorRecord;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 *
 * @author Edwin
 */
public class AstmParser
{
//    final static String SEGMENT_DELIMITER = "\r";

    final static String SEGMENT_DELIMITER = AsciiCode.STX;

    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final Logger logger = Logger.getLogger(AstmParser.class.getName());

    public LabMessage parseAstm(String data)
    {
        try
        {
            System.out.println("initiating parsing of Data.... \n");

            String[] segementsList = data.split(SEGMENT_DELIMITER);
//        logger.info("Data Segement");
//        StringUtil.printArray(segementsList);

            LabMessage message = new LabMessage();
            message.setRawMessage(data);

            for (String msgSegement : segementsList)
            {

                RecordType recordType = determineRecordType(msgSegement);
                if (recordType == null)
                {
                    continue;
                }

//                String[] fieldsList = msgSegement.split(Delimiter.field);

                System.out.println("record :::::: " + recordType + " -- " + msgSegement);

                if (recordType == RecordType.H)
                {
                    HeaderRecord headerRecord = processHeader(msgSegement);
                    message.setHeaderRecord(headerRecord);
                }
                if (recordType == RecordType.P)
                {
                    PatientRecord patientRecord = processPatientRecord(msgSegement);

                    message.setPatientName(patientRecord.getPatientName());
                    message.setPatientId(patientRecord.getPracticeAssignPatientId());

                    message.setPatientRecord(patientRecord);
                }
                if (recordType == RecordType.O)
                {
                    OrderRecord orderRecord = processOrderRecord(msgSegement);
                    message.setOrderRecord(orderRecord);
                    message.setSampleId(orderRecord.getSpecimenId());
//                message.addOrder(orderRecord);
                }
                if (recordType == RecordType.R)
                {
                    ResultRecord resultRecord = processResultRecord(msgSegement);
                    message.addResult(resultRecord);
                }
                if (recordType == RecordType.L)
                {
                    TerminatorRecord terminatorRecord = processTerminatorRecord(msgSegement);
                    message.setTerminatorRecord(terminatorRecord);
                }
            }

            return message;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public RecordType determineRecordType(String msgSegement)
    {
        String[] fieldsList = StringUtil.split(msgSegement, Delimiter.field);

        if (fieldsList.length > 0)
        {
            String firstField = fieldsList[0];

//            System.out.println(firstField);
            if (firstField.contains(RecordType.H.name()))
            {
                return RecordType.H;
            }
            if (firstField.contains(RecordType.P.name()))
            {
                return RecordType.P;
            }
            if (firstField.contains(RecordType.O.name()))
            {
                return RecordType.O;
            }
            if (firstField.contains(RecordType.R.name()))
            {
                return RecordType.R;
            }
            if (firstField.contains(RecordType.L.name()))
            {
                return RecordType.L;
            }
        }

        return null;
    }

    public HeaderRecord processHeader(String msgSegement)
    {
        HeaderRecord headerRecord = new HeaderRecord();

        String[] fieldsList = StringUtil.split(msgSegement, Delimiter.field);
        StringUtil.printArray(fieldsList);
        try
        {
            headerRecord.setDateTime(dateFormat.parse(fieldsList[13]));
        } catch (Exception e)
        {
        }

        return headerRecord;
    }

    public PatientRecord processPatientRecord(String msgSegement)
    {
        PatientRecord patientRecord = new PatientRecord();

        String[] fieldsList = StringUtil.split(msgSegement, Delimiter.field);
        StringUtil.printArray(fieldsList);

        try
        {
            patientRecord.setSequenceNumber(fieldsList[1]);
            patientRecord.setPracticeAssignPatientId(fieldsList[2]);
            patientRecord.setPatientName(fieldsList[5]);
        } catch (Exception e)
        {
        }

        if (Strings.isNullOrEmpty(patientRecord.getPatientName()))
        {
            patientRecord.setPatientName("<NO NAME AVAILABLE>");
        }

//        System.out.println("patient name ....... " + fieldsList[5]);
        try
        {
            patientRecord.setBirthDate(dateFormat.parse(fieldsList[7]));
        } catch (Exception e)
        {
        }

        try
        {
            patientRecord.setPatientSex(fieldsList[8]);
//        patientRecord.setPatientAddress(fieldsList[10]);
//        patientRecord.setAttendingPhysician(fieldsList[13]);
//        patientRecord.setLocation(fieldsList[25]);
        } catch (Exception e)
        {
            System.out.println("Error parsing some fields in patient record :: " + msgSegement);
        }

        patientRecord.setPatientName(patientRecord.getPatientName().replace(Delimiter.component, " "));

        return patientRecord;
    }

    public void logger()
    {

    }

    public OrderRecord processOrderRecord(String msgSegement)
    {
        OrderRecord orderRecord = new OrderRecord();

        String[] fieldsList = StringUtil.split(msgSegement, Delimiter.field);
        StringUtil.printArray(fieldsList);

        orderRecord.setSequenceNumber(fieldsList[1]);
        orderRecord.setSpecimenId(fieldsList[2]);
        orderRecord.setUniversalTestId(fieldsList[4]);
//        orderRecord.setPriority(fieldsList[5]);

        try
        {
            orderRecord.setSpecimenCollectDateTime(dateFormat.parse(fieldsList[7]));
        } catch (Exception e)
        {
        }

//        orderRecord.setActionCode(fieldsList[11]);
//        orderRecord.setSpecimenDescriptor(fieldsList[15]);
//        orderRecord.setReportTypes(fieldsList[25]);
        try
        {
            //break down of sample ID
            String fullSpecimenId = orderRecord.getSpecimenId();
            String[] specimentIDPart = StringUtil.split(fullSpecimenId, Delimiter.component);
            orderRecord.setSpecimenId(specimentIDPart[0]);

        } catch (Exception e)
        {
        }

        return orderRecord;
    }

    public ResultRecord processResultRecord(String msgSegement)
    {
        ResultRecord resultRecord = new ResultRecord();

        String[] fieldsList = StringUtil.split(msgSegement, Delimiter.field);
        StringUtil.printArray(fieldsList);

        resultRecord.setSequenceNumber(fieldsList[1]);
        resultRecord.setUniversalTestId(fieldsList[2]);
        resultRecord.setMeasurementData(fieldsList[3]);
        resultRecord.setUnitsOfMeasureValue(fieldsList[4]);
        resultRecord.setResultsAbnormalFlags(fieldsList[6]);
        resultRecord.setResultStatus(fieldsList[8]);
        resultRecord.setOperationIdentification(fieldsList[10]);

        try
        {
            resultRecord.setDateTimeTestStarted(dateFormat.parse(fieldsList[11]));
        } catch (Exception e)
        {
        }
        try
        {
            resultRecord.setDateTimeTestCompleted(dateFormat.parse(fieldsList[12]));
        } catch (Exception e)
        {
        }

        resultRecord.setInstrumentIdentification(fieldsList[13]);

        /// process measurementdata
        if (!Strings.isNullOrEmpty(resultRecord.getUniversalTestId()))
        {
            System.out.println("parsing measurement data : " + resultRecord.getUniversalTestId());
            String[] components = StringUtil.split(resultRecord.getUniversalTestId(), Delimiter.component);
            StringUtil.printArray(components);

            String localManuCode = components[3];
            System.out.println("localManuCode : " + localManuCode);

            String[] plusDiv = StringUtil.split(localManuCode, Delimiter.plus);
            StringUtil.printArray(plusDiv);

            String assayCode = plusDiv[1];

            String assayName = DictionaryManager.instance().lookupAssayCode(assayCode);

            resultRecord.setUniversalTestId(assayCode);
            resultRecord.setAssayName(assayName);

        }

        return resultRecord;
    }

    public TerminatorRecord processTerminatorRecord(String msgSegement)
    {
        TerminatorRecord terminatorRecord = new TerminatorRecord();

        String[] fieldsList = StringUtil.split(msgSegement, Delimiter.field);
        StringUtil.printArray(fieldsList);

        terminatorRecord.setSequenceNumber(fieldsList[1]);
        terminatorRecord.setTerminatorCode(fieldsList[2]);

        return terminatorRecord;
    }

}
