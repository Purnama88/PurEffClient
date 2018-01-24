/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RoleEntity implements Serializable{
    private String id;
    private String name;
    private String note;
    private boolean status;
    private boolean defaultrole;
    
    private Calendar lastmodified;
    private UserEntity lastmodifiedby;

    private boolean user_home;
    private boolean user_add;
    private boolean user_edit;
    private boolean user_detail;
    
    private boolean role_home;
    private boolean role_add;
    private boolean role_edit;
    
    private boolean menu_home;
    private boolean menu_detail;
    
    private boolean numbering_add;
    private boolean numbering_edit;
    
    private boolean currency_home;
    private boolean currency_add;
    private boolean currency_edit;
    private boolean currency_detail;
    
    private boolean rate_add;
    private boolean rate_detail;
    
    private boolean warehouse_home;
    private boolean warehouse_add;
    private boolean warehouse_edit;
    
    private boolean numberingname_home;
    private boolean numberingname_add;
    private boolean numberingname_edit;
    
    private boolean partnertype_home;
    private boolean partnertype_add;
    private boolean partnertype_edit;
    
    private boolean partner_home;
    private boolean partner_add;
    private boolean partner_edit;
    private boolean partner_detail;
            
    private boolean uom_home;
    private boolean uom_add;
    private boolean uom_edit;
    
    private boolean itemgroup_home;
    private boolean itemgroup_add;
    private boolean itemgroup_edit;
    
    private boolean item_home;
    private boolean item_add;
    private boolean item_edit;
    private boolean item_detail;
    
    private boolean buyprice_edit;
    private boolean sellprice_edit;
    
    private boolean invoicesales_draft;
    private boolean invoicesales_home;
    private boolean invoicesales_close;
    private boolean invoicesales_cancel;
    
    private boolean returnsales_draft;
    private boolean returnsales_home;
    private boolean returnsales_close;
    private boolean returnsales_cancel;
    
    private boolean delivery_draft;
    private boolean delivery_home;
    private boolean delivery_close;
    private boolean delivery_cancel;
    
    private boolean invoicepurchase_draft;
    private boolean invoicepurchase_home;
    private boolean invoicepurchase_close;
    private boolean invoicepurchase_cancel;
    
    private boolean returnpurchase_draft;
    private boolean returnpurchase_home;
    private boolean returnpurchase_close;
    private boolean returnpurchase_cancel;
    
    private boolean expenses_draft;
    private boolean expenses_home;
    private boolean expenses_close;
    private boolean expenses_cancel;
    
    private boolean adjustment_draft;
    private boolean adjustment_home;
    private boolean adjustment_close;
    private boolean adjustment_cancel;
    
    private boolean invoicewarehousein_draft;
    private boolean invoicewarehousein_home;
    private boolean invoicewarehousein_close;
    private boolean invoicewarehousein_cancel;
    
    private boolean invoicewarehouseout_draft;
    private boolean invoicewarehouseout_home;
    private boolean invoicewarehouseout_close;
    private boolean invoicewarehouseout_cancel;
    
    private boolean paymentin_draft;
    private boolean paymentin_home;
    private boolean paymentin_close;
    private boolean paymentin_cancel;
    
    private boolean paymentout_draft;
    private boolean paymentout_home;
    private boolean paymentout_close;
    private boolean paymentout_cancel;
    
    private boolean paymenttypein_home;
    private boolean paymenttypein_accept;
    private boolean paymenttypein_invalidate;
    
    private boolean paymenttypeout_home;
    private boolean paymenttypeout_accept;
    private boolean paymenttypeout_invalidate;
//    private boolean user_read;
//    private boolean user_write;
//    
//    private boolean role_read;
//    private boolean role_write;
//    
//    private boolean numberingname_read;
//    private boolean numberingname_write;
//    
//    private boolean numbering_read;
//    private boolean numbering_write;
//    
//    private boolean warehouse_write;
//    private boolean warehouse_read;
//    
//    private boolean partnertype_read;
//    private boolean partnertype_write;
//    
//    private boolean partner_read;
//    private boolean partner_write;
//    
//    private boolean uom_read;
//    private boolean uom_write;
//    
//    private boolean itemgroup_read;
//    private boolean itemgroup_write;
//    
//    private boolean item_read;
//    private boolean item_write;
//    
//    private boolean currency_read;
//    private boolean currency_write;
//    
//    private boolean invoicesales_read;
//    private boolean invoicesales_write;
//    
//    private boolean invoicepurchase_read;
//    private boolean invoicepurchase_write;
//    
//    private boolean expenses_read;
//    private boolean expenses_write;
//    
//    private boolean returnsales_read;
//    private boolean returnsales_write;
//    
//    private boolean returnpurchase_read;
//    private boolean returnpurchase_write;
//    
//    private boolean adjustment_read;
//    private boolean adjustment_write;
//    
//    private boolean invoicewarehousein_read;
//    private boolean invoicewarehousein_write;
//    
//    private boolean invoicewarehouseout_read;
//    private boolean invoicewarehouseout_write;
//    
//    private boolean delivery_read;
//    private boolean delivery_write;
//    
//    private boolean paymentin_read;
//    private boolean paymentin_write;
//    
//    private boolean paymentout_read;
//    private boolean paymentout_write;
//    
//    private boolean paymenttype_read;
//    private boolean paymenttype_write;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDefaultrole() {
        return defaultrole;
    }

    public void setDefaultrole(boolean defaultrole) {
        this.defaultrole = defaultrole;
    }

    public Calendar getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Calendar lastmodified) {
        this.lastmodified = lastmodified;
    }

    public UserEntity getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(UserEntity lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }
    
    @Override
    public String toString(){
        return getName();
    }

    public boolean isUser_home() {
        return user_home;
    }

    public void setUser_home(boolean user_home) {
        this.user_home = user_home;
    }

    public boolean isUser_add() {
        return user_add;
    }

    public void setUser_add(boolean user_add) {
        this.user_add = user_add;
    }

    public boolean isUser_edit() {
        return user_edit;
    }

    public void setUser_edit(boolean user_edit) {
        this.user_edit = user_edit;
    }

    public boolean isUser_detail() {
        return user_detail;
    }

    public void setUser_detail(boolean user_detail) {
        this.user_detail = user_detail;
    }

    public boolean isRole_home() {
        return role_home;
    }

    public void setRole_home(boolean role_home) {
        this.role_home = role_home;
    }

    public boolean isRole_add() {
        return role_add;
    }

    public void setRole_add(boolean role_add) {
        this.role_add = role_add;
    }

    public boolean isRole_edit() {
        return role_edit;
    }

    public void setRole_edit(boolean role_edit) {
        this.role_edit = role_edit;
    }

    public boolean isMenu_home() {
        return menu_home;
    }

    public void setMenu_home(boolean menu_home) {
        this.menu_home = menu_home;
    }

    public boolean isMenu_detail() {
        return menu_detail;
    }

    public void setMenu_detail(boolean menu_detail) {
        this.menu_detail = menu_detail;
    }

    public boolean isNumbering_add() {
        return numbering_add;
    }

    public void setNumbering_add(boolean numbering_add) {
        this.numbering_add = numbering_add;
    }

    public boolean isNumbering_edit() {
        return numbering_edit;
    }

    public void setNumbering_edit(boolean numbering_edit) {
        this.numbering_edit = numbering_edit;
    }

    public boolean isCurrency_home() {
        return currency_home;
    }

    public void setCurrency_home(boolean currency_home) {
        this.currency_home = currency_home;
    }

    public boolean isCurrency_add() {
        return currency_add;
    }

    public void setCurrency_add(boolean currency_add) {
        this.currency_add = currency_add;
    }

    public boolean isCurrency_edit() {
        return currency_edit;
    }

    public void setCurrency_edit(boolean currency_edit) {
        this.currency_edit = currency_edit;
    }

    public boolean isCurrency_detail() {
        return currency_detail;
    }

    public void setCurrency_detail(boolean currency_detail) {
        this.currency_detail = currency_detail;
    }

    public boolean isRate_add() {
        return rate_add;
    }

    public void setRate_add(boolean rate_add) {
        this.rate_add = rate_add;
    }

    public boolean isRate_detail() {
        return rate_detail;
    }

    public void setRate_detail(boolean rate_detail) {
        this.rate_detail = rate_detail;
    }

    public boolean isWarehouse_home() {
        return warehouse_home;
    }

    public void setWarehouse_home(boolean warehouse_home) {
        this.warehouse_home = warehouse_home;
    }

    public boolean isWarehouse_add() {
        return warehouse_add;
    }

    public void setWarehouse_add(boolean warehouse_add) {
        this.warehouse_add = warehouse_add;
    }

    public boolean isWarehouse_edit() {
        return warehouse_edit;
    }

    public void setWarehouse_edit(boolean warehouse_edit) {
        this.warehouse_edit = warehouse_edit;
    }

    public boolean isNumberingname_home() {
        return numberingname_home;
    }

    public void setNumberingname_home(boolean numberingname_home) {
        this.numberingname_home = numberingname_home;
    }

    public boolean isNumberingname_add() {
        return numberingname_add;
    }

    public void setNumberingname_add(boolean numberingname_add) {
        this.numberingname_add = numberingname_add;
    }

    public boolean isNumberingname_edit() {
        return numberingname_edit;
    }

    public void setNumberingname_edit(boolean numberingname_edit) {
        this.numberingname_edit = numberingname_edit;
    }

    public boolean isPartnertype_home() {
        return partnertype_home;
    }

    public void setPartnertype_home(boolean partnertype_home) {
        this.partnertype_home = partnertype_home;
    }

    public boolean isPartnertype_add() {
        return partnertype_add;
    }

    public void setPartnertype_add(boolean partnertype_add) {
        this.partnertype_add = partnertype_add;
    }

    public boolean isPartnertype_edit() {
        return partnertype_edit;
    }

    public void setPartnertype_edit(boolean partnertype_edit) {
        this.partnertype_edit = partnertype_edit;
    }

    public boolean isPartner_home() {
        return partner_home;
    }

    public void setPartner_home(boolean partner_home) {
        this.partner_home = partner_home;
    }

    public boolean isPartner_add() {
        return partner_add;
    }

    public void setPartner_add(boolean partner_add) {
        this.partner_add = partner_add;
    }

    public boolean isPartner_edit() {
        return partner_edit;
    }

    public void setPartner_edit(boolean partner_edit) {
        this.partner_edit = partner_edit;
    }

    public boolean isPartner_detail() {
        return partner_detail;
    }

    public void setPartner_detail(boolean partner_detail) {
        this.partner_detail = partner_detail;
    }

    public boolean isUom_home() {
        return uom_home;
    }

    public void setUom_home(boolean uom_home) {
        this.uom_home = uom_home;
    }

    public boolean isUom_add() {
        return uom_add;
    }

    public void setUom_add(boolean uom_add) {
        this.uom_add = uom_add;
    }

    public boolean isUom_edit() {
        return uom_edit;
    }

    public void setUom_edit(boolean uom_edit) {
        this.uom_edit = uom_edit;
    }

    public boolean isItemgroup_home() {
        return itemgroup_home;
    }

    public void setItemgroup_home(boolean itemgroup_home) {
        this.itemgroup_home = itemgroup_home;
    }

    public boolean isItemgroup_add() {
        return itemgroup_add;
    }

    public void setItemgroup_add(boolean itemgroup_add) {
        this.itemgroup_add = itemgroup_add;
    }

    public boolean isItemgroup_edit() {
        return itemgroup_edit;
    }

    public void setItemgroup_edit(boolean itemgroup_edit) {
        this.itemgroup_edit = itemgroup_edit;
    }

    public boolean isItem_home() {
        return item_home;
    }

    public void setItem_home(boolean item_home) {
        this.item_home = item_home;
    }

    public boolean isItem_add() {
        return item_add;
    }

    public void setItem_add(boolean item_add) {
        this.item_add = item_add;
    }

    public boolean isItem_edit() {
        return item_edit;
    }

    public void setItem_edit(boolean item_edit) {
        this.item_edit = item_edit;
    }

    public boolean isItem_detail() {
        return item_detail;
    }

    public void setItem_detail(boolean item_detail) {
        this.item_detail = item_detail;
    }

    public boolean isBuyprice_edit() {
        return buyprice_edit;
    }

    public void setBuyprice_edit(boolean buyprice_edit) {
        this.buyprice_edit = buyprice_edit;
    }

    public boolean isSellprice_edit() {
        return sellprice_edit;
    }

    public void setSellprice_edit(boolean sellprice_edit) {
        this.sellprice_edit = sellprice_edit;
    }

    public boolean isInvoicesales_draft() {
        return invoicesales_draft;
    }

    public void setInvoicesales_draft(boolean invoicesales_draft) {
        this.invoicesales_draft = invoicesales_draft;
    }

    public boolean isInvoicesales_home() {
        return invoicesales_home;
    }

    public void setInvoicesales_home(boolean invoicesales_home) {
        this.invoicesales_home = invoicesales_home;
    }

    public boolean isInvoicesales_close() {
        return invoicesales_close;
    }

    public void setInvoicesales_close(boolean invoicesales_close) {
        this.invoicesales_close = invoicesales_close;
    }

    public boolean isInvoicesales_cancel() {
        return invoicesales_cancel;
    }

    public void setInvoicesales_cancel(boolean invoicesales_cancel) {
        this.invoicesales_cancel = invoicesales_cancel;
    }

    public boolean isReturnsales_draft() {
        return returnsales_draft;
    }

    public void setReturnsales_draft(boolean returnsales_draft) {
        this.returnsales_draft = returnsales_draft;
    }

    public boolean isReturnsales_home() {
        return returnsales_home;
    }

    public void setReturnsales_home(boolean returnsales_home) {
        this.returnsales_home = returnsales_home;
    }

    public boolean isReturnsales_close() {
        return returnsales_close;
    }

    public void setReturnsales_close(boolean returnsales_close) {
        this.returnsales_close = returnsales_close;
    }

    public boolean isReturnsales_cancel() {
        return returnsales_cancel;
    }

    public void setReturnsales_cancel(boolean returnsales_cancel) {
        this.returnsales_cancel = returnsales_cancel;
    }

    public boolean isDelivery_draft() {
        return delivery_draft;
    }

    public void setDelivery_draft(boolean delivery_draft) {
        this.delivery_draft = delivery_draft;
    }

    public boolean isDelivery_home() {
        return delivery_home;
    }

    public void setDelivery_home(boolean delivery_home) {
        this.delivery_home = delivery_home;
    }

    public boolean isDelivery_close() {
        return delivery_close;
    }

    public void setDelivery_close(boolean delivery_close) {
        this.delivery_close = delivery_close;
    }

    public boolean isDelivery_cancel() {
        return delivery_cancel;
    }

    public void setDelivery_cancel(boolean delivery_cancel) {
        this.delivery_cancel = delivery_cancel;
    }

    public boolean isInvoicepurchase_draft() {
        return invoicepurchase_draft;
    }

    public void setInvoicepurchase_draft(boolean invoicepurchase_draft) {
        this.invoicepurchase_draft = invoicepurchase_draft;
    }

    public boolean isInvoicepurchase_home() {
        return invoicepurchase_home;
    }

    public void setInvoicepurchase_home(boolean invoicepurchase_home) {
        this.invoicepurchase_home = invoicepurchase_home;
    }

    public boolean isInvoicepurchase_close() {
        return invoicepurchase_close;
    }

    public void setInvoicepurchase_close(boolean invoicepurchase_close) {
        this.invoicepurchase_close = invoicepurchase_close;
    }

    public boolean isInvoicepurchase_cancel() {
        return invoicepurchase_cancel;
    }

    public void setInvoicepurchase_cancel(boolean invoicepurchase_cancel) {
        this.invoicepurchase_cancel = invoicepurchase_cancel;
    }

    public boolean isReturnpurchase_draft() {
        return returnpurchase_draft;
    }

    public void setReturnpurchase_draft(boolean returnpurchase_draft) {
        this.returnpurchase_draft = returnpurchase_draft;
    }

    public boolean isReturnpurchase_home() {
        return returnpurchase_home;
    }

    public void setReturnpurchase_home(boolean returnpurchase_home) {
        this.returnpurchase_home = returnpurchase_home;
    }

    public boolean isReturnpurchase_close() {
        return returnpurchase_close;
    }

    public void setReturnpurchase_close(boolean returnpurchase_close) {
        this.returnpurchase_close = returnpurchase_close;
    }

    public boolean isReturnpurchase_cancel() {
        return returnpurchase_cancel;
    }

    public void setReturnpurchase_cancel(boolean returnpurchase_cancel) {
        this.returnpurchase_cancel = returnpurchase_cancel;
    }

    public boolean isExpenses_draft() {
        return expenses_draft;
    }

    public void setExpenses_draft(boolean expenses_draft) {
        this.expenses_draft = expenses_draft;
    }

    public boolean isExpenses_home() {
        return expenses_home;
    }

    public void setExpenses_home(boolean expenses_home) {
        this.expenses_home = expenses_home;
    }

    public boolean isExpenses_close() {
        return expenses_close;
    }

    public void setExpenses_close(boolean expenses_close) {
        this.expenses_close = expenses_close;
    }

    public boolean isExpenses_cancel() {
        return expenses_cancel;
    }

    public void setExpenses_cancel(boolean expenses_cancel) {
        this.expenses_cancel = expenses_cancel;
    }

    public boolean isAdjustment_draft() {
        return adjustment_draft;
    }

    public void setAdjustment_draft(boolean adjustment_draft) {
        this.adjustment_draft = adjustment_draft;
    }

    public boolean isAdjustment_home() {
        return adjustment_home;
    }

    public void setAdjustment_home(boolean adjustment_home) {
        this.adjustment_home = adjustment_home;
    }

    public boolean isAdjustment_close() {
        return adjustment_close;
    }

    public void setAdjustment_close(boolean adjustment_close) {
        this.adjustment_close = adjustment_close;
    }

    public boolean isAdjustment_cancel() {
        return adjustment_cancel;
    }

    public void setAdjustment_cancel(boolean adjustment_cancel) {
        this.adjustment_cancel = adjustment_cancel;
    }

    public boolean isInvoicewarehousein_draft() {
        return invoicewarehousein_draft;
    }

    public void setInvoicewarehousein_draft(boolean invoicewarehousein_draft) {
        this.invoicewarehousein_draft = invoicewarehousein_draft;
    }

    public boolean isInvoicewarehousein_home() {
        return invoicewarehousein_home;
    }

    public void setInvoicewarehousein_home(boolean invoicewarehousein_home) {
        this.invoicewarehousein_home = invoicewarehousein_home;
    }

    public boolean isInvoicewarehousein_close() {
        return invoicewarehousein_close;
    }

    public void setInvoicewarehousein_close(boolean invoicewarehousein_close) {
        this.invoicewarehousein_close = invoicewarehousein_close;
    }

    public boolean isInvoicewarehousein_cancel() {
        return invoicewarehousein_cancel;
    }

    public void setInvoicewarehousein_cancel(boolean invoicewarehousein_cancel) {
        this.invoicewarehousein_cancel = invoicewarehousein_cancel;
    }

    public boolean isInvoicewarehouseout_draft() {
        return invoicewarehouseout_draft;
    }

    public void setInvoicewarehouseout_draft(boolean invoicewarehouseout_draft) {
        this.invoicewarehouseout_draft = invoicewarehouseout_draft;
    }

    public boolean isInvoicewarehouseout_home() {
        return invoicewarehouseout_home;
    }

    public void setInvoicewarehouseout_home(boolean invoicewarehouseout_home) {
        this.invoicewarehouseout_home = invoicewarehouseout_home;
    }

    public boolean isInvoicewarehouseout_close() {
        return invoicewarehouseout_close;
    }

    public void setInvoicewarehouseout_close(boolean invoicewarehouseout_close) {
        this.invoicewarehouseout_close = invoicewarehouseout_close;
    }

    public boolean isInvoicewarehouseout_cancel() {
        return invoicewarehouseout_cancel;
    }

    public void setInvoicewarehouseout_cancel(boolean invoicewarehouseout_cancel) {
        this.invoicewarehouseout_cancel = invoicewarehouseout_cancel;
    }

    public boolean isPaymentin_draft() {
        return paymentin_draft;
    }

    public void setPaymentin_draft(boolean paymentin_draft) {
        this.paymentin_draft = paymentin_draft;
    }

    public boolean isPaymentin_home() {
        return paymentin_home;
    }

    public void setPaymentin_home(boolean paymentin_home) {
        this.paymentin_home = paymentin_home;
    }

    public boolean isPaymentin_close() {
        return paymentin_close;
    }

    public void setPaymentin_close(boolean paymentin_close) {
        this.paymentin_close = paymentin_close;
    }

    public boolean isPaymentin_cancel() {
        return paymentin_cancel;
    }

    public void setPaymentin_cancel(boolean paymentin_cancel) {
        this.paymentin_cancel = paymentin_cancel;
    }

    public boolean isPaymentout_draft() {
        return paymentout_draft;
    }

    public void setPaymentout_draft(boolean paymentout_draft) {
        this.paymentout_draft = paymentout_draft;
    }

    public boolean isPaymentout_home() {
        return paymentout_home;
    }

    public void setPaymentout_home(boolean paymentout_home) {
        this.paymentout_home = paymentout_home;
    }

    public boolean isPaymentout_close() {
        return paymentout_close;
    }

    public void setPaymentout_close(boolean paymentout_close) {
        this.paymentout_close = paymentout_close;
    }

    public boolean isPaymentout_cancel() {
        return paymentout_cancel;
    }

    public void setPaymentout_cancel(boolean paymentout_cancel) {
        this.paymentout_cancel = paymentout_cancel;
    }

    public boolean isPaymenttypein_home() {
        return paymenttypein_home;
    }

    public void setPaymenttypein_home(boolean paymenttypein_home) {
        this.paymenttypein_home = paymenttypein_home;
    }

    public boolean isPaymenttypein_accept() {
        return paymenttypein_accept;
    }

    public void setPaymenttypein_accept(boolean paymenttypein_accept) {
        this.paymenttypein_accept = paymenttypein_accept;
    }

    public boolean isPaymenttypein_invalidate() {
        return paymenttypein_invalidate;
    }

    public void setPaymenttypein_invalidate(boolean paymenttypein_invalidate) {
        this.paymenttypein_invalidate = paymenttypein_invalidate;
    }

    public boolean isPaymenttypeout_home() {
        return paymenttypeout_home;
    }

    public void setPaymenttypeout_home(boolean paymenttypeout_home) {
        this.paymenttypeout_home = paymenttypeout_home;
    }

    public boolean isPaymenttypeout_accept() {
        return paymenttypeout_accept;
    }

    public void setPaymenttypeout_accept(boolean paymenttypeout_accept) {
        this.paymenttypeout_accept = paymenttypeout_accept;
    }

    public boolean isPaymenttypeout_invalidate() {
        return paymenttypeout_invalidate;
    }

    public void setPaymenttypeout_invalidate(boolean paymenttypeout_invalidate) {
        this.paymenttypeout_invalidate = paymenttypeout_invalidate;
    }

//    public boolean isUser_read() {
//        return user_read;
//    }
//
//    public void setUser_read(boolean user_read) {
//        this.user_read = user_read;
//    }
//
//    public boolean isUser_write() {
//        return user_write;
//    }
//
//    public void setUser_write(boolean user_write) {
//        this.user_write = user_write;
//    }
//
//    public boolean isRole_read() {
//        return role_read;
//    }
//
//    public void setRole_read(boolean role_read) {
//        this.role_read = role_read;
//    }
//
//    public boolean isRole_write() {
//        return role_write;
//    }
//
//    public void setRole_write(boolean role_write) {
//        this.role_write = role_write;
//    }
//
//    public boolean isNumberingname_read() {
//        return numberingname_read;
//    }
//
//    public void setNumberingname_read(boolean numberingname_read) {
//        this.numberingname_read = numberingname_read;
//    }
//
//    public boolean isNumberingname_write() {
//        return numberingname_write;
//    }
//
//    public void setNumberingname_write(boolean numberingname_write) {
//        this.numberingname_write = numberingname_write;
//    }
//
//    public boolean isNumbering_read() {
//        return numbering_read;
//    }
//
//    public void setNumbering_read(boolean numbering_read) {
//        this.numbering_read = numbering_read;
//    }
//
//    public boolean isNumbering_write() {
//        return numbering_write;
//    }
//
//    public void setNumbering_write(boolean numbering_write) {
//        this.numbering_write = numbering_write;
//    }
//
//    public boolean isWarehouse_write() {
//        return warehouse_write;
//    }
//
//    public void setWarehouse_write(boolean warehouse_write) {
//        this.warehouse_write = warehouse_write;
//    }
//
//    public boolean isWarehouse_read() {
//        return warehouse_read;
//    }
//
//    public void setWarehouse_read(boolean warehouse_read) {
//        this.warehouse_read = warehouse_read;
//    }
//
//    public boolean isPartnertype_read() {
//        return partnertype_read;
//    }
//
//    public void setPartnertype_read(boolean partnertype_read) {
//        this.partnertype_read = partnertype_read;
//    }
//
//    public boolean isPartnertype_write() {
//        return partnertype_write;
//    }
//
//    public void setPartnertype_write(boolean partnertype_write) {
//        this.partnertype_write = partnertype_write;
//    }
//
//    public boolean isPartner_read() {
//        return partner_read;
//    }
//
//    public void setPartner_read(boolean partner_read) {
//        this.partner_read = partner_read;
//    }
//
//    public boolean isPartner_write() {
//        return partner_write;
//    }
//
//    public void setPartner_write(boolean partner_write) {
//        this.partner_write = partner_write;
//    }
//
//    public boolean isUom_read() {
//        return uom_read;
//    }
//
//    public void setUom_read(boolean uom_read) {
//        this.uom_read = uom_read;
//    }
//
//    public boolean isUom_write() {
//        return uom_write;
//    }
//
//    public void setUom_write(boolean uom_write) {
//        this.uom_write = uom_write;
//    }
//
//    public boolean isItemgroup_read() {
//        return itemgroup_read;
//    }
//
//    public void setItemgroup_read(boolean itemgroup_read) {
//        this.itemgroup_read = itemgroup_read;
//    }
//
//    public boolean isItemgroup_write() {
//        return itemgroup_write;
//    }
//
//    public void setItemgroup_write(boolean itemgroup_write) {
//        this.itemgroup_write = itemgroup_write;
//    }
//
//    public boolean isItem_read() {
//        return item_read;
//    }
//
//    public void setItem_read(boolean item_read) {
//        this.item_read = item_read;
//    }
//
//    public boolean isItem_write() {
//        return item_write;
//    }
//
//    public void setItem_write(boolean item_write) {
//        this.item_write = item_write;
//    }
//
//    public boolean isCurrency_read() {
//        return currency_read;
//    }
//
//    public void setCurrency_read(boolean currency_read) {
//        this.currency_read = currency_read;
//    }
//
//    public boolean isCurrency_write() {
//        return currency_write;
//    }
//
//    public void setCurrency_write(boolean currency_write) {
//        this.currency_write = currency_write;
//    }
//
//    public boolean isInvoicesales_read() {
//        return invoicesales_read;
//    }
//
//    public void setInvoicesales_read(boolean invoicesales_read) {
//        this.invoicesales_read = invoicesales_read;
//    }
//
//    public boolean isInvoicesales_write() {
//        return invoicesales_write;
//    }
//
//    public void setInvoicesales_write(boolean invoicesales_write) {
//        this.invoicesales_write = invoicesales_write;
//    }
//
//    public boolean isInvoicepurchase_read() {
//        return invoicepurchase_read;
//    }
//
//    public void setInvoicepurchase_read(boolean invoicepurchase_read) {
//        this.invoicepurchase_read = invoicepurchase_read;
//    }
//
//    public boolean isInvoicepurchase_write() {
//        return invoicepurchase_write;
//    }
//
//    public void setInvoicepurchase_write(boolean invoicepurchase_write) {
//        this.invoicepurchase_write = invoicepurchase_write;
//    }
//
//    public boolean isExpenses_read() {
//        return expenses_read;
//    }
//
//    public void setExpenses_read(boolean expenses_read) {
//        this.expenses_read = expenses_read;
//    }
//
//    public boolean isExpenses_write() {
//        return expenses_write;
//    }
//
//    public void setExpenses_write(boolean expenses_write) {
//        this.expenses_write = expenses_write;
//    }
//
//    public boolean isReturnsales_read() {
//        return returnsales_read;
//    }
//
//    public void setReturnsales_read(boolean returnsales_read) {
//        this.returnsales_read = returnsales_read;
//    }
//
//    public boolean isReturnsales_write() {
//        return returnsales_write;
//    }
//
//    public void setReturnsales_write(boolean returnsales_write) {
//        this.returnsales_write = returnsales_write;
//    }
//
//    public boolean isReturnpurchase_read() {
//        return returnpurchase_read;
//    }
//
//    public void setReturnpurchase_read(boolean returnpurchase_read) {
//        this.returnpurchase_read = returnpurchase_read;
//    }
//
//    public boolean isReturnpurchase_write() {
//        return returnpurchase_write;
//    }
//
//    public void setReturnpurchase_write(boolean returnpurchase_write) {
//        this.returnpurchase_write = returnpurchase_write;
//    }
//
//    public boolean isAdjustment_read() {
//        return adjustment_read;
//    }
//
//    public void setAdjustment_read(boolean adjustment_read) {
//        this.adjustment_read = adjustment_read;
//    }
//
//    public boolean isAdjustment_write() {
//        return adjustment_write;
//    }
//
//    public void setAdjustment_write(boolean adjustment_write) {
//        this.adjustment_write = adjustment_write;
//    }
//
//    public boolean isInvoicewarehousein_read() {
//        return invoicewarehousein_read;
//    }
//
//    public void setInvoicewarehousein_read(boolean invoicewarehousein_read) {
//        this.invoicewarehousein_read = invoicewarehousein_read;
//    }
//
//    public boolean isInvoicewarehousein_write() {
//        return invoicewarehousein_write;
//    }
//
//    public void setInvoicewarehousein_write(boolean invoicewarehousein_write) {
//        this.invoicewarehousein_write = invoicewarehousein_write;
//    }
//
//    public boolean isInvoicewarehouseout_read() {
//        return invoicewarehouseout_read;
//    }
//
//    public void setInvoicewarehouseout_read(boolean invoicewarehouseout_read) {
//        this.invoicewarehouseout_read = invoicewarehouseout_read;
//    }
//
//    public boolean isInvoicewarehouseout_write() {
//        return invoicewarehouseout_write;
//    }
//
//    public void setInvoicewarehouseout_write(boolean invoicewarehouseout_write) {
//        this.invoicewarehouseout_write = invoicewarehouseout_write;
//    }
//
//    public boolean isDelivery_read() {
//        return delivery_read;
//    }
//
//    public void setDelivery_read(boolean delivery_read) {
//        this.delivery_read = delivery_read;
//    }
//
//    public boolean isDelivery_write() {
//        return delivery_write;
//    }
//
//    public void setDelivery_write(boolean delivery_write) {
//        this.delivery_write = delivery_write;
//    }
//
//    public boolean isPaymentin_read() {
//        return paymentin_read;
//    }
//
//    public void setPaymentin_read(boolean paymentin_read) {
//        this.paymentin_read = paymentin_read;
//    }
//
//    public boolean isPaymentin_write() {
//        return paymentin_write;
//    }
//
//    public void setPaymentin_write(boolean paymentin_write) {
//        this.paymentin_write = paymentin_write;
//    }
//
//    public boolean isPaymentout_read() {
//        return paymentout_read;
//    }
//
//    public void setPaymentout_read(boolean paymentout_read) {
//        this.paymentout_read = paymentout_read;
//    }
//
//    public boolean isPaymentout_write() {
//        return paymentout_write;
//    }
//
//    public void setPaymentout_write(boolean paymentout_write) {
//        this.paymentout_write = paymentout_write;
//    }
//
//    public boolean isPaymenttype_read() {
//        return paymenttype_read;
//    }
//
//    public void setPaymenttype_read(boolean paymenttype_read) {
//        this.paymenttype_read = paymenttype_read;
//    }
//
//    public boolean isPaymenttype_write() {
//        return paymenttype_write;
//    }
//
//    public void setPaymenttype_write(boolean paymenttype_write) {
//        this.paymenttype_write = paymenttype_write;
//    }
    
    @JsonIgnore
    public String getFormattedLastmodified(){
        return GlobalFields.PROPERTIES.getProperty("LABEL_LASTMODIFIED") 
                + " " + getLastmodified().getTime();
    }
}
