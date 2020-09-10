/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutInvoiceEntity {
    
    private String id;
    
    private double amount;
    
    private PaymentOutEntity paymentout;
    
    private String invoice_id;
    
    private String invoice_warehouse;
    
    private String invoice_date;
    
    private String invoice_duedate;
    
    private String invoice_currency;
    
    private String invoice_total;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentOutEntity getPaymentout() {
        return paymentout;
    }

    public void setPaymentout(PaymentOutEntity paymentout) {
        this.paymentout = paymentout;
    }
    
    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getInvoice_warehouse() {
        return invoice_warehouse;
    }

    public void setInvoice_warehouse(String invoice_warehouse) {
        this.invoice_warehouse = invoice_warehouse;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getInvoice_duedate() {
        return invoice_duedate;
    }

    public void setInvoice_duedate(String invoice_duedate) {
        this.invoice_duedate = invoice_duedate;
    }

    public String getInvoice_currency() {
        return invoice_currency;
    }

    public void setInvoice_currency(String invoice_currency) {
        this.invoice_currency = invoice_currency;
    }

    public String getInvoice_total() {
        return invoice_total;
    }

    public void setInvoice_total(String invoice_total) {
        this.invoice_total = invoice_total;
    }
    
    @JsonIgnore
    public String getFormattedAmount(){
        return GlobalFields.NUMBERFORMAT.format(getAmount());
    }
}
