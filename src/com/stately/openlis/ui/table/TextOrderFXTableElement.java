/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui.table;


import com.stately.openlis.entities.StatusCode;
import com.stately.openlis.entities.TestOrderImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author bryte
 */
public class TextOrderFXTableElement 
{
    TableView<TestOrderImpl> tableView;
    
    private final ObservableList<TestOrderImpl> labReportObservableList = FXCollections.observableArrayList();

    private final TableColumn<TestOrderImpl, String> orderNo = new TableColumn<>("Order No.");
    private final TableColumn<TestOrderImpl, String> patientID = new TableColumn<>("Patient ID");
    private final TableColumn<TestOrderImpl, String> patientName = new TableColumn<>("Patient Name");
//    private final TableColumn<TextOrderFX, String> resutltCol = new TableColumn<>("Result");
//    private final TableColumn<TextOrderFX, String> resultUnitCol = new TableColumn<>("Unit");
//    private final TableColumn<TextOrderFX, String> referenceRange = new TableColumn<>("Reference Range");
    
    

    private final ContextMenu contextMenu = new ContextMenu();
    private final TestOrderImpl textOrderFX = null;

    public TextOrderFXTableElement(TableView<TestOrderImpl> tableView)
    {
        tableView.getColumns().clear();
        this.tableView  = tableView;
        
        orderNo.setCellValueFactory(new PropertyValueFactory(TestOrderImpl._orderCode));
        patientID.setCellValueFactory(new PropertyValueFactory(TestOrderImpl._patientId));
        patientName.setCellValueFactory(new PropertyValueFactory(TestOrderImpl._patientName));
//        assayName.setCellValueFactory(new PropertyValueFactory(LabReportDetail._testName));
//        resutltCol.setCellValueFactory(new PropertyValueFactory(LabReportDetail._resultValue));
//        resultUnitCol.setCellValueFactory(new PropertyValueFactory(LabReportDetail._resultUnit));
//        referenceRange.setCellValueFactory(new PropertyValueFactory(LabReportDetail._referenceRange));
        

        orderNo.setCellFactory(column -> {
        return new TableCell<TestOrderImpl, String>() {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);

                if (getItem() == null)
                {
                    return;
                }

                setText(empty ? "" : getItem().toString());
                setGraphic(null);

                TableRow<TestOrderImpl> currentRow = getTableRow();

                if (!isEmpty())
                {
                    if (currentRow.getItem() != null)
                    {
                        TestOrderImpl testOrderImpl = currentRow.getItem();

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


//        tableView.getColumns().addAll(orderNo, patientID, patientName );
        tableView.getColumns().addAll(orderNo,patientName );
        
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

    }

    
    public void addLabReport(TestOrderImpl testOrder)
    {
        if(!labReportObservableList.contains(testOrder))
        {
            labReportObservableList.add(testOrder);
        }
    }

    public void setLabReportsList(List<TestOrderImpl> labReportsList)
    {
        if(labReportsList == null)
        {
            System.out.println("ignoring attempt to set empty lab results");
            return;
        }
//        System.out.println("adding lab reports t .... " + labReportsList);
        labReportObservableList.clear();
        labReportObservableList.addAll(labReportsList);

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                tableView.setItems(labReportObservableList);
            }
        });
        
    }

    public TestOrderImpl getselectedSysteme() {
        return textOrderFX;
    }
}
