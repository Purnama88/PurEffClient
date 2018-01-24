/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional.draft;


/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInDraftEntity extends ItemInvoiceDraftEntity{
    
    private InvoiceWarehouseInDraftEntity invoicewarehouseindraft;
    
    public InvoiceWarehouseInDraftEntity getInvoicewarehouseindraft() {
        return invoicewarehouseindraft;
    }

    public void setInvoicewarehouseindraft(InvoiceWarehouseInDraftEntity invoicewarehouseindraft) {
        this.invoicewarehouseindraft = invoicewarehouseindraft;
    }
}
