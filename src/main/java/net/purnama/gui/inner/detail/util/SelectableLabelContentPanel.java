/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MySelectableLabel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class SelectableLabelContentPanel extends JPanel{
    
    private final MySelectableLabel label, content;
    
    public SelectableLabelContentPanel(String labelvalue, String contentvalue){
        super(new FlowLayout(FlowLayout.LEFT));
        
        label = new MySelectableLabel(labelvalue, 150);
        content = new MySelectableLabel(contentvalue, 500);
        
        add(label);
        add(new MyLabel(":", 10));
        add(content);
        
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
    
    public void setContentValue(boolean value){
        if(value){
            content.setText(GlobalFields.PROPERTIES.getProperty("LABEL_ACTIVE"));
        }
        else{
            content.setText(GlobalFields.PROPERTIES.getProperty("LABEL_INACTIVE"));
        }
    }
    
    public void setContentValue(String value){
        content.setText(value);
    }
}

