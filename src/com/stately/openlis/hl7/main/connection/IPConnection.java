package com.stately.openlis.hl7.main.connection;

import com.stately.openlis.hl7.main.controllers.AppMainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by bryte on 3/24/14.
 */
public class IPConnection
{
    private final static Logger logger = LoggerFactory.getLogger(IPConnection.class);
    private String ipAddress;
    private int port;

    public IPConnection(int port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public Socket connect()
    {
        String msg = "[" + ipAddress + ":" + port + "]";
        try {
            logger.info("Connecting to server on: " + msg);
            AppMainController.addInfo("Connecting to server on: " + msg);
            SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
            Socket socket = new Socket();
            socket.connect(socketAddress, 10000);

            logger.info("Connection established");
            AppMainController.addInfo("Connection established");
            if(socket.isConnected())
            {
                return socket;
            }
        } catch (IOException e) {
            logger.error("Error connecting to server on " + msg);
            AppMainController.addError("Error connecting to server on " + msg);
        }

        return null;
    }
}
