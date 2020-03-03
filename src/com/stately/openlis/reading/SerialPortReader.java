package com.stately.openlis.reading;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.stately.openlis.astm.MessageReceiver;
import com.stately.openlis.parsers.AsciiCode;
import com.stately.openlis.parsers.LogManager;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
public class SerialPortReader implements SerialPortEventListener, TransSetupConfig
{

    private SerialPort serialPort;

    private boolean connected;

    String portNumber;
    MessageReceiver messageReceiver;// = new MessageReceiver();

    public SerialPortReader(String portNumber, MessageReceiver messageReceiver)
    {
        this.portNumber = portNumber;
        this.messageReceiver = messageReceiver;
    }

//    public SerialPortReader(SerialPort serialPort)
//    {
//        this.serialPort = serialPort;
//    }
    public SerialPortReader()
    {
    }

//    private StringBuffer record = new StringBuffer();
    @Override
    public void serialEvent(SerialPortEvent event)
    {

        try
        {
            String initLog = "Activity detected on Port " + event.getPortName()
                    + " event.getEventType(): " + event.getEventType()
                    + " event.getEventValue(): " + event.getEventValue()
                    + " Data Available : " + event.isRXCHAR();

            System.out.println(initLog);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (event.isRXCHAR())
        {//If data is available

            try
            {

                if (event.getEventValue() == 0)
                {
                    return;
                }

                String bufferRead = serialPort.readString();
                System.out.println(bufferRead);

                messageReceiver.processNewMessage(bufferRead);

                if (bufferRead == null)
                {
                    System.out.println("******************* NULL MSG READ ....");
//                    boolean sent = serialPort.writeInt(6);
                    return;
                }

                if (bufferRead.contains(AsciiCode.ETB))
                {
                    System.out.println("ETB DETECTED>>>>>>>>>>>>>>>>");
                }

                if (bufferRead.startsWith(AsciiCode.ENQ)
                        //                        || bufferRead.contains(AsciiCode.STX)
                        || bufferRead.contains(AsciiCode.ETX)
                        //                        || bufferRead.contains(AsciiCode.ETB)
                        || AsciiCode.hasETB(bufferRead) //                        || bufferRead.contains(AsciiCode.EOT)
                        )
                {
                    boolean sent = serialPort.writeInt(6);
//                    String ackSent = "<ACK>";
                    LogManager.instance().rawlog("\n<ACK>");
                    System.out.println("$$$$$  SENT <ACK> " + sent);
                }

            } catch (SerialPortException ex)
            {
                System.out.println(ex);
            }
            //}
        } else if (event.isCTS())
        {//If CTS line has changed state
            if (event.getEventValue() == 1)
            {//If line is ON
                System.out.println("CTS - ON");
            } else
            {
                System.out.println("CTS - OFF");
            }
        } else if (event.isDSR())
        {///If DSR line has changed state
            if (event.getEventValue() == 1)
            {//If line is ON
                System.out.println("DSR - ON");
            } else
            {
                System.out.println("DSR - OFF");
            }
        }

    }

    public static void main(String[] args) throws IOException
    {

        new SerialPortReader("COM1", null).initConnection();

    }

    @Override
    public boolean initConnection()
    {
        //In the constructor pass the name of the port with which we work
        serialPort = new SerialPort(portNumber);
        System.out.println("Connecting to ..... " + portNumber);

        try
        {
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
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
//            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask

            System.out.println("Port Connected to .. " + serialPort.getPortName());
            System.out.println("Initiating data reading .... ");
//            SerialPortReader portReader = new SerialPortReader(serialPort);
            serialPort.addEventListener(this);

            System.out.println("Data Reading Initialised...");

            connected = true;

            return connected;

        } catch (SerialPortException ex)
        {
            ex.printStackTrace();
            System.out.println(ex);
        }

        connected = false;
        return false;
    }

//    public static byte[] intToByteArray(int value) {
//        return new byte[]{
//            (byte) (value >>> 24),
//            (byte) (value >>> 16),
//            (byte) (value >>> 8),
//            (byte) value};
//    }
    private static byte[] intToByteArray(final int i) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(i);
        dos.flush();
        return bos.toByteArray();
    }

    public boolean isConnected()
    {
        return connected;
    }

}
