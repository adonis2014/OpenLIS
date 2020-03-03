/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;


import com.stately.openlis.ui.Resources;
import com.stately.openlis.ui.NotificationManagerUI;
import com.stately.openlis.ui.SubMenuSystem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Edwin
 */
public class MainUIController implements Initializable
{

    @FXML private VBox subMenuSystemContainer;
    @FXML private BorderPane pagesContainer;
    @FXML private Label pageTitleLabel;
    @FXML private TextArea tfSelectedDetail;
    
    private SubMenuSystem subMenuSystem;
    public static NotificationManagerUI notificationManager;
    
    
    public static TextArea selected;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        
        tfSelectedDetail.setText("Hello");
        
        selected = tfSelectedDetail;
        
        subMenuSystem = new SubMenuSystem(this);
        subMenuSystem.initUi();
        
        subMenuSystemContainer.getChildren().add(subMenuSystem);
        
        notificationManager = new NotificationManagerUI(this);
        notificationManager.initUi();
        subMenuSystemContainer.getChildren().add(notificationManager);
        
        
        subMenuSystem.workspaceUIButton.fire();
        
        WorkSpaceUIController controller = SubMenuSystem.map.get(Resources.WorkSpaceUI).getController();
        controller.setNotificationManager(notificationManager);
        controller.initDataReading();
        
        
    }    
    
    public void setPageTitle(String title)
    {        
        pageTitleLabel.setText(title);
    }
    
    public void setNewView(Node node)
    {
        pagesContainer.getChildren().clear();
        pagesContainer.getChildren().add(node);
    }
    
    public BorderPane getConainet()
    {
        return pagesContainer;
    }
    
}
