/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyPanel;
import net.purnama.util.GlobalFields;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Purnama
 */
public class MainTabbedPane extends JTabbedPane{
    
    protected final JPopupMenu popupmenu;
    
    protected final JMenuItem menucloseothertabs, menureloadtab, menuclosetab;
    
    public MainTabbedPane(){
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        popupmenu = new JPopupMenu();
        
        menucloseothertabs = new JMenuItem("Close Other Tabs");
        menureloadtab = new JMenuItem("Reload Tab");
        menuclosetab = new JMenuItem("Close Tab");
        
        popupmenu.add(menureloadtab);
        popupmenu.addSeparator();
        popupmenu.add(menuclosetab);
        popupmenu.addSeparator();
        popupmenu.add(menucloseothertabs);
        
        setComponentPopupMenu(popupmenu);
        
        menureloadtab.addActionListener((ActionEvent e) -> {
            int i = getSelectedIndex();
            
            if(i == -1){
                
            }
            else{
                MyPanel panel = (MyPanel)getComponentAt(i);
                panel.refresh();
            }
        });
        
        menuclosetab.addActionListener((ActionEvent e) -> {
            int i = getSelectedIndex();
            
            if(i == -1){
                
            }
            //last tab closed
            else if(i == (getTabCount()-1)){
                remove(i);
            }
            else{
                remove(i);
                
                for(int j = i; j < getTabCount(); j++){
                    MyPanel panel = (MyPanel)getComponentAt(j);
                    panel.setIndex(j);
                }
            }
        });
        
        menucloseothertabs.addActionListener((ActionEvent e) -> {
            int i = getSelectedIndex();
            
            if(i == -1){
                
            }
            else{
                int prev = i;
                
                int after = getTabCount() - 1 - i;
                
                for(int j = 0; j < prev; j++){
                    remove(0);
                }
                
                for(int j = 0; j < after; j++){
                    remove(1);
                }
                
                MyPanel panel = (MyPanel)getComponentAt(0);
                panel.setIndex(0);
            }
        });
        
    }
    
    public final void insertTab(int index, MyPanel panel){
        insertTab(panel.getName(), null, panel, "", index);
        setTabComponentAt(index,
                 new ButtonTabComponent(this));
        setSelectedIndex(index);
        
        for(int j = index; j < getTabCount(); j++){
            MyPanel temp = (MyPanel)getComponentAt(j);
            temp.setIndex(j);
        }
    }
    
    public final void insertTab(int index, String title, JRViewer view){
        MyPanel panel = new MyPanel(title);
        panel.add(view);
        
        insertTab(panel.getName(), null, panel, "", index);
        setTabComponentAt(index,
                 new ButtonTabComponent(this));
        setSelectedIndex(index);
        
        for(int j = index; j < getTabCount(); j++){
            MyPanel temp = (MyPanel)getComponentAt(j);
            temp.setIndex(j);
        }
    }
    
    public final void addTab(MyPanel panel){
        addTab(panel.getName(), panel);
        
        int index = getTabCount()-1;
        
        setTabComponentAt(index,
                 new ButtonTabComponent(this));
        setSelectedIndex(index);
        panel.setIndex(index);
    }
    
    public final void changeTabPanel(int index, MyPanel panel){
        setComponentAt(index, panel);
        
        setTabComponentAt(index,
                 new ButtonTabComponent(this));
        setTitleAt(index, panel.getName());
        panel.setState(MyPanel.SAVED);
        panel.setIndex(index);
    }
}

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


/**
 * Component to be used as tabComponent;
 * Contains a JLabel to show the text and 
 * a JButton to close the tab it belongs to 
 */ 
class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;

    public ButtonTabComponent(final JTabbedPane pane) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);
        
        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            @Override
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new TabButton();
        JButton refresh = new RefreshButton();
        add(refresh);
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class RefreshButton extends JButton implements ActionListener {
        public RefreshButton() {
            super(new MyImageIcon().getImage("net/purnama/image/Refresh_13.png"));
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            
            setToolTipText(GlobalFields.PROPERTIES.getProperty("LABEL_REFRESH"));
            
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            
            if(i == -1){
                
            }
            else{
                MyPanel panel = (MyPanel)pane.getComponentAt(i);
                panel.refresh();
            }
        }
    }
    
    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"));
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            
            
            
            if(i == -1){
                
            }
            //last tab closed
            else if(i == (pane.getTabCount()-1)){
                pane.remove(i);
            }
            else{
                pane.remove(i);
                
                for(int j = i; j < pane.getTabCount(); j++){
                    MyPanel panel = (MyPanel)pane.getComponentAt(j);
                    panel.setIndex(j);
                }
            }
        }

        //we don't want to update UI for this button
        @Override
        public void updateUI() {
        }

        //paint the cross
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}

