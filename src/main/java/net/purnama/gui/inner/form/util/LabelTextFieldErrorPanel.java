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
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTextField;

/**
 *
 * @author Purnama
 */
public class LabelTextFieldErrorPanel extends JPanel{
    
    private final MyTextField textfield;
    
    private final MyLabel label, errorlabel;
    
    private Object actioncommand;
    
    public LabelTextFieldErrorPanel(String title, String value){
        super(new FlowLayout(FlowLayout.LEFT));
        
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        label = new MyLabel(title, 150);
        errorlabel = new MyLabel("", 250);
        textfield = new MyTextField(value, 250);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        add(label);
        add(textfield);
        add(errorlabel);
        
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
    
    public String getTextFieldValue(){
        return textfield.getText();
    }
    
    public void setTextFieldValue(String value){
        textfield.setText(value);
    }
    
    public String getLabelValue(){
        return label.getText();
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
    
    public String getErrorLabel(){
        return errorlabel.getText();
    }
    
    public void setErrorLabel(String value){
        errorlabel.setText("<HTML><FONT COLOR=RED>" + value + "</FONT></HTML>");
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
    
    public boolean isTextFieldEmpty(){
        return textfield.isEmpty();
    }
    
    public boolean isTextFieldLong(int stringlong){
        return textfield.isLongLessThan(stringlong);
    }
    
    public boolean isTextFieldLongBetween(int min, int max){
        return textfield.isLongBetween(min, max);
    }
}
