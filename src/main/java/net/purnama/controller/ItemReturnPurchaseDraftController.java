/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.model.transactional.draft.ItemReturnPurchaseDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemReturnPurchaseDraftController {
    
    public ItemReturnPurchaseDraftEntity createEmptyItemReturnPurchaseDraft(ReturnPurchaseDraftEntity returnpurchasedraft){
        ItemReturnPurchaseDraftEntity newiis = new ItemReturnPurchaseDraftEntity();
        newiis.setQuantity(1);
        newiis.setPrice(0);
        newiis.setDiscount(0);
        newiis.setInvoice_ref("");
        newiis.setReturnpurchasedraft(returnpurchasedraft);
        
        
        return newiis;
    }
    
}
