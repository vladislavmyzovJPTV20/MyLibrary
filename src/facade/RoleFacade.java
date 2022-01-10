/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Author;
import entity.Role;
import entity.User;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author pupil
 */
public class RoleFacade extends AbstractFacade<Role>{
    private EntityManager em;

    public RoleFacade() {
        super(Role.class);
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
    
}
