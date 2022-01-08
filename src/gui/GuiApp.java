/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.components.ButtonComponent;
import gui.components.ListBooksComponent;
import gui.components.TabAddAuthorComponent;
import gui.components.TabAddBookComponent;
import gui.components.TabAddReaderComponent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    public GuiApp() {
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
//        buttonChangePanelComponent.getButton().addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                managerTabbed.addTab("Добавить книгу", tabAddBookComponent);
//                managerTabbed.addTab("Добавить читателя", tabAddReaderComponent);
//                managerTabbed.addTab("Добавить автора", tabAddAuthorComponent);                
//            }
//        });
        
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }

}
