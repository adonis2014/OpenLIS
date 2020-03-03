/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import com.stately.openlis.entities.Model;
import com.stately.openlis.entities.StatusCode;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "astm_message")
public class AstmMessage extends Model implements Serializable
{
    
    public static final String _sampleId = "sampleId";
    @Column(name = "sample_id")
    private String sampleId;
    
    
    public static final String _deviceId = "deviceId";
    @Column(name = "device_id")
    private String deviceId;
    
    public static final String _patientName = "patientName";
    @Column(name = "patient_name")
    private String patientName;
    
    public static final String _messageText = "messageText";
    @Column(name = "message_text", columnDefinition="TEXT")
    @Lob
    private String messageText;
    
    public static final String _statusCode = "statusCode";
    @Column(name = "status_code")
    @Enumerated(EnumType.STRING)
    @XmlAttribute
    private StatusCode statusCode = StatusCode.UNPROCESSED;

    public String getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public void setMessageText(String messageText)
    {
        this.messageText = messageText;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public StatusCode getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode)
    {
        this.statusCode = statusCode;
    }
    
    
}
