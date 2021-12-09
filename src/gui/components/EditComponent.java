/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author pupil
 */
public class EditComponent extends JPanel{

    private JLabel title;
    private JTextField editor;
    
    public EditComponent(String text, int widthWindow,int heightPanel,int widthEditor) {
        initComponents(widthWindow,heightPanel,text,widthEditor);
    }

    private void initComponents(int widthWindow,int heightPanel,String text, int widthEditor) {
        this.setPreferredSize(new Dimension(widthWindow,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        title = new JLabel(text);
        title.setPreferredSize(new Dimension(widthWindow/3,27));
        title.setMinimumSize(getPreferredSize());
        title.setMaximumSize(getPreferredSize());
        title.setHorizontalAlignment(JLabel.RIGHT);
        title.setFont(new Font("Tahoma",0,12));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(5,0)));
        editor = new JTextField();
        editor.setPreferredSize(new Dimension(widthEditor,27));
        editor.setMinimumSize(getPreferredSize());
        editor.setMaximumSize(getPreferredSize());
        this.add(editor);
    }
    
}