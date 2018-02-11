/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import net.purnama.gui.library.MyFrame;
import net.purnama.model.RoleEntity;
import net.purnama.model.UserEntity;

/**
 *
 * @author Purnama
 */
public class GlobalFields {
    
    public static final int CASH = 0;
    public static final int TRANSFER = 1;
    public static final int CREDITCARD = 2;
    public static final int CHECK = 3;
    public static final int ALL = 4;
    
    public static boolean SUCCESS = true;
    public static boolean FAIL = false;
    
    public static int CODE_MINIMUM_LENGTH = 1;
    public static int CODE_MAXIMUM_LENGTH = 25;
    
    public static int ITEM_PER_PAGE = 20;
    
    public static Properties PROPERTIES;
    
    public static String LANGUAGE;
    
    //93.188.166.220 http://93.188.166.220:8080/PurEff-Ika/
    
    public static String SERVER = "http://localhost:8080/PurEff/";
//    public static String SERVER = "http://93.188.166.220:8080/PurEff-1.0/";
    
    public static String API = "api/";
    
    public static String TOKEN;
    
    public static NumberFormat NUMBERFORMAT = DecimalFormat.getNumberInstance();
    public static DateFormat DATEFORMAT = new SimpleDateFormat ("dd MMM YYYY");
    
    public static int DECIMALPLACES = 2;
    
    public static RoleEntity ROLE;
    public static UserEntity USER;
    
    public static int TIMEOUT = 5000; //miliseconds
    
    public static MyFrame MAINFRAME;
}
