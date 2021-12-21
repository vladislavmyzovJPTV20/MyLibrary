/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ComboBoxComponent extends JPanel{
    private JLabel jLabelTitle;
    private JComboBox jComboBox;

    public ComboBoxComponent(String text,int widthPanel,int heightPanel, int widthEdit) {
        initComponents(text,widthPanel,heightPanel,widthEdit);
    }

    private void initComponents(String text,int widthPanel,int heightPanel, int widthEdit) {
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(widthPanel,heightPanel));
        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
        //this.setBorder(BorderFactory.createLineBorder(Color.black));
        jLabelTitle = new JLabel(text);
        jLabelTitle.setPreferredSize(new Dimension(widthPanel/3,27));
        jLabelTitle.setMinimumSize(jLabelTitle.getPreferredSize());
        jLabelTitle.setMaximumSize(jLabelTitle.getPreferredSize());
        jLabelTitle.setHorizontalAlignment(JLabel.RIGHT);
        jComboBox = new JComboBox();
        jComboBox.setMaximumSize(new Dimension());
        if(widthEdit == 0){
            jComboBox.setPreferredSize(new Dimension(((widthPanel-widthPanel/3)-40),27));
        }else{
            jComboBox.setPreferredSize(new Dimension(widthEdit,27));
        }
        jComboBox.setMaximumSize(jComboBox.getPreferredSize());
        jComboBox.setMinimumSize(jComboBox.getPreferredSize());
        this.add(jLabelTitle);
        this.add(jComboBox);
    }

  public JComboBox getjComboBox() {
    return jComboBox;
  }


}
