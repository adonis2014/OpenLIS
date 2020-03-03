/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.examples;

import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.parsers.AstmParser;
import com.stately.openlis.parsers.Samples;

/**
 *
 * @author Edwin
 */
public class SampleParser
{
    public static void main(String[] args)
    {
        LabMessage message = new AstmParser().parseAstm(Samples.msg);
        
        System.out.println(message);
    }
}
