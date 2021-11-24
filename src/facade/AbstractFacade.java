/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author pupil
 */
public abstract class AbstractFacade<T> {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyLibraryPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
    
    private Class<T> entityClass;
    
    public AbstractFacade(Class<T> entityClass) {
        
    }
    
    protected abstract EntityManager getEntityManager();
    
    public void create(T entity) {
        getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }
    
    public T find(Long id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll() {
        return getEntityManager().createQuery("SELECT entity FROM "+entityClass.getName()+" entity").getResultList();
    }
    
    public void edit(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit();
    }
}
