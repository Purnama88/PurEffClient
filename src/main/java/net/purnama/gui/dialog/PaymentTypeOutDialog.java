/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import net.purnama.gui.dialog.util.CashPanel;
import net.purnama.gui.dialog.util.CheckPanel;
import net.purnama.gui.dialog.util.CreditCardPanel;
import net.purnama.gui.dialog.util.TransferPanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.library.MyDialog;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTable;
import net.purnama.model.transactional.PaymentTypeOutEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentTypeOutTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeOutDialog extends MyDialog{
    
    private final JTabbedPane tabbedpane;
    
    private final JPanel mainpanel, tablepanel, totalpanel;
    
    private CashPanel cashpanel;
    private TransferPanel transferpanel;
    private CreditCardPanel creditcardpanel;
    private CheckPanel checkpanel;
    
    private final JScrollPane scrollpane;
    
    private final MyTable table;
    
    private final LabelDecimalTextFieldPanel initialamountpanel, amountpanel;
    
    private final JPopupMenu popupmenu;
    
    private final JMenuItem menuiteminvalidate;
    
    private final PaymentTypeOutTableModel paymenttypeouttablemodel;
    
    private ArrayList<PaymentTypeOutEntity> paymenttypeoutlist;
    
    private final String paymentid;
    
    public PaymentTypeOutDialog(String paymentid, double amount, boolean status){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_MANAGEPAYMENT"), 1200, 460);
        
        this.paymentid = paymentid;
        
        mainpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        tablepanel = new JPanel();
        tablepanel.setPreferredSize(new Dimension(750, 350));
        tablepanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        
        scrollpane = new JScrollPane();
        scrollpane.setPreferredSize(new Dimension(750, 300));
        
        paymenttypeoutlist = new ArrayList<>();
        
        paymenttypeouttablemodel = new PaymentTypeOutTableModel(paymenttypeoutlist);
        
        table = new MyTable();
        
        table.setModel(paymenttypeouttablemodel);
        
        popupmenu = new JPopupMenu();
        menuiteminvalidate = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_INVALIDATE"),
                new MyImageIcon().getImage("net/purnama/image/Delete_16.png"));
        popupmenu.add(menuiteminvalidate);
        
        table.setComponentPopupMenu(popupmenu);
        
        scrollpane.getViewport().add(table, null);
        
        tablepanel.add(scrollpane);
        
        totalpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        totalpanel.setPreferredSize(new Dimension(750, 50));
        
        initialamountpanel = new LabelDecimalTextFieldPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_INITIALAMOUNT"), amount, false);
        
        amountpanel = new LabelDecimalTextFieldPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_AMOUNT"), amount, false);
        
        totalpanel.add(initialamountpanel);
        totalpanel.add(new MyLabel("   "));
        totalpanel.add(amountpanel);
        
        tablepanel.add(totalpanel);
        
        tabbedpane = new JTabbedPane();
        
        if(status){
            cashpanel = new CashPanel(paymenttypeouttablemodel, paymentid, amountpanel);
            tabbedpane.add(cashpanel, GlobalFields.PROPERTIES.getProperty("LABEL_CASH"));
            transferpanel = new TransferPanel(paymenttypeouttablemodel, paymentid, amountpanel);
            tabbedpane.add(transferpanel, GlobalFields.PROPERTIES.getProperty("LABEL_TRANSFER"));
            creditcardpanel = new CreditCardPanel(paymenttypeouttablemodel, paymentid, amountpanel);
            tabbedpane.add(creditcardpanel, GlobalFields.PROPERTIES.getProperty("LABEL_CREDITCARD"));
            checkpanel = new CheckPanel(paymenttypeouttablemodel, paymentid, amountpanel);
            tabbedpane.add(checkpanel, GlobalFields.PROPERTIES.getProperty("LABEL_CHEQUE"));

            mainpanel.add(tabbedpane);
        }
        mainpanel.add(tablepanel);
        
        box.add(mainpanel);
        box.add(submitpanel);
        
        menuiteminvalidate.addActionListener((ActionEvent e) -> {
            int index = table.convertRowIndexToModel(table.getSelectedRow());
            PaymentTypeOutEntity ptie = paymenttypeouttablemodel.getPaymentTypeOut(index);
            if(ptie.isValid()){
                ptie.setValid(false);
                paymenttypeouttablemodel.fireTableDataChanged();
                amountpanel.setTextFieldValue(amountpanel.getTextFieldValue() - ptie.getAmount());
            }
        });
        
        submitbutton.addActionListener((ActionEvent e) -> {
            if(amountpanel.getTextFieldValue() != initialamountpanel.getTextFieldValue()){
                
            }
            else{
                save();
                dispose();
            }
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentTypeOutList?paymentid=" + paymentid);
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        paymenttypeoutlist = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentTypeOutEntity.class));
                        
                        paymenttypeouttablemodel.setPaymentTypeOutList(paymenttypeoutlist);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void save(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("savePaymentTypeOutList", 
                        paymenttypeouttablemodel.getPaymentTypeOutList());
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                }
            }
        };
        
        worker.execute();
    }
    
    public void showDialog(){
        setVisible(true);
    }
}
