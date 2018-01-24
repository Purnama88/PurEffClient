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
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;

/**
 *
 * @author Purnama
 */
public class StringComboBoxPanel extends JPanel{
    
    private final MyLabel label;
    private final JComboBox combobox;
    
    public StringComboBoxPanel(String labelvalue, String[] comboboxvalue){
        
        super(new FlowLayout(FlowLayout.LEFT));
        
        combobox = new JComboBox(comboboxvalue);
        combobox.setPreferredSize(new Dimension(245, 25));
        label = new MyLabel(labelvalue, 150);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(label);
        add(combobox);
    }
    
    public void setComboBoxEnabled(boolean value){
        combobox.setEnabled(value);
    }
    
    public int getSelectedIndex(){
        return combobox.getSelectedIndex();
    }
    
    public String getSelectedItem(){
        return (String)combobox.getSelectedItem();
    }
    
    public void setSelectedIndex(int index){
        combobox.setSelectedIndex(index);
    }
    
    public void setComboBoxValue(String value){
        ComboBoxModel model = combobox.getModel();
        
        int size = model.getSize();
        
        for(int i = 0; i < size; i++){
            String v = String.valueOf(model.getElementAt(i));
            if(v.equals(value)){
                model.setSelectedItem(v);
            }
        }
    }
    
    public void addItem(String value){
        combobox.addItem(value);
    }
}
