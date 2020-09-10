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
import net.purnama.gui.inner.detail.InvoiceSalesDetail;
import net.purnama.gui.inner.detail.ReturnSalesDetail;
import net.purnama.gui.library.MyDecimalTextField;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.transactional.draft.PaymentInInvoiceDraftEntity;
import net.purnama.model.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import net.purnama.model.transactional.draft.PaymentInReturnSalesDraftEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemPaymentPanel2 extends JPanel implements MouseListener, DocumentListener{
    
    private final JPanel valuepanel;
    
    private final JCheckBox jcb;
    
    private final JPopupMenu popupmenu;
    
    private final JMenuItem menuitem, reset;
    
    private final MyDecimalTextField textfield;
    
    private final PaymentInInvoiceDraftEntity paymentininvoicedraftentity;
    
    public ItemPaymentPanel2(PaymentInInvoiceDraftEntity paymentininvoicedraftentity){
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.paymentininvoicedraftentity = paymentininvoicedraftentity;
        
        setMinimumSize(new Dimension(542, 55));
        setMaximumSize(new Dimension(542, 55));
        setPreferredSize(new Dimension(542, 55));
        
        valuepanel = new JPanel(new GridLayout(2, 6, 5, 0));
        valuepanel.setPreferredSize(new Dimension(500, 45));
    
        textfield = new MyDecimalTextField(0, 25);
        
        if(paymentininvoicedraftentity  instanceof PaymentInInvoiceSalesDraftEntity){
                PaymentInInvoiceSalesDraftEntity piisd = 
                        (PaymentInInvoiceSalesDraftEntity)paymentininvoicedraftentity;
            valuepanel.add(new MyLabel(piisd.getInvoicesales().getNumber()));
            valuepanel.add(new MyLabel(piisd.getInvoicesales().getFormatteddate()));
            valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_INVOICE")));
            valuepanel.add(new MyLabel(piisd.getInvoicesales().getFormattedtotal_after_tax()));
            textfield.setValue(piisd.getAmount());
        }
        else{
            PaymentInReturnSalesDraftEntity pirsd = 
                        (PaymentInReturnSalesDraftEntity)paymentininvoicedraftentity;
            valuepanel.add(new MyLabel(pirsd.getReturnsales().getNumber()));
            valuepanel.add(new MyLabel(pirsd.getReturnsales().getFormatteddate()));
            valuepanel.add(new MyLabel("<HTML><FONT COLOR=RED>" 
                    + GlobalFields.PROPERTIES.getProperty("LABEL_RETURN")
                    + "</FONT></HTML>"));
            valuepanel.add(new MyLabel(pirsd.getReturnsales().getFormattedtotal_after_tax()));
            textfield.setValue(pirsd.getAmount());
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
            if(paymentininvoicedraftentity  instanceof PaymentInInvoiceSalesDraftEntity){
                PaymentInInvoiceSalesDraftEntity piisd = 
                        (PaymentInInvoiceSalesDraftEntity)paymentininvoicedraftentity;
                
                InvoiceSalesDetail isd = new InvoiceSalesDetail(piisd.getInvoicesales().getId());
                
//                new InvoiceViewDialog(
//                        isd.getLowerPanel(),
//                        GlobalFields.PROPERTIES.getProperty("LABEL_HEADER_INVOICESALES"),
//                        piisd.getInvoicesales().getId()).setVisible(true);
            }
            else{
                PaymentInReturnSalesDraftEntity pirsd = 
                        (PaymentInReturnSalesDraftEntity)paymentininvoicedraftentity;
                
                ReturnSalesDetail isd = new ReturnSalesDetail(pirsd.getReturnsales().getId());
                
//                new InvoiceViewDialog(
//                        isd.getLowerPanel(),
//                        GlobalFields.PROPERTIES.getProperty("LABEL_HEADER_RETURNSALES"),
//                        pirsd.getReturnsales().getId()).setVisible(true);
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
        if(paymentininvoicedraftentity  instanceof PaymentInInvoiceSalesDraftEntity){
            PaymentInInvoiceSalesDraftEntity piisd = 
                        (PaymentInInvoiceSalesDraftEntity)paymentininvoicedraftentity;
            
            return piisd.getInvoicesales().getRemaining() - piisd.getAmount();
        }
        else{
            PaymentInReturnSalesDraftEntity pirsd = 
                        (PaymentInReturnSalesDraftEntity)paymentininvoicedraftentity;
            
            return pirsd.getReturnsales().getRemaining() - pirsd.getAmount();
        }
    }
    
    public void setAmount(){
        paymentininvoicedraftentity.setAmount(GlobalFunctions.convertToDouble(textfield.getText()));
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
    
    public PaymentInInvoiceDraftEntity getPaymentInInvoiceDraft(){
        return paymentininvoicedraftentity;
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
