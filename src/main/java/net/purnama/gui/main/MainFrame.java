/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import net.purnama.gui.library.MyFrame;

/**
 *
 * @author Purnama
 */
public class MainFrame extends MyFrame{
//implements KeyListener
    
    private final MainMenuBar menubar;
    
    private final MainToolBar toolbar;
    private final MainInfoPanel infopanel;
    
    private final MainNavigation navigation;
    
    private final JSplitPane splitpane;
    
    private final MainTabbedPane tabbedpane;
    
    private final JPanel toppanel;
    
    public MainFrame(){
        super("Hello POS");
        setLayout(new BorderLayout());
        
        setMinimumSize(new Dimension(1240, 550));
        
        maximize();
        tabbedpane = new MainTabbedPane();
        
        navigation = new MainNavigation(tabbedpane);
        
        menubar = new MainMenuBar(this, tabbedpane);
        
//        setJMenuBar(menubar);
        
        toppanel = new JPanel();
        toppanel.setLayout(new BoxLayout(toppanel, BoxLayout.Y_AXIS));
        
        infopanel = new MainInfoPanel();
        
        toolbar = new MainToolBar(this, tabbedpane);
//        toolbar.setBackground(Color.DARK_GRAY);
        
        toppanel.add(infopanel);
        toppanel.add(toolbar);
        
        add(toppanel, BorderLayout.PAGE_START);
        
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigation, tabbedpane);
        
        add(splitpane, BorderLayout.CENTER);
        
//        addKeyListener(this);
        
    }

//    @Override
//    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyChar());
//        if((e.isControlDown() ) && (e.getKeyCode() == KeyEvent.VK_F))
//                {
//                    System.out.println("Key down !!");
//                }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
