/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import java.util.Date;

/**
 *
 * @author Edwin
 */
public class OrderRecord extends Record
{    
    private String specimenId;
    private String universalTestId;
    private String priority;
    private Date specimenCollectDateTime;
    private String actionCode;
    private String specimenDescriptor;
    private String reportTypes;

    public String getSpecimenId()
    {
        return specimenId;
    }

    public void setSpecimenId(String specimenId)
    {
        this.specimenId = specimenId;
    }

    public String getUniversalTestId()
    {
        return universalTestId;
    }

    public void setUniversalTestId(String universalTestId)
    {
        this.universalTestId = universalTestId;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public Date getSpecimenCollectDateTime()
    {
        return specimenCollectDateTime;
    }

    public void setSpecimenCollectDateTime(Date specimenCollectDateTime)
    {
        this.specimenCollectDateTime = specimenCollectDateTime;
    }

    public String getActionCode()
    {
        return actionCode;
    }

    public void setActionCode(String actionCode)
    {
        this.actionCode = actionCode;
    }

    public String getSpecimenDescriptor()
    {
        return specimenDescriptor;
    }

    public void setSpecimenDescriptor(String specimenDescriptor)
    {
        this.specimenDescriptor = specimenDescriptor;
    }

    public String getReportTypes()
    {
        return reportTypes;
    }

    public void setReportTypes(String reportTypes)
    {
        this.reportTypes = reportTypes;
    }

    @Override
    public String toString()
    {
        return "OrderRecord{" + "specimenId=" + specimenId + ", universalTestId=" + universalTestId + ", priority=" + priority + ", specimenCollectDateTime=" + specimenCollectDateTime + ", actionCode=" + actionCode + ", specimenDescriptor=" + specimenDescriptor + ", reportTypes=" + reportTypes + '}';
    }
    
    
    
}
