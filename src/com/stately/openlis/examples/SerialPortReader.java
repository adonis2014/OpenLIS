package com.stately.openlis.examples;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwin
 */
public class SerialPortReader implements SerialPortEventListener {

    SerialPort serialPort;

    public SerialPortReader(SerialPort serialPort)
    {
        this.serialPort = serialPort;
    }
    
    
    
     public void serialEvent(SerialPortEvent event) {

         try
         {
             System.out.println("\n\n\nActivity detected on Port :  .. "+event.getPortName());
             System.out.println("event.getEventType() :  .. "+event.getEventType());
             System.out.println("event.getEventValue() :"+event.getEventValue());
//             System.out.println("serialPort.readString() :"+serialPort.readString());
         } catch (Exception e)
         {
             e.printStackTrace();
         }
         
        if (event.isRXCHAR()) {//If data is available

                try {

                    System.out.println("Data Available with current port event:");
                    System.out.println("----------------------------");
                    
                    
//                    if(event.getEventValue() == 1)
//                    {
//                        int[] data = serialPort.readIntArray();
//                        for (int i : data)
//                        {
//                            System.out.println(i);
//                            Logger.get().log(i+"");
//                        }
//                    }
                    
                    String buffer = serialPort.readString();
                    Logger.get().log(buffer);
                    System.out.print(buffer);

//                    serialPort.
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            //}
        } else if (event.isCTS()) {//If CTS line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("CTS - ON");
            } else {
                System.out.println("CTS - OFF");
            }
        } else if (event.isDSR()) {///If DSR line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("DSR - ON");
            } else {
                System.out.println("DSR - OFF");
            }
        }

    }
     
}