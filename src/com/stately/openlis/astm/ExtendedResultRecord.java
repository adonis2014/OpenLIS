/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import java.util.Date;

/**
 *
 * @author Edwin
 */
public class ExtendedResultRecord extends Record
{
    private String univesalTestId;
    private String measurementData;
    private String unitsOfMeasure;
    private String resultsAbnormalFlags;
    private String resultStatus;
    private String operationIdentification;
    private Date dateTimeTestStarted;
    private Date dateTimeTestCompleted;
    private String instrumentIdentification;

    public String getUnivesalTestId()
    {
        return univesalTestId;
    }

    public void setUnivesalTestId(String univesalTestId)
    {
        this.univesalTestId = univesalTestId;
    }

    public String getMeasurementData()
    {
        return measurementData;
    }

    public void setMeasurementData(String measurementData)
    {
        this.measurementData = measurementData;
    }

    public String getUnitsOfMeasure()
    {
        return unitsOfMeasure;
    }

    public void setUnitsOfMeasure(String unitsOfMeasure)
    {
        this.unitsOfMeasure = unitsOfMeasure;
    }

    public String getResultsAbnormalFlags()
    {
        return resultsAbnormalFlags;
    }

    public void setResultsAbnormalFlags(String resultsAbnormalFlags)
    {
        this.resultsAbnormalFlags = resultsAbnormalFlags;
    }

    public String getResultStatus()
    {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus)
    {
        this.resultStatus = resultStatus;
    }

    public String getOperationIdentification()
    {
        return operationIdentification;
    }

    public void setOperationIdentification(String operationIdentification)
    {
        this.operationIdentification = operationIdentification;
    }

    public Date getDateTimeTestStarted()
    {
        return dateTimeTestStarted;
    }

    public void setDateTimeTestStarted(Date dateTimeTestStarted)
    {
        this.dateTimeTestStarted = dateTimeTestStarted;
    }

    public Date getDateTimeTestCompleted()
    {
        return dateTimeTestCompleted;
    }

    public void setDateTimeTestCompleted(Date dateTimeTestCompleted)
    {
        this.dateTimeTestCompleted = dateTimeTestCompleted;
    }

    public String getInstrumentIdentification()
    {
        return instrumentIdentification;
    }

    public void setInstrumentIdentification(String instrumentIdentification)
    {
        this.instrumentIdentification = instrumentIdentification;
    }
    
    
}
