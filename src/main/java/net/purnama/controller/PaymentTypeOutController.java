/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import java.util.Calendar;
import net.purnama.model.transactional.PaymentOutEntity;
import net.purnama.model.transactional.PaymentTypeOutEntity;

/**
 *
 * @author Purnama
 */
public class PaymentTypeOutController {
    
    public PaymentTypeOutEntity addPaymentTypeOut(int type, 
            Calendar duedate,
            String bank, String number, String expiry, double amount, 
            String paymentoutid){
        PaymentTypeOutEntity ptide = new PaymentTypeOutEntity();
        ptide.setType(type);
        ptide.setDate(Calendar.getInstance());
        ptide.setDuedate(duedate);
        ptide.setBank(bank);
        ptide.setNumber(number);
        ptide.setExpirydate(expiry);
        ptide.setAmount(amount);
        
        PaymentOutEntity paymentout = new PaymentOutEntity();
        paymentout.setId(paymentoutid);
        
        ptide.setPaymentout(paymentout);
        ptide.setStatus(false);
        ptide.setValid(true);
        ptide.setNote("");
        
        return ptide;
    }
    
}
