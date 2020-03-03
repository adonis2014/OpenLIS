/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.parsers;

import com.stately.openlis.model.TestOrder;
import com.stately.openlis.model.TestRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Edwin
 * @email edwin.amoakwa@gmail.com
 */
public class LogManager
{

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");

    private static final LogManager LOG_MANAGER = new LogManager();

    public static final String DATA_FOLDER = "data";

    public final Date CURRENT_DATE = new Date();

    public String date = SIMPLE_DATE_FORMAT.format(CURRENT_DATE);

    public final String data = "data";

    public final File LOG_DIR = new File("data" + File.separator + date);
    public final File testOrdersFolder = new File("data" + File.separator + "orders");

    public final File SERIAL_RAWLOG = new File(LOG_DIR, "raw" + date + ".txt");

    public final File FULL_MESSAGE_LOG = new File(LOG_DIR, "fullmessage" + date + ".txt");
    public final File SYSTEM_LOG = new File(LOG_DIR, "system_log" + date + ".txt");

    public final File testOrders = new File(data, "testOrders.xml");
//    public final File testOrders = new File(data , "testOrders.xml");

    public LogManager()
    {
        try
        {
            System.out.println("LOG_DIR : " + LOG_DIR.getAbsolutePath());
            System.out.println("RAWLOG : " + SERIAL_RAWLOG.getAbsolutePath());
            System.out.println("FORMATTEEDLOG : " + FULL_MESSAGE_LOG.getAbsolutePath());

            LOG_DIR.mkdirs();
            SERIAL_RAWLOG.createNewFile();
            SYSTEM_LOG.createNewFile();
            FULL_MESSAGE_LOG.createNewFile();
            testOrdersFolder.mkdirs();

            System.out.println("Log Files for " + CURRENT_DATE + " Initialised");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static LogManager instance()
    {
        return LOG_MANAGER;
    }

    public void initialiseWriters()
    {

    }

    public void writefullMessage(String data)
    {
        if (data == null)
        {
            return;
        }

        FileWriter fw = null;
        try
        {
            fw = new FileWriter(FULL_MESSAGE_LOG.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
//            bw.newLine();
            bw.close();
        } catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                fw.close();
            } catch (IOException ex)
            {
                java.util.logging.Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void rawlog(String data)
    {
        if (data == null)
        {
            return;
        }

        FileWriter fw = null;
        try
        {
            fw = new FileWriter(SERIAL_RAWLOG.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
//            bw.newLine();
            bw.close();
        } catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                fw.close();
            } catch (IOException ex)
            {
                java.util.logging.Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveTestOrder(TestOrder testOrder)
    {
        try
        {

            String fileName = createOrderFile(testOrder);

            File file = new File(testOrdersFolder, fileName + ".xml");
            boolean fileCreated = file.createNewFile();
            System.out.println("file created ... " + fileCreated + "  ..  " + file.getAbsolutePath());

            FileWriter fileWriter = new FileWriter(file, false);

            JAXBContext jaxbContext = JAXBContext.newInstance(TestOrder.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(testOrder, fileWriter);
            jaxbMarshaller.marshal(testOrder, System.out);
//                jaxbMarshaller.

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String createOrderFile(TestOrder testOrder)
    {
        String fileName = "";

        Set<String> sampleIdsList = new LinkedHashSet<>();

        //get all uninque speciment ID, by putting them in a set
        for (TestRequest request : testOrder.getRequests())
        {
            sampleIdsList.add(request.getSpecimenId());
        }

//        for (String specimentId : sampleIdsList)
//        {
//            fileName += specimentId + "_";
//        }
        fileName = String.join("_", sampleIdsList);

        return fileName;
    }

    public TestOrder getTestOrder(String sampleId)
    {
        try
        {
            if (sampleId == null)
            {
                return null;
            }

            File[] filesList = testOrdersFolder.listFiles();

            for (File file : filesList)
            {
                System.out.println(file);
                if (file.getAbsolutePath().contains(sampleId))
                {
                    JAXBContext jaxbContext = JAXBContext.newInstance(TestOrder.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    //We had written this file in marshalling example
                    TestOrder fetchedTestOrder = (TestOrder) jaxbUnmarshaller.unmarshal(file);

                    return fetchedTestOrder;
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<TestOrder> getTestOrderList()
    {
        List<TestOrder> testOrdersList = new LinkedList<>();
        try
        {

            File[] filesList = testOrdersFolder.listFiles();

            for (File file : filesList)
            {
                JAXBContext jaxbContext = JAXBContext.newInstance(TestOrder.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                //We had written this file in marshalling example
                TestOrder fetchedTestOrder = (TestOrder) jaxbUnmarshaller.unmarshal(file);

                testOrdersList.add(fetchedTestOrder);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return testOrdersList;
    }

}
