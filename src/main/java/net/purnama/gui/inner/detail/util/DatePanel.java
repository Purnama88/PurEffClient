/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.purnama.gui.library.MyLabel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;

/**
 *
 * @author Purnama
 */
public class DatePanel extends JPanel{
    
    private final UtilCalendarModel model;
    private final JDatePanelImpl tempdatepanel;
    private final JDatePickerImpl datepicker;
    
    private final MyLabel label;
    
    public DatePanel(Calendar date, String labelvalue, boolean disabled){
        super(new FlowLayout(FlowLayout.LEFT));
        
        model = new UtilCalendarModel();
        model.setValue(date);
        tempdatepanel = new JDatePanelImpl(model);
        datepicker = new JDatePickerImpl(tempdatepanel);
//        datepicker.getJDateInstantPanel().setDoubleClickAction(true);
        datepicker.setPreferredSize(new Dimension(200, 27));
        
        label = new MyLabel(labelvalue, 100);
        
        add(label);
        add(datepicker);
        
        if(!disabled){
            disableDate();
        }
    }
    
    public Calendar getCalendar(){
        return (Calendar)datepicker.getModel().getValue();
    }
    
    public final void disableDate(){
        JButton button = (JButton)datepicker.getComponent(1);
        button.setEnabled(false);
    }
    
    public void setDate(Calendar calendar){
        Date date = calendar.getTime();
        
        datepicker.getModel().setDate(date.getYear() + 1900, date.getMonth(), 
                date.getDate());
    }
    
}
