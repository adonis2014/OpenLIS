/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.services;

import com.stately.openlis.astm.ResultRecord;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestRequestImpl;
import com.stately.openlis.model.TestElement;
import com.stately.openlis.model.TestRequest;

/**
 *
 * @author Edwin
 */
public class StaticConverters
{

    public static TestElementImpl toTestElement(TestElementImpl elementImpl, ResultRecord resultRecord)
    {

        if (resultRecord != null)
        {
            elementImpl.setSelected(resultRecord.isValidated());
            elementImpl.setResultRecord(resultRecord);
            elementImpl.setDateTimeTestStarted(resultRecord.getDateTimeTestStarted());
            elementImpl.setDateTimeTestCompleted(resultRecord.getDateTimeTestCompleted());

            elementImpl.setTestName(resultRecord.getAssayName());
            elementImpl.setAnalyteCode(resultRecord.getUniversalTestId());

            elementImpl.setInstrumentIdentification(resultRecord.getInstrumentIdentification());
            elementImpl.setMeasurementData(resultRecord.getMeasurementData());
            elementImpl.setOperationIdentification(resultRecord.getOperationIdentification());
            elementImpl.setResultStatus(resultRecord.getResultStatus());
            elementImpl.setResultsAbnormalFlags(resultRecord.getResultsAbnormalFlags());
            elementImpl.setUnitsOfMeasureValue(resultRecord.getUnitsOfMeasureValue());
        }

        return elementImpl;
    }

    public static TestElementImpl toTestElement(ResultRecord resultRecord)
    {
        TestElementImpl elementImpl = new TestElementImpl();

        return toTestElement(elementImpl, resultRecord);
    }

    public static TestRequestImpl toTestRequestImpl(TestRequest testRequest)
    {
        TestRequestImpl testRequestImpl = new TestRequestImpl();

        testRequestImpl.setConceptCode(testRequest.getConceptCode());
        testRequestImpl.setSpecimenId(testRequest.getSpecimenId());
        testRequestImpl.setTestId(testRequest.getTestId());
        testRequestImpl.setTestName(testRequest.getTestName());

        return testRequestImpl;

    }

    public static TestElementImpl toTestElementImpl(TestElement testElement)
    {
        TestElementImpl testElementImpl = new TestElementImpl();

        testElementImpl.setAnalyteCode(testElement.getAnalyteCode());
        testElementImpl.setConceptCode(testElement.getConceptCode());
        testElementImpl.setDateTimeTestCompleted(testElement.getDateTimeTestCompleted());
        testElementImpl.setDateTimeTestStarted(testElement.getDateTimeTestStarted());
        testElementImpl.setInstrumentIdentification(testElement.getInstrumentIdentification());
        testElementImpl.setMeasurementData(testElement.getMeasurementData());
        testElementImpl.setTestName(testElement.getName());
        testElementImpl.setOperationIdentification(testElement.getOperationIdentification());
        testElementImpl.setResultStatus(testElement.getResultStatus());
        testElementImpl.setResultsAbnormalFlags(testElement.getResultsAbnormalFlags());

        testElementImpl.setUnitsOfMeasureValue(testElement.getUnitsOfMeasureValue());

        return testElementImpl;

    }
}
