/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the  editor.
 */

package com.stately.openlis.ui;

/**
 *
 * @author Edwin
 */
public interface Resources
{
    String fxml_base = "/fxml/";
    String css_base = "/styles/";

    String app_main = fxml_base + "MainApp.fxml";
    String MainUI = fxml_base + "MainUI.fxml";
    String DevicesUI = fxml_base + "DevicesUI.fxml";
    String MedicalSystemUI = fxml_base + "MedicalSystemUI.fxml";
    String LabReportDetailUI = fxml_base + "LabReportDetailUI.fxml";
    String WorkSpaceUI = fxml_base + "WorkSpaceUI.fxml";
    String LisConfigurationUI = fxml_base + "LisConfigurationUI.fxml";
    String TestOrdersUI = fxml_base + "TestOrdersUI.fxml";


    String lan_connection = fxml_base + "LanConnection.fxml";
    String serial_connection = fxml_base + "SerialPortConnection.fxml";
    String app_css = css_base + "Styles.css";
}
