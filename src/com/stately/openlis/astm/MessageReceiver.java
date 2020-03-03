/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import com.stately.openlis.DataCallback;
import com.google.common.base.Strings;
import com.stately.openlis.entities.Device;
import com.stately.openlis.parsers.AsciiCode;
import com.stately.openlis.parsers.AstmParser;
import com.stately.openlis.parsers.LogManager;
import com.stately.openlis.parsers.StringUtil;
import java.io.File;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class MessageReceiver
{

    StringBuilder fullMessageBuilder = new StringBuilder();
    StringBuilder newTextBuilder = new StringBuilder();
    boolean newTextInProgress = false;

    private DataCallback messageCallback = null;
    private Device device;

    public MessageReceiver()
    {
    }

    public MessageReceiver(Device device)
    {
        this.device = device;
    }

    public static void main(String[] args)
    {
        new MessageReceiver().reParseCurrentMessageLog();
    }

    public List<LabMessage> reParseCurrentMessageLog()
    {
        List<LabMessage> labMessagesList = new LinkedList<>();

        try
        {
            File datafolder = LogManager.instance().FULL_MESSAGE_LOG;

            System.out.println("reloading file " + datafolder);

            String fileData = com.google.common.io.Files.toString(datafolder, Charset.defaultCharset());

            String[] fieldsList = StringUtil.split(fileData, AsciiCode.EOT);

            int counter = 0;

            for (String astmMessage : fieldsList)
            {

                LabMessage labMessage = parseSingleAstmMessage(astmMessage);
                counter++;

                if (labMessage != null)
                {
                    labMessage.setNo(counter);
                    labMessagesList.add(labMessage);
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return labMessagesList;
    }

    public LabMessage parseSingleAstmMessage(String astmMessage)
    {
        try
        {
            AstmParser astmParser = new AstmParser();

            LabMessage labMessage = astmParser.parseAstm(astmMessage);

            if (device != null)
            {
                labMessage.setDeviceId(device.getDeviceId());
            }

            return labMessage;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void processNewMessage(String newMessage)
    {
        try
        {

            if (Strings.isNullOrEmpty(newMessage))
            {
                return;
            }

            LogManager.instance().rawlog(newMessage);
            LogManager.instance().rawlog("\n<LINE>");

            if (newMessage.startsWith(AsciiCode.ENQ))
            {
//                System.out.println("New Message comming " + newMessage);
                fullMessageBuilder.append("\n");

                fullMessageBuilder.append(AsciiCode.ENQ); // added ENQ
                fullMessageBuilder.append("\n");

            } else if (newMessage.startsWith(AsciiCode.STX))
            {
                newTextInProgress = true;

                newTextBuilder.append(newMessage);
                if (newMessage.contains(AsciiCode.ETX))
                {
                    eotDetected(newMessage);
                }

            } else if (newMessage.contains(AsciiCode.ETX))
            {
                eotDetected(newMessage);
            } else if (newMessage.startsWith(AsciiCode.EOT))
            {

//                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.println("EOT Detected ..");
//                System.out.println(AsciiCode.EOT + " : " + newMessage);

//                newTextBuilder.append(processNewMessage);
//                fullMessageBuilder.append(newTextBuilder.toString());
                newTextBuilder.delete(0, newTextBuilder.length());

                fullMessageBuilder.append(newMessage); // add EOT
//                fullMessageBuilder.append(AsciiCode.EOT); // add EOT
                fullMessageBuilder.append("\n");

                String fullMessage = fullMessageBuilder.toString();

                LogManager.instance().writefullMessage(fullMessage);
                fullMessageBuilder.delete(0, fullMessageBuilder.length());

                if (messageCallback != null)
                {
                    messageCallback.onData(fullMessage);
                }

                newTextInProgress = false;
            } else //if it doesn't begin with a control character and STX has been detected
            {
                if (newTextInProgress)
                {
                    newTextBuilder.append(newMessage);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setMessageCallback(DataCallback messageCallback)
    {
        this.messageCallback = messageCallback;
    }

    private void eotDetected(String newMessage)
    {
//        System.out.println("ETX detected ... " + newMessage + " .. " + AsciiCode.ETX);
        newTextInProgress = false;

        newTextBuilder.append(newMessage);
        fullMessageBuilder.append(newTextBuilder.toString());
        newTextBuilder.delete(0, newTextBuilder.length());

        fullMessageBuilder.append("\n");
    }

}
