/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui.table;

import com.stately.openlis.entities.Device;
import com.stately.openlis.hl7.models.constant.Status;
import com.stately.openlis.services.CrudService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 *
 * @author bryte
 */
public class DeviceTableView extends TableView<Device>
{
    private final ObservableList<Device> devices = FXCollections.observableArrayList();

    private final TableColumn<Device, String> deviceNameCol = new TableColumn<>("Device Name");
    private final TableColumn<Device, String> ipAddressCol = new TableColumn<>("IP Address");
    private final TableColumn<Device, Integer> portCol = new TableColumn<>("Port");
    private final TableColumn<Device, Status> statusCol = new TableColumn<>("Status");

    private final ContextMenu contextMenu = new ContextMenu();
    private Device selectedDevice;

    public DeviceTableView()
    {
        deviceNameCol.setCellValueFactory(new PropertyValueFactory("deviceName"));
        ipAddressCol.setCellValueFactory(new PropertyValueFactory("ipAddress"));
        portCol.setCellValueFactory(new PropertyValueFactory("port"));
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));

        getColumns().addAll(deviceNameCol, ipAddressCol, portCol, statusCol);
        setEditable(true);
        setContextMenu(contextMenu);

        getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Device>() {
            @Override
            public void changed(ObservableValue<? extends Device> observableValue, Device device, Device device2)
            {
                selectedDevice = device2;
            }
        });

        buildContextMenu();
    }

    private void buildContextMenu()
    {
        MenuItem connectMenuItem = new MenuItem("Connect");
        MenuItem disconnectMenuItem = new MenuItem("Disconnect");

        MenuItem separator = new SeparatorMenuItem();
        MenuItem deleteMenuItem = new MenuItem("Delete");

        contextMenu.getItems().addAll(connectMenuItem, disconnectMenuItem, separator, deleteMenuItem);

        connectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                //DeviceManager.addDevice(selectedDevice);
            }
        });

        disconnectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //DeviceManager.removeDevice(selectedDevice);
            }
        });

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                boolean deleted = CrudService.instance().delete(selectedDevice);
//                if(deleted)
//                {
//                    devices.remove(selectedDevice);
//                }
            }
        });
    }

    public void addDevice(Device device)
    {
        if(!devices.contains(device))
        {
            devices.add(device);
        }
    }

    public void addDevices(List<Device> deviceList)
    {
        devices.clear();
        devices.addAll(deviceList);

        setItems(devices);
    }

    public Device getSelectedDevice() {
        return selectedDevice;
    }
}
