/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Purnama
 */
public class CalendarUtil {
    
    public static Calendar toStartOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static Calendar toEndOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year+1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar;
    }
    
    public static Calendar toStartofMonth(Calendar calendar){
        toStartOfDay(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }
    
    public static Calendar toEndofMonth(Calendar calendar){
        toEndofDay(calendar);
        calendar.set(Calendar.DAY_OF_MONTH,
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }
    
    public static int getDaysofMonth(int month, int year){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Calendar getCalendar(Date date) {
        System.out.println(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar toStartOfDay(Calendar calendar) {
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar;
    }

    public static Calendar toEndofDay(Calendar calendar) {
        calendar.set(Calendar.AM_PM, 1);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }
    
    public static Calendar addDatebyDay(Calendar calendar, int days){
        calendar.add(Calendar.DATE, days);
        return calendar;
    }
}
