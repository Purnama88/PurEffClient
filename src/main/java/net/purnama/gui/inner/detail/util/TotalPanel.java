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
public class TotalPanel extends JPanel{
    
    
    private LabelDecimalTextFieldPanel tpanel;
    
    public TotalPanel(){
        super(new FlowLayout(FlowLayout.RIGHT));
        
        tpanel = new LabelDecimalTextFieldPanel(GlobalFields.
                PROPERTIES.getProperty("LABEL_TOTAL"), 0, false, 80, 140);
        
        add(tpanel);
    }
    
    public double getTotal(){
        return tpanel.getTextFieldValue();
    }
    
    public void setTotal(double value){
        tpanel.setTextFieldValue(value);
    }
}