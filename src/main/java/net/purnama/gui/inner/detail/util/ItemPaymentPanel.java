/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.inner.detail.ExpensesDetail;
import net.purnama.gui.inner.detail.InvoicePurchaseDetail;
import net.purnama.gui.inner.detail.ReturnPurchaseDetail;
import net.purnama.gui.library.MyDecimalTextField;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.transactional.draft.PaymentOutExpensesDraftEntity;
import net.purnama.model.transactional.draft.PaymentOutInvoiceDraftEntity;
import net.purnama.model.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
import net.purnama.model.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemPaymentPanel extends JPanel implements MouseListener, DocumentListener{
    
    private final JPanel valuepanel;
    
    private final JCheckBox jcb;
    
    private final JPopupMenu popupmenu;
    
    private final JMenuItem menuitem, reset;
    
    private final MyDecimalTextField textfield;
    
    private final PaymentOutInvoiceDraftEntity paymentoutinvoicedraftentity;
    
    public ItemPaymentPanel(PaymentOutInvoiceDraftEntity paymentoutinvoicedraftentity){
        
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.paymentoutinvoicedraftentity = paymentoutinvoicedraftentity;
        
        setMinimumSize(new Dimension(542, 30));
        setMaximumSize(new Dimension(542, 30));
        setPreferredSize(new Dimension(542, 30));
        
        valuepanel = new JPanel(new GridLayout(1, 6, 5, 0));
        valuepanel.setPreferredSize(new Dimension(500, 20));
    
        textfield = new MyDecimalTextField(0, 25);
        
        if(paymentoutinvoicedraftentity  instanceof PaymentOutInvoicePurchaseDraftEntity){
                PaymentOutInvoicePurchaseDraftEntity poipd = 
                        (PaymentOutInvoicePurchaseDraftEntity)paymentoutinvoicedraftentity;
            valuepanel.add(new MyLabel(poipd.getInvoicepurchase().getNumber()));
            valuepanel.add(new MyLabel(poipd.getInvoicepurchase().getFormatteddate()));
            valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_INVOICE")));
            valuepanel.add(new MyLabel(poipd.getInvoicepurchase().getFormattedtotal_after_tax()));
            textfield.setValue(poipd.getAmount());
            
        }
        else if(paymentoutinvoicedraftentity  instanceof PaymentOutReturnPurchaseDraftEntity){
            PaymentOutReturnPurchaseDraftEntity porpd = 
                        (PaymentOutReturnPurchaseDraftEntity)paymentoutinvoicedraftentity;
            valuepanel.add(new MyLabel(porpd.getReturnpurchase().getNumber()));
            valuepanel.add(new MyLabel(porpd.getReturnpurchase().getFormatteddate()));
            valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_RETURN")));
            valuepanel.add(new MyLabel("<HTML><FONT COLOR=RED>" + 
                    porpd.getReturnpurchase().getFormattedtotal_after_tax() + "</FONT></HTML>"));
            textfield.setValue(porpd.getAmount());
            
        }
        else{
            PaymentOutExpensesDraftEntity poed = 
                        (PaymentOutExpensesDraftEntity)paymentoutinvoicedraftentity;
            valuepanel.add(new MyLabel(poed.getExpenses().getNumber()));
            valuepanel.add(new MyLabel(poed.getExpenses().getFormatteddate()));
            valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_EXPENSES")));
            valuepanel.add(new MyLabel(poed.getExpenses().getFormattedtotal_after_tax()));
            textfield.setValue(poed.getAmount());
            
        }
        
        valuepanel.add(textfield);
        
        jcb = new JCheckBox();
        
        add(jcb);
        add(valuepanel);
        
        popupmenu = new JPopupMenu();
        popupmenu.add(reset = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_RESET"), 
                new MyImageIcon().getImage("net/purnama/image/Reset_16.png")));
        popupmenu.add(menuitem = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_VIEW"), 
                new MyImageIcon().getImage("net/purnama/image/View_16.png")));
        
        menuitem.addActionListener((ActionEvent e) -> {
            if(paymentoutinvoicedraftentity  instanceof PaymentOutInvoicePurchaseDraftEntity){
                PaymentOutInvoicePurchaseDraftEntity piisd = 
                        (PaymentOutInvoicePurchaseDraftEntity)paymentoutinvoicedraftentity;
                
                InvoicePurchaseDetail isd = new InvoicePurchaseDetail(piisd.getInvoicepurchase().getId());
                
//                new InvoiceViewDialog(
//                        isd.getLowerPanel(),
//                        GlobalFields.PROPERTIES.getProperty("LABEL_HEADER_INVOICEPURCHASE"),
//                        piisd.getInvoicepurchase().getId()).setVisible(true);
            }
            else if(paymentoutinvoicedraftentity  instanceof PaymentOutReturnPurchaseDraftEntity){
                PaymentOutReturnPurchaseDraftEntity pirsd = 
                        (PaymentOutReturnPurchaseDraftEntity)paymentoutinvoicedraftentity;
                
                ReturnPurchaseDetail isd = new ReturnPurchaseDetail(pirsd.getReturnpurchase().getId());
                
//                new InvoiceViewDialog(
//                        isd.getLowerPanel(),
//                        GlobalFields.PROPERTIES.getProperty("LABEL_HEADER_RETURNPURCHASE"),
//                        pirsd.getReturnpurchase().getId()).setVisible(true);
            }
            else{
                PaymentOutExpensesDraftEntity poed = 
                        (PaymentOutExpensesDraftEntity)paymentoutinvoicedraftentity;
                
                ExpensesDetail isd = new ExpensesDetail(poed.getExpenses().getId());
                
//                new InvoiceViewDialog(
//                        isd.getLowerPanel(),
//                        GlobalFields.PROPERTIES.getProperty("LABEL_HEADER_EXPENSES"),
//                        poed.getExpenses().getId()).setVisible(true);
            }
        });
        
        addMouseListener(this);
        
        textfield.getDocument().addDocumentListener(this);
    }

    public boolean isSelected(){
        return jcb.isSelected();
    }
    
    public void setSelected(boolean value){
        jcb.setSelected(value);
    }
    
    public void setTextFieldValue(double value){
        textfield.setValue(value);
    }
    
    public double getTextFieldValue(){
        return GlobalFunctions.convertToDouble(textfield.getText());
    }
    
    public void setTextFieldEditable(boolean value){
        textfield.setEditable(value);
    }
    
    public double getDifference(){
        if(paymentoutinvoicedraftentity  instanceof PaymentOutInvoicePurchaseDraftEntity){
            PaymentOutInvoicePurchaseDraftEntity piisd = 
                        (PaymentOutInvoicePurchaseDraftEntity)paymentoutinvoicedraftentity;
            
            return piisd.getInvoicepurchase().getRemaining() - piisd.getAmount();
        }
        else if(paymentoutinvoicedraftentity  instanceof PaymentOutReturnPurchaseDraftEntity){
            PaymentOutReturnPurchaseDraftEntity pirsd = 
                        (PaymentOutReturnPurchaseDraftEntity)paymentoutinvoicedraftentity;
            
            return pirsd.getReturnpurchase().getRemaining() - pirsd.getAmount();
        }
        else{
            PaymentOutExpensesDraftEntity poed = 
                        (PaymentOutExpensesDraftEntity)paymentoutinvoicedraftentity;
            
            return poed.getExpenses().getRemaining() - poed.getAmount();
        }
    }
    
    public void setAmount(){
        paymentoutinvoicedraftentity.setAmount(GlobalFunctions.convertToDouble(textfield.getText()));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            popupmenu.show(this, e.getX(), e.getY());
        }
        else if(SwingUtilities.isLeftMouseButton(e)){
            jcb.setSelected(!jcb.isSelected());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBorder(null);
    }
    
    public PaymentOutInvoiceDraftEntity getPaymentOutInvoiceDraft(){
        return paymentoutinvoicedraftentity;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        setAmount();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        setAmount();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        setAmount();
    }
}
