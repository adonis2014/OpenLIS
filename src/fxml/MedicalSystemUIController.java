/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.google.common.base.Strings;
import com.stately.openlis.entities.MedicalSystem;
import com.stately.openlis.hl7.models.constant.CommunicationMode;
import com.stately.openlis.services.Store;
import com.stately.openlis.ui.MsgHelper;
import com.stately.openlis.ui.table.MedicalSystemsTableElement;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Edwin
 */
public class MedicalSystemUIController implements Initializable
{

    @FXML private TextField tfSystemName;
    @FXML TextField tfVendorName;
    @FXML TextField tfSystemDescription;
    @FXML TextField tfCommunicationPortNo;
    @FXML ComboBox<CommunicationMode> cbCommuncationProtocol;
    @FXML TableView<MedicalSystem> tblMedicalSystems;
    
    private MedicalSystemsTableElement medicalSystemsTableElement;
    
    private MedicalSystem medicalSystem = new MedicalSystem();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        cbCommuncationProtocol.setItems(FXCollections.observableArrayList(Arrays.asList(CommunicationMode.values())));
        cbCommuncationProtocol.setValue(CommunicationMode.MLLP);
        
        medicalSystemsTableElement = new MedicalSystemsTableElement(tblMedicalSystems);
        
        reloadData();
        
//        tblMedicalSystems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MedicalSystem>() {
        tblMedicalSystems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MedicalSystem>() {
            @Override
            public void changed(ObservableValue<? extends MedicalSystem> observableValue, MedicalSystem oldItem, MedicalSystem newItem)
            {
                medicalSystem = newItem;
                
                System.out.println("new item selected ... ");
                pupulateDate();
            }
        });
    }    
    
    public void reloadData()
    {
        try
        {
            //        medicalSystemsTableElement.setDevicesList(CrudService.instance().findAll(MedicalSystem.class));
        } catch (Exception e)
        {
        }

    }
    
    public boolean collectDate()
    {
        if(Strings.isNullOrEmpty(tfSystemName.getText().trim()))
        {
            MsgHelper.showError("Please enter system's name");
        }
        medicalSystem.setSystemName(tfSystemName.getText());
        medicalSystem.setVendorName(tfVendorName.getText());
        medicalSystem.setDescription(tfSystemDescription.getText());
        medicalSystem.setCommProtocol(cbCommuncationProtocol.getValue());
        medicalSystem.setProtocolAddress(tfCommunicationPortNo.getText());
        
        return true;
    }
    
    public void pupulateDate()
    {
        if(medicalSystem == null)
        {
            return;        
        }
        
        tfSystemName.setText(medicalSystem.getSystemName());
        tfVendorName.setText(medicalSystem.getVendorName());
        tfSystemDescription.setText(medicalSystem.getDescription());
        cbCommuncationProtocol.setValue(medicalSystem.getCommProtocol());
        tfCommunicationPortNo.setText(medicalSystem.getProtocolAddress());
    }
    
    public void saveMedicalSystem()
    {
        
        if(collectDate())
        {
            boolean saved = Store.get().save(medicalSystem);
        
        if(saved)
        {
            MsgHelper.showInfo();
            medicalSystem = new MedicalSystem();
            pupulateDate();
            reloadData();
        }
        else
        {
            MsgHelper.showError();
        }
        }
        
        
    }
    
    public void clearForm()
    {
        medicalSystem = new MedicalSystem();
        pupulateDate();
    }
    
}
