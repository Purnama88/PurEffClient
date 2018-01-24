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
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
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
public abstract class InvoiceDraftDetailPanel extends MyPanel implements DocumentListener{

    protected final JPanel detailpanel, leftdetailpanel, middledetailpanel, rightdetailpanel;
    
    protected final JTabbedPane tabbedpane;
    
    protected final JPanel summarypanel, leftsummarypanel, 
            middlesummarypanel, 
            rightsummarypanel,
            buttonpanel, leftbuttonpanel, rightbuttonpanel, temppanel;
    
    protected final UpperPanel upperpanel;
    
    protected final MyButton closebutton, savebutton, deletebutton, discardbutton;
    
    protected final NotePanel notepanel;
    
    protected final DatePanel datepanel;
    
    protected final LabelTextFieldPanel warehousepanel, idpanel;
    
    protected final DiscountSubtotalPanel discountsubtotalpanel;
    protected final ExpensesPanel expensespanel;
    protected final TotalPanel totalpanel;
    
//    protected final JProgressBar progressbar;
    
    public InvoiceDraftDetailPanel(String name) {
        super(name);
        
        upperpanel = new UpperPanel();
        
        detailpanel = new JPanel(new GridLayout(1, 3, 5, 0));
        detailpanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        detailpanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftdetailpanel = new JPanel(new GridLayout(3, 1));
        middledetailpanel = new JPanel(new GridLayout(3, 1));
        rightdetailpanel = new JPanel(new GridLayout(3, 1));
        
        datepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DATE"),
                true);
        
        warehousepanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_WAREHOUSE"),
            "", false, this);
        
        idpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ID"),
            "", false, this);
        
        leftdetailpanel.add(datepanel);
        leftdetailpanel.add(warehousepanel);
        leftdetailpanel.add(idpanel);
    
        detailpanel.add(leftdetailpanel);
        detailpanel.add(middledetailpanel);
        detailpanel.add(rightdetailpanel);
        
        tabbedpane = new JTabbedPane();
        tabbedpane.setAlignmentX(Component.LEFT_ALIGNMENT);
    
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
        
        middlesummarypanel = new JPanel(new GridLayout(3, 1));
        
        leftsummarypanel = new JPanel(new BorderLayout());
        temppanel = new JPanel();
        temppanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE")));
        leftsummarypanel.add(temppanel, BorderLayout.LINE_START);
        notepanel = new NotePanel("", true, this);
        leftsummarypanel.add(notepanel, BorderLayout.CENTER);
        
//        progressbar = new JProgressBar();
//        progressbar.setIndeterminate(true);
//        progressbar.setVisible(false);
        
//        leftsummarypanel.add(progressbar, BorderLayout.SOUTH);
        
        rightsummarypanel = new JPanel(new GridLayout(3, 1));
        
        rightsummarypanel.add(discountsubtotalpanel);
        rightsummarypanel.add(expensespanel);
        rightsummarypanel.add(totalpanel);
        
        summarypanel.add(leftsummarypanel);
        summarypanel.add(rightsummarypanel);
        
        deletebutton  = new MyButton(new MyImageIcon().getImage("net/purnama/image/Delete_16.png"), 
                GlobalFields.PROPERTIES.getProperty("LABEL_DELETE"), 100, 24);
        savebutton  = new MyButton(new MyImageIcon().getImage("net/purnama/image/Save_16.png"), 
                GlobalFields.PROPERTIES.getProperty("LABEL_SAVE"), 100, 24);
        closebutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Close_16.png"),
                GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), 100, 24);
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
        
        rightbuttonpanel.add(deletebutton);
        rightbuttonpanel.add(closebutton);
        
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
    public void changedUpdate(DocumentEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        tabbedPane.setTitleAt(getIndex(), getPendingName());
        setState(MyPanel.PENDING);
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        tabbedPane.setTitleAt(getIndex(), getPendingName());
        setState(MyPanel.PENDING);
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        tabbedPane.setTitleAt(getIndex(), getPendingName());
        setState(MyPanel.PENDING);
    }
    
    protected abstract void home();
    protected abstract void save();
    protected abstract void close();
    protected abstract void delete();
    
    protected abstract void changepanel(String id);
    
    @Override
    public abstract void refresh();
}
