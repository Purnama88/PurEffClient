/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseOutDraftEntity extends ItemInvoiceDraftEntity {
    
    private InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft;
    
    public InvoiceWarehouseOutDraftEntity getInvoicewarehouseoutdraft() {
        return invoicewarehouseoutdraft;
    }

    public void setInvoicewarehouseoutdraft(InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft) {
        this.invoicewarehouseoutdraft = invoicewarehouseoutdraft;
    }
}
