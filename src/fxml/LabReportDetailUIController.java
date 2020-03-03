/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.astm.ResultRecord;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.entities.TestRequestImpl;
import com.stately.openlis.jms.JmsIntegrator;
import com.stately.openlis.resources.ReportGenerator;
import com.stately.openlis.services.ApplicationScopeBean;
import com.stately.openlis.services.Store;
import com.stately.openlis.ui.table.TestElementTableElement;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Edwin
 */
public class LabReportDetailUIController implements Initializable
{

    @FXML private TextField tfSampleID;
    @FXML private TextField tfPatientName;
    
    @FXML private ChoiceBox<String> deviceUserCombobox;
    
    @FXML private BorderPane borderPane;
    @FXML private MenuButton actionsMenuButton;
    
    TreeItem rootItem = new TreeItem("Test Requests");
    private TreeTableView<TestElementImpl> tblResult = new TreeTableView<>(rootItem);
    private ReportGenerator reportGenerator = new ReportGenerator();
    
    private LabMessage selectedLabMessage;
    private TestOrderImpl testOrderImpl;
    
    private TestElementTableElement detailTableElement;
    
    JmsIntegrator jmsIntegrator;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        detailTableElement = new TestElementTableElement(tblResult);
        borderPane.setCenter(tblResult);
        
        
        deviceUserCombobox.setItems(FXCollections.observableArrayList(ApplicationScopeBean.usersList));
        
        MenuItem resetStatusMenuItem = new MenuItem("Reset Status");
        resetStatusMenuItem.setOnAction((ActionEvent event) ->
        {
            if(testOrderImpl == null)
            {
                return;
            }
            
            testOrderImpl.setOrderStatus(null);
            
            Store.get().crudService().save(testOrderImpl);
        });
        
        actionsMenuButton.getItems().clear();
        actionsMenuButton.getItems().add(resetStatusMenuItem);
        
    }    
    
    public void saveChanges()
    {
        System.out.println("saving changes to lab results....  at " + LocalDateTime.now());
        
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                String validator = deviceUserCombobox.getValue();
//                System.out.println("validator name : " + validator);
                Date validatedOn = new Date();
                
                List<TestRequestImpl> testRequestImpls = Store.get().testOrderService().getTestRequestList(selectedLabMessage.getSampleId());
//                    System.out.println("test requests imls .. " + testRequestImpls);
                for (TestRequestImpl testRequestImpl : testRequestImpls)
                {
                    testRequestImpl.setValidatedBy(validator);
                    testRequestImpl.setValidatedOn(validatedOn);
                    
                    
                    Store.get().crudService().save(testRequestImpl);
//                    System.out.println("saved ... " + testRequestImpl.getValidatedBy());
//                    System.out.println("saved ... " + testRequestImpl.getValidatedOn());
                }
                
                List<TestElementImpl> testElementImplsList = selectedLabMessage.getAllTestElementList();
                for (TestElementImpl testElementImpl : testElementImplsList)
                {
                    ResultRecord resultRecord = testElementImpl.getResultRecord();
                    
                    resultRecord.setMeasurementData(testElementImpl.getMeasurementData());
                    resultRecord.setResultAltered(testElementImpl.isResultAltered());
                    
                    
                    if (testElementImpl.isSelected())
                    {
                        resultRecord.setValidated(true);
//                        System.out.println("...... selected .... " + resultRecord);
                    } else
                    {
                        resultRecord.setValidated(false);
                    }
                    
                    ResultRecord savedrecord = Store.get().crudService().save(resultRecord);
                    
//                    System.out.println("saved ... " + savedrecord);

                }
            }
        });
        
        
    }
    
    public void printReport()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                reportGenerator.generateLabReport(selectedLabMessage);
            }
        });
    }
    
    public void showLabMessage(LabMessage labMessage)
    {        
        this.selectedLabMessage = labMessage;
        
        testOrderImpl = labMessage.getTestOrderImpl();
        
        pupulateDate();
    }
    
    public boolean collectDate()
    {
//        if(Strings.isNullOrEmpty(tfSystemName.getText().trim()))
//        {
//            MsgHelper.showError("Please enter system's name");
//        }
        
        selectedLabMessage.setSampleId(tfSampleID.getText());
        selectedLabMessage.setPatientName(tfPatientName.getText());        
        
        return true;
    }
    
    public void pupulateDate()
    {
        if(selectedLabMessage == null)
        {
            return;        
        }
        
        tfSampleID.setText(selectedLabMessage.getSampleId());
        tfPatientName.setText(selectedLabMessage.getPatientName());
        
        
        detailTableElement.setLabReportsList(selectedLabMessage.getTestRequestImplsList());
        
        MainUIController.selected.setText(selectedLabMessage.getRawMessage());
        
    }

    public void setJmsIntegrator(JmsIntegrator jmsIntegrator)
    {
        this.jmsIntegrator = jmsIntegrator;
    }

    
    
    public void sendMessageToEMR()
    {
        
        
        if(jmsIntegrator != null)
        {
            
            selectedLabMessage.setValidatorName(deviceUserCombobox.getValue());
            jmsIntegrator.sendJmsMessage(selectedLabMessage);
        }
    }
}
