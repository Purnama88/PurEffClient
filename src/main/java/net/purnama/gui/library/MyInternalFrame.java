/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.Dimension;
import javax.swing.JInternalFrame;

/**
 *
 * @author Purnama
 */
public class MyInternalFrame extends JInternalFrame {
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;

    public MyInternalFrame() {
        super("Document #" + (++openFrameCount), 
              false, //resizable
              true, //closable
              false, //maximizable
              true);//iconifiable

        add(new MyLabel("testing"));
        
        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setMaximumSize(new Dimension(300, 300));

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }
}