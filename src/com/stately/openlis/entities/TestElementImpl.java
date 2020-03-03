/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.entities;

import com.stately.openlis.astm.ResultRecord;
import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edem
 */

@Entity
@Table(name = "test_elements")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestElementImpl extends Model implements Serializable{
    
    
    public static final String _no = "no";
    @Transient
    private String no;
    
    public static final String _testRequest = "testRequest";
    public static final String _testRequest_testOrder = _testRequest + "." + TestRequestImpl._testOrder;
    public static final String _testRequest_testName = _testRequest + "." + TestRequestImpl._testName;
    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "test_request")
    private TestRequestImpl testRequest;
    
    public static final String _testName = "testName";
    @XmlAttribute
    @Column(name = "test_name")
    private String testName;
    @XmlAttribute
    @Column(name = "concept_code")
    private String conceptCode;
    
    public static final String _analyteCode = "analyteCode";
    @XmlAttribute
    @Column(name = "analyte_code")
    private String analyteCode;
    
    public static final String _measurementData = "measurementData";
    @XmlAttribute
    @Column(name = "measurement_data")
    private String measurementData;
    
    public static final String _unitsOfMeasureValue = "unitsOfMeasureValue";
    @XmlAttribute
    @Column(name = "units_of_measure_value")
    private String unitsOfMeasureValue;
    @XmlAttribute
    @Column(name = "results_abnormal_flags")
    private String resultsAbnormalFlags;
    @XmlAttribute
    
    @Column(name = "result_status")
    private String resultStatus;
    @XmlAttribute
    @Column(name = "operation_identification")
    private String operationIdentification;
    @XmlAttribute
    @Column(name = "date_time_test_started")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTimeTestStarted;
    @XmlAttribute
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_time_test_completed")
    private Date dateTimeTestCompleted;
    
    @XmlAttribute
    @Column(name = "instrument_identification")
    private String instrumentIdentification;
    
    public static final String _resultAltered = "resultAltered";
    @XmlAttribute
    @Column(name = "result_altered")
    private boolean resultAltered;
    
    
    
    
    @Transient
    private ResultRecord resultRecord;
    
    public static final String _selected = "selected";
    @Transient
    private final BooleanProperty selected = new SimpleBooleanProperty();

    public TestElementImpl() {
    }

    public TestElementImpl(TestRequestImpl test, String analyteCode,String name,String conceptCode) {
        this.testRequest = test;
        this.analyteCode = analyteCode;
        this.testName = name;
        this.conceptCode = conceptCode;
    }

    public TestRequestImpl getTestRequest() {
        return testRequest;
    }

    public String getAnalyteCode() {
        return analyteCode;
    }

    public String getMeasurementData() {
        return measurementData;
    }

    public void setMeasurementData(String measurementData) {
        this.measurementData = measurementData;
    }

    public String getUnitsOfMeasureValue() {
        return unitsOfMeasureValue;
    }

    public void setUnitsOfMeasureValue(String unitsOfMeasureValue) {
        this.unitsOfMeasureValue = unitsOfMeasureValue;
    }

    public String getResultsAbnormalFlags() {
        return resultsAbnormalFlags;
    }

    public void setResultsAbnormalFlags(String resultsAbnormalFlags) {
        this.resultsAbnormalFlags = resultsAbnormalFlags;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getOperationIdentification() {
        return operationIdentification;
    }

    public void setOperationIdentification(String operationIdentification) {
        this.operationIdentification = operationIdentification;
    }

    public Date getDateTimeTestStarted() {
        return dateTimeTestStarted;
    }

    public void setDateTimeTestStarted(Date dateTimeTestStarted) {
        this.dateTimeTestStarted = dateTimeTestStarted;
    }

    public Date getDateTimeTestCompleted() {
        return dateTimeTestCompleted;
    }

    public void setDateTimeTestCompleted(Date dateTimeTestCompleted) {
        this.dateTimeTestCompleted = dateTimeTestCompleted;
    }

    public String getInstrumentIdentification() {
        return instrumentIdentification;
    }

    public void setInstrumentIdentification(String instrumentIdentification) {
        this.instrumentIdentification = instrumentIdentification;
    }

    public void setAnalyteCode(String analyteCode) {
        this.analyteCode = analyteCode;
    }

    public void setTestRequest(TestRequestImpl testRequest) {
        this.testRequest = testRequest;
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.testRequest = (TestRequestImpl) parent;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getConceptCode() {
        return conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public String getNo()
    {
        return no;
    }

    public void setNo(String no)
    {
        this.no = no;
    }
    
    public boolean isSelected()
    {
        return selected.get();
    }

    public void setSelected(boolean selected)
    {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty()
    {
        return selected;
    }

    public ResultRecord getResultRecord()
    {
        return resultRecord;
    }

    public void setResultRecord(ResultRecord resultRecord)
    {
        this.resultRecord = resultRecord;
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
        return "TestElementImpl{" + "testName=" + testName + ", conceptCode=" + conceptCode + ", analyteCode=" + analyteCode + ", measurementData=" + measurementData + ", unitsOfMeasureValue=" + unitsOfMeasureValue + ", dateTimeTestStarted=" + dateTimeTestStarted + ", dateTimeTestCompleted=" + dateTimeTestCompleted + ", validated=" + selected + '}';
    }

    
    

}
