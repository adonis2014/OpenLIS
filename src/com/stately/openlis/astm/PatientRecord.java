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
public class PatientRecord extends Record
{    
    private String practiceAssignPatientId; //3
    private String patientName; // 6
    private Date birthDate;
    private String patientSex;
    private String patientAddress;
    private String attendingPhysician;
    private String location; // 10 Patient Room Number
    
    
    public String getPracticeAssignPatientId()
    {
        return practiceAssignPatientId;
    }

    public void setPracticeAssignPatientId(String practiceAssignPatientId)
    {
        this.practiceAssignPatientId = practiceAssignPatientId;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getPatientSex()
    {
        return patientSex;
    }

    public void setPatientSex(String patientSex)
    {
        this.patientSex = patientSex;
    }

    public String getPatientAddress()
    {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress)
    {
        this.patientAddress = patientAddress;
    }

    public String getAttendingPhysician()
    {
        return attendingPhysician;
    }

    public void setAttendingPhysician(String attendingPhysician)
    {
        this.attendingPhysician = attendingPhysician;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    
    
    @Override
    public String toString()
    {
        return "PatientRecord{" + "practiceAssignPatientId=" + practiceAssignPatientId + ", patientName=" + patientName + ", birthDate=" + birthDate + ", patientSex=" + patientSex + ", patientAddress=" + patientAddress + ", attendingPhysician=" + attendingPhysician + ", location=" + location + '}';
    }

    
    
    
}
