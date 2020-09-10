/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTextField;

/**
 *
 * @author Purnama
 */
public class LabelTextFieldPanel extends JPanel{
    
    private final MyTextField textfield, textfield2;
    private final MyLabel label;
    
    public LabelTextFieldPanel(String labelvalue, String tfvalue, boolean editabled, DocumentListener dl){
        super(new FlowLayout(FlowLayout.LEFT));
        
        textfield = new MyTextField(tfvalue, 200);
        textfield2 = new MyTextField("", 98);
        label = new MyLabel(labelvalue, 100);
        add(label);
        add(textfield);
        setTextFieldEnabled(editabled);
        setDocumentListener(dl);
    }
    
    public LabelTextFieldPanel(String labelvalue, String tfvalue, boolean editabled){
        super(new FlowLayout(FlowLayout.LEFT));
        
        textfield = new MyTextField(tfvalue, 200);
        textfield2 = new MyTextField("", 98);
        label = new MyLabel(labelvalue, 100);
        add(label);
        add(textfield);
        setTextFieldEnabled(editabled);
    }
    
    public final void setDocumentListener(DocumentListener dl){
        textfield.getDocument().addDocumentListener(dl);
    }
    
    public final void setTextFieldEnabled(boolean value){
        textfield.setEditable(value);
    }
    
    public String getTextFieldValue(){
        return textfield.getText();
    }
    
    public void setTextFieldValue(String value){
        textfield.setText(value);
    }
    
    public void addTextField(String value, boolean editable){
        textfield.setPreferredSize(new Dimension(97, 25));
        
        textfield2.setText(value);
        textfield2.setEditable(editable);
        
        add(textfield2);
    }
}
