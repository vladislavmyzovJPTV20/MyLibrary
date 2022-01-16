/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Reader;
import entity.Role;
import entity.User;
import entity.UserRoles;
import facade.ReaderFacade;
import facade.RoleFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.GuestComponent;
import gui.components.InfoComponent;
import gui.components.reader.ReaderComponent;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



public class GuiApp extends JFrame{
    public static final int WIDTH_WINDOW = 700;
    public static final int HEIGHT_WINDOW = 450;
    private ButtonComponent buttonChangePanelComponent;
    private InfoComponent infoTopComponent;
    private ReaderComponent readerComponent;
    public GuiApp guiApp = this;
    public static User user;
    public static String role;
    private UserFacade userFacade = new UserFacade();
    private RoleFacade roleFacade = new RoleFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    private ReaderFacade readerFacade = new ReaderFacade(Reader.class);
    private JPanel guestPanel;
    
    public GuiApp() {
        superAdmin();
        setTitle("Библиотека группы JPTV20");
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        guestPanel = new GuestComponent(WIDTH_WINDOW,HEIGHT_WINDOW);
        buttonChangePanelComponent = new ButtonComponent("Войти", 50, 470, 200);
        guestPanel.add(buttonChangePanelComponent);
        this.add(guestPanel);
        buttonChangePanelComponent.getButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              //Аутентификация
                int widthWin = 350;
                int heightWin = 260;
                JDialog dialogLogin = new JDialog(guiApp,"Введите логин и пароль",Dialog.ModalityType.DOCUMENT_MODAL);
                dialogLogin.setPreferredSize(new Dimension(widthWin,heightWin));
                dialogLogin.setMaximumSize(dialogLogin.getPreferredSize());
                dialogLogin.setMinimumSize(dialogLogin.getPreferredSize());
                dialogLogin.getContentPane().setLayout(new BoxLayout(dialogLogin.getContentPane(), BoxLayout.Y_AXIS));
                dialogLogin.setLocationRelativeTo(null);
                CaptionComponent captionComponent = new CaptionComponent("Введите логин и пароль", widthWin, 27);
                InfoComponent infoComponent = new InfoComponent("", widthWin, 27);
                EditComponent loginComponent = new EditComponent("Логин",200, 27, 120);
                EditComponent passwordComponent = new EditComponent("Пароль", 200, 27, 120);
                ButtonComponent enterComponent = new ButtonComponent("Войти", 27,180, 100);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
                dialogLogin.getContentPane().add(captionComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(infoComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(loginComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,5)));
                dialogLogin.getContentPane().add(passwordComponent);
                dialogLogin.getContentPane().add(Box.createRigidArea(new Dimension(0,15)));
                dialogLogin.getContentPane().add(enterComponent);
                enterComponent.getButton().addActionListener( new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Аутентификация - узнать есть ли такой пользователь
                        User user = userFacade.find(loginComponent.getEditor().getText().trim());
                        if(user == null){
                            infoComponent.getInfo().setText("Нет такого пользователя");
                            return;
                        }
                        //Авторизация - он ли это пользователь и какие у него права.
                        if(!user.getPassword().equals(passwordComponent.getEditor().getText().trim())){
                            infoComponent.getInfo().setText("Нет такого пользователя, или неверный пароль");
                            return;
                        } 
                        GuiApp.user = user;
                        //Пользователь тот за кого себя выдает, устанавливаем разрешения.
                        String role = userRolesFacade.getTopRole(user);
                        GuiApp.role = role;
                        infoTopComponent.getInfo().setText("Hello "+user.getReader().getFirstname());
                        guiApp.getContentPane().remove(guestPanel);
                        guiApp.getContentPane().remove(buttonChangePanelComponent);
                        JTabbedPane jTabbedPane = new JTabbedPane();
                        jTabbedPane.setPreferredSize(new Dimension(WIDTH_WINDOW,HEIGHT_WINDOW));
                        jTabbedPane.setMinimumSize(jTabbedPane.getPreferredSize());
                        jTabbedPane.setMaximumSize(jTabbedPane.getPreferredSize());
                        if("READER".equals(GuiApp.role)){
                            readerComponent = new ReaderComponent(WIDTH_WINDOW,HEIGHT_WINDOW);
                            jTabbedPane.addTab("Читатель", readerComponent);
                        }
                        guiApp.getContentPane().add(jTabbedPane);
                        guiApp.repaint();
                        guiApp.revalidate();
                        dialogLogin.setVisible(false);
                        dialogLogin.dispose();
                    }
                    
                });
                dialogLogin.pack();
                dialogLogin.setVisible(true);
//            guiApp.getContentPane().removeAll();
//            JTabbedPane managerTabbed = new JTabbedPane();
//            managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
//            managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
//            managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
//            guiApp.add(managerTabbed);
//            TabAddBookComponent tabAddBookComponent = new TabAddBookComponent(guiApp.getWidth());
//            managerTabbed.addTab("Добавить книгу", tabAddBookComponent);
//            TabAddReaderComponent tabAddReaderComponent = new TabAddReaderComponent(guiApp.getWidth());
//            managerTabbed.addTab("Добавить читателя", tabAddReaderComponent);
//            TabAddAuthorComponent tabAddAuthorComponent = new TabAddAuthorComponent(guiApp.getWidth());
//            managerTabbed.addTab("Добавить автора", tabAddAuthorComponent);              
            }
        });
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }

    private void superAdmin() {
        List<User> users = userFacade.findAll();
        if(!users.isEmpty()) return;
        Reader reader = new Reader();
        reader.setFirstname("Vladislav");
        reader.setLastname("Myzov");
        reader.setPhone("23132132132");
        readerFacade.create(reader);
        
        User user = new User();
        user.setLogin("admin");
        user.setPassword("12345");
        user.setReader(reader);
        userFacade.create(user);
        
        Role role = new Role();
        role.setRoleName("ADMINISTRATOR");
        roleFacade.create(role);
        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        userRolesFacade.create(userRoles);
        
        role = new Role();
        role.setRoleName("MANAGER");
        roleFacade.create(role);
        userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        userRolesFacade.create(userRoles);
        
        role = new Role();
        role.setRoleName("READER");
        roleFacade.create(role);
        userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(role);
        userRolesFacade.create(userRoles);
    }

}
