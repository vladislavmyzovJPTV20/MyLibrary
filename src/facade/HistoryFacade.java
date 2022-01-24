/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.History;
import entity.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author pupil
 */
public class HistoryFacade extends AbstractFacade<History>{
    private EntityManager em;

    public HistoryFacade(Class<History> entityClass) {
        super(entityClass);
        init();
    }

    private void init() {
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
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

    public List<History> find(Reader reader) {
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.reader=:reader AND h.returnedDate = null")
                    .setParameter("reader", reader)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
