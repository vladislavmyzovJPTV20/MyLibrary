/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import gui.GuiApp;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;

public class GuestComponent extends JPanel{
    private ListBooksComponent listBooksComponent;
    
    public GuestComponent() {
        initComponents(GuiApp.HEIGHT_WINDOW);
    }

    public GuestComponent(int heightList) {
        initComponents(heightList);
    }

    private void initComponents(int heightList) {
       this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,GuiApp.HEIGHT_WINDOW));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       listBooksComponent = new ListBooksComponent(false, "Список книг библиотеки",0, heightList, GuiApp.WIDTH_WINDOW);
       this.add(Box.createRigidArea(new Dimension(0,10)));
       this.add(listBooksComponent);
       this.add(Box.createRigidArea(new Dimension(0,10)));
    }

    public ListBooksComponent getListBooksComponent() {
        return listBooksComponent;
    }

    public void setListBooksComponent(ListBooksComponent listBooksComponent) {
        this.listBooksComponent = listBooksComponent;
    }
    
}
