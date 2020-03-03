package com.stately.openlis.hl7.main.controllers;

import com.stately.openlis.AppLauncher;
import com.stately.openlis.entities.Device;
import com.stately.openlis.ui.table.DeviceTableView;
import com.stately.openlis.services.CrudService;
import com.stately.openlis.ui.FxmlLoader;
import com.stately.openlis.ui.Resources;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppMainController implements Initializable {

    @FXML private BorderPane borderPane;
    @FXML private static TextArea logTextArea;
    @FXML private RadioButton lanRadioButton;
    @FXML private RadioButton serialRadioButton;
    @FXML private Pane pane;
    @FXML private AnchorPane lanConnectPane;
    @FXML private AnchorPane serialConnectPane;

    private DeviceTableView deviceTableView = new DeviceTableView();
    private Device selectedDevice;

    private AppLauncher appLauncher;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        logTextArea.setEditable(false);
//        logTextArea.setWrapText(true);

        lanConnectPane = FxmlLoader.load(Resources.lan_connection, AnchorPane.class);
        serialConnectPane = FxmlLoader.load(Resources.serial_connection, AnchorPane.class);

//        List<Device> devices = CrudService.instance().findAll(Device.class);
//        deviceTableView.addDevices(devices);

        ToggleGroup connectionToggleGroup = new ToggleGroup();
        lanRadioButton.setToggleGroup(connectionToggleGroup);
        serialRadioButton.setToggleGroup(connectionToggleGroup);

        connectionToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle2) {
                pane.getChildren().clear();
                if(lanRadioButton.isSelected())
                {
                    pane.getChildren().add(lanConnectPane);
                }
                else if(serialRadioButton.isSelected())
                {
                    pane.getChildren().add(serialConnectPane);
                }
            }
        });
        borderPane.setCenter(deviceTableView);
    }

    public static void addInfo(final String message)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logTextArea.appendText("\nINFO: " + message);
            }
        });
    }

    public static void addError(final String message)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try
                {
                    logTextArea.appendText("\nERROR: " + message);
                } catch (Exception e)
                {
                }
                
            }
        });
    }

    public void setAppLauncher(AppLauncher appLauncher)
    {
        this.appLauncher = appLauncher;
    }

}
