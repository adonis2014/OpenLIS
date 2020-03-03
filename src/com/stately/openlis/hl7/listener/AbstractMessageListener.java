/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.hl7.listener;

import com.stately.openlis.hl7.main.controllers.AppMainController;
import javafx.concurrent.Task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author bryte
 */
public abstract class AbstractMessageListener extends Task<Void>
{
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;

    protected AbstractMessageListener(Socket socket)
    {
        this.socket = socket;
        try
        {
            dis = new DataInputStream(this.socket.getInputStream());
            dos = new DataOutputStream(this.socket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean disconnect()
    {
        return cancel(true);
    }

    public void onMessage(String msg){};

    @Override
    protected Void call() throws Exception 
    {
        try 
        {
            dos.writeUTF("OK");
            dos.flush();

            AppMainController.addInfo("IP Listener started");
            String msg;
            while((msg = dis.readUTF()) != null)
            {
                onMessage(msg);
            }
        }
        catch (IOException e)
        {
        }
        
        return null;
    }
}
