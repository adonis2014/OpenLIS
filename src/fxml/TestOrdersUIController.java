/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import com.stately.common.model.DateRange;
import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.entities.TestElementImpl;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.entities.TestRequestImpl;
import com.stately.openlis.model.TestElement;
import com.stately.openlis.model.TestOrder;
import com.stately.openlis.model.TestRequest;
import com.stately.openlis.services.ApplicationScopeBean;
import com.stately.openlis.services.StaticConverters;
import com.stately.openlis.services.Store;
import com.stately.openlis.ui.Resources;
import com.stately.openlis.ui.table.TextOrderFXTableElement;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Edwin
 */
public class TestOrdersUIController implements Initializable
{

    @FXML
    private TableView<TestOrderImpl> textOrderFXTable;
    @FXML
    private AnchorPane mainFormPane;

    @FXML
    private TextField sampleIdTestField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TitledPane testDetailsContrainer;

    private TestOrderImpl selectedTestOrderImpl;

    private TextOrderFXTableElement testOrderFXTableElement = null;

    private LabReportDetailUIController reportDetailUIController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        testOrderFXTableElement = new TextOrderFXTableElement(textOrderFXTable);

        try
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(Resources.LabReportDetailUI));
            AnchorPane labReportDetailUI = (AnchorPane) myLoader.load();
            reportDetailUIController = myLoader.getController();
//            reportDetailUIController.setJmsIntegrator(this);

            testDetailsContrainer.setContent(labReportDetailUI);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        registerTableListener();

    }

    public void searchTestOrders()
    {

        mainFormPane.setCursor(Cursor.WAIT);

        Platform.runLater(()
                -> 
                {
                    try
                    {
                        DateRange dateRange = new DateRange();

                        if (startDatePicker.getValue() != null)
                        {
                            dateRange.setFromDate(Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        }

                        if (endDatePicker.getValue() != null)
                        {
                            dateRange.setToDate(Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        }

                        String sampleId = sampleIdTestField.getText().trim();

                        List<TestOrderImpl> testOrderImplsList = Store.get().testOrderService().getTestOrderImplsList(dateRange, sampleId);
                        System.out.println("executing search completed ..... ");
//        System.out.println("search result .. " + testOrderImplsList);
                        testOrderFXTableElement.setLabReportsList(testOrderImplsList);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    mainFormPane.setCursor(Cursor.DEFAULT);
        });

    }

    public void registerTableListener()
    {
        System.out.println("registering table listerner");
        textOrderFXTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TestOrderImpl>()
        {
            @Override
            public void changed(ObservableValue<? extends TestOrderImpl> observableValue, TestOrderImpl oldItem, TestOrderImpl newItem)
            {
                selectedTestOrderImpl = newItem;

                if (selectedTestOrderImpl == null)
                {
                    return;
                }

                System.out.println("new item selected ... " + TestOrderImpl.class.getSimpleName() + " ::: - " + selectedTestOrderImpl);

                LabMessage labMessage = Store.get().testOrderService().toLabMessage(selectedTestOrderImpl);

                reportDetailUIController.setJmsIntegrator(ApplicationScopeBean.jmsIntegrator);

                TestOrder testOrder = Store.get().testOrderService().getPackageTestOrder(labMessage.getSampleId());

                List<TestRequestImpl> testRequestImplsList = new LinkedList<>();
                for (TestRequest request : testOrder.getRequests())
                {
                    TestRequestImpl testRequestImpl = StaticConverters.toTestRequestImpl(request);
                    testRequestImplsList.add(testRequestImpl);

                    List<TestElementImpl> testElementImplsList = new LinkedList<>();

                    for (TestElement testElement : request.getElements())
                    {
                        TestElementImpl elementImpl = StaticConverters.toTestElementImpl(testElement);

                        testElementImplsList.add(elementImpl);
                    }

                    testRequestImpl.setElements(testElementImplsList);
                }

                labMessage.setTestRequestImplsList(testRequestImplsList);
                labMessage.setTestOrderImpl(selectedTestOrderImpl);
                reportDetailUIController.showLabMessage(labMessage);

            }
        });
    }
}
