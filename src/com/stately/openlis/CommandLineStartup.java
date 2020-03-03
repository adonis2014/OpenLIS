/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis;

import com.stately.openlis.astm.MessageReceiver;
import com.stately.openlis.parsers.LogManager;
import com.stately.openlis.services.Store;
import com.stately.openlis.model.TestOrder;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class CommandLineStartup 
{

    public static void main(String[] args) throws IOException
    {
        System.out.println("Runnung Commmand line ...");
        Store store = Store.get();
        
        TestOrder testOrder = LogManager.instance().getTestOrder("0009396");
        
        store.testOrderService().saveOrder(testOrder);
        
    }

    public static void sampleDave() throws IOException
    {
        System.out.println("Runnung Commmand line ...");
        Store store = Store.get();
        
//        TestOrder testOrder = LogManager.instance().getTestOrder("0009396");
        
        List<TestOrder> testOrdersList = LogManager.instance().getTestOrderList();
        for (TestOrder testOrder : testOrdersList)
        {
            store.testOrderService().saveOrder(testOrder);
        }
        
        
        
    }

    public  void maind()
    {
        
        
//        ClassLoader cl = ClassLoader.getSystemClassLoader();
//        URL[] urls = ((URLClassLoader)cl).getURLs();
//        System.out.println(urls);
//        for(URL url: urls){
//        	System.out.println(url.getFile());
//        }
        
//        ReportGenerator reportGenerator = new ReportGenerator();
//            reportGenerator.generateSampleLabReport();
        
        
        MessageReceiver messagReceiver = new MessageReceiver();
        
        String fileName = "data" + File.separator + "Logs27012016.txt";
//            
//            File file = new File(fileName);
//            System.out.println(file.getPath());
            
//            List<String> fileLines = Files.readLines(new File(fileName), Charset.defaultCharset());
//            System.out.println(fileLines);
//            
//            for (String fileLine : fileLines)
//            {
//               messagReceiver.processNewMessage(fileLine);
//                
//            }
        
    }
    
    
}
