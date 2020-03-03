package com.stately.openlis.examples;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.nio.charset.StandardCharsets;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Edwin
 */
public class Main {
 
    public static void main(String[] args) {
        //In the constructor pass the name of the port with which we work
        SerialPort serialPort = new SerialPort("COM1");
        try {
            //Open port
            boolean portOpened = serialPort.openPort();
            System.out.println("serialPort.openPort() : " + portOpened);
            //We expose the settings. You can also use this line - serialPort.setParams(9600, 8, 1, 0);
            serialPort.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            //Writes data to port
//            serialPort.writeBytes("Test".getBytes());
            //Read the data of 10 bytes. Be careful with the method readBytes(), if the number of bytes in the input buffer
            //is less than you need, then the method will wait for the right amount. Better to use it in conjunction with the
            //interface SerialPortEventListener.
//            byte[] buffer = serialPort.readBytes(10);
            //Closing the port
//            serialPort.closePort();


        boolean sent = serialPort.writeBytes("5".getBytes(StandardCharsets.US_ASCII));
        
        if(sent)
        {
            System.out.println("ACK Msg Sent ...");
        }
        else
        {
            System.out.println("Unable to send ACK Msg");
        }
       
        
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
//            serialPort.setEventsMask(mask);//Set mask

            System.out.println("Port Connected to .. " +serialPort.getPortName() );
            System.out.println("Initiating data reading .... ");
SerialPortReader portReader = new SerialPortReader(serialPort);
serialPort.addEventListener(portReader);

            System.out.println("Data Reading Initialised...");
            
            
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
}
