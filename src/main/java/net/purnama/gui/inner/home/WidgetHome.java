/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home;

import java.awt.GridLayout;
import net.purnama.gui.library.MyInternalFrame;
import net.purnama.gui.library.MyPanel;

/**
 *
 * @author Purnama
 */
public class WidgetHome extends MyPanel{

    public WidgetHome() {
        super("HelloPOS");
        
        setLayout(new GridLayout(3, 3, 5, 5));
        
        MyInternalFrame frame = new MyInternalFrame();
        frame.setVisible(true);
        MyInternalFrame frame2 = new MyInternalFrame();
        frame2.setVisible(true);
        MyInternalFrame frame3 = new MyInternalFrame();
        frame3.setVisible(true);
        MyInternalFrame frame5 = new MyInternalFrame();
        frame5.setVisible(true);
        MyInternalFrame frame4 = new MyInternalFrame();
        frame4.setVisible(true);
        
        add(frame);
        add(frame2);
        add(frame3);
        add(frame4);
        add(frame5);
    }
    
}
