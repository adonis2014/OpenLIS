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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author edem
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestOrder implements Serializable{
    @XmlAttribute
    private String orderId;
    @XmlAttribute
    private String patientName;
    @XmlAttribute
    private String patientId;    
    @XmlAttribute
    private String facilityId;
    @XmlAttribute
    private String facilityName;
    @XmlAttribute
    private Date orderDate;
    @XmlElementWrapper(name = "tests")
    @XmlElement(name = "test")
    private List<TestRequest> requests = new ArrayList<>(); 

    public TestOrder() {
    }
    

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public List<TestRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<TestRequest> requests) {
        this.requests = requests;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    
            
}
