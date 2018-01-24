/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;

/**
 *
 * @author Purnama
 */
public class MainInfoPanel extends JPanel{
    
    private final JPanel customerpanel, datepanel, utilitypanel;
    
    private final MyButton logoutbutton;
    
    private final MyLabel logolabel;
    
    public MainInfoPanel(){
        super(new GridLayout(1, 3, 5, 0));
        
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        customerpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datepanel = new JPanel();
        utilitypanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        add(customerpanel);
        add(datepanel);
        add(utilitypanel);
        
        datepanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        utilitypanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        logoutbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Logout_32.png"), 32, 32);
        
        logolabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Logo.png"));
        
        customerpanel.add(new MyLabel("<HTML><FONT SIZE=6></FONT></HTML>"));
        datepanel.add(new Clock());
        utilitypanel.add(logolabel);
    }
}

class Clock extends JLabel implements ActionListener{
    
    public Clock(){
        super();
        
        Date date = new Date();
        setDate(date);
        
        Timer t = new Timer(1000, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = new Date();
        setDate(date);
    }
    
    public final void setDate(Date date){
        SimpleDateFormat dt1 = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss");
        String result = "<HTML><FONT SIZE=4>" + dt1.format(date)
                + "</FONT></HTML>";
        setText(result);
    }
}