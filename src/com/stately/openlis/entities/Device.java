package com.stately.openlis.entities;

import com.stately.openlis.hl7.models.constant.Standard;
import com.stately.openlis.hl7.models.constant.Status;
import com.stately.openlis.hl7.models.constant.TransmissionProtocol;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */

//@Entity
//@Table(name = "equipment_detail")
public class Device implements Serializable
{
    @Column
    @Id
    private String id;

    @Column(name = "equipment_name")
    private String deviceName;
    
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "standard")
    @Enumerated(EnumType.STRING)
    private Standard standard;

    public static final String _transmissionProtocol = "transmissionProtocol";
    @Column(name = "transmission_protocol")
    @Enumerated(EnumType.STRING)
    private TransmissionProtocol transmissionProtocol;

    @Column(name="serial_number")
    private String serialNumber;

    private String ipAddress;

    @Column(name = "port")
    private String port;

    @Column(name = "handshake_connection_method")
    private boolean handshakeConnectionMethod = false;

    @Transient
    private Status status = Status.DISCONNECTED;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isHandshakeConnectionMethod() {
        return handshakeConnectionMethod;
    }

    public void setHandshakeConnectionMethod(boolean handshakeConnectionMethod) {
        this.handshakeConnectionMethod = handshakeConnectionMethod;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public TransmissionProtocol getTransmissionProtocol()
    {
        return transmissionProtocol;
    }

    public void setTransmissionProtocol(TransmissionProtocol transmissionProtocol)
    {
        this.transmissionProtocol = transmissionProtocol;
    }
    
    

    @Override
    public String toString()
    {
        return deviceName ;
//        return "Device{" + "deviceName=" + deviceName + ", deviceId=" + deviceId + ", standard=" + standard + ", serialNumber=" + serialNumber + ", ipAddress=" + ipAddress + ", port=" + port + ", handshakeConnectionMethod=" + handshakeConnectionMethod + ", status=" + status + '}';
    }
    
    
}
