/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.jms;

import com.stately.openlis.model.TestRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edwin
 */
  @XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestOrderWrapper implements Serializable
{
      
    @XmlElementWrapper(name = "tests")
    @XmlElement(name = "test")
    private List<TestRequest> requests = new ArrayList<>(); 
    
}
