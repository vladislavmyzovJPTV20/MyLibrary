/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author pupil
 */
public class CaptionComponent extends JPanel{

    JLabel caption;
    
    public CaptionComponent(String text, int widthWindow,int heightPanel) {
        initComponents(widthWindow,heightPanel,text);
    }

    private void initComponents(int widthWindow,int heightPanel,String text) {
        this.setPreferredSize(new Dimension(widthWindow,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        caption = new JLabel(text);
        caption.setPreferredSize(new Dimension(widthWindow,heightPanel));
        caption.setMinimumSize(getPreferredSize());
        caption.setMaximumSize(getPreferredSize());
        caption.setHorizontalAlignment(JLabel.CENTER);
        caption.setFont(new Font("Tahoma",1,16));
        this.add(caption);
    }
    
}
