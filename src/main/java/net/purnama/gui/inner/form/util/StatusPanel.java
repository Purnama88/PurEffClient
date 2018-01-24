/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.purnama.gui.library.MyLabel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class StatusPanel extends JPanel implements ActionListener{
    
    private final MyLabel label;
    
    private final ButtonGroup buttongroup;
    
    private final JRadioButton jbg1, jbg2;
    
    public StatusPanel(String labelcontent){
        super(new FlowLayout(FlowLayout.LEFT));
        
        label = new MyLabel(labelcontent, 150);

        buttongroup = new ButtonGroup();

        jbg1 = new JRadioButton(GlobalFields.PROPERTIES.getProperty("LABEL_ACTIVE"));
        jbg1.setSelected(true);
        jbg2 = new JRadioButton(GlobalFields.PROPERTIES.getProperty("LABEL_INACTIVE"));

        jbg1.setActionCommand("active");
        jbg2.setActionCommand("inactive");

        jbg1.setPreferredSize(new Dimension(120, 30));
        jbg2.setPreferredSize(new Dimension(120, 30));

        buttongroup.add(jbg1);
        buttongroup.add(jbg2);
        
        add(label);
        add(jbg1);
        add(jbg2);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        jbg1.addActionListener(this);
        jbg2.addActionListener(this);
    }
    
    public boolean getSelectedValue(){
        
        if(buttongroup.getSelection().getActionCommand().equals("active")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setSelectedValue(boolean status){
        buttongroup.clearSelection();
        if(status == true){
            jbg1.setSelected(true);
        }
        else{
            jbg2.setSelected(true);
        }
    }
    
    public String getLabelValue(){
        return label.getText();
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
    
    public void setButtonGroupEnabled(boolean value){
        jbg1.setEnabled(value);
        jbg2.setEnabled(value);
    }
    
    public void reset(){
        jbg1.setSelected(true);
    }
    
    public void setButtonGroupLabel(String first, String second){
        jbg1.setText(first);
        jbg2.setText(second);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        GlobalFields.STATE = false;
    }
}
