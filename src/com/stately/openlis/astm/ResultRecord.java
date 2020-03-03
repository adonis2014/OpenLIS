/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "astm_result_record")
public class ResultRecord extends Record implements Serializable
{
    public static final String NO_RESULT = "No Result";
    
    @Column(name = "universal_test_id")
    private String universalTestId;
    
    @Column(name = "measurement_data")
    private String measurementData;
    
    @Column(name = "units_of_measure_value")
    private String unitsOfMeasureValue = "";
    
    @Column(name = "results_abnormal_flags")
    private String resultsAbnormalFlags;
    
    @Column(name = "result_status")
    private String resultStatus;
    
    @Column(name = "operation_identification")
    private String operationIdentification;
    
    @Column(name = "date_time_test_started")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTimeTestStarted;
    
    public static final String _dateTimeTestCompleted = "dateTimeTestCompleted";
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_time_test_completed")
    private Date dateTimeTestCompleted;
    
    @Column(name = "instrument_identification")
    private String instrumentIdentification;
    
    public static final String _assayName = "assayName";
    @Column(name = "assay_name")
    private String assayName;
    
    public static final String _validated = "validated";
    @XmlAttribute
    @Column(name = "validated")
    private boolean validated ;
    
    public static final String _resultAltered = "resultAltered";
    @XmlAttribute
    @Column(name = "result_altered")
    private boolean resultAltered;

    public ResultRecord()
    {
        setRecordType(RecordType.R);
        setCreatedOn(new Date());
        setRecievedTime(new Date());
    }
    
    

    public String getUniversalTestId()
    {
        return universalTestId;
    }

    public void setUniversalTestId(String universalTestId)
    {
        this.universalTestId = universalTestId;
    }

    public String getMeasurementData()
    {
        return measurementData;
    }

    public void setMeasurementData(String measurementData)
    {
        this.measurementData = measurementData;
    }

    public String getUnitsOfMeasureValue()
    {
        return unitsOfMeasureValue;
    }

    public void setUnitsOfMeasureValue(String unitsOfMeasureValue)
    {
        this.unitsOfMeasureValue = unitsOfMeasureValue;
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

    public String getAssayName()
    {
        return assayName;
    }

    public void setAssayName(String assayName)
    {
        this.assayName = assayName;
    }

    public boolean isValidated()
    {
        return validated;
    }

    public void setValidated(boolean validated)
    {
        this.validated = validated;
    }

    public boolean isResultAltered()
    {
        return resultAltered;
    }

    public void setResultAltered(boolean resultAltered)
    {
        this.resultAltered = resultAltered;
    }

    
    
    @Override
    public String toString()
    {
        return "ResultRecord{" + "universalTestId=" + universalTestId + ", measurementData=" + measurementData + ", dateTimeTestStarted=" + dateTimeTestStarted + ", dateTimeTestCompleted=" + dateTimeTestCompleted + ", assayName=" + assayName + '}';
    }

    
    
    
}
