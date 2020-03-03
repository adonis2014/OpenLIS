package com.stately.openlis.hl7.main.controllers;

import com.stately.openlis.hl7.listener.IPMessageListener;
import com.stately.openlis.hl7.main.connection.IPConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by bryte on 3/24/14.
 */
public class IPConnectionController implements Initializable
{
    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;
    @FXML private Button connectButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final String ip = ipTextField.getText();
                Integer passedPort = null;

                try
                {
                    passedPort = Integer.parseInt(portTextField.getText());
                }catch (Exception e){}

                if(passedPort == null)
                {
                    return;
                }
                final int port = passedPort;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        IPConnection ipConnection = new IPConnection(port, ip);
                        Socket socket = ipConnection.connect();

                        if(socket != null)
                        {
                            IPMessageListener ipMessageListener = new IPMessageListener(socket);
                            Platform.runLater(ipMessageListener);
                        }
                    }
                });



            }
        });
    }
}
