/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.purnama.gui.library.WrapLayout;

/**
 *
 * @author Purnama
 */
public class PaymentPanel extends JPanel{
    
    public PaymentPanel(){
        super(new WrapLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void refresh(){
        revalidate();
        repaint();
    }
    
    public void setChildComponentValues(boolean value){
        for(Component component : getComponents()){
            if(component instanceof ItemPaymentPanel2){
                ItemPaymentPanel2 ipp2 = (ItemPaymentPanel2)component;

                ipp2.setSelected(value);
            }
            else{
                ItemPaymentPanel ipp = (ItemPaymentPanel)component;

                ipp.setSelected(value);
            }
        }
    }
}
