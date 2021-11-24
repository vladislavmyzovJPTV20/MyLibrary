/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.History;
import java.util.ArrayList;
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
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV20MyLibraryPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public HistoryFacade(Class<History> entityClass) {
        super(entityClass);
    }

    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public List<History> findWithGivenBooks() {
        try {
            return em.createQuery(
                        "SELECT history FROM History history WHERE history.returnedDate = null AND history.book.count < history.book.quantity")
                     .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    
}
