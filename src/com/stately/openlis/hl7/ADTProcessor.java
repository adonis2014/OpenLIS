/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.hl7;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.datatype.PN;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.parser.Parser;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public class ADTProcessor 
{
             
    public void process(String msg)
    {
        HapiContext context = new DefaultHapiContext();
        Parser parser = context.getGenericParser();
        
        Message hl7Message = null;
        try
        {
            hl7Message = parser.parse(msg);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        ADT_A01 adtMessage = (ADT_A01) hl7Message;
        
        MSH msh = adtMessage.getMSH();
        
//        PN pn = adtMessage.getPID().
//        String msgType = msh.getMessageType().g
    }
}
