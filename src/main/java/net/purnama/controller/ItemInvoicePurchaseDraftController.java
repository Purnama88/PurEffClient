/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoicePurchaseDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemInvoicePurchaseDraftController {
    
    public ItemInvoicePurchaseDraftEntity createEmptyItemInvoicePurchaseDraft(InvoicePurchaseDraftEntity invoicepurchasedraft){
        ItemInvoicePurchaseDraftEntity newiis = new ItemInvoicePurchaseDraftEntity();
        newiis.setQuantity(1);
        newiis.setPrice(0);
        newiis.setDiscount(0);
        newiis.setInvoicepurchasedraft(invoicepurchasedraft);
        
        return newiis;
    }
    
}
