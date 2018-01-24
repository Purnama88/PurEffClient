/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.controller;

import net.purnama.model.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseOutDraftController {
    
    public ItemInvoiceWarehouseOutDraftEntity createEmptyItemInvoiceWarehouseOutDraft(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft){
        ItemInvoiceWarehouseOutDraftEntity newiis = new ItemInvoiceWarehouseOutDraftEntity();
        newiis.setQuantity(1);
        newiis.setInvoicewarehouseoutdraft(invoicewarehouseoutdraft);
        
        return newiis;
    }
    
}
