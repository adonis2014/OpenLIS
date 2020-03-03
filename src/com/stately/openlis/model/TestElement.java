/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.model;

import java.io.Serializable;
import java.util.Date;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestElement implements Serializable{
    @XmlTransient
    private TestRequest test;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String conceptCode;
    @XmlAttribute
    private String analyteCode;
    @XmlAttribute
    private String measurementData;
    @XmlAttribute
    private String unitsOfMeasureValue;
    @XmlAttribute
    private String resultsAbnormalFlags;
    @XmlAttribute
    private String resultStatus;
    @XmlAttribute
    private String operationIdentification;
    @XmlAttribute
    private Date dateTimeTestStarted;
    @XmlAttribute
    private Date dateTimeTestCompleted;
    @XmlAttribute
    private String instrumentIdentification;

    public TestElement() {
    }

    public TestElement(TestRequest test, String analyteCode,String name,String conceptCode) {
        this.test = test;
        this.analyteCode = analyteCode;
        this.name = name;
        this.conceptCode = conceptCode;
    }

    public TestRequest getTest() {
        return test;
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

    public void setTest(TestRequest test) {
        this.test = test;
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.test = (TestRequest) parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConceptCode() {
        return conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }
    
    

}
