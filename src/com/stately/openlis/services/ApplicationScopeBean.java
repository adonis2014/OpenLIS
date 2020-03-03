/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.services;

import com.stately.openlis.entities.Device;
import com.stately.openlis.jms.JmsIntegrator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ApplicationScopeBean
{
    public static JmsIntegrator jmsIntegrator;
    
    public static final List<Device> devicesList = new LinkedList<>();
    public static final List<String> usersList = new LinkedList<>();
}
