/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;

/**
 *
 * @author Purnama
 */
public class CheckBoxPanel extends JPanel{
    
    private final MyLabel label;
    
    public CheckBoxPanel(String labelcontent){
        super(new FlowLayout(FlowLayout.LEFT));
        
        label = new MyLabel(labelcontent, 150);
        
        add(label);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    public void addCheckBox(JCheckBox jcb){
        jcb.setPreferredSize(new Dimension(100, 30));
        add(jcb);
    }
    
    public void addSpace(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 30));
        add(panel);
    }
}
