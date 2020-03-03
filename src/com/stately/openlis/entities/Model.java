/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public class Model
{
    @Id
    @Column(name = "id")
    private String id;
    
    public static final String _createdOn = "createdOn";
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "created_on")
    private Date createdOn = new Date();

    public static final String _recievedTime = "recievedTime";    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "received_time")
    private Date recievedTime = new Date();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn)
    {
        this.createdOn = createdOn;
    }

    public Date getRecievedTime()
    {
        return recievedTime;
    }

    public void setRecievedTime(Date recievedTime)
    {
        this.recievedTime = recievedTime;
    }
    
    
}
