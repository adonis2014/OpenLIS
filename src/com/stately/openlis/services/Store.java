/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.services;

import com.stately.modules.jpa2.UniqueEntityModel2;
import java.util.UUID;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public class Store {

    private static final CrudService CRUD_SERVICE = new CrudService();
    
    private static final Store STORE = new Store();
    
    private TestOrderService testOrderService;
    
    public String getId()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Store()
    {
        testOrderService = new TestOrderService(CRUD_SERVICE);
    }

    public TestOrderService testOrderService()
    {
        return testOrderService;
    }
    
    
    
    public static Store get()
    {
        return STORE;
    }
    
    public boolean save(UniqueEntityModel2 model)
    {
//        System.out.println("if ... "+model.genId());
//        if(Strings.isNullOrEmpty(model.genId() + ""))
        if(model.getId() == null)
        {
            model.setId(UUID.randomUUID().toString());            
        }
        
        if(CRUD_SERVICE.save(model) != null)
        {
            return true;
        }
        
        return false;
    }

    public CrudService crudService()
    {
        return CRUD_SERVICE;
    }
}
