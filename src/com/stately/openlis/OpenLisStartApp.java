/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis;

import com.google.common.base.Strings;
import com.stately.openlis.astm.AstmMessage;
import com.stately.openlis.entities.Device;
import com.stately.openlis.entities.StatusCode;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.hl7.models.constant.TransmissionProtocol;
import com.stately.openlis.ui.Resources;
import com.stately.openlis.parsers.DictionaryManager;
import com.stately.openlis.parsers.LogManager;
import com.stately.openlis.parsers.StringUtil;
import com.stately.openlis.services.ApplicationScopeBean;
import com.stately.openlis.services.Store;
import com.stately.openlis.services.UserSession;
import com.stately.openlis.ui.SubMenuSystem;
import de.jensd.fx.glyphs.GlyphsStyle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.inject.Inject;

/**
 *
 * @author Edwin
 */
public class OpenLisStartApp extends Application
{

    @Inject UserSession userSession;

    @Override
    public void start(Stage primaryStage)
    {
        try
        {

//            Weld weld = new Weld();
//            WeldContainer container = weld.initialize();
//
            System.out.println("userSession : " + userSession);

            System.out.println("contani");
//            redirectLog();
            System.out.println("Starting Integrator at " + LocalDateTime.now());


//            CommandLineStartup.sampleDave();
//
//              updateOldRecord();

//            System.exit(0);
//

            loadConfiguration();


//             System.exit(0);

            DictionaryManager.instance();

            try {

            } catch (Exception e) {
            }




            Set<Object> strings = System.getProperties().keySet();

//            for (Object string : strings)
//            {
//                System.out.println(System.getProperty(string.toString()));
//            }

//            System.out.println(System.getenv());

//            System.out.println(EntityManager.class.getResource("EntityManager.class"));

//            System.out.println(CrudService.instance().findAll(MedicalSystem.class));
//            Font.loadFont(OpenLisStartApp.class.getResource("/fonts/awesome.ttf").toExternalForm(), 12);

            FXMLLoader mainUIFxmlLoader = new FXMLLoader(getClass().getResource(Resources.MainUI));

            Parent mainUiInterface = mainUIFxmlLoader.load();

            SubMenuSystem.map.put(Resources.MainUI, mainUIFxmlLoader);



            StackPane root = new StackPane();
            root.getChildren().add(mainUiInterface);

            Scene scene = new Scene(root, 900,650);
            scene.getStylesheets().add(Resources.app_css);
            scene.getStylesheets().add(GlyphsStyle.BLUE.getStylePath());

            primaryStage.setTitle("Carewex - Integrator");
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public void redirectLog()
    {
        try
        {

            Runnable logrediretor = new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        System.out.println("redicteing trunning ...");
                        PrintStream printStream = new PrintStream(new FileOutputStream(LogManager.instance().SYSTEM_LOG));
                        System.setOut(printStream);
                        System.out.println("redirecting done");
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            };

//            Platform.runLater(logrediretor);

            new Thread(logrediretor).start();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void loadConfiguration()
    {
        try
        {
            String content = new String(Files.readAllBytes(new File("devices.conf").toPath()));
            System.out.println("configuration content : " + content);

            String fileSeparator = "#";

            String[] sections = StringUtil.split(content, fileSeparator);
            for (String section : sections)
            {

                System.out.println("SECTION : " + section);
                if(Strings.isNullOrEmpty(section))
                {
                    continue;
                }
                if(section.trim().startsWith("[Device]"))
                {
                    System.out.println("going to pass device");
                    String[] deviceParts = section.split("\n");

                    Map<String,String> map = new LinkedHashMap<>();
                    for (String devicePart : deviceParts)
                    {
                        System.out.println(devicePart);
                        try
                        {
                            String[] keyValue = devicePart.split("=");
//                        map.put("DEVICE_ID", keyValue[0]);
                        map.put(keyValue[0], keyValue[1]);
                        } catch (Exception e)
                        {
                            String msg = "Error parsing : " + devicePart + " ::: ";
                            System.out.println(msg + e.getMessage());
                        }


                    }

                    TransmissionProtocol transmissionProtocol = null;

                    Device device = new Device();
                    device.setDeviceId(map.get("DEVICE_ID"));
                    device.setDeviceName(map.get("DEVICE_NAME"));
                    device.setPort(map.get("PORT_NO"));
                    device.setIpAddress(map.get("IP_ADDRESS"));

                    try
                    {
                        String transProcString = map.get("TRANSMISSION_PROTOCOL");
                        if(!Strings.isNullOrEmpty(transProcString))
                        {
                            transmissionProtocol = TransmissionProtocol.getEnum(transProcString);
                            device.setTransmissionProtocol(transmissionProtocol);
                        }

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    System.out.println(device.getTransmissionProtocol() + " ******  " +device.getTransmissionProtocol());

                    ApplicationScopeBean.devicesList.add(device);
                    System.out.println(ApplicationScopeBean.devicesList);
//                    System.out.println(map);
                }
                else if(section.trim().startsWith("[USERS]"))
                {
                    String[] usersArray = section.split("\n");
                    List<String> usersList = new LinkedList<>(Arrays.asList(usersArray));

                    //remove header [USERS]
                    usersList.remove(0);

                    ApplicationScopeBean.usersList.addAll(usersList);
                    System.out.println("load Users = " + ApplicationScopeBean.usersList);
                }


            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void updateOldRecord()
    {
        int counter = 0;
        List<TestOrderImpl>  testOrderImplsList = Store.get().testOrderService().getTestOrderImplsList(null, StatusCode.DISPATCHED);
        for (TestOrderImpl testOrderImpl : testOrderImplsList)
        {
            if(Strings.isNullOrEmpty(testOrderImpl.getOrderCode()))
            {
                continue;
            }

            counter++;
            List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(testOrderImpl.getOrderCode());
            for (AstmMessage astmMessage : astmMessagesList)
            {
                astmMessage.setStatusCode(StatusCode.DISPATCHED);
                Store.get().crudService().save(astmMessage);
            }

            System.out.println(counter + "   .. out of " + testOrderImplsList.size());
        }

    }
}
