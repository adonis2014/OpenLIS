package com.stately.openlis.hl7.main.controllers;

import com.google.common.base.Strings;
import com.stately.openlis.hl7.listener.SerialMessageListener;
import com.stately.openlis.hl7.main.connection.SerialConnectionManager;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by bryte on 3/24/14.
 */
public class SerialConnectionController implements Initializable
{
    @FXML private ListView listView;
    @FXML private Button connectButton;

    private ObservableList<String> serialPorts = FXCollections.observableArrayList();
    private SerialConnectionManager serialConnectionManager = new SerialConnectionManager();
    private Map<String, CommPortIdentifier> portMap = new HashMap<>();

    private String selectedPort;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        List<CommPortIdentifier> identifiers = serialConnectionManager.searchSerialPorts();

        if(identifiers.isEmpty())
        {
            AppMainController.addError("No com ports available, check and connect cable");
            return;
        }

        for(CommPortIdentifier port: identifiers)
        {
            serialPorts.add(port.getName());
            portMap.put(port.getName(), port);
        }

        listView.setItems(serialPorts);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                selectedPort = o2.toString();
                System.out.println("selected port: " + selectedPort);
            }
        });
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if(Strings.isNullOrEmpty(selectedPort))
                {
                    return;
                }

                SerialPort serialPort = serialConnectionManager.connectPort(portMap.get(selectedPort));
                if(serialPort != null)
                {
                    AppMainController.addInfo("Connection established on " + selectedPort);
                    SerialMessageListener serialMessageListener = new SerialMessageListener(serialPort);
                }
                else
                {
                    AppMainController.addError("Could not connect on " + selectedPort);

                }
            }
        });
    }
}
