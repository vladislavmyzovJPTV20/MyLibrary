/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.components.CaptionComponent;
import gui.components.EditComponent;
import gui.components.InfoComponent;
import gui.components.ListAuthorsComponent;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
public class GuiApp extends JFrame{
    
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent editComponent;
    private ListAuthorsComponent listAuthorsComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;

    public GuiApp() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(600,400));
        this.setMinimumSize(getPreferredSize());
        this.setMaximumSize(getPreferredSize());
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,25)));
        captionComponent = new CaptionComponent("Добавление книги в библиотеку", this.getWidth(), 30);
        this.add(captionComponent);
        infoComponent = new InfoComponent("Информация о добавлении книги в библиотеку", this.getWidth(), 27);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        editComponent = new EditComponent("Название книги", this.getWidth(), 30, 250);
        this.add(editComponent);
        listAuthorsComponent = new ListAuthorsComponent("Авторы", this.getWidth(), 120, 250);
        this.add(listAuthorsComponent);
        publishedYearComponent = new EditComponent("Год издания книги", this.getWidth(), 30, 100);
        this.add(publishedYearComponent);
        quantityComponent = new EditComponent("Количество экземпляров книги", this.getWidth(), 30, 50);
        this.add(quantityComponent);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }

}
