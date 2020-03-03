/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui.table;


import com.google.common.base.Strings;
import com.stately.openlis.astm.AstmMessage;
import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.entities.StatusCode;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.services.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author bryte
 */
public class LabReportTableElement //extends TableView<MedicalSystem>
{
    TableView<LabMessage> tableView;
    
    private  ObservableList<LabMessage> labReportObservableList = FXCollections.observableArrayList();

    private final TableColumn<LabMessage, String> noCol = new TableColumn<>("No.");
    private final TableColumn<LabMessage, String> sampleID = new TableColumn<>("Sample Id");
    private final TableColumn<LabMessage, String> deviceID = new TableColumn<>("Device Id");
    private final TableColumn<LabMessage, String> patientNameCol = new TableColumn<>("Patient Name");
    

    private final ContextMenu contextMenu = new ContextMenu();
    

    public LabReportTableElement(TableView<LabMessage> tableView)
    {
        tableView.getColumns().clear();
        this.tableView  = tableView;
        
        
        
        noCol.setCellValueFactory(new PropertyValueFactory(LabMessage._no));
        deviceID.setCellValueFactory(new PropertyValueFactory(LabMessage._deviceId));
        sampleID.setCellValueFactory(new PropertyValueFactory(LabMessage._sampleId));
        patientNameCol.setCellValueFactory(new PropertyValueFactory(LabMessage._patientName));
        
        sampleID.setCellFactory(column -> {
        return new TableCell<LabMessage, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                
                TableRow<LabMessage> currentRow = getTableRow();
                
                if(getItem() == null)
                {
                    currentRow.setStyle("");
//                    currentRow.setStyle("-fx-background-color:lightgreen");
                    setText("");
                    return;
                }
                
                
                if (!isEmpty())
                {
                    if (currentRow.getItem() != null)
                    {
                        
                        setText(empty ? "" : getItem().toString());
                        setGraphic(null);
                        
                        if (currentRow.getItem().astmMessage != null)
                        {
                            if (currentRow.getItem().getAstmMessage().getStatusCode() == StatusCode.INVALID)
                            {
                                currentRow.setStyle("-fx-background-color:black");
                                return;
                            }
                        }
                        
                        TestOrderImpl testOrderImpl = currentRow.getItem().getTestOrderImpl();
                        
                        if(testOrderImpl == null)
                        {
                            return;
                        }

                        if (testOrderImpl.getOrderStatus() == StatusCode.DISPATCHED)
                        {
                            currentRow.setStyle("-fx-background-color:lightgreen");
                        } else
                        {
                            currentRow.setStyle("-fx-background-color:lightcoral");
                        }
                    }
                }
            }
        };
    });
        
        MenuItem menuItem = new MenuItem("Set as Invalid");
        contextMenu.getItems().add(menuItem);
        

        tableView.getColumns().addAll(noCol, deviceID,sampleID,patientNameCol );
        
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);
        tableView.setContextMenu(contextMenu);
        
        menuItem.setOnAction((ActionEvent event) ->
        {
            LabMessage labMessage = tableView.getSelectionModel().getSelectedItem();
            if(labMessage == null)
            {
                return;
            }
            AstmMessage astmMessage = labMessage.astmMessage;
            if(astmMessage != null)
            {
                astmMessage.setStatusCode(StatusCode.INVALID);
                Store.get().crudService().save(astmMessage);
                System.out.println("status changed... ");
            }
        });
        
    }

    
    public void addLabReport(LabMessage labMessage)
    {
        if(labMessage == null) 
        {
            return;
        }
        
        if (!labReportObservableList.contains(labMessage)) 
        {
            labMessage.setNo(labReportObservableList.size() +1);
            labReportObservableList.add(0,labMessage);

            Platform.runLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    tableView.setItems(labReportObservableList);
                }
            });
        }

    }

    public void setLabReportsList(List<LabMessage> labReportsList)
    {
        if(labReportsList == null)
        {
            System.out.println("ignoring attempt to set empty lab results");
            return;
        }
//        System.out.println("adding lab reports t .... " + labReportsList);
        
        
        int counter = 1;
        for (LabMessage labMessage : labReportsList) 
        {
            labMessage.setNo(counter);
            counter++;
            
            if(Strings.isNullOrEmpty(labMessage.getSampleId()))
            {
                continue;
            }
            
            TestOrderImpl testOrderImpl = Store.get().testOrderService().findTestOrderImplBySampleId(labMessage.getSampleId());
            labMessage.setTestOrderImpl(testOrderImpl);
        }
        
        labReportObservableList.clear();
        labReportObservableList.addAll(labReportsList);
        
        labReportObservableList = FXCollections.observableArrayList(labReportsList);

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
//                tableView.getItems().clear();
                tableView.setItems(labReportObservableList);
            }
        });
        
    }

    
}
