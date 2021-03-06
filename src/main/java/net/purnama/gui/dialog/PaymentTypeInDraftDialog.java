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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
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
import net.purnama.model.transactional.draft.PaymentTypeInDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentTypeInDraftTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInDraftDialog extends MyDialog{
    
    private final JScrollPane scrollpane;
    
    private final JPanel totalpanel;
    
    private final MyTable table;
    
    private final JTabbedPane tabbedpane;
    
    private final JPanel mainpanel, detailpanel;
    
    private final CashPanel cashpanel;
    private final TransferPanel transferpanel;
    private final CreditCardPanel creditcardpanel;
    private final CheckPanel checkpanel;
    
    private final PaymentTypeInDraftTableModel paymenttypeindrafttablemodel;
    
    private final LabelDecimalTextFieldPanel amountpanel, remainingpanel;
    
    private final JPopupMenu popupmenu;
    
    private final JMenuItem menuitemdelete;
    
    private ArrayList<PaymentTypeInDraftEntity> paymenttypeindraftlist;
    
    private final String paymentdraftid;
    
    private double amount;
    
    public PaymentTypeInDraftDialog(String paymentdraftid, double tamount, double remaining){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_MANAGEPAYMENT"), 1200, 460);
        
        this.amount = tamount;
        this.paymentdraftid = paymentdraftid;
        
        mainpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        paymenttypeindraftlist = new ArrayList<>();
        
        detailpanel = new JPanel();
        
        detailpanel.setPreferredSize(new Dimension(750, 350));
        detailpanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        
        scrollpane = new JScrollPane();
        
        scrollpane.setPreferredSize(new Dimension(750, 300));
        
        table = new MyTable();
        
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Point p = e.getPoint();
		if(SwingUtilities.isRightMouseButton(e)){
                    int rowNumber = table.rowAtPoint( p );
                    ListSelectionModel model = table.getSelectionModel();
                    model.setSelectionInterval( rowNumber, rowNumber );
		}
            }
        });
        
        popupmenu = new JPopupMenu();
        menuitemdelete = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_DELETE"),
                new MyImageIcon().getImage("net/purnama/image/Delete_16.png"));
        popupmenu.add(menuitemdelete);
        
        table.setComponentPopupMenu(popupmenu);
        
        paymenttypeindrafttablemodel = new PaymentTypeInDraftTableModel(paymenttypeindraftlist);
        
        table.setModel(paymenttypeindrafttablemodel);
        
        scrollpane.getViewport().add(table, null);
        
        detailpanel.add(scrollpane);
        
        totalpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        remainingpanel = new LabelDecimalTextFieldPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_REMAINING"), remaining, false);
        
        amountpanel = new LabelDecimalTextFieldPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_AMOUNT"), amount, false);
        
        totalpanel.setPreferredSize(new Dimension(750, 50));
        totalpanel.add(remainingpanel);
        totalpanel.add(new MyLabel("   "));
        totalpanel.add(amountpanel);
        
        detailpanel.add(totalpanel);
        
        tabbedpane = new JTabbedPane();
        
        cashpanel = new CashPanel(paymenttypeindrafttablemodel, paymentdraftid, amountpanel, remainingpanel);
        tabbedpane.add(cashpanel, GlobalFields.PROPERTIES.getProperty("LABEL_CASH"));
        transferpanel = new TransferPanel(paymenttypeindrafttablemodel, paymentdraftid, amountpanel, remainingpanel);
        tabbedpane.add(transferpanel, GlobalFields.PROPERTIES.getProperty("LABEL_TRANSFER"));
        creditcardpanel = new CreditCardPanel(paymenttypeindrafttablemodel, paymentdraftid, amountpanel, remainingpanel);
        tabbedpane.add(creditcardpanel, GlobalFields.PROPERTIES.getProperty("LABEL_CREDITCARD"));
        checkpanel = new CheckPanel(paymenttypeindrafttablemodel, paymentdraftid, amountpanel, remainingpanel);
        tabbedpane.add(checkpanel, GlobalFields.PROPERTIES.getProperty("LABEL_CHEQUE"));
        
        mainpanel.add(tabbedpane);
        mainpanel.add(detailpanel);
        
        box.add(mainpanel);
        box.add(submitpanel);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            int index = table.convertRowIndexToModel(table.getSelectedRow());
            double deleteamount = paymenttypeindrafttablemodel.deleteRow(index);
            amountpanel.setTextFieldValue(amountpanel.getTextFieldValue() - deleteamount);
            remainingpanel.setTextFieldValue(remainingpanel.getTextFieldValue() + deleteamount);
        });
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            amount = amountpanel.getTextFieldValue();
            save();
            delete();
            dispose();
        });
        
        load();
        
    }
    
    public double showDialog(){
        setVisible(true);
        return amount;
    }
    
    public final void delete(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deletePaymentTypeInDraftList" ,
                        paymenttypeindrafttablemodel.getDeletedPaymentTypeInDraftList());
                
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
    
    public final void save(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("savePaymentTypeInDraftList", paymenttypeindrafttablemodel.getPaymentTypeInDraftList());
                
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
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentTypeInDraftList?paymentid=" + paymentdraftid);
                
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
                        paymenttypeindraftlist = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentTypeInDraftEntity.class));
                        
                        paymenttypeindrafttablemodel.setPaymentTypeInDraftList(paymenttypeindraftlist);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}
