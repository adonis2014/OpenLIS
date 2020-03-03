package com.stately.openlis.examples;


import com.stately.openlis.jms.JmsConConfig;
import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.logging.Logger;


public class JMSIntegrator_temp implements MessageListener
{

    private static final Logger logger = Logger.getLogger(JMSIntegrator_temp.class.getName());

    private JmsConConfig conConfig;

    public JMSIntegrator_temp(JmsConConfig conConfig) 
    {
        this.conConfig = conConfig;
    }
    
    
    
    private MessageProducer producer;
    private MessageConsumer consumer;

    
    public void init(JmsConConfig conConfig)
    {
        this.conConfig = conConfig;
        init();
    }

    
    
    public void init()
    {
        try
        {
//            logger.info("*****************************************");
//            logger.info(" ** initiating connection to JMS Broker **");
//            logger.info("*****************************************");
//            InitialContext initialContext = new InitialContext(getProperties());
//            logger.info("initial context successful");
//
////            ConnectionFactory connectionFactory = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");
//            ConnectionFactory connectionFactory = (ConnectionFactory)initialContext.lookup("jms/queue/LabTestResultsFactory");
//            System.out.println("lookup successful");
//            Connection connection = connectionFactory.createConnection();
//
//            Queue ordersQueue = (Queue)initialContext.lookup(conConfig.getLabResult());
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            consumer = session.createConsumer(ordersQueue);
//            
//
//            System.out.println("connection successful");
//            logger.info("Jms Connection factory and resources created without errors");
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
        
        String jnp = "jnp://"+conConfig.getJmsBrokerIp()+":1099";
        
        Properties props = new Properties();
        props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
//        props.put("java.naming.provider.url", "jnp://localhost:1099");
        props.put("java.naming.provider.url", jnp);
        props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

        return props;
    }

    

    @Override
    public void onMessage(Message message)
    {
        try
        {
            if(message instanceof TextMessage)
            {
                String msg = ((TextMessage) message).getText();
                System.out.println("Message Received from Hims: " + msg);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
