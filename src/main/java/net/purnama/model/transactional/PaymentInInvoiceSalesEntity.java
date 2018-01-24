/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional;


/**
 *
 * @author Purnama
 */
public class PaymentInInvoiceSalesEntity extends PaymentInInvoiceEntity{
    
    private InvoiceSalesEntity invoicesales;
    
    public InvoiceSalesEntity getInvoicesales() {
        return invoicesales;
    }

    public void setInvoicesales(InvoiceSalesEntity invoicesales) {
        this.invoicesales = invoicesales;
    }
}
