/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;

/**
 *
 * @author Purnama
 */
public class LabelPanel extends JPanel{
    
    private final MyLabel label;
    
    public LabelPanel(String labelcontent){
        super(new FlowLayout(FlowLayout.LEFT));
        
        label = new MyLabel(labelcontent, 500);
        
        setMaximumSize(new Dimension(700, 25));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(label);
    }
    
    public MyLabel getLabelValue(){
        return label;
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
}
