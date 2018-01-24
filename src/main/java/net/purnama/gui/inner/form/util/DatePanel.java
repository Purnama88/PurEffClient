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
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyLabel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;

/**
 *
 * @author Purnama
 */
public class DatePanel extends JPanel{
    private final MyLabel label, errorlabel;
    
    private final UtilCalendarModel model;
    private final JDatePanelImpl datepanel;
    private final JDatePickerImpl datepicker;
    
    public DatePanel(String title, Calendar time){
        super(new FlowLayout(FlowLayout.LEFT));
        
        label = new MyLabel(title, 150);
        errorlabel = new MyLabel("", 200);
        
        model = new UtilCalendarModel();
        model.setSelected(true);
        
        datepanel = new JDatePanelImpl(model);
        datepicker = new JDatePickerImpl(datepanel);
        datepicker.setPreferredSize(new Dimension(245, 27));
        
        Date date = time.getTime();
        datepicker.getModel().setDate(date.getYear() + 1900, date.getMonth(), 
                date.getDate());
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(label);
        add(datepicker);
        add(errorlabel);
    }
    
    public Calendar getCalendar(){
        return (Calendar)datepicker.getModel().getValue();
    }
    
    public void setDocumentListener(DocumentListener dl){
        JTextField tf = (JTextField)datepicker.getComponent(0);
        tf.getDocument().addDocumentListener(dl);
    }
    
    public void setDate(Calendar calendar){
        Date date = calendar.getTime();
        
        datepicker.getModel().setDate(date.getYear() + 1900, date.getMonth(), 
                date.getDate());
    }
    
    public void setLabel(String value){
        label.setText(value);
    }
    
    public String getLabel(){
        return label.getText();
    }
    
    public String getErrorLabel(){
        return errorlabel.getText();
    }
    
    public void setErrorLabel(String value){
        errorlabel.setText("<HTML><FONT COLOR=RED>" + value + "</FONT></HTML>");
    }
    
    public void disableDate(){
        JButton button = (JButton)datepicker.getComponent(1);
        button.setEnabled(false);
    }
}

