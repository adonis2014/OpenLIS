/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edem
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestRequest implements Serializable {
    @XmlAttribute
    private String testId;
    @XmlAttribute
    private String specimenId;
    @XmlAttribute
    private String testName;
    @XmlAttribute
    private String conceptCode;
    @XmlElementWrapper(name = "elements")
    @XmlElement(name = "element")
    private List<TestElement> elements = new ArrayList<>();
    @XmlTransient
    private TestOrder order;
    private String analyserId;
    @XmlAttribute
    private String validatedBy;
    @XmlAttribute
    private Date validatedOn;

    public TestRequest() {       
    }
    
    
    public TestRequest(String testId,  String testName,TestOrder order) {
        this.testId = testId;
        this.testName = testName;
        this.order = order;
    }

    public String getTestId() {
        return testId;
    }

    public String getSpecimenId() {
        return specimenId;
    }

    public String getTestName() {
        return testName;
    }

    public String getConceptCode() {
        return conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public List<TestElement> getElements() {
        return elements;
    }

    public TestOrder getOrder() {
        return order;
    }

    public void setOrder(TestOrder order) {
        this.order = order;
    }

    public void setElements(List<TestElement> elements) {
        this.elements = elements;
    }

    public void setSpecimenId(String specimenId) {
        this.specimenId = specimenId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }    
    
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.order = (TestOrder) parent;
    }

    public String getAnalyserId() {
        return analyserId;
    }

    public void setAnalyserId(String analyserId) {
        this.analyserId = analyserId;
    }

    public String getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Date getValidatedOn() {
        return validatedOn;
    }

    public void setValidatedOn(Date validatedOn) {
        this.validatedOn = validatedOn;
    }
    
    
}
