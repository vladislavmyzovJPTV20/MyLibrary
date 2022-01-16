/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Role;
import entity.User;
import entity.UserRoles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import tools.Singleton;


public class UserRolesFacade extends AbstractFacade<UserRoles>{
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UserRolesFacade() {
        super(UserRoles.class);
        init();
    }
    private void init(){
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }
    
    public String getTopRole(User user) {
        try {
            List<String> roles = em.createQuery("SELECT ur.role.roleName FROM UserRoles ur WHERE ur.user = :user")
                    .setParameter("user", user)
                    .getResultList();
            if(roles.contains("ADMINISTRATOR")){
                return "ADMINISTRATOR";
            }else if(roles.contains("MANAGER")){
                return "MANAGER";
            }else if(roles.contains("READER")){
                return "READER";
            }else{
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}