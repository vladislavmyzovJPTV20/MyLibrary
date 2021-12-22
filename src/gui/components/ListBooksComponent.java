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
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class ListBooksComponent extends JPanel{
    private JLabel title;
    private JList<Book> list;
    /**
     * Список книг библиотеки с заголовком
     * @param xORy расположение компонентов на панели: true - горизонтальное, false - вертикальное
     * @param text текст в JLabel
     * @param left ширина JLabel
     * @param heightPanel высота панели компонента
     * @param widthEditor ширина JList
     */
    public ListBooksComponent(boolean xORy,String text, int left, int heightPanel, int widthEditor) {
        initComponents(xORy, text, left, heightPanel,widthEditor);
    }

    public ListBooksComponent(String text, int left, int heightPanel, int widthEditor) {
        this.initComponents(false, text, left, heightPanel, widthEditor);
    }

    private void initComponents(boolean xORy, String text, int left, int heightPanel,int widthEditor) {

       this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW-50,heightPanel));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       title = new JLabel(text);
       title.setMinimumSize(title.getPreferredSize());
       title.setMaximumSize(title.getPreferredSize());
       list = new JList<>();

       if(xORy){
           this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
           title.setPreferredSize(new Dimension(left,27));
           title.setHorizontalAlignment(JLabel.RIGHT);
           title.setAlignmentY(TOP_ALIGNMENT);
       }else{
           this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
           this.add(Box.createRigidArea(new Dimension(0,10)));
           title.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW-50,27));
           title.setHorizontalAlignment(JLabel.CENTER);
           title.setAlignmentY(TOP_ALIGNMENT);
           this.add(Box.createRigidArea(new Dimension(0,10)));


       }
       title.setFont(new Font("Tahoma",0,12));
       this.add(title);
       if(xORy) {
           this.add(Box.createRigidArea(new Dimension(5,0)));
       }else{
           this.add(Box.createRigidArea(new Dimension(0,10)));
       }
       list.setModel(getListModel());
       list.setCellRenderer(new ListBooksCellRenderer());
       list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
       list.setLayoutOrientation(JList.HEIGHT);
       JScrollPane scrollPane = new JScrollPane(list);
       scrollPane.setPreferredSize(new Dimension(widthEditor,heightPanel));
       scrollPane.setMinimumSize(scrollPane.getPreferredSize());
       scrollPane.setMaximumSize(scrollPane.getPreferredSize());
       scrollPane.setAlignmentX(LEFT_ALIGNMENT);
       scrollPane.setAlignmentY(TOP_ALIGNMENT);
       this.add(scrollPane);
    }

    private ListModel<Book> getListModel() {
        BookFacade bookFacade = new BookFacade(Book.class);
        List<Book> books = bookFacade.findAll();

        DefaultListModel<Book> defaultListModel = new DefaultListModel<>();
        for (Book book : books) {
            defaultListModel.addElement(book);
        }
        return defaultListModel;
    }

    public JList<Book> getList() {
        return list;
    }

}
