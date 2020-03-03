/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.entities;

import com.stately.openlis.hl7.models.constant.CommunicationMode;
import com.stately.modules.jpa2.UniqueEntityModel2;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
@Entity
@Table(name = "medical_system")
public class MedicalSystem extends UniqueEntityModel2 implements Serializable {
    
    
    public static final String _systemName = "systemName";
    @Column(name = "system_name")
    private String systemName;
    
    public static final String _vendorName = "vendorName";
    @Column(name = "vendor_name")
    private String vendorName;
    
    public static final String _description = "description";
    @Column(name = "description")
    private String description;
    
    public static final String _protocolAddress = "protocolAddress";
    @Column(name = "protocol_address")
    private String protocolAddress;
    
    public static final String _commProtocol = "commProtocol";
    @Column(name = "comm_protocol")
    private CommunicationMode commProtocol;

   
    
    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    public String getVendorName()
    {
        return vendorName;
    }

    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getProtocolAddress()
    {
        return protocolAddress;
    }

    public void setProtocolAddress(String protocolAddress)
    {
        this.protocolAddress = protocolAddress;
    }

    public CommunicationMode getCommProtocol()
    {
        return commProtocol;
    }

    public void setCommProtocol(CommunicationMode commProtocol)
    {
        this.commProtocol = commProtocol;
    }
    
    
    
}
