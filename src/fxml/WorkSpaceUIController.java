/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.google.common.base.Strings;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.jms.Message;
import org.controlsfx.control.MasterDetailPane;
import com.stately.common.model.DateRange;
import com.stately.openlis.DataCallback;
import com.stately.openlis.astm.AstmMessage;
import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.astm.MessageReceiver;
import com.stately.openlis.astm.ResultRecord;
import com.stately.openlis.entities.Device;
import com.stately.openlis.entities.StatusCode;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestRequestImpl;
import com.stately.openlis.hl7.models.constant.TransmissionProtocol;
import com.stately.openlis.ui.Resources;
import com.stately.openlis.jms.JmsConConfig;
import com.stately.openlis.jms.JmsIntegrator;
import com.stately.openlis.model.DeviceConnection;
import com.stately.openlis.model.TestOrder;
import com.stately.openlis.parsers.LogManager;
import com.stately.openlis.reading.SerialPortReader;
import com.stately.openlis.reading.TcpIp_Reader;
import com.stately.openlis.services.ApplicationScopeBean;
import com.stately.openlis.services.StaticConverters;
import com.stately.openlis.services.Store;
import com.stately.openlis.ui.NotificationManagerUI;
import com.stately.openlis.ui.table.LabReportTableElement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.jms.ObjectMessage;


/**
 * FXML Controller class
 *
 * @author Edwin
 */
public class WorkSpaceUIController implements Initializable
{

    @FXML
    private AnchorPane containerBorderPane;
//    private BorderPane containerBorderPane;

    private final TableView tableView = new TableView();
    private final LabReportTableElement labReportTableElement = new LabReportTableElement(tableView);

    private LabReportDetailUIController reportDetailUIController;

    private LabMessage selectedLabMessage;

//    private MessageReceiver messageReceiver = new MessageReceiver();
    final JmsIntegrator jmsIntegrator = new JmsIntegrator();

    private MasterDetailPane masterDetailPane = null;

    private NotificationManagerUI notificationManager;

    final TextField searchTextField = new TextField();

    private DateRange dateRange;//= DateRange.sameDayRange(new Date());

    private CheckBox consolidateCheckbox = new CheckBox("Consolidate");
    
    private VBox searchBox = null;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        System.out.println("workspace loadeddd ...");
        searchBox = createSearchBox();

        try
        {

            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(Resources.LabReportDetailUI));
            AnchorPane labReportDetailUI = (AnchorPane) myLoader.load();
            reportDetailUIController = myLoader.getController();

            reportDetailUIController.setJmsIntegrator(jmsIntegrator);

            masterDetailPane = new MasterDetailPane(Side.LEFT, labReportDetailUI, searchBox, true);
            masterDetailPane.setDividerPosition(0.299);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        containerBorderPane.getChildren().add(masterDetailPane);
        masterDetailPane.setMaxWidth(Double.MAX_VALUE);

        loadLast24_hoursRecords();
    }

    public VBox createSearchBox()
    {
        VBox vBox = new VBox();
        System.out.println("workspace loadeddd ...");
        try
        {

            consolidateCheckbox.setSelected(true);

            registerTableListener();

            
            Button searchButton = new Button("Search");
            Button loadNewRecord = new Button("Unprocessed");
            
            HBox boxholder = new HBox(1.5);
            boxholder.setPadding(new Insets(8));
            boxholder.getChildren().add(searchButton);
            boxholder.getChildren().add(loadNewRecord);
            
            
            loadNewRecord.setOnAction((ActionEvent event) ->
            {
                
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
//                        loadNewRecord.setCursor(Cursor.WAIT);
                        List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(StatusCode.UNPROCESSED);
                        showMessages(astmMessagesList);
//                        searchBox.setCursor(Cursor.DEFAULT);
                    }
                });
                
            });
            
            

            ComboBox<Device> comboBox = new ComboBox<>();
            comboBox.setItems(FXCollections.observableList(ApplicationScopeBean.devicesList));

            GridPane gridPane = new GridPane();

            gridPane.setVgap(2.0);
            gridPane.setHgap(2.0);
            gridPane.add(new Label("Device"), 0, 0);
            gridPane.add(comboBox, 1, 0);
            gridPane.add(new Label("Sample ID"), 0, 1);
            gridPane.add(searchTextField, 1, 1);
            gridPane.add(boxholder, 1, 2);
            gridPane.add(consolidateCheckbox, 2, 2);

            vBox.getChildren().add(gridPane);

            vBox.getChildren().add(tableView);

            EventHandler searchEventHandler = new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    doSearch();                            
                }
            };

            searchButton.setOnAction(searchEventHandler);
            searchTextField.setOnAction(searchEventHandler);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return vBox;
    }

    public void doSearch()
    {
        
        
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    searchBox.setCursor(Cursor.WAIT);
                    String searchText = searchTextField.getText().trim();

                    List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(dateRange, searchText);

                    System.out.println("search result size = " + astmMessagesList.size());

                    showMessages(astmMessagesList);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                searchBox.setCursor(Cursor.DEFAULT);
            }
        });

        
    }

    public void loadLast24_hoursRecords()
    {
        try
        {

            DateRange dayDateRange = new DateRange(new Date(), new Date());

            LocalDate localDate = LocalDate.now().minusDays(1);

            dayDateRange.setFromDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(dayDateRange, "");

            showMessages(astmMessagesList);

        } catch (Exception e)
        {
            System.out.println("Error loading default data for the past 24 hours");
            e.printStackTrace();
        }

    }

    public void showMessages(List<AstmMessage> astmMessagesList)
    {
        try
        {

            List<LabMessage> labMessagesList = new LinkedList<>();

            for (AstmMessage astmMessage : astmMessagesList)
            {
                LabMessage labMessage = new LabMessage();

                labMessage.setSampleId(astmMessage.getSampleId());
                labMessage.setPatientName(astmMessage.getPatientName());
                labMessage.setRawMessage(astmMessage.getMessageText());
                labMessage.setDeviceId(astmMessage.getDeviceId());

                labMessagesList.add(labMessage);

                labMessage.astmMessage = astmMessage;

            }

            labReportTableElement.setLabReportsList(labMessagesList);

        } catch (Exception e)
        {
            System.out.println("Error loading default data for the past 24 hours");
            e.printStackTrace();
        }

    }

    public void setNotificationManager(NotificationManagerUI notificationManagerUI)
    {
        this.notificationManager = notificationManagerUI;
    }

    public void initDataReading()
    {

        jmsIntegrator.setTestOrdersCallback(new DataCallback<Message>()
        {
            @Override
            public void onData(Message jmsMessage)
            {

                //1. Transform to TestOrder and persist
                //2. Extract all analyte code, create ASTM/HL7 message and send to device
                try
                {

                    ObjectMessage objectMessage = (ObjectMessage) jmsMessage;
                    TestOrder testOrder = (TestOrder) objectMessage.getObject();

                    Store.get().testOrderService().saveOrder(testOrder);

                    LogManager.instance().saveTestOrder(testOrder);

                    System.out.println("testorder : " + testOrder);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                System.out.println("jms message received in workspace .. " + jmsMessage);

            }
        });

        List<Device> devicesList = ApplicationScopeBean.devicesList;

        List<DeviceConnection> deviceConnectionsList = new LinkedList<>();

        for (Device device : devicesList)
        {
            MessageReceiver messageReceiver = new MessageReceiver(device);

            messageReceiver.setMessageCallback(new DataCallback<String>()
            {
                @Override
                public void onData(String newMessageBlock)
                {
                    try
                    {
                        System.out.println("******************************");
                        System.out.println("New message recieved from Device \n" + newMessageBlock);
                        LabMessage labMessage = messageReceiver.parseSingleAstmMessage(newMessageBlock);

                        if (labMessage == null)
                        {
                            return;
                        }

                        AstmMessage astmMessage = Store.get().testOrderService().saveLabMessage(labMessage);

                        if (astmMessage != null)
                        {
                            labMessage.astmMessage = astmMessage;

                            labReportTableElement.addLabReport(labMessage);
                        } else
                        {
                            System.out.println("Unable to save labMessage : " + labMessage);
                        }

//                        jmsIntegrator.sendJmsMessage(labMessage);
                    } catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println("error occured in processing newAstm Message");
                    }

                }
            });
            
            DeviceConnection deviceConnection = new DeviceConnection();
            
            if (device.getTransmissionProtocol() == TransmissionProtocol.SERIAL_PORT)
            {

                SerialPortReader dataReader = new SerialPortReader(device.getPort(), messageReceiver);
                boolean portConnected = dataReader.initConnection();

                deviceConnection.setDevice(device);
                deviceConnection.setTransSetupConfig(dataReader);
            }
            else if(device.getTransmissionProtocol() == TransmissionProtocol.TCP_IP)
            {
                TcpIp_Reader dataReader = new TcpIp_Reader(device.getIpAddress(), device.getPort(), messageReceiver);
                boolean portConnected = dataReader.initConnection();

                deviceConnection.setDevice(device);
                deviceConnection.setTransSetupConfig(dataReader);
            }

            

            deviceConnectionsList.add(deviceConnection);
        }

        notificationManager.deviceConnection(deviceConnectionsList);

        //Serial Port Connection Setup
        //JmsConnection Setup
        JmsConConfig jmsConConfig = new JmsConConfig();

        boolean jmsConnected = jmsIntegrator.initJmsConn();
        System.out.println("######## Notification manager ... " + notificationManager);
        notificationManager.jmsStatus(jmsConnected);
        ApplicationScopeBean.jmsIntegrator = jmsIntegrator;

    }

    public void registerTableListener()
    {
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LabMessage>()
        {
            @Override
            public void changed(ObservableValue<? extends LabMessage> observableValue, LabMessage oldItem, LabMessage newItem)
            {
                try
                {
//                    System.out.println("observableValue = " + observableValue);
//                    System.out.println("observableValue.getValue() = " + observableValue.getValue());
//                    System.out.println("oldItem = " + oldItem);
//                    System.out.println("newItem = " + newItem);

                    selectedLabMessage = newItem;

                    if (selectedLabMessage == null)
                    {
                        return;
                    }

                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                            System.out.println("new labresult selected ..." + selectedLabMessage);
                            

                            List<ResultRecord> resultRecordsList = null;

                            if (consolidateCheckbox.isSelected())
                            {
                                
                                if (Strings.isNullOrEmpty(selectedLabMessage.getSampleId()))
                                {
                                    resultRecordsList = Store.get().testOrderService().getResultRecord(selectedLabMessage.astmMessage);
                                }
                                else
                                {
                                    resultRecordsList = Store.get().testOrderService().getResultRecord(selectedLabMessage.astmMessage.getSampleId());
                                }
                                
                                
                            } else
                            {
                                resultRecordsList = Store.get().testOrderService().getResultRecord(selectedLabMessage.astmMessage);
                            }

//                    List<ResultRecord> resultRecordsList = Store.get().testOrderService().getResultRecord(selectedLabMessage.astmMessage);
                            selectedLabMessage.setResultRecordsList(resultRecordsList);

                            TestRequestImpl testRequestImpl = new TestRequestImpl();
                            testRequestImpl.setTestName("Test Result");
                            List<TestRequestImpl> testRequestImplsList = new LinkedList<>();
                            testRequestImplsList.add(testRequestImpl);

                            List<TestElementImpl> testElementImplsList = new LinkedList<>();

                            for (ResultRecord resultRecord : resultRecordsList)
                            {
                                testElementImplsList.add(StaticConverters.toTestElement(resultRecord));
                            }

                            testRequestImpl.setElements(testElementImplsList);
                            selectedLabMessage.setTestRequestImplsList(testRequestImplsList);

                            reportDetailUIController.showLabMessage(selectedLabMessage);
                        }
                    });

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

}
