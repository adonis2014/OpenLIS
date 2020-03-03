/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

/**
 *
 * @author Edwin
 */
public class TerminatorRecord extends Record
{
    private String terminatorCode;

    public TerminatorRecord()
    {
        setRecordType(RecordType.L);
    }
    
    

    public String getTerminatorCode()
    {
        return terminatorCode;
    }

    public void setTerminatorCode(String terminatorCode)
    {
        this.terminatorCode = terminatorCode;
    }
    
}
