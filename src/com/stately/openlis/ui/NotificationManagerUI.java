/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui;

import com.stately.openlis.entities.Device;
import com.stately.openlis.model.DeviceConnection;
import com.stately.openlis.reading.SerialPortReader;
import com.stately.openlis.reading.TransSetupConfig;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import fxml.MainUIController;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public class NotificationManagerUI extends VBox
{
    
    private static final int animationDuration = 400;
    
    private final BorderPane stackPane;
    
    private MainUIController mainUIController;
    
    
    private Button jmsConnectionButton = GlyphsDude.createIconButton(FontAwesomeIcon.BARCODE, "JMS Connection",null, null, ContentDisplay.LEFT);
//    private Button vistros = GlyphsDude.createIconButton(FontAwesomeIcon.BARCODE, "Vistros",null, null, ContentDisplay.LEFT);
//    
    private final String offColor = "-fx-background-color: #FF0000;";
    private final String onColor = "-fx-background-color: #00FF00;";
    
    public NotificationManagerUI(MainUIController mainUIController)
    {
        this.mainUIController = mainUIController;
        stackPane = mainUIController.getConainet();
        
        jmsStatus(false);
                
        System.out.println("stack pane .....  " + stackPane);
        
    }
    
    public void jmsStatus(boolean status)
    {
        System.out.println("passed status of JMS Connection .. " + status);
        if(status)
        {
            jmsConnectionButton.setText("EMR ON");
            jmsConnectionButton.setStyle(onColor);
        }
        else
        {
            jmsConnectionButton.setText("EMR OFF");
            jmsConnectionButton.setStyle(offColor);
        }
    }
    
    public void deviceConnection(List<DeviceConnection> deviceConnectionsList)
    {
        for (DeviceConnection deviceConnection : deviceConnectionsList)
        {           
            Device device = deviceConnection.getDevice();
            TransSetupConfig transSetupConfig = deviceConnection.getTransSetupConfig();            
            
            if(transSetupConfig == null)
            {
                continue;
            }
            
            boolean status = transSetupConfig.isConnected();
        
            Button button = GlyphsDude.createIconButton(FontAwesomeIcon.BARCODE, device.getDeviceName(),null, null, ContentDisplay.LEFT);
            
            button.setMaxWidth(Double.MAX_VALUE);
        
        getChildren().add(button);
            
        System.out.println("passed status of JMS Connection .. " + status);
        if(status)
        {
            button.setText(device.getDeviceName() +" ON");
            button.setStyle(onColor);
        }
        else
        {
            button.setText(device.getDeviceName()+ " OFF");
            button.setStyle(offColor);
        }
            
            
        }
    }
    
    
    public void initUi()
    {
        setSpacing(5);
        setPadding(new Insets(10));
        
        jmsConnectionButton.setMaxWidth(Double.MAX_VALUE);
                
        getChildren().add(jmsConnectionButton);
        
                        
        jmsConnectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
//                mainUIController.setPageTitle("Workspace");
                
            }
        });
        
        
        
    }
    
    
}
