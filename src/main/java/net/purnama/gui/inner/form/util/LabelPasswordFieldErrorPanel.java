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
import net.purnama.gui.library.MyPasswordField;

/**
 *
 * @author Purnama
 */
public class LabelPasswordFieldErrorPanel extends JPanel{
    
    private final MyPasswordField passwordfield;
    
    private final MyLabel label, errorlabel;
    
    private Object actioncommand;
    
    public LabelPasswordFieldErrorPanel(String title, String value){
        super(new FlowLayout(FlowLayout.LEFT));
        
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        label = new MyLabel(title, 150);
        errorlabel = new MyLabel("", 200);
        passwordfield = new MyPasswordField(value, 250);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        add(label);
        add(passwordfield);
        add(errorlabel);
        
    }
    
    public void setPasswordFieldEditable(boolean status){
        passwordfield.setEditable(status);
    }
    
    public void setDocumentListener(DocumentListener dl){
        passwordfield.getDocument().addDocumentListener(dl);
    }
    
    public void setPasswordFieldActionListener(ActionListener al){
        passwordfield.addActionListener(al);
    }
    
    public String getPasswordFieldValue(){
        return passwordfield.getText();
    }
    
    public void setPasswordFieldValue(String value){
        passwordfield.setText(value);
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
        passwordfield.setText("");
    }
    
    @Override
    public void requestFocus(){
        passwordfield.requestFocusInWindow();
    }
    
    public boolean isPasswordFieldEmpty(){
        return passwordfield.isEmpty();
    }
    
    public boolean isPasswordFieldLong(int stringlong){
        return passwordfield.isLongLessThan(stringlong);
    }
    
    public boolean isPasswordFieldLongBetween(int min, int max){
        return passwordfield.isLongBetween(min, max);
    }
}
