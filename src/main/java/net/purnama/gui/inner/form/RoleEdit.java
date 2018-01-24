/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyPanel;
import net.purnama.model.RoleEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RoleEdit extends RoleAdd{
    
    private RoleEntity role;
    
    private final String id;
    
    public RoleEdit(String id){
        super();
        
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ROLEEDIT"));
        
        this.id = id;
        
        load();
        
    }
    
    @Override
    protected void submit() {
        if(validateinput()){
            
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                    submitpanel.loading();
                    
                    role.setName(namepanel.getTextFieldValue());
                    role.setStatus(statuspanel.getSelectedValue());
                    role.setDefaultrole(false);
                    role.setStatus(statuspanel.getSelectedValue());
                    role.setNote(notepanel.getTextAreaValue());
                    
                    role.setUser_home(user_home.isSelected());
                    role.setUser_add(user_add.isSelected());
                    role.setUser_edit(user_edit.isSelected());
                    role.setUser_detail(user_detail.isSelected());
                    
                    role.setRole_home(role_home.isSelected());
                    role.setRole_add(role_add.isSelected());
                    role.setRole_edit(role_edit.isSelected());
                    
                    role.setMenu_home(menu_home.isSelected());
                    role.setMenu_detail(menu_detail.isSelected());
                    
                    role.setNumbering_add(numbering_add.isSelected());
                    role.setNumbering_edit(numbering_edit.isSelected());
                    
                    role.setCurrency_home(currency_home.isSelected());
                    role.setCurrency_add(currency_add.isSelected());
                    role.setCurrency_edit(currency_edit.isSelected());
                    role.setCurrency_detail(currency_detail.isSelected());
                    
                    role.setRate_add(rate_add.isSelected());
                    role.setRate_detail(rate_detail.isSelected());
                    
                    role.setWarehouse_home(warehouse_home.isSelected());
                    role.setWarehouse_add(warehouse_add.isSelected());
                    role.setWarehouse_edit(warehouse_edit.isSelected());
                    
                    role.setNumberingname_home(numberingname_home.isSelected());
                    role.setNumberingname_add(numberingname_add.isSelected());
                    role.setNumberingname_edit(numberingname_edit.isSelected());
                    
                    role.setPartnertype_home(partnertype_home.isSelected());
                    role.setPartnertype_add(partnertype_add.isSelected());
                    role.setPartnertype_edit(partnertype_edit.isSelected());
                    
                    role.setPartner_home(partner_home.isSelected());
                    role.setPartner_add(partner_add.isSelected());
                    role.setPartner_edit(partner_edit.isSelected());
                    role.setPartner_detail(partner_detail.isSelected());
                    
                    role.setUom_home(uom_home.isSelected());
                    role.setUom_add(uom_add.isSelected());
                    role.setUom_edit(uom_edit.isSelected());
                    
                    role.setItemgroup_home(itemgroup_home.isSelected());
                    role.setItemgroup_add(itemgroup_add.isSelected());
                    role.setItemgroup_edit(itemgroup_edit.isSelected());
                    
                    role.setItem_home(item_home.isSelected());
                    role.setItem_add(item_add.isSelected());
                    role.setItem_edit(item_edit.isSelected());
                    role.setItem_detail(item_detail.isSelected());
                    
                    role.setBuyprice_edit(buyprice_edit.isSelected());
                    
                    role.setSellprice_edit(sellprice_edit.isSelected());
                    
                    role.setInvoicesales_home(invoicesales_home.isSelected());
                    role.setInvoicesales_draft(invoicesales_draft.isSelected());
                    role.setInvoicesales_cancel(invoicesales_cancel.isSelected());
                    role.setInvoicesales_close(invoicesales_close.isSelected());
                    
                    role.setReturnsales_home(returnsales_home.isSelected());
                    role.setReturnsales_draft(returnsales_draft.isSelected());
                    role.setReturnsales_cancel(returnsales_cancel.isSelected());
                    role.setReturnsales_close(returnsales_close.isSelected());
                    
                    role.setDelivery_home(delivery_home.isSelected());
                    role.setDelivery_draft(delivery_draft.isSelected());
                    role.setDelivery_cancel(delivery_cancel.isSelected());
                    role.setDelivery_close(delivery_close.isSelected());
                    
                    role.setInvoicepurchase_home(invoicepurchase_home.isSelected());
                    role.setInvoicepurchase_draft(invoicepurchase_draft.isSelected());
                    role.setInvoicepurchase_cancel(invoicepurchase_cancel.isSelected());
                    role.setInvoicepurchase_close(invoicepurchase_close.isSelected());
                    
                    role.setReturnpurchase_home(returnpurchase_home.isSelected());
                    role.setReturnpurchase_draft(returnpurchase_draft.isSelected());
                    role.setReturnpurchase_cancel(returnpurchase_cancel.isSelected());
                    role.setReturnpurchase_close(returnpurchase_close.isSelected());
                    
                    role.setExpenses_home(expenses_home.isSelected());
                    role.setExpenses_draft(expenses_draft.isSelected());
                    role.setExpenses_cancel(expenses_cancel.isSelected());
                    role.setExpenses_close(expenses_close.isSelected());
                    
                    role.setAdjustment_home(adjustment_home.isSelected());
                    role.setAdjustment_draft(adjustment_draft.isSelected());
                    role.setAdjustment_cancel(adjustment_cancel.isSelected());
                    role.setAdjustment_close(adjustment_close.isSelected());
                    
                    role.setInvoicewarehouseout_home(invoicewarehouseout_home.isSelected());
                    role.setInvoicewarehouseout_draft(invoicewarehouseout_draft.isSelected());
                    role.setInvoicewarehouseout_cancel(invoicewarehouseout_cancel.isSelected());
                    role.setInvoicewarehouseout_close(invoicewarehouseout_close.isSelected());
                    
                    role.setInvoicewarehousein_home(invoicewarehousein_home.isSelected());
                    role.setInvoicewarehousein_draft(invoicewarehousein_draft.isSelected());
                    role.setInvoicewarehousein_cancel(invoicewarehousein_cancel.isSelected());
                    role.setInvoicewarehousein_close(invoicewarehousein_close.isSelected());
                    
                    role.setPaymentout_home(paymentout_home.isSelected());
                    role.setPaymentout_draft(paymentout_draft.isSelected());
                    role.setPaymentout_cancel(paymentout_cancel.isSelected());
                    role.setPaymentout_close(paymentout_close.isSelected());
                    
                    role.setPaymentin_home(paymentin_home.isSelected());
                    role.setPaymentin_draft(paymentin_draft.isSelected());
                    role.setPaymentin_cancel(paymentin_cancel.isSelected());
                    role.setPaymentin_close(paymentin_close.isSelected());
                    
                    role.setPaymenttypein_home(paymenttypein_home.isSelected());
                    role.setPaymenttypein_invalidate(paymenttypein_cancel.isSelected());
                    
                    role.setPaymenttypeout_home(paymenttypeout_home.isSelected());
                    role.setPaymenttypeout_invalidate(paymenttypeout_cancel.isSelected());
                    
                    clientresponse = RestClient.put("updateRole", role);
                    
                    return true;
                }
                
                
                @Override
                protected void done() {
                    submitpanel.finish();
                    
                    if(clientresponse == null){
                        upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(clientresponse.getStatus() != 200) {
                        String output = clientresponse.getEntity(String.class);
                        
                        JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                        output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                        + clientresponse.getStatus(), 
                        JOptionPane.ERROR_MESSAGE);
                    
                    }
                    else{
                        home();
                    }
                }
            };
            
            submitworker.execute();
        }
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getRole?id=" + id);
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                upperpanel.setNotifLabel(number);
                }
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
                        role = mapper.readValue(output, RoleEntity.class);
                        
                        namepanel.setTextFieldValue(role.getName());
                        statuspanel.setSelectedValue(role.isStatus());
                        notepanel.setTextAreaValue(role.getNote());
                        
                        user_home.setSelected(role.isUser_home());
                        user_add.setSelected(role.isUser_add());
                        user_edit.setSelected(role.isUser_edit());
                        user_detail.setSelected(role.isUser_detail());
                        
                        role_home.setSelected(role.isRole_home());
                        role_add.setSelected(role.isRole_add());
                        role_edit.setSelected(role.isRole_edit());
                        
                        menu_home.setSelected(role.isMenu_home());
                        menu_detail.setSelected(role.isMenu_detail());
                        
                        numbering_add.setSelected(role.isNumbering_add());
                        numbering_edit.setSelected(role.isNumbering_edit());
                        
                        currency_home.setSelected(role.isCurrency_home());
                        currency_add.setSelected(role.isCurrency_add());
                        currency_edit.setSelected(role.isCurrency_edit());
                        currency_detail.setSelected(role.isCurrency_detail());
                        
                        rate_add.setSelected(role.isRate_add());
                        rate_detail.setSelected(role.isRate_detail());
                        
                        warehouse_home.setSelected(role.isWarehouse_home());
                        warehouse_add.setSelected(role.isWarehouse_add());
                        warehouse_edit.setSelected(role.isWarehouse_edit());
                        
                        numberingname_home.setSelected(role.isNumberingname_home());
                        numberingname_add.setSelected(role.isNumberingname_add());
                        numberingname_edit.setSelected(role.isNumberingname_edit());
                        
                        partnertype_home.setSelected(role.isPartnertype_home());
                        partnertype_add.setSelected(role.isPartnertype_add());
                        partnertype_edit.setSelected(role.isPartnertype_edit());
                        
                        partner_home.setSelected(role.isPartner_home());
                        partner_add.setSelected(role.isPartner_add());
                        partner_edit.setSelected(role.isPartner_edit());
                        partner_detail.setSelected(role.isPartner_detail());
                        
                        uom_home.setSelected(role.isUom_home());
                        uom_add.setSelected(role.isUom_add());
                        uom_edit.setSelected(role.isUom_edit());
                        
                        itemgroup_home.setSelected(role.isItemgroup_home());
                        itemgroup_add.setSelected(role.isItemgroup_add());
                        itemgroup_edit.setSelected(role.isItemgroup_edit());
                        
                        item_home.setSelected(role.isItem_home());
                        item_add.setSelected(role.isItem_add());
                        item_edit.setSelected(role.isItem_edit());
                        item_detail.setSelected(role.isItem_detail());
                        
                        buyprice_edit.setSelected(role.isBuyprice_edit());
                        
                        sellprice_edit.setSelected(role.isSellprice_edit());
                        
                        invoicesales_home.setSelected(role.isInvoicesales_home());
                        invoicesales_cancel.setSelected(role.isInvoicesales_cancel());
                        invoicesales_draft.setSelected(role.isInvoicesales_draft());
                        invoicesales_close.setSelected(role.isInvoicesales_close());
                        
                        returnsales_home.setSelected(role.isReturnsales_home());
                        returnsales_cancel.setSelected(role.isReturnsales_cancel());
                        returnsales_draft.setSelected(role.isReturnsales_draft());
                        returnsales_close.setSelected(role.isReturnsales_close());
                        
                        delivery_home.setSelected(role.isDelivery_home());
                        delivery_cancel.setSelected(role.isDelivery_cancel());
                        delivery_draft.setSelected(role.isDelivery_draft());
                        delivery_close.setSelected(role.isDelivery_close());
                        
                        invoicepurchase_home.setSelected(role.isInvoicepurchase_home());
                        invoicepurchase_cancel.setSelected(role.isInvoicepurchase_cancel());
                        invoicepurchase_draft.setSelected(role.isInvoicepurchase_draft());
                        invoicepurchase_close.setSelected(role.isInvoicepurchase_close());
                        
                        returnpurchase_home.setSelected(role.isReturnpurchase_home());
                        returnpurchase_cancel.setSelected(role.isReturnpurchase_cancel());
                        returnpurchase_draft.setSelected(role.isReturnpurchase_draft());
                        returnpurchase_close.setSelected(role.isReturnpurchase_close());
                        
                        expenses_home.setSelected(role.isExpenses_home());
                        expenses_cancel.setSelected(role.isExpenses_cancel());
                        expenses_draft.setSelected(role.isExpenses_draft());
                        expenses_close.setSelected(role.isExpenses_close());
                        
                        adjustment_home.setSelected(role.isAdjustment_home());
                        adjustment_cancel.setSelected(role.isAdjustment_cancel());
                        adjustment_draft.setSelected(role.isAdjustment_draft());
                        adjustment_close.setSelected(role.isAdjustment_close());
                        
                        invoicewarehousein_home.setSelected(role.isInvoicewarehousein_home());
                        invoicewarehousein_cancel.setSelected(role.isInvoicewarehousein_cancel());
                        invoicewarehousein_draft.setSelected(role.isInvoicewarehousein_draft());
                        invoicewarehousein_close.setSelected(role.isInvoicewarehousein_close());
                        
                        invoicewarehouseout_home.setSelected(role.isInvoicewarehouseout_home());
                        invoicewarehouseout_cancel.setSelected(role.isInvoicewarehouseout_cancel());
                        invoicewarehouseout_draft.setSelected(role.isInvoicewarehouseout_draft());
                        invoicewarehouseout_close.setSelected(role.isInvoicewarehouseout_close());
                        
                        paymentin_home.setSelected(role.isPaymentin_home());
                        paymentin_cancel.setSelected(role.isPaymentin_cancel());
                        paymentin_draft.setSelected(role.isPaymentin_draft());
                        paymentin_close.setSelected(role.isPaymentin_close());
                        
                        paymentout_home.setSelected(role.isPaymentout_home());
                        paymentout_cancel.setSelected(role.isPaymentout_cancel());
                        paymentout_draft.setSelected(role.isPaymentout_draft());
                        paymentout_close.setSelected(role.isPaymentout_close());
                        
                        paymenttypein_home.setSelected(role.isPaymenttypein_home());
                        paymenttypein_cancel.setSelected(role.isPaymenttypein_invalidate());
                        
                        paymenttypeout_home.setSelected(role.isPaymenttypeout_home());
                        paymenttypeout_cancel.setSelected(role.isPaymenttypeout_invalidate());
                        
                        upperpanel.setStatusLabel(role.getFormattedLastmodified());
                        
                        setState(MyPanel.SAVED);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}
