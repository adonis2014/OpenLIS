/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stately.openlis.jms;

import com.google.common.base.Strings;
import com.stately.openlis.astm.LabMessage;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.stately.openlis.DataCallback;
import com.stately.openlis.astm.AstmMessage;
import com.stately.openlis.entities.StatusCode;
import com.stately.openlis.entities.TestOrderImpl;
import com.stately.openlis.entities.TestRequestImpl;
import com.stately.openlis.model.TestOrder;
import com.stately.openlis.model.TestRequest;
import com.stately.openlis.parsers.LogManager;
import com.stately.openlis.services.Store;
import java.time.LocalDateTime;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.jms.ObjectMessage;

public class JmsIntegrator implements MessageListener{
    private static final Logger log = Logger.getLogger(JmsIntegrator.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String LabTestOrders_queue = "jms/queue/LabTestOrders";
    private static final String LabTestResults_queue = "jms/queue/LabTestResults";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "frontend";
    private static final String DEFAULT_PASSWORD = "frontendB055#";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "jms://192.168.2.18:4547";
//    private static final String PROVIDER_URL = "remote://192.168.2.18:5545";
//    private static final String PROVIDER_URL = "remote://localhost:4447";

    public DataCallback testOrdersCallback;

    ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;
        Destination LabTestOrders_destination = null;
        Destination LabTestResults_destination = null;
        TextMessage message = null;
        Context context = null;

    public boolean initJmsConn()
    {

        printClassPath();



        try {
            // Set up the context for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, System.getProperty("username", DEFAULT_USERNAME));
            env.put(Context.SECURITY_CREDENTIALS, System.getProperty("password", DEFAULT_PASSWORD));
            context = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryString);
            log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", LabTestOrders_queue);
            log.info("Attempting to acquire destination \"" + destinationString + "\"");
            LabTestOrders_destination = (Destination) context.lookup(destinationString);
            log.info("Found destination \"" + destinationString + "\" in JNDI");

            LabTestResults_destination = (Destination) context.lookup(LabTestResults_queue);

            // Create the JMS connection, session, producer, and consumer
            connection = connectionFactory.createConnection(System.getProperty("username", DEFAULT_USERNAME), System.getProperty("password", DEFAULT_PASSWORD));
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(LabTestResults_destination);
            consumer = session.createConsumer(LabTestOrders_destination);

            consumer.setMessageListener(this);

            connection.start();

            System.out.println("JMS Connection Started");

            return true;

//            int count = Integer.parseInt(System.getProperty("message.count", DEFAULT_MESSAGE_COUNT));
//            String content = System.getProperty("message.content", DEFAULT_MESSAGE);
//
//            log.info("Sending " + count + " messages with content: " + content);
//
//            // Send the specified number of messages
//            for (int i = 0; i < count; i++) {
//                message = session.createTextMessage(content);
//                producer.send(message);
//            }
//
//            // Then receive the same number of messages that were sent
//            for (int i = 0; i < count; i++) {
//                message = (TextMessage) consumer.receive(5000);
//                log.info("Received message with content " + message.getText());
//            }
        } catch (Exception e)
        {
            log.severe(e.getMessage());

        }



//        finally {
//            if (context != null) {
//                context.close();
//            }
//
//            // closing the connection takes care of the session, producer, and consumer
//            if (connection != null) {
//                connection.close();
//            }
//        }

        return false;
    }


    public boolean sendMessage(TestOrder testOrder)
    {
        try
        {
            if(testOrder == null)
            {

                System.out.println("returing from attempt to send null test order");
                return false;
            }
            System.out.println("received message to send to jms queue");

            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(testOrder);

            producer.send(objectMessage);

            System.out.println("message sent + " + testOrder);

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static void printClassPath()
    {

        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        System.out.println(urls);
        for(URL url: urls){
        	System.out.println(url.getFile());
        }
    }

    @Override
    public void onMessage(Message msg)
    {
        System.out.println("New Mesage Recieved .. " + msg);

        if(testOrdersCallback != null)
        {
            testOrdersCallback.onData(msg);
        }
        else
        {
            System.out.println("JMS Message Received at " + LocalDateTime.now() + " plus not call back configured to process it");
        }
    }


    public void setTestOrdersCallback(DataCallback testOrdersCallback)
    {
        this.testOrdersCallback = testOrdersCallback;
    }



    public void sendJmsMessage(LabMessage labMessage)
    {

        if (Strings.isNullOrEmpty(labMessage.getSampleId()))
        {
            System.out.println("Cannot send result with empty sample result");
            return;
        }

        TestOrder testOrder = Store.get().testOrderService().getPackageTestOrder(labMessage.getSampleId());

        if(testOrder == null)
        {
            return;
        }


        for (TestRequest request : testOrder.getRequests())
        {
            request.setAnalyserId("CP23");
//            request.setAnalyserId("Vistros 5,1 FS");
        }


        //temporary check of validation
        boolean hasValidation = true;
        for (TestRequest request : testOrder.getRequests())
        {
            if(request.getValidatedOn() == null)
            {
                hasValidation = false;
            }
        }

        if (hasValidation == false)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Carewex Integrator");
            alert.setHeaderText(null);
            alert.setContentText("The Result is not validated");

            alert.showAndWait();

            return;
        }




//        testOrder.set
        boolean sentToCarewex = sendMessage(testOrder);

        try
        {
            TestOrderImpl testOrderImpl = Store.get().testOrderService().findTestOrderImplBySampleId(labMessage.getSampleId());

            //save validators
            List<TestRequestImpl> testRequestImplsList = Store.get().testOrderService().getTestRequestList(testOrderImpl);
            for (TestRequestImpl testRequestImpl : testRequestImplsList)
            {
                testRequestImpl.setValidatedBy(labMessage.getValidatorName());
                Store.get().crudService().save(testRequestImpl);
            }

            if (sentToCarewex)
            {
                testOrderImpl.setOrderStatus(StatusCode.DISPATCHED);

                List<AstmMessage> astmMessagesList = Store.get().testOrderService().getAstmMessages(labMessage.getSampleId());
                for (AstmMessage astmMessage : astmMessagesList)
                {
                    astmMessage.setStatusCode(StatusCode.DISPATCHED);
                    Store.get().crudService().save(astmMessage);
                }

            } else
            {
                testOrderImpl.setOrderStatus(StatusCode.DISPATCH_FAILD);
            }



            LogManager.instance().saveTestOrder(testOrder);
            System.out.println(testOrderImpl + " ---  updated to -- " + testOrderImpl.getOrderStatus());
            Store.get().crudService().save(testOrderImpl);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}

