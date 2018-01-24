/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home;

import javax.swing.JTabbedPane;
import net.purnama.gui.inner.home.util.ChangePasswordPanel;
import net.purnama.gui.library.MyPanel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class SettingHome extends MyPanel{

    private final JTabbedPane tabbedpane;
    
    private final ChangePasswordPanel changepasswordpanel;
    
    public SettingHome() {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_SETTING"));
        
        tabbedpane = new JTabbedPane();
        
        changepasswordpanel = new ChangePasswordPanel();
        tabbedpane.add(changepasswordpanel, GlobalFields.PROPERTIES.getProperty("LABEL_PASSWORD"));
        
        add(tabbedpane);
    }
}
