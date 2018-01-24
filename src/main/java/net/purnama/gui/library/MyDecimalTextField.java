/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MyDecimalTextField extends JFormattedTextField{
    
    private final JPopupMenu popupmenu;
    
    private final JMenuItem menuitemcopy, menuitemselect, menuitempaste, menuitemcut;
    
    public MyDecimalTextField(Object value, int width){
        super(new DefaultFormatterFactory(
                                new NumberFormatter(GlobalFields.NUMBERFORMAT),
                                new NumberFormatter(GlobalFields.NUMBERFORMAT),
                                new NumberFormatter(GlobalFields.NUMBERFORMAT)));
        
        setPreferredSize(new Dimension(width, 25));
        setValue(value);
        
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
}
