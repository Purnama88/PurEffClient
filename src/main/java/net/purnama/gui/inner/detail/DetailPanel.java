/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import net.purnama.gui.inner.detail.util.NotePanel;
import net.purnama.gui.inner.detail.util.UpperPanel;
import net.purnama.gui.library.MyPanel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public abstract class DetailPanel extends MyPanel{
    
    protected final UpperPanel upperpanel;
    
    protected final JTabbedPane tabbedpane;
    
    protected final JScrollPane scrollpane;
    
    protected final NotePanel notepanel;
    
    protected final JPanel detailpanel;
    
    public DetailPanel(String name){
        super(name);
        
        upperpanel = new UpperPanel();
        upperpanel.addEditButton();
        
        tabbedpane = new JTabbedPane();
        tabbedpane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(upperpanel);
        add(tabbedpane);
        
        scrollpane = new JScrollPane();
        scrollpane.setBorder(null);
        
        detailpanel = new JPanel();
        
        detailpanel.setLayout(new BoxLayout(detailpanel, BoxLayout.Y_AXIS));
        detailpanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        scrollpane.getViewport().add(detailpanel);
        
        notepanel = new NotePanel("", false, null);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"), scrollpane);
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE"), notepanel);
        
        upperpanel.getHomeButton().addActionListener((ActionEvent e) -> {
            home();
        });
        
        upperpanel.getEditButton().addActionListener((ActionEvent e) -> {
            edit();
        });
    }
    
    protected abstract void load();
    protected abstract void home();
    protected abstract void edit();
}
