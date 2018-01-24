/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.convertion;

/**
 *
 * @author Purnama
 */
public class IndonesianNumberConvertion {
    
    static String[] said = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam",
        "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
    
    public static String numberToSaid(Double number){
        if(number < 0){
            return "";
        }
        if(number < 12){
            return said[number.intValue()];
        }
        if(number >= 12 && number <= 19){
            return said[number.intValue() % 10] + " Belas";
        }
        if(number >= 20 && number <= 99){
            return numberToSaid(number/10) + " Puluh " + said[number.intValue() %10];
        }
        if(number >= 100 && number <= 199){
            return "Seratus " + numberToSaid(number % 100);
        }
        if(number >= 200 && number <= 999){
            return numberToSaid(number / 100) + " Ratus " + numberToSaid(number % 100);
        }
        if(number >= 1000 && number <= 1999){
            return "Seribu " + numberToSaid(number % 1000);
        }
        if(number >= 2000 && number <= 999999){
            return numberToSaid(number / 1000) + " Ribu " + numberToSaid(number % 1000);
        }
        if(number >= 1000000 &&  number <= 999999999){
            return numberToSaid(number / 1000000) + " Juta " + numberToSaid(number % 1000000);
        }
        if(number >= 1000000000 &&  number <= 999999999999L){
            return numberToSaid(number / 1000000000) + " Milyar " + numberToSaid(number % 1000000000L);
        }
        return "";
    }
}
