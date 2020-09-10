/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MyTextField extends JTextField{
    protected final JPopupMenu popupmenu;
    
    protected final JMenuItem menuitemcopy, menuitemselect, menuitempaste, menuitemcut;
    
    public MyTextField(){
        super();
        
        popupmenu = new JPopupMenu();
        
        menuitemcopy = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_COPY"),
                new MyImageIcon().getImage("net/purnama/gui/image/Copy_16.png"));
        menuitemselect = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_SELECTALL"),
                new MyImageIcon().getImage("net/purnama/gui/image/Select_16.png"));
        menuitempaste = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_PASTE"),
                new MyImageIcon().getImage("net/purnama/gui/image/Paste_16.png"));
        menuitemcut = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_CUT"),
                new MyImageIcon().getImage("net/purnama/gui/image/Cut_16.png"));
        
        popupmenu.add(menuitemselect);
        popupmenu.addSeparator();
        popupmenu.add(menuitemcopy);
        popupmenu.add(menuitemcut);
        popupmenu.addSeparator();
        popupmenu.add(menuitempaste);
        
        setComponentPopupMenu(popupmenu);
        
        menuitemcut.addActionListener((ActionEvent e) -> {
            cut();
        });
        
        menuitemcopy.addActionListener((ActionEvent e) -> {
            copy();
        });
        
        menuitempaste.addActionListener((ActionEvent e) -> {
           paste(); 
        });
        
        menuitemselect.addActionListener((ActionEvent e) -> {
           selectAll(); 
        });
        
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                selectAll();
            }
        });
    }
    
    public MyTextField(String title, int width){
        super(title);
        setPreferredSize(new Dimension(width, 25));
        
        popupmenu = new JPopupMenu();
        
        menuitemcopy = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_COPY"),
                new MyImageIcon().getImage("net/purnama/image/Copy_16.png"));
        menuitemselect = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_SELECTALL"),
                new MyImageIcon().getImage("net/purnama/image/Select_16.png"));
        menuitempaste = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_PASTE"),
                new MyImageIcon().getImage("net/purnama/image/Paste_16.png"));
        menuitemcut = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_CUT"),
                new MyImageIcon().getImage("net/purnama/image/Cut_16.png"));
        
        popupmenu.add(menuitemselect);
        popupmenu.addSeparator();
        popupmenu.add(menuitemcopy);
        popupmenu.add(menuitemcut);
        popupmenu.addSeparator();
        popupmenu.add(menuitempaste);
        
        setComponentPopupMenu(popupmenu);
        
        menuitemcut.addActionListener((ActionEvent e) -> {
            cut();
        });
        
        menuitemcopy.addActionListener((ActionEvent e) -> {
            copy();
        });
        
        menuitempaste.addActionListener((ActionEvent e) -> {
           paste(); 
        });
        
        menuitemselect.addActionListener((ActionEvent e) -> {
           selectAll(); 
        });
        
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(() -> {
                    selectAll();
                });
            }
        });
    }
    
    public boolean isEmpty(){
        return getText().matches("^\\s*$");
    }
    
    public boolean isLongLessThan(int lo){
        return getText().matches("[A-Za-z0-9/\\\\\\-]" + "{" + lo + "}");
    }
    
    public boolean isLongBetween(int min, int max){
        return getText().matches("[A-Za-z0-9/\\\\\\-]" + "{" + min + "," + max + "}");
    }
    
    public boolean isNumeric(){
        return getText().matches("[0-9]+");
    }
}
