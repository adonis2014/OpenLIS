package com.stately.openlis.hl7.listener;

import com.stately.openlis.hl7.main.controllers.AppMainController;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * Created by bryte on 3/25/14.
 */
public class SerialMessageListener implements SerialPortEventListener
{
    private static final Logger logger = LoggerFactory.getLogger(SerialMessageListener.class);
    private SerialPort serialPort;
    private DataInputStream dataInputStream;

    public SerialMessageListener(SerialPort serialPort) {
        this.serialPort = serialPort;
        try
        {
            this.serialPort.addEventListener(this);
            this.serialPort.notifyOnDataAvailable(true);

            AppMainController.addInfo("Serial Port listener started...");
        }
        catch (TooManyListenersException e)
        {
            this.serialPort.removeEventListener();
            logger.error("Error attaching listener");
        }

        try
        {
            dataInputStream = new DataInputStream(this.serialPort.getInputStream());
        } catch (IOException e)
        {
            logger.error("no input stream for this serial port");
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                String data = dataInputStream.readUTF();

                AppMainController.addInfo("Message:- " + data);
            }
            catch (IOException e)
            {
                String msg =  "Error reading data from serial port";
                AppMainController.addError(msg);
                logger.error(msg);
            }
        }
    }
}
