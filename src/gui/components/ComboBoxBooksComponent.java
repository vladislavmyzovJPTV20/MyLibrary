/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.Book;
import facade.BookFacade;
import gui.GuiApp;
import gui.components.renderers.ListAuthorCellRenderer;
import gui.components.renderers.ListBooksCellRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class ComboBoxBooksComponent extends JPanel{
    private JLabel title;
    private JComboBox<Book> comboBox;
    /**
     * Список книг библиотеки с заголовком
     * @param xORy расположение компонентов на панели: true - горизонтальное, false - вертикальное
     * @param text текст в JLabel
     * @param left ширина JLabel
     * @param heightPanel высота панели компонента
     * @param widthEditor ширина JList
     */
    public ComboBoxBooksComponent(String text, int left, int heightPanel, int widthEditor) {
        initComponents(text, left, heightPanel,widthEditor);
    }


    private void initComponents(String text, int left, int heightPanel,int widthEditor) {

       this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
       this.setBorder(BorderFactory.createLineBorder(Color.yellow));
       this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,heightPanel));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       title = new JLabel(text);
       title.setPreferredSize(new Dimension(left, heightPanel   ));
       title.setMinimumSize(title.getPreferredSize());
       title.setMaximumSize(title.getPreferredSize());
       title.setHorizontalAlignment(JLabel.RIGHT);
       comboBox = new JComboBox<>();
       title.setPreferredSize(new Dimension(left,27));
      // title.setAlignmentY(TOP_ALIGNMENT);
       title.setFont(new Font("Tahoma",0,12));
       this.add(title);
       comboBox.setModel(getComboBoxModel());
       comboBox.setRenderer(new ListBooksCellRenderer());
       comboBox.setSelectedIndex(-1);
       comboBox.setPreferredSize(new Dimension(widthEditor,heightPanel));
       comboBox.setMinimumSize(comboBox.getPreferredSize());
       comboBox.setMaximumSize(comboBox.getPreferredSize());
       this.add(comboBox);
    }

    private ComboBoxModel<Book> getComboBoxModel() {
        BookFacade bookFacade = new BookFacade(Book.class);
        List<Book> books = bookFacade.findAll();

        DefaultComboBoxModel<Book> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Book book : books) {
            defaultComboBoxModel.addElement(book);
        }
        return defaultComboBoxModel;
    }

    public JComboBox<Book> getComboBox() {
        return comboBox;
    }
    
}
