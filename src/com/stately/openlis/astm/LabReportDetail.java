/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Edwin
 * @email edwin.amoakwa@gmail.com
 */
public class LabReportDetail
{

    public static final String _no = "no";
    private int no;

    public static final String _patientName = "patientName";
    private String patientName;
    private String patientAddress;
    private String gender;
    private String age;

    public static final String _testName = "testName";
    private String testName;

    public static final String _analyteCode = "analyteCode";
    private String analyteCode;

    public static final String _resultValue = "resultValue";
    private String resultValue;

    public static final String _resultUnit = "resultUnit";
    private String resultUnit = "";

    public static final String _referenceRange = "referenceRange";
    private String referenceRange = "   -   ";

    private Date valueDate = new Date();

    public static final String _sampleId = "sampleId";
    private String sampleId;
    private String fluidType;

    public static final String _patientId = "patientId";
    private String patientId;

    public int getNo()
    {
        return no;
    }

    public void setNo(int no)
    {
        this.no = no;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getTestName()
    {
        return testName;
    }

    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    public String getResultValue()
    {
        return resultValue;
    }

    public void setResultValue(String resultValue)
    {
        this.resultValue = resultValue;
    }

    public String getReferenceRange()
    {
        return referenceRange;
    }

    public void setReferenceRange(String referenceRange)
    {
        this.referenceRange = referenceRange;
    }

    public Date getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(Date valueDate)
    {
        this.valueDate = valueDate;
    }

    public String getPatientAddress()
    {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress)
    {
        this.patientAddress = patientAddress;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }

    public String getFluidType()
    {
        return fluidType;
    }

    public void setFluidType(String fluidType)
    {
        this.fluidType = fluidType;
    }

    public String getPatientId()
    {
        return patientId;
    }

    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
    }

    public String getResultUnit()
    {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit)
    {
        this.resultUnit = resultUnit;
    }

    public String getAnalyteCode()
    {
        return analyteCode;
    }

    public void setAnalyteCode(String analyteCode)
    {
        this.analyteCode = analyteCode;
    }

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 67 * hash + Objects.hashCode(this.patientName);
//        hash = 67 * hash + Objects.hashCode(this.sampleId);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final LabReportDetail other = (LabReportDetail) obj;
//        if (!Objects.equals(this.patientName, other.patientName)) {
//            return false;
//        }
//        if (!Objects.equals(this.sampleId, other.sampleId)) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString()
    {
        return "LabReport{" + "no=" + no + ", patientName=" + patientName + ", patientAddress=" + patientAddress + ", gender=" + gender + ", age=" + age + ", assayName=" + testName + ", result=" + resultValue + ", referenceRange=" + referenceRange + ", valueDate=" + valueDate + ", sampleId=" + sampleId + ", fluidType=" + fluidType + ", patientId=" + patientId + '}';
    }

}
