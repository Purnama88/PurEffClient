/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.library.MyDialog;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ExpensesDialog extends MyDialog{
    
    public ExpensesDialog(LabelDecimalTextFieldPanel roundingpanel, LabelDecimalTextFieldPanel freightpanel,
            LabelDecimalTextFieldPanel taxpanel, LabelDecimalTextFieldPanel totalpanel){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_OTHERS"), 450, 250);
        
        box.add(roundingpanel);
        box.add(freightpanel);
        box.add(taxpanel);
        
        totalpanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        
        box.add(totalpanel);
        box.add(submitpanel);
        
        submitbutton.addActionListener((ActionEvent e) -> {
           dispose();
        });
    }
    
    public void showDialog(){
        setVisible(true);
    }
}
