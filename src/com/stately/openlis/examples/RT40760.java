/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.examples;

/**
 *
 * @author Edwin
 */
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RT40760 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        final TreeTableView<HeavyItem> table;
        final TreeItem<HeavyItem> root;
        {
            //setup table
            TreeTableColumn<HeavyItem, Boolean> column = new TreeTableColumn<>();
            column.setCellValueFactory(c -> c.getValue().getValue());
            column.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(column));

            table = new TreeTableView<>();
            table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
            table.getColumns().add(column);

            root = new TreeItem<>(new HeavyItem(0));
            table.setRoot(root);
            table.setShowRoot(false);
        }

        final Button fillButton;
        final Button clearButton;
        {
            clearButton = new Button("Clear");
            clearButton.setOnAction(e -> root.getChildren().clear());

            fillButton = new Button("Fill");
            fillButton.setOnAction(e -> {
                for (int i = 0; i < 1; i++) {
                    root.getChildren().add(new TreeItem<>(new HeavyItem(1)));
                }
            });
        }

        VBox holder = new VBox();
        holder.getChildren().add(fillButton);
        holder.getChildren().add(clearButton);
        holder.getChildren().add(table);

        primaryStage.setScene(new Scene(holder, 200, 200));
        primaryStage.show();
    }




    private class HeavyItem extends SimpleBooleanProperty {

        private final double[] burden;

        public HeavyItem(int size) {
            super(true);
            burden = new double[size*100*1024/8*1024];
        }
    }

}