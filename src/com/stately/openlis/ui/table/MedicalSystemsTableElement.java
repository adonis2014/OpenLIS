/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui.table;


import com.stately.openlis.hl7.models.constant.Status;
import com.stately.openlis.entities.MedicalSystem;
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
public class MedicalSystemsTableElement //extends TableView<MedicalSystem>
{
    TableView<MedicalSystem> tableView;
    
    private final ObservableList<MedicalSystem> medicalSystemsList = FXCollections.observableArrayList();

    private final TableColumn<MedicalSystem, String> systemNameCol = new TableColumn<>("System Name");
    private final TableColumn<MedicalSystem, String> vendorNameCol = new TableColumn<>(" Vendor");
    private final TableColumn<MedicalSystem, String> descriptionCol = new TableColumn<>("Description");
    private final TableColumn<MedicalSystem, Integer> communicationProtocolCol = new TableColumn<>("Communication Protocol");
    private final TableColumn<MedicalSystem, Integer> protocolAddress = new TableColumn<>("Protocol Address");
    

    private final ContextMenu contextMenu = new ContextMenu();
    private MedicalSystem selectedSystem;

    public MedicalSystemsTableElement(TableView<MedicalSystem> tableView)
    {
        tableView.getColumns().clear();
        this.tableView  = tableView;
        
        
        
        systemNameCol.setCellValueFactory(new PropertyValueFactory(MedicalSystem._systemName));
        vendorNameCol.setCellValueFactory(new PropertyValueFactory(MedicalSystem._vendorName));
        descriptionCol.setCellValueFactory(new PropertyValueFactory(MedicalSystem._description));
        protocolAddress.setCellValueFactory(new PropertyValueFactory(MedicalSystem._protocolAddress));
        communicationProtocolCol.setCellValueFactory(new PropertyValueFactory(MedicalSystem._commProtocol));

        tableView.getColumns().addAll(systemNameCol, vendorNameCol,descriptionCol,communicationProtocolCol, protocolAddress );
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);
        tableView.setContextMenu(contextMenu);

//        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MedicalSystem>() {
//            @Override
//            public void changed(ObservableValue<? extends MedicalSystem> observableValue, MedicalSystem oldItem, MedicalSystem newItem)
//            {
//                selectedSystem = newItem;
//            }
//        });

//        buildContextMenu();
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
//                boolean deleted = CrudService.instance().delete(selectedSystem);
//                if(deleted)
//                {
//                    medicalSystemsList.remove(selectedSystem);
//                }
            }
        });
    }

    public void addDevice(MedicalSystem medicalSystem)
    {
        if(!medicalSystemsList.contains(medicalSystem))
        {
            medicalSystemsList.add(medicalSystem);
        }
    }

    public void setDevicesList(List<MedicalSystem> systemsList)
    {
        medicalSystemsList.clear();
        medicalSystemsList.addAll(systemsList);

        tableView.setItems(medicalSystemsList);
    }

    public MedicalSystem getselectedSysteme() {
        return selectedSystem;
    }
}
