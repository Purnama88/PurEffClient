/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import java.io.Serializable;
import net.purnama.model.transactional.InvoicePurchaseEntity;

/**
 *
 * @author Purnama
 */
public class PaymentOutInvoicePurchaseDraftEntity extends PaymentOutInvoiceDraftEntity{
    
    private InvoicePurchaseEntity invoicepurchase;

    public InvoicePurchaseEntity getInvoicepurchase() {
        return invoicepurchase;
    }

    public void setInvoicepurchase(InvoicePurchaseEntity invoicepurchase) {
        this.invoicepurchase = invoicepurchase;
    }
}
