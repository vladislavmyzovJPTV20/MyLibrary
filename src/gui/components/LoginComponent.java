/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.User;
import facade.UserFacade;
import facade.UserRolesFacade;
import gui.GuiApp;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Melnikov
 */
public class LoginComponent extends JFrame{
    private UserFacade userFacade;
    private UserRolesFacade userRolesFacade;

    public LoginComponent() {
        userFacade = new UserFacade();
        userRolesFacade = new UserRolesFacade();
        initComponents();
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(300,200));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
    //    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JDialog dialogLogin = new JDialog(this,"Авторизация",Dialog.ModalityType.DOCUMENT_MODAL);
        dialogLogin.setPreferredSize(new Dimension(300,200));
        dialogLogin.setMaximumSize(dialogLogin.getPreferredSize());
        dialogLogin.setMinimumSize(dialogLogin.getPreferredSize());
        dialogLogin.getContentPane().setLayout(new BoxLayout(dialogLogin.getContentPane(), BoxLayout.Y_AXIS));
        dialogLogin.setLocationRelativeTo(null);
        CaptionComponent captionComponent = new CaptionComponent("Введите логи и пароль", 300, 27);
        InfoComponent infoComponent = new InfoComponent("", 300, 27);
        EditComponent loginComponent = new EditComponent("Логин", 300, 27, 150);
        EditComponent passwordComponent = new EditComponent("Пароль", 300, 27, 150);
        ButtonComponent enterComponent = new ButtonComponent("Войти", 300, 27, 105, 100);
        enterComponent.getButton().addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String login = loginComponent.getEditor().getText();
            String password = passwordComponent.getEditor().getText();
            if("".equals(login) || password.isEmpty()){
              infoComponent.getInfo().setText("Логин и пароль не должны быть пустыми");
              return;
            }
            User user = userFacade.find(login);
            if(user == null){
              infoComponent.getInfo().setText("Нет такого пользователя");
              return;
            }
            if(!password.equals(user.getPassword())){
              infoComponent.getInfo().setText("Логин или пароль неверные");
              return;
            }
            GuiApp.user = user;
            GuiApp.role = userRolesFacade.getTopRole(user);
            dialogLogin.setVisible(false);
          }

        });
        dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0, 5)));
        dialogLogin.getContentPane().add(captionComponent);
        dialogLogin.getContentPane().add(infoComponent);
        dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0, 5)));
        dialogLogin.getContentPane().add(loginComponent);
        dialogLogin.getContentPane().add(passwordComponent);
        dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        dialogLogin.getContentPane().add(enterComponent);

        dialogLogin.pack();
        dialogLogin.setVisible(true);
    }
    
    
}
