package com.stately.openlis.hl7.main.connection;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bryte on 3/24/14.
 */
public class SerialConnectionManager
{
    private static final Logger logger = LoggerFactory.getLogger(SerialConnectionManager.class);
    private final int TIMEOUT = 2000;
    private Enumeration ports;

    public List<CommPortIdentifier> searchSerialPorts()
    {
        List<CommPortIdentifier> serialPortIdentifiers = new LinkedList<>();
        ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements())
        {
            CommPortIdentifier currentPort = (CommPortIdentifier) ports.nextElement();

            if(currentPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                serialPortIdentifiers.add(currentPort);
            }
        }

        return serialPortIdentifiers;
    }

    public SerialPort connectPort(CommPortIdentifier identifier)
    {
        try
        {
            CommPort commPort = identifier.open("TigerControlPanel", TIMEOUT);
            return (SerialPort)commPort;
        }
        catch (PortInUseException e)
        {
            logger.error("Port is already in use", e);
        }
        return null;
    }
}
