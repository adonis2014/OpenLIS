/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.jms;

/**
 *
 * @author Edwin
 */
public class JmsConConfig {
    
    private String labResult = "queue/LabTestResults";
    private String labRequest = "queue/LabTestOrders";
    private String jmsBrokerIp = "localhost";
//    private String jmsBrokerIp = "192.168.2.18";
    private String jmsPort = "1099";

    public String getLabResult() {
        return labResult;
    }

    public void setLabResult(String labResult) {
        this.labResult = labResult;
    }

    public String getLabRequest() {
        return labRequest;
    }

    public void setLabRequest(String labRequest) {
        this.labRequest = labRequest;
    }

    public String getJmsBrokerIp() {
        return jmsBrokerIp;
    }

    public void setJmsBrokerIp(String jmsBrokerIp) {
        this.jmsBrokerIp = jmsBrokerIp;
    }

    public String getJmsPort() {
        return jmsPort;
    }

    public void setJmsPort(String jmsPort) {
        this.jmsPort = jmsPort;
    }
    
    
}
