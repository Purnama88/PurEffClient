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
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class SubmitPanel extends JPanel{
    
    private final MyButton submitbutton, cancelbutton;
    
    public SubmitPanel(){
        super(new FlowLayout(FlowLayout.CENTER));
        
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        submitbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        cancelbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"));
        
        add(submitbutton);
    }
    
    public void addCancelButton(){
        add(cancelbutton);
    }
    
    public void loading(){
        submitbutton.setIcon(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        submitbutton.setText("");
        cancelbutton.setEnabled(false);
        disable();
    }
    
    public void finish(){
        submitbutton.setText(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        submitbutton.setIcon(null);
        cancelbutton.setEnabled(true);
        enable();
    }
    
    public MyButton getCancelButton(){
        return cancelbutton;
    }
    
    public MyButton getSubmitButton(){
        return submitbutton;
    }
    
    @Override
    public void disable(){
        submitbutton.setEnabled(false);
        cancelbutton.setEnabled(false);
    }
    
    @Override
    public void enable(){
        submitbutton.setEnabled(true);
        cancelbutton.setEnabled(true);
    }
}
