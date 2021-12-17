/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Author;
import entity.Book;
import facade.BookFacade;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListAuthorsComponent;
import gui.components.TabAddAuthorComponent;
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
private CaptionComponent captionComponent;
private InfoComponent infoComponent;
private EditComponent nameBookComponent;
private EditComponent publishedYearComponent;
private EditComponent quantityComponent;
private ButtonComponent buttonComponent;
private ListAuthorsComponent listAuthorsComponent;

    public GuiApp() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(600,400));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        JTabbedPane managerTabbed = new JTabbedPane();
        managerTabbed.setPreferredSize(new Dimension(600,400));
        managerTabbed.setMinimumSize(managerTabbed.getPreferredSize());
        managerTabbed.setMaximumSize(managerTabbed.getPreferredSize());
        this.add(managerTabbed);
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new BoxLayout(addBookPanel, BoxLayout.Y_AXIS));
        addBookPanel.add(Box.createRigidArea(new Dimension(0,25)));
        managerTabbed.addTab("Добавить книгу", addBookPanel);
        TabAddReaderComponent tabAddReaderComponent = new TabAddReaderComponent(this.getWidth());
        managerTabbed.addTab("Добавить читателя", tabAddReaderComponent);
        TabAddAuthorComponent tabAddAuthorComponent = new TabAddAuthorComponent(this.getWidth());
        managerTabbed.addTab("Добавить автора", tabAddAuthorComponent);
        
        captionComponent = new CaptionComponent("Добавление книги в библиотеку", this.getWidth(), 30);
        addBookPanel.add(captionComponent);
        infoComponent = new InfoComponent("", this.getWidth(),30);
        addBookPanel.add(infoComponent);
        addBookPanel.add(Box.createRigidArea(new Dimension(0,10)));
        nameBookComponent = new EditComponent("Название книги:",this.getWidth(), 30, 300);
        addBookPanel.add(nameBookComponent);
        listAuthorsComponent = new ListAuthorsComponent("Авторы:", this.getWidth(), 120, 300);
        addBookPanel.add(listAuthorsComponent);
        publishedYearComponent = new EditComponent("Год издания книги:", this.getWidth(), 30, 100);
        addBookPanel.add(publishedYearComponent);
        quantityComponent = new EditComponent("Количество экземпляров:", this.getWidth(), 30, 50);
        addBookPanel.add(quantityComponent);
        buttonComponent = new ButtonComponent("Добавить книгу", this.getWidth(), 30, 350, 150);
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
                    infoComponent.getInfo().setText("Введите год издания книги (цифрами)");
                    return;
                }
                try {
                    book.setQuantity(Integer.parseInt(quantityComponent.getEditor().getText()));
                    book.setCount(book.getQuantity());
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите количество книг (цифрами)");
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
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }

}
