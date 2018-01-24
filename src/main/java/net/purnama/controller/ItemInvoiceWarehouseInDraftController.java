/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.controller;

import net.purnama.model.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceWarehouseInDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInDraftController {
    
    public ItemInvoiceWarehouseInDraftEntity createEmptyItemInvoiceWarehouseInDraft(InvoiceWarehouseInDraftEntity invoicewarehouseindraft){
        ItemInvoiceWarehouseInDraftEntity newiis = new ItemInvoiceWarehouseInDraftEntity();
        newiis.setQuantity(1);
        newiis.setInvoicewarehouseindraft(invoicewarehouseindraft);
        
        return newiis;
    }
    
}
