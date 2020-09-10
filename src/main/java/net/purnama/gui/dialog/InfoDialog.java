/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import net.purnama.gui.library.MyDialog;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InfoDialog extends MyDialog{

    public InfoDialog() {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_ABOUT"), 400, 300);
    }
    
    public void showDialog(){
        setVisible(true);
    }
    
}
