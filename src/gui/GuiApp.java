/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Author;
import entity.Book;
import entity.Reader;
import entity.Role;
import entity.User;
import entity.UserRoles;
import facade.BookFacade;
import facade.ReaderFacade;
import facade.RoleFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListAuthorsComponent;
import gui.components.ListBooksComponent;
import gui.components.TabAddAuthorComponent;
import gui.components.TabAddBookComponent;
import gui.components.TabAddReaderComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
    public GuiApp guiApp = this;
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameBookComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ButtonComponent buttonChangePanelComponent;
    private ListAuthorsComponent listAuthorsComponent;
    private ListBooksComponent listBooksComponent;
    private ReaderFacade readerFacade = new ReaderFacade(Reader.class);
    private UserFacade userFacade = new UserFacade();
    private RoleFacade roleFacade = new RoleFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    
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
        JPanel guestPanel = new JPanel();
        listBooksComponent = new ListBooksComponent(false, "Список книг библиотеки", GuiApp.HEIGHT_WINDOW, GuiApp.HEIGHT_WINDOW - 100, GuiApp.WIDTH_WINDOW);
        guestPanel.add(Box.createRigidArea(new Dimension(0,10)));
        guestPanel.add(listBooksComponent);
        
        buttonChangePanelComponent = new ButtonComponent("К выбору книг", 50, 470, 200);
        guestPanel.add(buttonChangePanelComponent);
        buttonChangePanelComponent.getButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                guiApp.getContentPane().removeAll();
                JTabbedPane managerTabbed = new JTabbedPane();
                managerTabbed.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
                managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
                managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
                guiApp.add(managerTabbed);
                JPanel addBookPanel = new JPanel();
                managerTabbed.addTab("Добавить книгу", addBookPanel);
                addBookPanel.setLayout(new BoxLayout(addBookPanel, BoxLayout.Y_AXIS));
                addBookPanel.add(Box.createRigidArea(new Dimension(0,25)));
                captionComponent = new CaptionComponent("Добавление книги в библиотеку", guiApp.getWidth(), 30);
                addBookPanel.add(captionComponent);
                infoComponent = new InfoComponent("", guiApp.getWidth(),27);
                addBookPanel.add(infoComponent);
                addBookPanel.add(Box.createRigidArea(new Dimension(0,10)));
                nameBookComponent = new EditComponent("Название книги",240, 30, 300);
                addBookPanel.add(nameBookComponent);
                listAuthorsComponent = new ListAuthorsComponent("Авторы", 240, 120, 300);
                addBookPanel.add(listAuthorsComponent);
                publishedYearComponent = new EditComponent("Год изания книги", 240, 30, 100);
                addBookPanel.add(publishedYearComponent);
                quantityComponent = new EditComponent("Колличество экземпляров", 240, 30, 50);
                addBookPanel.add(quantityComponent);
                buttonComponent = new ButtonComponent("Добавть книгу", 30, 350, 150);
                addBookPanel.add(buttonComponent);
                buttonComponent.getButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Book book = new Book();
                        if(nameBookComponent.getEditor().getText().isEmpty()){
                            infoComponent.getInfo().setForeground(Color.red);
                            infoComponent.getInfo().setText("Введите название книги");
                            return;
                        }
                        book.setBookName(nameBookComponent.getEditor().getText());

                        List<Author> authorsBook = listAuthorsComponent.getList().getSelectedValuesList();
                        if(authorsBook.isEmpty()){
                            infoComponent.getInfo().setForeground(Color.red);
                            infoComponent.getInfo().setText("Выберите авторов книги");
                            return;
                        }
                        book.setAuthor(authorsBook);
                        try {
                            book.setPublishedYear(Integer.parseInt(publishedYearComponent.getEditor().getText()));
                        } catch (Exception ex) {
                            infoComponent.getInfo().setForeground(Color.red);
                            infoComponent.getInfo().setText("Введите год издания книги цифрами");
                            return;
                        }
                        try {
                            book.setQuantity(Integer.parseInt(quantityComponent.getEditor().getText()));
                            book.setCount(book.getQuantity());
                        } catch (Exception ex) {
                            infoComponent.getInfo().setForeground(Color.red);
                            infoComponent.getInfo().setText("Введите количество книг цифрами");
                            return;
                        }
                        BookFacade bookFacade = new BookFacade(Book.class);
                        try {
                            bookFacade.create(book);
                            infoComponent.getInfo().setForeground(Color.BLUE);
                            infoComponent.getInfo().setText("Книга успешно добавлена");
                            nameBookComponent.getEditor().setText("");
                            publishedYearComponent.getEditor().setText("");
                            quantityComponent.getEditor().setText("");
                            listAuthorsComponent.getList().clearSelection();
                        } catch (Exception ex) {
                            infoComponent.getInfo().setForeground(Color.RED);
                            infoComponent.getInfo().setText("Книгу добавить не удалось");
                        }

                    }
                });
            }
        });
        
        this.add(guestPanel);
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
        reader.setPhone("58664899");
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
