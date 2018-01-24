/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.DeliveryDraftEntity;
import net.purnama.model.transactional.draft.ItemDeliveryDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemDeliveryDraftController {
    
    public ItemDeliveryDraftController(){
        
    }
    
    public ItemDeliveryDraftEntity createEmptyItemDeliveryDraft(DeliveryDraftEntity deliverydraft){
        ItemDeliveryDraftEntity newiis = new ItemDeliveryDraftEntity();
//        newiis.setId(IdGenerator.generateId());
        newiis.setQuantity("");
        newiis.setDescription("");
        newiis.setRemark("");
        newiis.setDeliverydraft(deliverydraft);
        
        return newiis;
    }
}
