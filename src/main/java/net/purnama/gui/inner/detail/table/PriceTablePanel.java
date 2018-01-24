/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PriceTablePanel extends JPanel{
    
    private final BuyPriceTablePanel buypricepanel;
    private final SellPriceTablePanel sellpricepanel;
    
    private final JPanel titlepanel, contentpanel;
    
    public PriceTablePanel(String itemid){
        super();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(700, 300));
        
        titlepanel = new JPanel(new GridLayout(1, 2, 5, 0));
        titlepanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        contentpanel = new JPanel(new GridLayout(1, 2, 5, 0));
        contentpanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        buypricepanel = new BuyPriceTablePanel(itemid);
        sellpricepanel = new SellPriceTablePanel(itemid);
        
        titlepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_BUYPRICE")));
        titlepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_SELLPRICE")));
        
        contentpanel.add(buypricepanel);
        contentpanel.add(sellpricepanel);
        
        add(titlepanel);
        add(contentpanel);
    }
    
    public void load(){
        buypricepanel.load();
        sellpricepanel.load();
    }
}
