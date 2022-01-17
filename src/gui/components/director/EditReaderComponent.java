/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.director;

import gui.components.reader.*;
import gui.components.*;
import entity.Author;
import entity.Book;
import facade.BookFacade;
import gui.GuiApp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class EditReaderComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameBookComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ListAuthorsComponent listAuthorsComponent;
    
    public EditReaderComponent() {
        initComponents();
    }    

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление книги в библиотеку", GuiApp.WIDTH_WINDOW, 30);
        this.add(captionComponent);
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW,30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameBookComponent = new EditComponent("Название книги:",GuiApp.WIDTH_WINDOW, 30, 300);
        this.add(nameBookComponent);
        listAuthorsComponent = new ListAuthorsComponent("Авторы:", GuiApp.WIDTH_WINDOW, 120, 300);
        this.add(listAuthorsComponent);
        publishedYearComponent = new EditComponent("Год издания книги:", GuiApp.WIDTH_WINDOW, 30, 100);
        this.add(publishedYearComponent);
        quantityComponent = new EditComponent("Количество экземпляров:", GuiApp.WIDTH_WINDOW, 30, 50);
        this.add(quantityComponent);
        buttonComponent = new ButtonComponent("Добавить книгу", 30, 350, 150);
        this.add(buttonComponent);
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
}
