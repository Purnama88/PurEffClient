/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.inner.form.util.SubmitPanel;
import net.purnama.gui.inner.form.util.UpperPanel;
import net.purnama.gui.library.MyPanel;

/**
 *
 * @author Purnama
 */
public abstract class FormPanel extends MyPanel implements DocumentListener, ActionListener{

    protected final UpperPanel upperpanel;
    
    protected final SubmitPanel submitpanel;
    
    protected final JScrollPane scrollpane;
    
    protected final JPanel detailpanel;
    
    public FormPanel(String name) {
        super(name);
        
        upperpanel = new UpperPanel();
        
        scrollpane = new JScrollPane();
        scrollpane.setBorder(null);
        
        detailpanel = new JPanel();
        detailpanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        detailpanel.setLayout(new BoxLayout(detailpanel, BoxLayout.Y_AXIS));
        
        scrollpane.getViewport().add(detailpanel);
        scrollpane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        submitpanel = new SubmitPanel();
        submitpanel.getSubmitButton().addActionListener(this);
        
        add(upperpanel);
        add(scrollpane);
        add(submitpanel);
        
        upperpanel.getHomeButton().addActionListener((ActionEvent e) -> {
            home();
        });
    }
    
    protected abstract boolean validateinput();
    
    protected abstract void submit();
    protected abstract void home();
    
    @Override
    public void changedUpdate(DocumentEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        tabbedPane.setTitleAt(getIndex(), getPendingName());
        setState(MyPanel.PENDING);
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        tabbedPane.setTitleAt(getIndex(), getPendingName());
        setState(MyPanel.PENDING);
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        tabbedPane.setTitleAt(getIndex(), getPendingName());
        setState(MyPanel.PENDING);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        submit();
    }
}
