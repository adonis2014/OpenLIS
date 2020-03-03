/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui;

import javafx.scene.control.Alert;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public class MsgHelper 
{

    public static void showError(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Action Status");
        alert.setHeaderText("Interaction Response");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public static void showInfo(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action Status");
        alert.setHeaderText("Interaction Response");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public static void showInfo()
    {
        String msg = "Action Sucessful";
        showInfo(msg);
    }
    
    public static void showError()
    {
        String msg = "Action Failed";
        showError(msg);
    }
}
