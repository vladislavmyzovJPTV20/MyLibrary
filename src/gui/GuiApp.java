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
import gui.components.ListBooksComponent;
import gui.components.TabAddAuthorComponent;
import gui.components.TabAddBookComponent;
import gui.components.TabAddReaderComponent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Melnikov
 */
public class GuiApp extends JFrame{
    public static final int WIDTH_WINDOW = 700;
    public static final int HEIGHT_WINDOW = 450;
    private ButtonComponent buttonChangePanelComponent;
    private ListBooksComponent listBooksComponent;
    public GuiApp guiApp = this;
    public static User user;
    public static Role role;
    private UserFacade userFacade = new UserFacade();
    private RoleFacade roleFacade = new RoleFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    private ReaderFacade readerFacade = new ReaderFacade(Reader.class);
    
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
        JTabbedPane managerTabbed = new JTabbedPane();
        managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
        managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
        managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
        this.add(managerTabbed);
        TabAddBookComponent tabAddBookComponent = new TabAddBookComponent(this.getWidth());
        managerTabbed.addTab("Добавить книгу", tabAddBookComponent);
        TabAddReaderComponent tabAddReaderComponent = new TabAddReaderComponent(this.getWidth());
        managerTabbed.addTab("Добавить читателя", tabAddReaderComponent);
        TabAddAuthorComponent tabAddAuthorComponent = new TabAddAuthorComponent(this.getWidth());
        managerTabbed.addTab("Добавить автора", tabAddAuthorComponent);
        JPanel guestPanel = new JPanel();
        listBooksComponent = new ListBooksComponent(false, "Список книг библиотеки", GuiApp.HEIGHT_WINDOW, GuiApp.HEIGHT_WINDOW - 100, GuiApp.WIDTH_WINDOW);
        guestPanel.add(Box.createRigidArea(new Dimension(0,10)));
        guestPanel.add(listBooksComponent);
        guestPanel.add(Box.createRigidArea(new Dimension(0,10)));
        buttonChangePanelComponent = new ButtonComponent("К Выбору книг", 50, 470, 200);
        guestPanel.add(buttonChangePanelComponent);
        this.add(guestPanel);
        buttonChangePanelComponent.getButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            guiApp.getContentPane().removeAll();
            JTabbedPane managerTabbed = new JTabbedPane();
            managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
            managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
            managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
            guiApp.add(managerTabbed);
            TabAddBookComponent tabAddBookComponent = new TabAddBookComponent(guiApp.getWidth());
            managerTabbed.addTab("Добавить книгу", tabAddBookComponent);
            TabAddReaderComponent tabAddReaderComponent = new TabAddReaderComponent(guiApp.getWidth());
            managerTabbed.addTab("Добавить читателя", tabAddReaderComponent);
            TabAddAuthorComponent tabAddAuthorComponent = new TabAddAuthorComponent(guiApp.getWidth());
            managerTabbed.addTab("Добавить автора", tabAddAuthorComponent);              
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
