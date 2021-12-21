/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.components.ListBooksComponent;
import gui.components.TabAddAuthorComponent;
import gui.components.TabAddBookComponent;
import gui.components.TabAddReaderComponent;
import java.awt.Dimension;
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
    private ListBooksComponent listBooksComponent;
    
    public GuiApp() {
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
        listBooksComponent = new ListBooksComponent(false, "Книги", GuiApp.HEIGHT_WINDOW, GuiApp.HEIGHT_WINDOW - 80, GuiApp.WIDTH_WINDOW);
        guestPanel.add(listBooksComponent);
        this.add(guestPanel);
        
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }

}
