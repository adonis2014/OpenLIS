/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.parsers;

import com.stately.modules.excel.ExcelDataLoader;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edwin
 * @email edwin.amoakwa@gmail.com
 */
public class DictionaryManager 
{
    
    private Map<String,String> map = new LinkedHashMap<>();
    
    private static final DictionaryManager DICTIONARY_MANAGER = new DictionaryManager();
    
    public static final String DATA_FOLDER = "data";
    
    
    public final File VISTROS_ASSAY_DICTIONARY = new File("vistros-dictionary.xls");

    public DictionaryManager()
    {
        try
        {
            
            List<Object[]> objectsList = ExcelDataLoader.read(VISTROS_ASSAY_DICTIONARY.getAbsolutePath(), "AnalyteCode").getWorkBookData();
            
//            StringUtil.printObjectListArray(objectsList);
            
            for (Object[] objects : objectsList)
            {
                String key = com.stately.common.utils.StringUtil.removeTraillingZero(objects[0].toString());
                String value = objects[2].toString();
                
                map.put(key, value);
            }
            
            if(!map.isEmpty())
            {
                System.out.println("Dictionary Loaded Successuflly .... ");
            }
            
//            System.out.println(map);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    
    public static DictionaryManager instance()
    {
        return DICTIONARY_MANAGER;
    }
    
    public String lookupAssayCode(String assayCode)
    {
        return map.get(assayCode);
    }
    
    
}
