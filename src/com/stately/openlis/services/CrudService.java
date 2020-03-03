package com.stately.openlis.services;

import com.stately.modules.jpa2.CrudController;
import com.stately.modules.jpa2.Enviroment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class CrudService extends CrudController
{
    private  EntityManagerFactory emf = null;
    private  EntityManager em = null;

    public CrudService()
    {
        try
        {
            //        emf = Persistence.createEntityManagerFactory("himsPU");
            
        emf = Persistence.createEntityManagerFactory("CarewexIntegratorPU");
        em = emf.createEntityManager();
        
        setEm(em);
        setEnviroment(Enviroment.JAVA_SE);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static final CrudService crudService = new CrudService();
    public static CrudService instance()
    {
        return crudService;
    }

    
    
}
