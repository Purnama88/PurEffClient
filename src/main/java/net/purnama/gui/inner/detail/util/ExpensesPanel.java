/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.dialog.ExpensesDialog;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ExpensesPanel extends JPanel{
    
    private LabelDecimalTextFieldPanel roundingpanel, freightpanel, taxpanel, totalexpenses, ttotalexpenses;
    
    private MyButton menubutton;
    
    public ExpensesPanel(){
        
        super(new FlowLayout(FlowLayout.RIGHT));
        
        totalexpenses = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_OTHERS"),
                0, false, 80, 140);
        
        ttotalexpenses = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_TOTAL"),
                0, false);
        
        roundingpanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ROUNDING"),
                0, true);
        freightpanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_FREIGHT"),
                0, true);
        taxpanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_TAX"),
                0, true);
        
        menubutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Menu_16.png"), 
                20, 20);
        
        add(menubutton);
        add(totalexpenses);
        
        menubutton.addActionListener((ActionEvent e) -> {
            new ExpensesDialog(roundingpanel, freightpanel, taxpanel, ttotalexpenses).showDialog();
        });
        
        DocumentListener documentListener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                calculatetotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculatetotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculatetotal();
            }
            
            private void calculatetotal(){
                double rounding = getRounding();
                double freight = getFreight();
                double  tax = getTax();

                double total = freight + tax - rounding;

                setTotalexpenses(total);
            }
        };
        
        roundingpanel.setDocumentListener(documentListener);
        freightpanel.setDocumentListener(documentListener);
        taxpanel.setDocumentListener(documentListener);
    }
    
    public double getRounding(){
        return roundingpanel.getTextFieldValue();
    }
    
    public double getFreight(){
        return freightpanel.getTextFieldValue();
    }
    
    public double getTax(){
        return taxpanel.getTextFieldValue();
    }
    
    public double getTotalExpenses(){
        return totalexpenses.getTextFieldValue();
    }
    
    public void setRounding(double value){
        roundingpanel.setTextFieldValue(value);
    }
    
    public void setFreight(double value){
        freightpanel.setTextFieldValue(value);
    }
    
    public void setTax(double value){
        taxpanel.setTextFieldValue(value);
    }
    
    public void setTotalexpenses(double value){
        totalexpenses.setTextFieldValue(value);
        ttotalexpenses.setTextFieldValue(value);
    }
    
    public void setDocumentListener(DocumentListener dl){
        totalexpenses.setDocumentListener(dl);
    }
    
    public void setTextFieldEnabled(boolean status){
        roundingpanel.setTextFieldEnabled(status);
        freightpanel.setTextFieldEnabled(status);
        taxpanel.setTextFieldEnabled(status);
    }
}
