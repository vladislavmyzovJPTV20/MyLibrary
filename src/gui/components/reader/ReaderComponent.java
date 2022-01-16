/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components.reader;

import gui.components.*;
import static gui.GuiApp.HEIGHT_WINDOW;
import static gui.GuiApp.WIDTH_WINDOW;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ReaderComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditComponent nameBookComponent;
    private EditComponent publishedYearComponent;
    private EditComponent quantityComponent;
    private ButtonComponent buttonComponent;
    private ListAuthorsComponent listAuthorsComponent;
    
    public ReaderComponent(int widthPanel, int heightPanel) {
        initComponents(widthPanel, heightPanel);
    }    

    private void initComponents(int widthPanel, int heightPanel) {
        JTabbedPane readerTabbed = new JTabbedPane();
        readerTabbed.setPreferredSize(new Dimension(WIDTH_WINDOW,HEIGHT_WINDOW));
        readerTabbed.setMinimumSize(readerTabbed.getPreferredSize());
        readerTabbed.setMaximumSize(readerTabbed.getPreferredSize());
        this.add(readerTabbed);
        readerTabbed.addTab("Взять книгу", new TakeBookComponent(WIDTH_WINDOW, HEIGHT_WINDOW));
        readerTabbed.addTab("Вернуть книгу", new ReturnBookComponent(WIDTH_WINDOW, HEIGHT_WINDOW));        
    }
}
