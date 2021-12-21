/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.Reader;
import facade.ReaderFacade;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Melnikov
 */
public class TabAddReaderComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameComponent;
    private EditComponent lastNameComponent;
    private EditComponent phoneComponent;
    private ButtonComponent buttonComponent;
    
    public TabAddReaderComponent(int widthPanel) {
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление нового читателя", widthPanel, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", widthPanel, 30);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditComponent("Имя:", widthPanel, 30, 300);
        this.add(nameComponent);
        lastNameComponent = new EditComponent("Фамилия:", widthPanel, 30, 300);
        this.add(lastNameComponent);
        phoneComponent = new EditComponent("Телефон:", widthPanel, 30, 200);
        this.add(phoneComponent);
        buttonComponent = new ButtonComponent("Добавить читателя",widthPanel, 30, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(ButtonAddReader());
    }
    private ActionListener ButtonAddReader(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Reader reader = new Reader();
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите имя");
                    return;
                }
                reader.setFirstname(nameComponent.getEditor().getText());
                if(lastNameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите фамилию");
                    return;
                }
                reader.setLastname(lastNameComponent.getEditor().getText());
                
                if(phoneComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Введите телефон");
                    return;
                } 
                reader.setPhone(phoneComponent.getEditor().getText());
                
                ReaderFacade readerFacade = new ReaderFacade(Reader.class);
                
                try {
                    readerFacade.create(reader);
                    infoComponent.getInfo().setText("Читатель успешно добавлен");
                    infoComponent.getInfo().setForeground(Color.BLUE);
                    phoneComponent.getEditor().setText("");
                    lastNameComponent.getEditor().setText("");
                    nameComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setForeground(Color.red);
                    infoComponent.getInfo().setText("Читателя добавить не удалось");
                }
            }
        };
    }
    
    
}