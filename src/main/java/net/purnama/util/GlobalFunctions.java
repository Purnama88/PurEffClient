/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;
import javax.swing.Spring;
import javax.swing.SpringLayout;

/**
 *
 * @author Purnama
 */
public class GlobalFunctions {
    
    public static double convertToDouble(String value){
        try{
            NumberFormat format = DecimalFormat.getNumberInstance();
            Number number = format.parse(value);
            double d = number.doubleValue();
            
            return d;
        }
        catch(ParseException e){
            return 0;
        }
    }
    
    public static double round(double value){
        DecimalFormat df;
        
        if(GlobalFields.DECIMALPLACES == 3){
            df = new DecimalFormat("#.###");
        }
        else if(GlobalFields.DECIMALPLACES == 4){
            df = new DecimalFormat("#.####");
        }
        else{
            df = new DecimalFormat("#.##");
        }
        
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        return GlobalFunctions.convertToDouble(df.format(value));
    }
    
    public static double convertToQuantity(String value){
        try{
            NumberFormat format = DecimalFormat.getNumberInstance();
            Number number = format.parse(value);
            double d = number.doubleValue();
    
            return Math.abs(d);
        }
        catch(ParseException e){
            return 1;
        }
    }
    
    public static String toSuperscript(String include, String exclude){
        return "<HTML>" + 
                exclude + " " +
                "<SUP>" + include + "</SUP></HTML>";
    }
    
    public static void setPercentage(
        SpringLayout.Constraints c, Spring pw, Spring ph,
        float sx, float sy, float sw, float sh) {
        c.setX(Spring.scale(pw, sx));
        c.setY(Spring.scale(ph, sy));
        c.setWidth(Spring.scale(pw,  sw));
        c.setHeight(Spring.scale(ph, sh));
    }
    
    protected String getSaltString() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    
}
