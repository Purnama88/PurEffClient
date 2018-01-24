/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import javax.swing.UIManager;

/**
 *
 * @author Purnama
 */
public class MySelectableLabel extends MyTextField{
    
    public MySelectableLabel(String value, int width){
        
        super(value, width);
        
        setEditable(false);
        setBorder(null);
        setBackground(UIManager.getColor("Label.background"));
        setForeground(UIManager.getColor("Label.foreground"));
        setFont(UIManager.getFont("Label.font"));
        
        popupmenu.removeAll();
        
        popupmenu.add(menuitemcopy);
        popupmenu.add(menuitemselect);
    }
}