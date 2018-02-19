/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Purnama
 */
public class MyPanel extends JPanel{
    public static final boolean SAVED      = true;
    public static final boolean PENDING    = false;
    
    private int index;
    
    private boolean state;
    
    public MyPanel(String name){
        super();
        
        setMinimumSize(new Dimension(950, Integer.MAX_VALUE));
        
        setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        setName(name);
        state = SAVED;
    }
    
    public String getSavedName(){
        return getName();
    }
    
    public String getPendingName(){
        return getName() + "*";
    }
    
    public boolean getState(){
        return state;
    }
    
    public void setState(boolean state){
        this.state = state;
    }
    
    public int getIndex(){
        return index;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    public void refresh(){
        
    }
    
    public void print(){
        JOptionPane.showMessageDialog(this, 
                    "You Dont Have Print Method", "", 
                    JOptionPane.ERROR_MESSAGE);
    }
    
    public void printpreview(){
        JOptionPane.showMessageDialog(this, 
                    "You Dont Have Print Preview Method", "", 
                    JOptionPane.ERROR_MESSAGE);
    }
    
    public void export(){
        JOptionPane.showMessageDialog(this, 
                    "You Dont Have Export Method", "", 
                    JOptionPane.ERROR_MESSAGE);
    }
}
