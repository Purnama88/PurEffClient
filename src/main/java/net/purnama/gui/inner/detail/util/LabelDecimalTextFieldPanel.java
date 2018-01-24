/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyDecimalTextField;
import net.purnama.gui.library.MyLabel;

/**
 *
 * @author Purnama
 */
public class LabelDecimalTextFieldPanel extends JPanel{
    
    private final MyDecimalTextField textfield;
    private final MyLabel label;
    
    public LabelDecimalTextFieldPanel(String labelvalue, double tfvalue, boolean editabled,
            int labelwidth, int tfwidth){
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        textfield = new MyDecimalTextField(tfvalue, tfwidth);
        label = new MyLabel(labelvalue, labelwidth);
        add(label);
        add(textfield);
        setTextFieldEnabled(editabled);
    }
    
    public LabelDecimalTextFieldPanel(String labelvalue, double tfvalue, boolean editabled
            ,DocumentListener dl){
        super(new FlowLayout(FlowLayout.LEFT));
        
        textfield = new MyDecimalTextField(tfvalue, 200);
        label = new MyLabel(labelvalue, 100);
        add(label);
        add(textfield);
        setTextFieldEnabled(editabled);
        setDocumentListener(dl);
    }
    
    public LabelDecimalTextFieldPanel(String labelvalue, double tfvalue, boolean editabled
            ){
        super(new FlowLayout(FlowLayout.LEFT));
        
        textfield = new MyDecimalTextField(tfvalue, 200);
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
    
    public double getTextFieldValue(){
        try{
            return ((Number)textfield.getValue()).doubleValue();
        }
        catch(Exception e){
            return 0;
        }
    }
    
    public void setTextFieldValue(double value){
        textfield.setValue(value);
    }
}
