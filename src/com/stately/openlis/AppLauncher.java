package com.stately.openlis;

import com.stately.openlis.hl7.main.controllers.AppMainController;
import com.stately.openlis.ui.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Properties;


public class AppLauncher extends Application //implements MessageListener
{

    @Override
    public void start(Stage stage) throws Exception 
    {
//        System.setProperty( "java.library.path", "libs" );
        
        FXMLLoader appMainLoader = new FXMLLoader(getClass().getResource(Resources.app_main));

        Parent root = (Parent)appMainLoader.load();
        root.prefHeight(600);
        root.prefWidth(1100);

        AppMainController appMainController = appMainLoader.getController();
        appMainController.setAppLauncher(this);

//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);

        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add(Resources.app_css);
        
        stage.setTitle("Hims-Lab Equipment Interface");
        stage.setScene(scene);
        stage.show();
        //init();
    }

//    private MessageProducer producer;
//    private MessageConsumer consumer;

    /**
    * */
    public void init(int yea)
    {
        try
        {
            InitialContext initialContext = new InitialContext(getProperties());
            //logger.info("initial context successful");

//            ConnectionFactory connectionFactory = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");
//            System.out.println("lookup successful");
//            Connection connection = connectionFactory.createConnection();
//
//            Queue bridgeQueue = (Queue)initialContext.lookup("/queue/bridgeQueue");
//            Queue himsQueue = (Queue)initialContext.lookup("/queue/himsQueue");
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            consumer = session.createConsumer(bridgeQueue);
//            producer = session.createProducer(himsQueue);

            System.out.println("connection successful");
            //logger.info("Jms Connection factory and resources created without errors");
//            connection.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //logger.error("error looking up jms connection factory", e);
        }
    }

    private Properties getProperties()
    {
        Properties props = new Properties();
        props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.put("java.naming.provider.url", "jnp://localhost:1099");
        props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

        return props;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

//    @Override
//    public void onMessage(Message message)
//    {
//        try
//        {
//            if(message instanceof TextMessage)
//            {
//                String msg = ((TextMessage) message).getText();
//                System.out.println("Message Received from Hims: " + msg);
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}
