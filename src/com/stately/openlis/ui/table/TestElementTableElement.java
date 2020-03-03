/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.ui.table;

import com.stately.openlis.entities.MedicalSystem;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestRequestImpl;
import javafx.scene.control.*;

import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

/**
 *
 * @author Edwin
 */
public class TestElementTableElement
{

    TreeTableView<TestElementImpl> tableView;

    TestElementImpl elementImpl = new TestElementImpl();

    private final TreeTableColumn<TestElementImpl, String> noCol = new TreeTableColumn<>("Test Name.");
    private final TreeTableColumn<TestElementImpl, String> assayCodeCol = new TreeTableColumn<>("Code");
    private final TreeTableColumn<TestElementImpl, String> assayName = new TreeTableColumn<>("Assay Name");
    private final TreeTableColumn<TestElementImpl, String> resutltCol = new TreeTableColumn<>("Result");
    private final TreeTableColumn<TestElementImpl, String> resultUnitCol = new TreeTableColumn<>("Unit");
    private final TreeTableColumn<TestElementImpl, Boolean> acceptCol = new TreeTableColumn<>("Accept");

    TreeItem rootItem = new TreeItem("Test Requests", new Circle(10, Color.BLUE));

//    private final ContextMenu contextMenu = new ContextMenu();
    private final MedicalSystem selectedSystem = null;

    public TestElementTableElement(TreeTableView<TestElementImpl> treeTableView)
    {

        treeTableView.getColumns().clear();
        this.tableView = treeTableView;

        tableView.setRoot(rootItem);
        tableView.setShowRoot(false);

        noCol.setCellValueFactory(new TreeItemPropertyValueFactory(TestElementImpl._no));
        assayCodeCol.setCellValueFactory(new TreeItemPropertyValueFactory(TestElementImpl._analyteCode));
        assayName.setCellValueFactory(new TreeItemPropertyValueFactory(TestElementImpl._testName));
        resutltCol.setCellValueFactory(new TreeItemPropertyValueFactory(TestElementImpl._measurementData));
        resultUnitCol.setCellValueFactory(new TreeItemPropertyValueFactory(TestElementImpl._unitsOfMeasureValue));
        acceptCol.setCellValueFactory(new TreeItemPropertyValueFactory(TestElementImpl._selected));
//        acceptCol.setCellValueFactory(new Callback<CellDataFeatures<TestElementImpl, Boolean>, ObservableValue<Boolean>>()
//        {
//            @Override
//            public ObservableValue<Boolean> call(CellDataFeatures<TestElementImpl, Boolean> param)
//            {
//                return param.getValue().getValue().selectedProperty();                
//            }
//        });

        acceptCol.setCellFactory(new Callback<TreeTableColumn<TestElementImpl, Boolean>, TreeTableCell<TestElementImpl, Boolean>>()
        {
            @Override
            public TreeTableCell<TestElementImpl, Boolean> call(TreeTableColumn<TestElementImpl, Boolean> p)
            {
                class BooleanCell extends TreeTableCell<TestElementImpl, Boolean>
                {

                    CheckBox checkBox;

                    public BooleanCell()
                    {
                        checkBox = new CheckBox();
                        checkBox.setDisable(true);
                        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>()
                        {
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                            {
                                if (isEditing())
                                {
                                    commitEdit(newValue == null ? false : newValue);
                                }
                            }
                        });
                        this.setGraphic(checkBox);
                        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        this.setEditable(true);
                        
                        setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void startEdit()
                    {
                        super.startEdit();
                        if (isEmpty())
                        {
                            return;
                        }
                        checkBox.setDisable(false);
                        checkBox.requestFocus();
                    }

                    @Override
                    public void cancelEdit()
                    {
                        super.cancelEdit();
                        checkBox.setDisable(true);
                    }

                    public void commitEdit(Boolean value)
                    {
                        super.commitEdit(value);
                        checkBox.setDisable(true);
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty)
                    {
                        
                        super.updateItem(item, empty);
                        
                        System.out.println("item state .... " + item + " isEmpty == " + isEmpty());
                        if (!isEmpty())
                        {
                            checkBox.setSelected(item);
                        }
                    }
                }
                
                CheckBoxTreeTableCell<TestElementImpl, Boolean> cell = new CheckBoxTreeTableCell<TestElementImpl, Boolean>()
                {   
                };
//                cell.selectedProperty().addListener(new ChangeListener<Boolean>()
//                        {
//                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
//                            {
//                                if (cell.isEditing())
//                                {
//                                    cell.commitEdit(newValue == null ? false : newValue);
//                                }
//                            }
//                        });
                
                return cell;
                
//                return new BooleanCell();
                
                
                
                
//                CheckBoxTreeTableCell<TestElementImpl, Boolean> cell = new CheckBoxTreeTableCell<TestElementImpl, Boolean>(){
//                    @Override
//                    public void updateItem(Boolean item, boolean empty)
//                    {
//                       
//                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
//                                                
//                         if(isEditing() == false)
//                        {
//                            return;
//                        }
//                                                
//                        System.out.println("called ...  " + item + " empty state ... " + empty);                        
//                        if (item != null)
//                        {                           
//                            if(getTreeTableRow().getTreeItem() != null)
//                            {
//                                TestElementImpl actionPoint = getTreeTableRow().getTreeItem().getValue();
//                            System.out.println("action point .. " + actionPoint);
//                            }                            
//                        }
//                        
//                    }
//                    
//                };
//                
//                cell.setAlignment(Pos.CENTER);
//                
//                return cell;
            }
        });
        
        
        resutltCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        
        acceptCol.setPrefWidth(150);
        

        tableView.getColumns().setAll(noCol, assayCodeCol, assayName, resutltCol, resultUnitCol, acceptCol);
        tableView.setEditable(true);
        
        acceptCol.setEditable(true);
        resutltCol.setEditable(true);
        
        acceptCol.setOnEditCommit(new EventHandler<CellEditEvent<TestElementImpl, Boolean>>()
        {
            @Override
            public void handle(CellEditEvent<TestElementImpl, Boolean> event)
            {
                System.out.println(".................." + event.getNewValue());
                System.out.println("Edit commit for " + event.getRowValue());
            }
        });

        
        resutltCol.setOnEditCommit(new EventHandler<CellEditEvent<TestElementImpl, String>>()
        {
            @Override
            public void handle(CellEditEvent<TestElementImpl, String> event)
            {
                System.out.println("Edit commit for result editting" + event.getRowValue());
                
                TestElementImpl testElementImpl = event.getRowValue().getValue();
                
                if(testElementImpl != null)
                {
                    String newValue = event.getNewValue();
                    testElementImpl.setMeasurementData(newValue);
                    
                    if(!newValue.equalsIgnoreCase(testElementImpl.getMeasurementData()))
                    {
                        testElementImpl.setResultAltered(true);
                    }
                }
            }
        });

    }

    public void setLabReportsList(List<TestRequestImpl> labReportsList)
    {

        if (labReportsList == null)
        {
            System.out.println("ignoring attempt to set empty lab results");
            return;
        }

        Color[] colors = new Color[]
        {
            Color.CHOCOLATE, Color.TURQUOISE,
            Color.YELLOWGREEN, Color.BURLYWOOD, Color.CORNFLOWERBLUE,
            Color.DARKCYAN, Color.DARKTURQUOISE, Color.DARKMAGENTA, Color.BLANCHEDALMOND,
            Color.AZURE, Color.BLUEVIOLET
        };

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                rootItem.getChildren().clear();

                int counter = 0;

                for (TestRequestImpl testRequestImpl : labReportsList)
                {

                    TestElementImpl noteLabelItem = new TestElementImpl();
                    noteLabelItem.setNo(testRequestImpl.getTestName());

                    CheckBoxTreeItem<TestElementImpl> node = new CheckBoxTreeItem(noteLabelItem, new Circle(7, colors[counter]));

                    rootItem.getChildren().add(node);

                    counter++;

                    if (labReportsList.size() == 1)
                    {
                        node.setExpanded(true);
                    }

                    List<TestElementImpl> testElementImplsList = testRequestImpl.getElements();
                    for (TestElementImpl testElementImpl : testElementImplsList)
                    {
                        node.getChildren().add(new TreeItem<>(testElementImpl));
                    }

                }

            }
        });

    }

    
}
