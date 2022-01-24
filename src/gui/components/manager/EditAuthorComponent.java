/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.manager;

import entity.Author;
import facade.AuthorFacade;
import gui.GuiApp;
import gui.components.ButtonComponent;
import gui.components.CaptionComponent;
import gui.components.ComboBoxAuthorsComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListAuthorsComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListModel;


public class EditAuthorComponent extends JPanel{
    public EditAuthorComponent editAuthorComponent = this;
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameComponent;
    private EditComponent surNameComponent;
    private EditComponent birthYearComponent;
    private ButtonComponent buttonComponent;
    private ListAuthorsComponent listAuthorsComponent;
    
    private ComboBoxAuthorsComponent comboBoxAuthorsComponent;
    private AuthorFacade authorFacade;
    private Author editAuthor;
    
    public EditAuthorComponent() {
        authorFacade = new AuthorFacade(Author.class);
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Изменение автора", GuiApp.WIDTH_WINDOW, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", GuiApp.WIDTH_WINDOW, 30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        comboBoxAuthorsComponent = new ComboBoxAuthorsComponent("Авторы", 240, 30, 300);
        this.add(comboBoxAuthorsComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditComponent("Имя автора:", GuiApp.WIDTH_WINDOW, 30, 300);
        this.add(nameComponent);
        surNameComponent = new EditComponent("Фамилия автора:", GuiApp.WIDTH_WINDOW, 30, 300);
        this.add(surNameComponent);
        birthYearComponent = new EditComponent("Год рождения автора:", GuiApp.WIDTH_WINDOW, 30, 200);
        this.add(birthYearComponent);
        buttonComponent = new ButtonComponent("Изменить автора",30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Author updateAuthor = authorFacade.find(editAuthor.getId());
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите имя автора");
                    return;
                }
                updateAuthor.setFirstname(nameComponent.getEditor().getText());
                if(surNameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите фамилию автора");
                    return;
                }
                updateAuthor.setLastname(surNameComponent.getEditor().getText());
                
                try {
                    updateAuthor.setBirthYear(Integer.parseInt(birthYearComponent.getEditor().getText()));
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите год рождения автора (цифрами)");
                    return;
                }
                
                AuthorFacade authorFacade = new AuthorFacade(Author.class);
                
                try {
                    authorFacade.create(updateAuthor);
                    infoComponent.getInfo().setText("Автор успешно изменён");
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    comboBoxAuthorsComponent.getComboBox().setModel(comboBoxAuthorsComponent.getComboBoxModel());
                    comboBoxAuthorsComponent.getComboBox().setSelectedIndex(-1);
                    
                    birthYearComponent.getEditor().setText("");
                    surNameComponent.getEditor().setText("");
                    nameComponent.getEditor().setText("");
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Автора изменить не удалось");
                }
                } catch (Exception eq) {
                    System.out.println("Ошибка");
                }

            }
        });
        comboBoxAuthorsComponent.getComboBox().addItemListener((ItemEvent e) -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            
            if(comboBox.getSelectedIndex() == -1) {
                birthYearComponent.getEditor().setText("");
                surNameComponent.getEditor().setText("");
                nameComponent.getEditor().setText("");
            }else{
                editAuthor = (Author) e.getItem();
                nameComponent.getEditor().setText(editAuthor.getFirstname());
                surNameComponent.getEditor().setText(editAuthor.getLastname());
                birthYearComponent.getEditor().setText(((Integer)editAuthor.getBirthYear()).toString());
            }
        });
    }
}

