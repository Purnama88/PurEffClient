/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Purnama
 */
public class MyFrame extends JFrame{
    
    public MyFrame(String title){
        super(title);
        
        display();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public final void maximize(){
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    public final void minimize(){
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    public final void setLocationToCenter(){
        int width = super.getSize().width;
        int height = super.getSize().height;
        
        Toolkit tool = Toolkit.getDefaultToolkit();
        
        setLocation((int)(tool.getScreenSize().getWidth()-width)/2,
                (int)(tool.getScreenSize().getHeight()-height)/2);
    }
    
    public final void display(){
        setVisible(true);
    }
}
