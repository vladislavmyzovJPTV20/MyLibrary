/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import gui.GuiApp;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonComponent extends JPanel{
    private JLabel title;
    private JButton button;
    
    public ButtonComponent(String text, int heightPanel, int left, int widthEditor) {
        initComponents(text,GuiApp.WIDTH_WINDOW, heightPanel,left,widthEditor);
    }
    
    public ButtonComponent(String text, int widthPanel, int heightPanel, int left, int widthEditor) {
        initComponents(text,widthPanel, heightPanel,left,widthEditor);
    }

    private void initComponents(String text, int widthPanel, int heightPanel,int left,int widthEditor) {
       this.setPreferredSize(new Dimension(widthPanel,heightPanel));
       this.setMinimumSize(this.getPreferredSize());
       this.setMaximumSize(this.getPreferredSize());
       this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
       this.add(Box.createRigidArea(new Dimension(left,0)));       
       this.add(Box.createRigidArea(new Dimension(5,0)));
       button = new JButton(text);
       button.setPreferredSize(new Dimension(widthEditor,27));
       button.setMinimumSize(button.getPreferredSize());
       button.setMaximumSize(button.getPreferredSize());
       this.add(button);
    }

    public JButton getButton() {
        return button;
    }
    
}
