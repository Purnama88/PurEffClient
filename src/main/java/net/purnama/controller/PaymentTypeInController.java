/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import java.util.Calendar;
import net.purnama.model.transactional.PaymentInEntity;
import net.purnama.model.transactional.PaymentTypeInEntity;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInController {
    
    public PaymentTypeInEntity addPaymentTypeIn(int type, 
            Calendar duedate,
            String bank, String number, String expiry, double amount, 
            String paymentinid){
        PaymentTypeInEntity ptide = new PaymentTypeInEntity();
        ptide.setType(type);
        ptide.setDate(Calendar.getInstance());
        ptide.setDuedate(duedate);
        ptide.setBank(bank);
        ptide.setNumber(number);
        ptide.setExpirydate(expiry);
        ptide.setAmount(amount);
        
        PaymentInEntity paymentin = new PaymentInEntity();
        paymentin.setId(paymentinid);
        
        ptide.setPaymentin(paymentin);
        ptide.setStatus(false);
        ptide.setValid(true);
        ptide.setNote("");
        
        return ptide;
    }
    
}
