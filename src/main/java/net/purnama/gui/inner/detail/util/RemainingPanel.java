/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RemainingPanel extends JPanel{
    
    private final LabelDecimalTextFieldPanel remainingpanel;
    
    public RemainingPanel(){
        super(new FlowLayout(FlowLayout.RIGHT));
        
        remainingpanel = new LabelDecimalTextFieldPanel(GlobalFields.
                PROPERTIES.getProperty("LABEL_REMAINING"), 0, false, 80, 140);
        
        add(remainingpanel);
    }
    
    public double getRemaining(){
        return remainingpanel.getTextFieldValue();
    }
    
    public void setRemaining(double value){
        remainingpanel.setTextFieldValue(value);
    }
}
