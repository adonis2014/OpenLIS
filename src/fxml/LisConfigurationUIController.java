/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edwin
 */
public class LisConfigurationUIController implements Initializable
{

    @FXML TextArea tfConfigurationText;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        try
        {
            tfConfigurationText.setEditable(false);
            List<String> read = Files.readAllLines(new File("devices.conf").toPath());
            
            for (String string : read)
            {
                tfConfigurationText.setText(tfConfigurationText.getText() + "\n" +string);
            }
            
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }    
    
}
