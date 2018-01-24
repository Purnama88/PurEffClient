/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.ExpensesPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.detail.util.NotePanel;
import net.purnama.gui.inner.detail.util.TotalPanel;
import net.purnama.gui.inner.detail.util.UpperPanel;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyPanel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public abstract class InvoiceDetailPanel extends MyPanel{

    protected final JPanel detailpanel, leftdetailpanel, middledetailpanel, rightdetailpanel;
    
    protected final JTabbedPane tabbedpane;
    
    protected final MyButton savebutton, cancelbutton, closebutton, mailbutton, discardbutton;
    
    protected final JPanel summarypanel, leftsummarypanel, 
            rightsummarypanel, 
            buttonpanel, leftbuttonpanel, rightbuttonpanel,
            temppanel;
    
    protected final UpperPanel upperpanel;
    
    protected final DatePanel datepanel;
    
    protected final LabelTextFieldPanel warehousepanel, draftidpanel, numberingpanel;
    
    protected final NotePanel notepanel;
    
    protected final DiscountSubtotalPanel discountsubtotalpanel;
    protected final ExpensesPanel expensespanel;
    protected final TotalPanel totalpanel;
    
    public InvoiceDetailPanel(String name) {
        super(name);
        
        upperpanel = new UpperPanel();
        upperpanel.addPrintPreviewButton();
        
        detailpanel = new JPanel(new GridLayout(1, 3, 5, 0));
        detailpanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        detailpanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftdetailpanel = new JPanel(new GridLayout(3, 1));
        middledetailpanel = new JPanel(new GridLayout(3, 1));
        rightdetailpanel = new JPanel(new GridLayout(3, 1));
        
        datepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DATE"),
                false);
        
        warehousepanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_WAREHOUSE"),
            "", false);
        
        draftidpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFTID"),
            "", false);
        
        leftdetailpanel.add(datepanel);
        leftdetailpanel.add(warehousepanel);
        leftdetailpanel.add(draftidpanel);
        
        numberingpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NUMBERING"),
            "", false, null);
        
        rightdetailpanel.add(numberingpanel);
        
        detailpanel.add(leftdetailpanel);
        detailpanel.add(middledetailpanel);
        detailpanel.add(rightdetailpanel);
        
        tabbedpane = new JTabbedPane();
        
        discountsubtotalpanel = new DiscountSubtotalPanel();
        expensespanel = new ExpensesPanel();
        totalpanel = new TotalPanel();
        
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
                double subtotal = discountsubtotalpanel.getSubtotal();
                double discount = discountsubtotalpanel.getDiscount();
                double expenses = expensespanel.getTotalExpenses();

                double total = subtotal - discount + expenses;
                
                totalpanel.setTotal(
                        total
                );
            }
        };
        
        discountsubtotalpanel.setDocumentListener(documentListener);
        expensespanel.setDocumentListener(documentListener);
        
        summarypanel = new JPanel(new GridLayout(1, 2, 5, 0));
        summarypanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        summarypanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        summarypanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 100));
        summarypanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        summarypanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftsummarypanel = new JPanel(new BorderLayout());
        temppanel = new JPanel();
        temppanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE")));
        leftsummarypanel.add(temppanel, BorderLayout.LINE_START);
        notepanel = new NotePanel("", true, null);
        leftsummarypanel.add(notepanel, BorderLayout.CENTER);
        
        rightsummarypanel = new JPanel(new GridLayout(3, 1));
        
        rightsummarypanel.add(discountsubtotalpanel);
        rightsummarypanel.add(expensespanel);
        rightsummarypanel.add(totalpanel);

        summarypanel.add(leftsummarypanel);
        summarypanel.add(rightsummarypanel);
        
        cancelbutton  = new MyButton(new MyImageIcon().getImage("net/purnama/image/Delete_16.png"),
                GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), 100, 24);
        savebutton  = new MyButton(new MyImageIcon().getImage("net/purnama/image/Save_16.png"), 
                GlobalFields.PROPERTIES.getProperty("LABEL_SAVE"), 100, 24);
        closebutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Close_16.png"),
                GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), 100, 24);
        mailbutton  = new MyButton(new MyImageIcon().getImage("net/purnama/image/Mail_16.png"), 
                GlobalFields.PROPERTIES.getProperty("LABEL_MAIL"), 100, 24);
        discardbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Discard_16.png"),
                GlobalFields.PROPERTIES.getProperty("LABEL_DISCARD"), 100, 24);
        
        buttonpanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonpanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        buttonpanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonpanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonpanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonpanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftbuttonpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightbuttonpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        leftbuttonpanel.add(savebutton);
        leftbuttonpanel.add(discardbutton);
        
        rightbuttonpanel.add(mailbutton);
        rightbuttonpanel.add(closebutton);
        rightbuttonpanel.add(cancelbutton);
        
        buttonpanel.add(leftbuttonpanel);
        buttonpanel.add(rightbuttonpanel);
        
        add(upperpanel);
        add(detailpanel);
        add(tabbedpane);
        add(summarypanel);
        add(buttonpanel);
        
        upperpanel.getHomeButton().addActionListener((ActionEvent e) -> {
            home();
        });
        
        upperpanel.getPrintPreviewButton().addActionListener((ActionEvent e) -> {
            printpreview();
        });
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        cancelbutton.addActionListener((ActionEvent e) -> {
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_CANCELINVOICE"),
                "", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                cancel();
            }
        });
        
        discardbutton.addActionListener((ActionEvent e) -> {
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_DISCARDINVOICE"),
                "", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION){
                home();
            }
        });
    }
    
    @Override
    public void refresh(){
        load();
    }
    
    protected abstract void save();
    protected abstract void load();
    protected abstract void home();
    protected abstract void cancel();
    protected abstract void mail();
    
    @Override
    public abstract void printpreview();
}
