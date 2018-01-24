/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.JPanel;
import net.purnama.controller.PaymentTypeInController;
import net.purnama.controller.PaymentTypeInDraftController;
import net.purnama.controller.PaymentTypeOutController;
import net.purnama.controller.PaymentTypeOutDraftController;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.form.util.SubmitPanel;
import net.purnama.model.transactional.draft.PaymentTypeInDraftEntity;
import net.purnama.model.transactional.draft.PaymentTypeOutDraftEntity;
import net.purnama.tablemodel.PaymentTypeInDraftTableModel;
import net.purnama.tablemodel.PaymentTypeInTableModel;
import net.purnama.tablemodel.PaymentTypeOutDraftTableModel;
import net.purnama.tablemodel.PaymentTypeOutTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class CashPanel extends JPanel{
    
    private LabelDecimalTextFieldPanel amountpanel;
    private SubmitPanel submitpanel;
    
    public CashPanel(PaymentTypeInDraftTableModel tablemodel, String paymentindraftid,
            LabelDecimalTextFieldPanel totalpanel, LabelDecimalTextFieldPanel remainingpanel){
        super(new FlowLayout(FlowLayout.LEFT));
    
        PaymentTypeInDraftController paymenttypeindraftController = new PaymentTypeInDraftController();
        
        init();
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            
            double amount = amountpanel.getTextFieldValue();
            
            if(amount > 0){
            
                PaymentTypeInDraftEntity ptide = paymenttypeindraftController.
                        addPaymentTypeInDraft(GlobalFields.CASH, 
                                Calendar.getInstance(),
                                "", "", "", amount, paymentindraftid);

                tablemodel.addRow(ptide);

                totalpanel.setTextFieldValue(totalpanel.getTextFieldValue() + amount);
                remainingpanel.setTextFieldValue(remainingpanel.getTextFieldValue() - amount);
            }
            amountpanel.setTextFieldValue(0);
            
        });
    }
    
    public CashPanel(PaymentTypeInTableModel tablemodel, String paymentinid, 
            LabelDecimalTextFieldPanel totalpanel){
        super(new FlowLayout(FlowLayout.LEFT));
        
        PaymentTypeInController paymenttypeinController = new PaymentTypeInController();
        
        init();
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            
            double amount = amountpanel.getTextFieldValue();
            
            if(amount > 0){

                tablemodel.addRow(paymenttypeinController.addPaymentTypeIn(GlobalFields.CASH, 
                                Calendar.getInstance(),
                                "", "", "", amount, paymentinid));

                totalpanel.setTextFieldValue(totalpanel.getTextFieldValue() + amount);
            }
            amountpanel.setTextFieldValue(0);
            
        });
    }
    
    public CashPanel(PaymentTypeOutDraftTableModel tablemodel, String paymentoutdraftid,
            LabelDecimalTextFieldPanel totalpanel, LabelDecimalTextFieldPanel remainingpanel){
        super(new FlowLayout(FlowLayout.LEFT));
        
        PaymentTypeOutDraftController paymenttypeoutdraftController = new PaymentTypeOutDraftController();
        
        init();
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            
            double amount = amountpanel.getTextFieldValue();
            
            if(amount > 0){
                PaymentTypeOutDraftEntity ptide = paymenttypeoutdraftController.
                        addPaymentTypeOutDraft(GlobalFields.CASH, 
                                Calendar.getInstance(),
                                "", "", "", amount, paymentoutdraftid);

                tablemodel.addRow(ptide);

                totalpanel.setTextFieldValue(totalpanel.getTextFieldValue() + amount);
                remainingpanel.setTextFieldValue(remainingpanel.getTextFieldValue() - amount);
            }
            amountpanel.setTextFieldValue(0);
            
        });
    }
    
    public CashPanel(PaymentTypeOutTableModel tablemodel, String paymentoutid, 
            LabelDecimalTextFieldPanel totalpanel){
        super(new FlowLayout(FlowLayout.LEFT));
        
        PaymentTypeOutController paymenttypeoutController = new PaymentTypeOutController();
        
        init();
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            
            double amount = amountpanel.getTextFieldValue();
            
            if(amount > 0){
                tablemodel.addRow(paymenttypeoutController.addPaymentTypeOut(GlobalFields.CASH, 
                                Calendar.getInstance(),
                                "", "", "", amount, paymentoutid));

                totalpanel.setTextFieldValue(totalpanel.getTextFieldValue() + amount);
            }
            amountpanel.setTextFieldValue(0);
            
        });
    }
    
    public final void init(){
        setPreferredSize(new Dimension(400, 350));
        
        amountpanel = new LabelDecimalTextFieldPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_AMOUNT"), 0, true);
        submitpanel = new SubmitPanel();
        
        add(amountpanel);
        add(submitpanel);
    }
}
