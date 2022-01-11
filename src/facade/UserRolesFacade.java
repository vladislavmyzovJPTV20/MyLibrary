/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Role;
import entity.User;
import entity.UserRoles;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author Melnikov
 */
public class UserRolesFacade extends AbstractFacade<UserRoles>{
   
    private EntityManager em;
    
    public UserRolesFacade() {
        super(UserRoles.class);
        init();
    }

    private void init(){
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Role getTopRole(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}