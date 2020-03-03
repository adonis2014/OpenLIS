/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.examples;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import java.util.Enumeration;
import java.util.HashSet;

/**
 *
 * @author Edwin
 */
public class FindPorts
{
    public static void main(String[] args)
    {
        try
        {
            
            System.out.println("running listing of ports");
            listPorts();
            System.out.println("First Pass Completed ...");
            getAvailableSerialPorts();

            System.out.println("trying to initiate connection ....");
            TwoWaySerialComm comm = new TwoWaySerialComm();
            comm.connect("COM1",9600);
            
//            System.out.println("connection done !!!!!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    /**
     * @return    A HashSet containing the CommPortIdentifier for all serial ports that are not currently being used.
     */
    public static HashSet<CommPortIdentifier> getAvailableSerialPorts() 
    {
        System.out.println("runing available com ports not in use");
        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
            System.out.println("next to try ... " + com);
            System.out.println("next to try ... " + com.getName());
            switch (com.getPortType()) {
            case CommPortIdentifier.PORT_SERIAL:
                try {
                    CommPort thePort = com.open("CommUtil", 50);
                    thePort.close();
                    h.add(com);
                } catch (PortInUseException e) {
                    System.out.println("Port, "  + com.getName() + ", is in use.");
                } catch (Exception e) {
                    System.err.println("Failed to open port " +  com.getName());
                    e.printStackTrace();
                }
            }
        }
        return h;
    }
    
    static void listPorts()
    {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        System.out.println(portEnum);
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            String details = portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType())
                    +" - " + portIdentifier.getCurrentOwner() + " - " + portIdentifier.isCurrentlyOwned();
            System.out.println( details);
        }        
    }
    
    static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
}
