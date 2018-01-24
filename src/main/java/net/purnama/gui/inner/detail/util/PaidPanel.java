/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaidPanel extends JPanel{
    
    private final LabelDecimalTextFieldPanel paidpanel;
    
    public PaidPanel(){
        super(new FlowLayout(FlowLayout.RIGHT));
        
        paidpanel = new LabelDecimalTextFieldPanel(GlobalFields.
                PROPERTIES.getProperty("LABEL_AMOUNT"), 0, false, 80, 140);
        
        add(paidpanel);
    }
    
    public double getPaid(){
        return paidpanel.getTextFieldValue();
    }
    
    public void setPaid(double value){
        paidpanel.setTextFieldValue(value);
    }
    
    public void setDocumentListener(DocumentListener dl){
        paidpanel.setDocumentListener(dl);
    }
}