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
import net.purnama.gui.inner.detail.table.ItemReturnPurchaseDraftTablePanel;
import net.purnama.gui.inner.detail.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.home.ReturnPurchaseDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ReturnPurchaseDraftDetail extends InvoiceDraftDetailPanel{
    
    private final DatePanel duedatepanel;
    private final LabelDecimalTextFieldPanel ratepanel;
    
    private final NumberingComboBoxPanel numberingpanel;
    private final PartnerComboBoxPanel partnerpanel;
    private final CurrencyComboBoxPanel currencypanel;
    
    private final ItemReturnPurchaseDraftTablePanel itemreturnpurchasedrafttablepanel;
    
    private ReturnPurchaseDraftEntity returnpurchasedraft;
    
    private final String id;
    
    public ReturnPurchaseDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_RETURNPURCHASEDRAFTDETAIL"));
        
        this.id = id;
        
        partnerpanel = new PartnerComboBoxPanel(false, true, false);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                true);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        numberingpanel = new NumberingComboBoxPanel(19);
        currencypanel = new CurrencyComboBoxPanel();
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, true, this);
        
        rightdetailpanel.add(numberingpanel);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        itemreturnpurchasedrafttablepanel = new ItemReturnPurchaseDraftTablePanel(id, discountsubtotalpanel);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemreturnpurchasedrafttablepanel);
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            itemreturnpurchasedrafttablepanel.submitItemReturnPurchaseDraftList();
            itemreturnpurchasedrafttablepanel.submitDeletedItemReturnPurchaseDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            itemreturnpurchasedrafttablepanel.submitItemReturnPurchaseDraftList();
            itemreturnpurchasedrafttablepanel.submitDeletedItemReturnPurchaseDraftList();
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

                response = RestClient.get("closeReturnPurchaseDraft?id=" + returnpurchasedraft.getId());

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

                returnpurchasedraft.setCurrency(currencypanel.getComboBoxValue());
                returnpurchasedraft.setDate(datepanel.getCalendar());
                returnpurchasedraft.setDiscount(discountsubtotalpanel.getDiscount());
                returnpurchasedraft.setDuedate(duedatepanel.getCalendar());
                returnpurchasedraft.setFreight(expensespanel.getFreight());
                returnpurchasedraft.setNote(notepanel.getNote());
                returnpurchasedraft.setNumbering(numberingpanel.getComboBoxValue());
                returnpurchasedraft.setPartner(partnerpanel.getComboBoxValue());
                returnpurchasedraft.setRate(ratepanel.getTextFieldValue());
                returnpurchasedraft.setRounding(expensespanel.getRounding());
                returnpurchasedraft.setStatus(true);
                returnpurchasedraft.setSubtotal(discountsubtotalpanel.getSubtotal());
                returnpurchasedraft.setTax(expensespanel.getTax());

                response = RestClient.put("updateReturnPurchaseDraft", returnpurchasedraft);

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
                
                response = RestClient.get("getReturnPurchaseDraft?id=" + id);
                
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
                        returnpurchasedraft = mapper.readValue(output, ReturnPurchaseDraftEntity.class);
                        
                        notepanel.setNote(returnpurchasedraft.getNote());
                        datepanel.setDate(returnpurchasedraft.getDate());
                        warehousepanel.setTextFieldValue(returnpurchasedraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(returnpurchasedraft.getId());
                        partnerpanel.setComboBoxValue(returnpurchasedraft.getPartner());
                        duedatepanel.setDate(returnpurchasedraft.getDuedate());
                        numberingpanel.setComboBoxValue(returnpurchasedraft.getNumbering());
                        currencypanel.setComboBoxValue(returnpurchasedraft.getCurrency());
                        ratepanel.setTextFieldValue(returnpurchasedraft.getRate());
                        expensespanel.setRounding(returnpurchasedraft.getRounding());
                        expensespanel.setFreight(returnpurchasedraft.getFreight());
                        expensespanel.setTax(returnpurchasedraft.getTax());
                        
                        discountsubtotalpanel.setSubtotal(returnpurchasedraft.getSubtotal());
                        discountsubtotalpanel.setDiscount(returnpurchasedraft.getDiscount());
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
        
        tabbedPane.changeTabPanel(getIndex(), new ReturnPurchaseDraftHome());
    }

    @Override
    protected final void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deleteReturnPurchaseDraft?id=" + returnpurchasedraft.getId());

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
    public void refresh(){
        partnerpanel.refresh();
        currencypanel.refresh();
        numberingpanel.refresh();
        load();
    }
    
    @Override
    protected void changepanel(String id) {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
        getAncestorOfClass(MainTabbedPane.class, this);

        tabbedPane.changeTabPanel(getIndex(), new ReturnPurchaseDetail(id));
    }
}
