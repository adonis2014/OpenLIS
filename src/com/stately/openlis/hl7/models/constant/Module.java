/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.hl7.models.constant;

import com.stately.common.api.MessageResolvable;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum Module implements MessageResolvable
{

    DATA_TRANSFER("DATA_TRANSFER","Data Transfers"),
    CONFIGURATIONS("CONFIGURATIONS","Configurations");
    
    
    private final String code;
    private final String label;

    private Module(String code, String label)
    {
        this.code = code;
        this.label = label;
    }
    
    


    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
    
}
