/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.AdjustmentDraftEntity;
import net.purnama.model.transactional.draft.ItemAdjustmentDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemAdjustmentDraftController {
    
    public ItemAdjustmentDraftEntity createEmptyItemAdjustmentDraft(AdjustmentDraftEntity 
            adjustmentdraft){
        ItemAdjustmentDraftEntity newiis = new ItemAdjustmentDraftEntity();
//        newiis.setId(IdGenerator.generateId());
        newiis.setQuantity(0);
        newiis.setRemark("");
        newiis.setAdjustmentdraft(adjustmentdraft);
        
        return newiis;
    }
    
}
