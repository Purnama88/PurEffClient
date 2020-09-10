/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.table.ItemExpensesDraftTablePanel;
import net.purnama.gui.inner.detail.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.home.ExpensesDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.ExpensesDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ExpensesDraftDetail extends InvoiceDraftDetailPanel{
    
    private final DatePanel duedatepanel;
    private final LabelDecimalTextFieldPanel ratepanel;
    
    private final PartnerComboBoxPanel partnerpanel;
    private final CurrencyComboBoxPanel currencypanel;
    private final NumberingComboBoxPanel numberingpanel;
    
    private final ItemExpensesDraftTablePanel itemexpensesdrafttablepanel;
    
    private ExpensesDraftEntity expensesdraft;
    
    private final String id;
    
    public ExpensesDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_EXPENSESDRAFTDETAIL"));
        
        this.id = id;
        
        partnerpanel = new PartnerComboBoxPanel(false, false, true);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                true);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        numberingpanel = new NumberingComboBoxPanel(4);
        currencypanel = new CurrencyComboBoxPanel();
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, true, this);
        
        rightdetailpanel.add(numberingpanel);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        itemexpensesdrafttablepanel = new ItemExpensesDraftTablePanel(id, discountsubtotalpanel);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemexpensesdrafttablepanel);
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            itemexpensesdrafttablepanel.submitItemExpensesDraftList();
            itemexpensesdrafttablepanel.submitDeletedItemExpensesDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            itemexpensesdrafttablepanel.submitItemExpensesDraftList();
            itemexpensesdrafttablepanel.submitDeletedItemExpensesDraftList();
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_CLOSEINVOICE"),
                "", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    close();
                }
        });
        
        deletebutton.addActionListener((ActionEvent e) -> {
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_DELETEINVOICE"),
                "", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    delete();
                }
        });
        
        load();
    }
    
    @Override
    protected final void close(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.get("closeExpensesDraft?id=" + expensesdraft.getId());

                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel("");
                    String output = response.getEntity(String.class);
                        
                    JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus(), 
                    JOptionPane.ERROR_MESSAGE);
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);
                    changepanel(output);
                }
            }
        };
        saveworker.execute();
    }
    
    @Override
    protected final void save(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                expensesdraft.setCurrency(currencypanel.getComboBoxValue());
                expensesdraft.setDate(datepanel.getCalendar());
                expensesdraft.setDiscount(discountsubtotalpanel.getDiscount());
                expensesdraft.setDuedate(duedatepanel.getCalendar());
                expensesdraft.setFreight(expensespanel.getFreight());
                expensesdraft.setNote(notepanel.getNote());
                expensesdraft.setNumbering(numberingpanel.getComboBoxValue());
                expensesdraft.setPartner(partnerpanel.getComboBoxValue());
                expensesdraft.setRate(ratepanel.getTextFieldValue());
                expensesdraft.setRounding(expensespanel.getRounding());
                expensesdraft.setStatus(true);
                expensesdraft.setSubtotal(discountsubtotalpanel.getSubtotal());
                expensesdraft.setTax(expensespanel.getTax());

                response = RestClient.put("updateExpensesDraft", expensesdraft);

                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    upperpanel.setNotifLabel("");
                }
            }
        };
        saveworker.execute();
    }
    
    public final void load(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getExpensesDraft?id=" + id);
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    upperpanel.setNotifLabel("");
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        expensesdraft = mapper.readValue(output, ExpensesDraftEntity.class);
                        
                        datepanel.setDate(expensesdraft.getDate());
                        warehousepanel.setTextFieldValue(expensesdraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(expensesdraft.getId());
                        partnerpanel.setComboBoxValue(expensesdraft.getPartner());
                        duedatepanel.setDate(expensesdraft.getDuedate());
                        numberingpanel.setComboBoxValue(expensesdraft.getNumbering());
                        currencypanel.setComboBoxValue(expensesdraft.getCurrency());
                        ratepanel.setTextFieldValue(expensesdraft.getRate());
                        notepanel.setNote(expensesdraft.getNote());
                        expensespanel.setRounding(expensesdraft.getRounding());
                        expensespanel.setFreight(expensesdraft.getFreight());
                        expensespanel.setTax(expensesdraft.getTax());
                        
                        discountsubtotalpanel.setSubtotal(expensesdraft.getSubtotal());
                        discountsubtotalpanel.setDiscount(expensesdraft.getDiscount());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new ExpensesDraftHome());
    }

    @Override
    protected final void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deleteExpensesDraft?id=" + expensesdraft.getId());

                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    upperpanel.setNotifLabel("");
                    home();
                }
            }
        };
        saveworker.execute();
    }

    @Override
    protected void changepanel(String id) {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
        getAncestorOfClass(MainTabbedPane.class, this);

        tabbedPane.changeTabPanel(getIndex(), new ExpensesDetail(id));
    }
    
    @Override
    public void refresh(){
        partnerpanel.refresh();
        currencypanel.refresh();
        numberingpanel.refresh();
        load();
    }
}
