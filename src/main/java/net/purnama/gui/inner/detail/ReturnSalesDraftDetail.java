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
import net.purnama.gui.inner.detail.table.ItemReturnSalesDraftTablePanel;
import net.purnama.gui.inner.detail.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.home.ReturnSalesDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ReturnSalesDraftDetail extends InvoiceDraftDetailPanel{
    
    private final DatePanel duedatepanel;
    private final LabelDecimalTextFieldPanel ratepanel;
    
    private final NumberingComboBoxPanel numberingpanel;
    private final PartnerComboBoxPanel partnerpanel;
    private final CurrencyComboBoxPanel currencypanel;
    
    private final ItemReturnSalesDraftTablePanel itemreturnsalesdrafttablepanel;
    
    private ReturnSalesDraftEntity returnsalesdraft;
    
    private final String id;
    
    public ReturnSalesDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_RETURNSALESDRAFTDETAIL"));
        
        this.id = id;
        
        partnerpanel = new PartnerComboBoxPanel(true, false, false);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                true);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        numberingpanel = new NumberingComboBoxPanel(20);
        currencypanel = new CurrencyComboBoxPanel();
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, true, this);
        
        rightdetailpanel.add(numberingpanel);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        itemreturnsalesdrafttablepanel = new ItemReturnSalesDraftTablePanel(id, discountsubtotalpanel);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemreturnsalesdrafttablepanel);
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            itemreturnsalesdrafttablepanel.submitItemReturnSalesDraftList();
            itemreturnsalesdrafttablepanel.submitDeletedItemReturnSalesDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            itemreturnsalesdrafttablepanel.submitItemReturnSalesDraftList();
            itemreturnsalesdrafttablepanel.submitDeletedItemReturnSalesDraftList();
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

                response = RestClient.get("closeReturnSalesDraft?id=" + returnsalesdraft.getId());

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

                returnsalesdraft.setCurrency(currencypanel.getComboBoxValue());
                returnsalesdraft.setDate(datepanel.getCalendar());
                returnsalesdraft.setDiscount(discountsubtotalpanel.getDiscount());
                returnsalesdraft.setDuedate(duedatepanel.getCalendar());
                returnsalesdraft.setFreight(expensespanel.getFreight());
                returnsalesdraft.setNote(notepanel.getNote());
                returnsalesdraft.setNumbering(numberingpanel.getComboBoxValue());
                returnsalesdraft.setPartner(partnerpanel.getComboBoxValue());
                returnsalesdraft.setRate(ratepanel.getTextFieldValue());
                returnsalesdraft.setRounding(expensespanel.getRounding());
                returnsalesdraft.setStatus(true);
                returnsalesdraft.setSubtotal(discountsubtotalpanel.getSubtotal());
                returnsalesdraft.setTax(expensespanel.getTax());

                response = RestClient.put("updateReturnSalesDraft", returnsalesdraft);

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
                
                response = RestClient.get("getReturnSalesDraft?id=" + id);
                
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
                        returnsalesdraft = mapper.readValue(output, ReturnSalesDraftEntity.class);
                        
                        notepanel.setNote(returnsalesdraft.getNote());
                        datepanel.setDate(returnsalesdraft.getDate());
                        warehousepanel.setTextFieldValue(returnsalesdraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(returnsalesdraft.getId());
                        partnerpanel.setComboBoxValue(returnsalesdraft.getPartner());
                        duedatepanel.setDate(returnsalesdraft.getDuedate());
                        numberingpanel.setComboBoxValue(returnsalesdraft.getNumbering());
                        currencypanel.setComboBoxValue(returnsalesdraft.getCurrency());
                        ratepanel.setTextFieldValue(returnsalesdraft.getRate());
                        expensespanel.setRounding(returnsalesdraft.getRounding());
                        expensespanel.setFreight(returnsalesdraft.getFreight());
                        expensespanel.setTax(returnsalesdraft.getTax());
                        
                        discountsubtotalpanel.setSubtotal(returnsalesdraft.getSubtotal());
                        discountsubtotalpanel.setDiscount(returnsalesdraft.getDiscount());
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
        
        tabbedPane.changeTabPanel(getIndex(), new ReturnSalesDraftHome());
    }

    @Override
    protected final void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deleteReturnSalesDraft?id=" + returnsalesdraft.getId());

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

        tabbedPane.changeTabPanel(getIndex(), new ReturnSalesDetail(id));
    }

    @Override
    public void refresh(){
        partnerpanel.refresh();
        currencypanel.refresh();
        numberingpanel.refresh();
        load();
    }
}
