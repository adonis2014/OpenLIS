package com.stately.openlis.reading;



import com.stately.openlis.astm.MessageReceiver;
import java.nio.charset.StandardCharsets;
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
public class _SerialPortReader1 implements SerialPortEventListener {

    SerialPort serialPort;

    MessageReceiver messageReceiver = new MessageReceiver();
    
    public _SerialPortReader1(SerialPort serialPort)
    {
        this.serialPort = serialPort;
    }
    
    
    private StringBuffer record = new StringBuffer();
    
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
                    
                    
                    if (event.getEventValue() == -1) {
//                        String value =  serialPort.readString().trim();
                        int[] data = serialPort.readIntArray();
                        System.out.println("array lent is .. " + data.length);
//                        System.out.println("data received is .... " + value);

                        for (int i : data) {
                            System.out.println(i);
//                            Logger.get().log(i+"");
                        }

                        try {
//                            int valueInt = Integer.parseInt(value);
                            int valueInt = data[0];
                            System.out.println("valueInt = " + valueInt);
                            if (valueInt == 5) {
                                boolean sent = serialPort.writeInt(6);
                                System.out.println("ACK sent ...  " + sent);
                            }
                            if (valueInt == 4) {
//                                 boolean sent = serialPort.writeInt(6);
//                                 System.out.println("ACK sent ...  " + sent);

                                String msg = "\n\n**************EOT Detected ..................************************\n";

                                
                                record = new StringBuffer();

                                System.out.println(msg);
                            }
                        } catch (Exception e) {
                            System.out.println("error in processing ENQ result " + e.getMessage());
                        }

//                        int[] data = serialPort.readIntArray();
//                        for (int i : data)
//                        {
//                            System.out.println(i);
//                            Logger.get().log(i+"");
//                        }

                        return;
                    }
                    
                    String buffer = serialPort.readString();
                    
                    System.out.println("BUFFER : "+buffer);

                    
                    if(buffer == null || buffer.isEmpty() || buffer.equalsIgnoreCase("null"))
                    {
                        
                    }
                    else
                    {
                        record.append(buffer);
                        boolean sent = serialPort.writeInt(6);
//                    boolean sent = serialPort.writeBytes(buffer.getBytes());
////                    boolean sent = serialPort.writeBytes(buffer.getBytes(StandardCharsets.UTF_8));
                    System.out.println("sent : " +sent);
                    }
                    
//                    boolean sent = serialPort.writeInt(6);
////                    boolean sent = serialPort.writeBytes(buffer.getBytes());
//////                    boolean sent = serialPort.writeBytes(buffer.getBytes(StandardCharsets.UTF_8));
//                    System.out.println("sent : " +sent);
                    
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