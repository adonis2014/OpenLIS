/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author edem
 */
@Entity
@Table(name = "test_orders")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestOrderImpl extends Model implements Serializable{
    
    public static final String _orderCode = "orderCode";
    @Column(name = "order_code")
    private String orderCode;
    
    public static final String _orderId = "orderId";
    @XmlAttribute
    @Column(name = "order_id")
    private String orderId;
    
    public static final String _patientName = "patientName";
    @XmlAttribute
    @Column(name = "patient_name")
    private String patientName;
    
    public static final String _patientId = "patientId";
    @XmlAttribute
    @Column(name = "patient_id")
    private String patientId;  
    
    public static final String _facilityId = "facilityId";
    @Column(name = "facility_id")
    @XmlAttribute
    private String facilityId;
    
    public static final String _facilityName = "facilityName";
    @Column(name = "facility_name")
    @XmlAttribute
    private String facilityName;
    
    @Column(name = "order_date")
    @Temporal(javax.persistence.TemporalType.DATE)
     private Date orderDate;
    
    public static final String _orderStatus = "orderStatus";
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    @XmlAttribute
    private StatusCode orderStatus;
    
//    @XmlElementWrapper(name = "tests")
//    @XmlElement(name = "test")
    @Transient
    private List<TestRequestImpl> requests = new ArrayList<>(); 

    public TestOrderImpl() {
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

    public List<TestRequestImpl> getRequests() {
        return requests;
    }

    public void setRequests(List<TestRequestImpl> requests) {
        this.requests = requests;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public StatusCode getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(StatusCode orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }
            
    
}
