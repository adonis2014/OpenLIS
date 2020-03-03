package com.stately.openlis.hl7.listener;

import com.stately.openlis.hl7.main.controllers.AppMainController;

import java.net.Socket;

/**
 * Created by bryte on 3/25/14.
 */
public class IPMessageListener extends AbstractMessageListener
{
    public IPMessageListener(Socket socket)
    {
        super(socket);
    }

    @Override
    public void onMessage(String msg)
    {
        AppMainController.addInfo("Message:- " + msg);
    }
}
