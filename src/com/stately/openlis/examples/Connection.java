/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.examples;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edwin
 */
public class Connection
{

    public static int port = 0;
    public static DatagramSocket socket;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException
    {
        try
        {
            port = 8000;
            port = 1111;
            
//            DatagramSocket c  = null;
//            
//            c = new DatagramSocket(null);

//            DatagramSocket socket = new DatagramSocket(null);
//            DatagramSocket socket = new DatagramSocket(InetAddress.getByName("0.0.0.0"));
//            DatagramSocket socket = new DatagramSocket(9600, InetAddress.getByName("0.0.0.0"));
//            DatagramSocket socportket = new DatagramSocket(7001, InetAddress.getByName("148.177.5.225"));
//            DatagramSocket socket = new DatagramSocket(1985, InetAddress.getByName("192.168.2.93"));
//            DatagramSocket socket = new DatagramSocket(8000, InetAddress.getByName("localhost"));
            socket = new DatagramSocket(port);
//            DatagramSocket socket = new DatagramSocket(1985);
//            DatagramSocket socket = new DatagramSocket(5001, InetAddress.getByName("255.255.255.0"));
            socket.setBroadcast(true);
            System.out.println("Listen on " + socket.getLocalAddress() + " from " + socket.getInetAddress() + " port " + socket.getBroadcast());
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            
            sendData("Hello");
            
            while (true)
            {
                System.out.println("Waiting for data");
                socket.receive(packet);
                System.out.println("Data received");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public static void sendData(String data)
    {
        try
        {
            byte[] sendData = data.getBytes();
            System.out.println("sending data : " + data);
            System.out.println("sending data : " + sendData.length);
            DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length);
            
            datagramPacket.setAddress(InetAddress.getByName("192.168.2.92"));
            datagramPacket.setPort(port);

            socket.send(datagramPacket);
            
//            socket.s
        } catch (IOException ex)
        {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
