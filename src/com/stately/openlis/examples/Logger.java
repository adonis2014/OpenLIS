/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author Edwin
 */
public class Logger
{
    File file = new File("logs.txt");
    
    private static final Logger LOGGER = new Logger();

    public Logger()
    {
        System.out.println(file);
        
    }
    
    public static Logger get()
    {
        return LOGGER;
    }
    
    public void log(String data)
    {
        FileWriter fw = null;
        try
        {
            fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
            bw.close();
        } catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                fw.close();
            } catch (IOException ex)
            {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
