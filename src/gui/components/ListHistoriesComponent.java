/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import entity.History;
import entity.Reader;
import facade.HistoryFacade;
import gui.GuiApp;
import gui.components.renderers.ListHistoriesCellRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Melnikov
 */
public class ListHistoriesComponent extends JPanel{
    private JLabel title;
    private JList<History> list;
    private JScrollPane scrollPane; 
    /**
     * Список книг библиотеки с заголовком
     * @param xORy расположение компонентов на панели: true - горизонтальное, false - вертикальное
     * @param text текст в JLabel
     * @param left ширина JLabel
     * @param heightPanel высота панели компонента
     * @param widthEditor ширина JList
     */
    public ListHistoriesComponent(boolean xORy,String text, int left, int heightPanel, int widthEditor) {
        initComponents(xORy, text, left, heightPanel,widthEditor);
    }

    public ListHistoriesComponent(String text, int left, int heightPanel, int widthEditor) {
        this.initComponents(false, text, left, heightPanel, widthEditor);
    }
    
    private void initComponents(boolean xORy, String text, int left, int heightPanel,int widthEditor) {
       
       this.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW-50,heightPanel));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
       title = new JLabel(text);
       title.setMinimumSize(title.getPreferredSize());
       title.setMaximumSize(title.getPreferredSize());
       title.setBorder(BorderFactory.createLineBorder(Color.BLUE));
       list = new JList<>();
       
       if(xORy){
           this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
           title.setPreferredSize(new Dimension(left,27));
           title.setHorizontalAlignment(JLabel.RIGHT);
           title.setAlignmentY(TOP_ALIGNMENT);
       }else{
           this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
           this.add(Box.createRigidArea(new Dimension(0,10)));
           title.setPreferredSize(new Dimension(GuiApp.WIDTH_WINDOW,27));
           title.setHorizontalAlignment(JLabel.LEFT);
           title.setAlignmentY(TOP_ALIGNMENT);
           
           
       }
       title.setFont(new Font("Tahoma",0,12));
       this.add(title);
       if(xORy){
           this.add(Box.createRigidArea(new Dimension(5,0)));
       }else{
           this.add(Box.createRigidArea(new Dimension(0,10)));
           
       }
       list.setModel(getListHistoriesModel(GuiApp.user.getReader()));
       list.setCellRenderer(new ListHistoriesCellRenderer());
       list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
       list.setLayoutOrientation(JList.HEIGHT);
       scrollPane = new JScrollPane(list);
       scrollPane.setPreferredSize(new Dimension(widthEditor,heightPanel));
       scrollPane.setMinimumSize(scrollPane.getPreferredSize());
       scrollPane.setMaximumSize(scrollPane.getPreferredSize());
       scrollPane.setAlignmentX(LEFT_ALIGNMENT);
       if(xORy){
           scrollPane.setAlignmentY(TOP_ALIGNMENT);
       }else{
           scrollPane.setAlignmentY(LEFT_ALIGNMENT);
       }
       this.add(scrollPane);
    }

    private ListModel<History> getListHistoriesModel(Reader reader) {
        HistoryFacade historyFacade = new HistoryFacade(History.class);
        List<History> histories = historyFacade.find(reader);
        DefaultListModel<History> defaultListModel = new DefaultListModel<>();
        if(histories != null){
            for (History history : histories) {
                defaultListModel.addElement(history);
            }
        }
        return defaultListModel;
    }

    public JList<History> getList() {
        return list;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    
    
}