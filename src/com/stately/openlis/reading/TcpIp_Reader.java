package com.stately.openlis.reading;

import com.stately.openlis.astm.MessageReceiver;
import com.stately.openlis.parsers.AsciiCode;
import com.stately.openlis.parsers.LogManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javafx.application.Platform;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Edwin
 */
public class TcpIp_Reader implements TransSetupConfig
{
    
    private boolean connected;

    private String portNo = null;
    private String ipAddress = null;
    private MessageReceiver messageReceiver = null;

    private Socket clientSocket = null;
    private BufferedReader inFromServer = null;
    private DataOutputStream outToServer = null;

    public TcpIp_Reader(String portNo, MessageReceiver messageReceiver)
    {
        this.portNo = portNo;
        this.messageReceiver = messageReceiver;
    }

    public TcpIp_Reader(String ipAddress, String portNumber, MessageReceiver messageReceiver)
    {
        this.ipAddress = ipAddress;
        this.portNo = portNumber;
        this.messageReceiver = messageReceiver;
    }

    public void listenToData()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    while (true)
                    {
                        try
                        {
                            String data = inFromServer.readLine();

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        });

    }

    
    public void serialEvent(String dataChuck)
    {

        try
        {
//            String initLog = "Activity detected on Port " + event.getPortName()
//                    + " event.getEventType(): " + event.getEventType()
//                    + " event.getEventValue(): " + event.getEventValue()
//                    + " Data Available : " + event.isRXCHAR();
//
//            System.out.println(initLog);

            

            messageReceiver.processNewMessage(dataChuck);

            if (dataChuck == null)
            {
                System.out.println("******************* NULL MSG READ ....");
                return;
            }
            
            if (dataChuck.startsWith(AsciiCode.ENQ)
                    || dataChuck.contains(AsciiCode.ETX)
                    || AsciiCode.hasETB(dataChuck)
                    )
            {
                outToServer.writeInt(6);
                boolean sent = true;
//                    String ackSent = "<ACK>";
                LogManager.instance().rawlog("\n<ACK>");
                System.out.println("$$$$$  SENT <ACK> " + sent);
            }

        } catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

    public static void main(String[] args) throws IOException
    {

        new TcpIp_Reader("localhost", "43434", null).initConnection();

    }

    @Override
    public boolean initConnection()
    {

        try
        {            
            clientSocket = new Socket(ipAddress, Integer.parseInt(portNo));
            connected = true;

            listenToData();
            return connected;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        connected = false;
        return false;
    }

    @Override
    public boolean isConnected()
    {
        return connected;
    }

}
