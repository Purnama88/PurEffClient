/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class AmountPanel extends JPanel{
    
    private LabelDecimalTextFieldPanel amountpanel;
    
    private MyButton paymentbutton;
    
    public AmountPanel(){
        super(new FlowLayout(FlowLayout.RIGHT));
        
        amountpanel = new LabelDecimalTextFieldPanel(GlobalFields.
                PROPERTIES.getProperty("LABEL_PAID"), 0, false, 80, 140);
        
        paymentbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Currency_16.png"), 
                20, 20);
        
        add(paymentbutton);
        add(amountpanel);
    }
    
    public double getAmount(){
        return amountpanel.getTextFieldValue();
    }
    
    public void setAmount(double value){
        amountpanel.setTextFieldValue(value);
    }
    
    public void setDocumentListener(DocumentListener dl){
        amountpanel.setDocumentListener(dl);
    }
    
    public MyButton getPaymentButton(){
        return paymentbutton;
    }
}
