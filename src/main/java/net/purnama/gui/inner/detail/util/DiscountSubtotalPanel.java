/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MySelectableLabel;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class DiscountSubtotalPanel extends JPanel{
    
    private double subtotalval;
    
    LabelDecimalTextFieldPanel discountpanel, subtotalpanel;
    
    public DiscountSubtotalPanel(){
        
        super(new FlowLayout(FlowLayout.RIGHT));
        
        discountpanel = 
            new LabelDecimalTextFieldPanel(GlobalFields.
                    PROPERTIES.getProperty("LABEL_DISCOUNT"), 0, false, 80, 140);
        
        subtotalpanel = 
             new LabelDecimalTextFieldPanel(GlobalFields.
                    PROPERTIES.getProperty("LABEL_SUBTOTAL"), 0, false, 80, 140);
        
        JPanel dummypanel = new JPanel();
        dummypanel.setPreferredSize(new Dimension(24, 24));
        
        add(discountpanel);
        add(dummypanel);
        add(subtotalpanel);
        
    }
    
    public double getSubtotal(){
        return subtotalval;
    }
    
    public void setSubtotal(double value){
        subtotalval = value;
        setSubtotalminDiscount(getSubtotalminDiscount());
    }
    
    public double getDiscount(){
        return discountpanel.getTextFieldValue();
    }
    
    public void setDiscount(double value){
        discountpanel.setTextFieldValue(value);
        setSubtotalminDiscount(getSubtotalminDiscount());
    }
    
    public void setSubtotalminDiscount(double value){
        subtotalpanel.setTextFieldValue(value);
    }
    
    public double getSubtotalminDiscount(){
        return getSubtotal()-getDiscount();
    }
    
    public void setDocumentListener(DocumentListener dl){
        discountpanel.setDocumentListener(dl);
        subtotalpanel.setDocumentListener(dl);
    }
}
