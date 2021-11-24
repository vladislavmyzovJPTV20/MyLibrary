/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.History;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author pupil
 */
public class HistoryFacade extends AbstractFacade<History>{
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyLibraryPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
    
    public List<History> findWithGivenBooks() {
        return em.createQuery(null)
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
