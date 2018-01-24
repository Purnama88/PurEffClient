/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class TitlePaymentPanel extends JPanel{
    
    private final JCheckBox jcb;
    
    private final JPanel valuepanel;
    
    public TitlePaymentPanel(){
        super(new FlowLayout(FlowLayout.LEFT));
        
        setMinimumSize(new Dimension(550, 30));
        setMaximumSize(new Dimension(550, 30));
        setPreferredSize(new Dimension(550, 30));
        
        jcb = new JCheckBox();
        
        valuepanel = new JPanel(new GridLayout(1, 5, 5, 0));
        valuepanel.setPreferredSize(new Dimension(500, 20));
        
        add(jcb);
        add(valuepanel);
        valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_INVOICENO")));
        valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_DATE")));
        valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_TYPE")));
        valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_TOTAL")));
        valuepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_UNPAID")));
    }
    
    public JCheckBox getCheckBox(){
        return jcb;
    }
}
