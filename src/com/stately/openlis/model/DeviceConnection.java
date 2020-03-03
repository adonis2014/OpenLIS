/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.model;

import com.stately.openlis.entities.Device;
import com.stately.openlis.reading.TransSetupConfig;

/**
 *
 * @author Edwin
 */
public class DeviceConnection
{
    private Device device;
    private TransSetupConfig transSetupConfig;

    public Device getDevice()
    {
        return device;
    }

    public void setDevice(Device device)
    {
        this.device = device;
    }

    public TransSetupConfig getTransSetupConfig()
    {
        return transSetupConfig;
    }

    public void setTransSetupConfig(TransSetupConfig transSetupConfig)
    {
        this.transSetupConfig = transSetupConfig;
    }
    
    
    
}
