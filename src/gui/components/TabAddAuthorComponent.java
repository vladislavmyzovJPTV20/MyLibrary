/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.Author;
import facade.AuthorFacade;
import gui.GuiApp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class TabAddAuthorComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameComponent;
    private EditComponent surNameComponent;
    private EditComponent birthYearComponent;
    private ButtonComponent buttonComponent;
    
    public TabAddAuthorComponent(int widthPanel) {
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление нового автора", widthPanel, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", widthPanel, 30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditComponent("Имя автора:", widthPanel, 30, 300);
        this.add(nameComponent);
        surNameComponent = new EditComponent("Фамилия автора:", widthPanel, 30, 300);
        this.add(surNameComponent);
        birthYearComponent = new EditComponent("Год рождения автора:", widthPanel, 30, 200);
        this.add(birthYearComponent);
        buttonComponent = new ButtonComponent("Добавить автора",GuiApp.WIDTH_WINDOW, 30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(ButtonAddReader());
    }
    private ActionListener ButtonAddReader(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Author author = new Author();
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите имя автора");
                    return;
                }
                author.setFirstname(nameComponent.getEditor().getText());
                if(surNameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите фамилию автора");
                    return;
                }
                author.setLastname(surNameComponent.getEditor().getText());
                
                try {
                    author.setBirthYear(Integer.parseInt(birthYearComponent.getEditor().getText()));
                } catch (Exception ex) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите год рождения автора (цифрами)");
                    return;
                }
                
                AuthorFacade authorFacade = new AuthorFacade(Author.class);
                
                try {
                    authorFacade.create(author);
                    infoComponent.getInfo().setText("Автор успешно добавлен");
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    birthYearComponent.getEditor().setText("");
                    surNameComponent.getEditor().setText("");
                    nameComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Автора добавить не удалось");
                }
            }
        };
    }
    
    
}
