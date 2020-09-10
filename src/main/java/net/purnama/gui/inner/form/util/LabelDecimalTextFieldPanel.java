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
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyDecimalTextField;
import net.purnama.gui.library.MyLabel;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class LabelDecimalTextFieldPanel extends JPanel{
    
    private final MyLabel label;
    private final MyDecimalTextField textfield;
    
    private Object actioncommand;
    
    public LabelDecimalTextFieldPanel(String title, Object value){
        super(new FlowLayout(FlowLayout.LEFT));
        
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        label = new MyLabel(title, 150);
        
        textfield = new MyDecimalTextField(value, 250);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        add(label);
        add(textfield);
    }
    
    public void setTextFieldEditable(boolean status){
        textfield.setEditable(status);
    }
    
    public void setDocumentListener(DocumentListener dl){
        textfield.getDocument().addDocumentListener(dl);
    }
    
    public void setTextFieldActionListener(ActionListener al){
        textfield.addActionListener(al);
    }
    
    public double getTextFieldValue(){
        return GlobalFunctions.convertToDouble(textfield.getText());
    }
    
    public void setTextFieldValue(Object value){
        textfield.setValue(value);
    }
    
    public String getLabelValue(){
        return label.getText();
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
    
    public Object getActioncommand() {
        return actioncommand;
    }

    public void setActioncommand(Object actioncommand) {
        this.actioncommand = actioncommand;
    }
    
    public void reset(){
        textfield.setText("");
    }
    
    @Override
    public void requestFocus(){
        textfield.requestFocusInWindow();
    }
}
