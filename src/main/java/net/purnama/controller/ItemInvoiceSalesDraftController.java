/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceSalesDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceSalesDraftController {
    
    public ItemInvoiceSalesDraftEntity createEmptyItemInvoiceSalesDraft(InvoiceSalesDraftEntity invoicesalesdraft){
        ItemInvoiceSalesDraftEntity newiis = new ItemInvoiceSalesDraftEntity();
        newiis.setQuantity(1);
        newiis.setPrice(0);
        newiis.setDiscount(0);
        newiis.setInvoicesalesdraft(invoicesalesdraft);
        
        return newiis;
    }
    
}
