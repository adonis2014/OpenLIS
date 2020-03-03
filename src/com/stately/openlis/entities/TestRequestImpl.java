/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.entities;

import com.stately.openlis.astm.ResultRecord;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edem
 */
@Entity
@Table(name = "test_requests")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestRequestImpl extends Model implements Serializable {
    
    public static final String _testId = "testId";
    @XmlAttribute
    @Column(name = "test_id")
    private String testId;
    
    @XmlAttribute
    @Column(name = "specimen_id")
    private String specimenId;
    
    public static final String _testName = "testName";
    @XmlAttribute
    @Column(name = "test_name")
    private String testName;
    
    @XmlAttribute
    @Column(name = "concept_code")
    private String conceptCode;
    
    @XmlElementWrapper(name = "elements")
    @XmlElement(name = "element")
    @Transient
    private List<TestElementImpl> elements = new ArrayList<>();
    
    public static final String _testOrder = "testOrder";
    public static final String _testOrder_orderCode = _testOrder + "." + TestOrderImpl._orderCode;
    @XmlTransient
    @JoinColumn(name = "test_order")
    @ManyToOne
    private TestOrderImpl testOrder;    
    
    public static final String _validator = "validator";
    @XmlAttribute
    @Column(name = "validated_by")
    private String validatedBy;
    
    @XmlAttribute
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "validated_on")
    private Date validatedOn;
    
    
    public TestRequestImpl() {       
    }
    
    
    public TestRequestImpl(String testId,  String testName,TestOrderImpl order) {
        this.testId = testId;
        this.testName = testName;
        this.testOrder = order;
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

    public List<TestElementImpl> getElements() {
        return elements;
    }

    public TestOrderImpl getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(TestOrderImpl testOrder) {
        this.testOrder = testOrder;
    }

    public void setElements(List<TestElementImpl> elements) {
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
        this.testOrder = (TestOrderImpl) parent;
    }

    public String getValidatedBy()
    {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy)
    {
        this.validatedBy = validatedBy;
    }

    public Date getValidatedOn()
    {
        return validatedOn;
    }

    public void setValidatedOn(Date validatedOn)
    {
        this.validatedOn = validatedOn;
    }
    
    
    
}
