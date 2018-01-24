/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.PaymentInDetail;
import net.purnama.gui.inner.detail.PaymentInDraftDetail;
import net.purnama.gui.inner.form.util.SubmitPanel;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.PaymentTypeInEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentTypeInTableModel;
import net.purnama.util.GlobalFields;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInHome extends HomePanel{
    
    private final SubmitPanel submitpanel;
    
    private final PaymentTypeInTableModel paymenttypeintablemodel;
    
    private ArrayList<PaymentTypeInEntity> list;
    
    private final UtilDateModel startmodel;
    private final JDatePanelImpl startdatepanel;
    private final JDatePickerImpl startdatepicker;
    
    private final UtilDateModel endmodel;
    private final JDatePanelImpl enddatepanel;
    private final JDatePickerImpl enddatepicker;
    
    private final JComboBox combobox;
    
    private final JCheckBox validjcb, acceptedjcb;
    
    public PaymentTypeInHome(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PAYMENTTYPEINHOME"));
        
        list = new ArrayList<>();
        
        paymenttypeintablemodel = new PaymentTypeInTableModel(list);
        
        table.setModel(paymenttypeintablemodel);
        
        upperpanel.removeAddButton();
        upperpanel.removeSearchTextField();
        upperpanel.removePagination();
        
        startmodel = new UtilDateModel();
        startmodel.setValue(Calendar.getInstance().getTime());
        startdatepanel = new JDatePanelImpl(startmodel);
        startdatepicker = new JDatePickerImpl(startdatepanel);
        
        endmodel = new UtilDateModel();
        endmodel.setValue(Calendar.getInstance().getTime());
        enddatepanel = new JDatePanelImpl(endmodel);
        enddatepicker = new JDatePickerImpl(enddatepanel);
        
        upperpanel.getLeftPanel().setLayout(new BoxLayout(upperpanel.getLeftPanel(),
                BoxLayout.X_AXIS));
        upperpanel.getLeftPanel().add(startdatepicker);
        upperpanel.getLeftPanel().add(new MyLabel(" - "));
        upperpanel.getLeftPanel().add(enddatepicker);
        
        combobox = new JComboBox(PaymentTypeInEntity.PAYMENT_TYPE);
        combobox.addItem(GlobalFields.PROPERTIES.getProperty("LABEL_ALL"));
        
        validjcb = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_VALID"), true);
        acceptedjcb = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ACCEPTED"), false);
        
        upperpanel.getMiddlePanel().add(acceptedjcb);
        upperpanel.getMiddlePanel().add(validjcb);
        upperpanel.getMiddlePanel().add(combobox);
        
        submitpanel = new SubmitPanel();
        add(submitpanel);
        
        loaddata();
        
        combobox.addItemListener((ItemEvent e) -> {
            loaddata();
        });
                
        startdatepicker.addActionListener((ActionEvent e) -> {
            loaddata();
        });
        
        enddatepicker.addActionListener((ActionEvent e) -> {
            loaddata();
        });
        
        validjcb.addActionListener((ActionEvent e) ->{
            loaddata();
        });
        
        acceptedjcb.addActionListener((ActionEvent e) ->{
            loaddata();
        });
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) ->{
            save();
        });
    }

    @Override
    protected void detail() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        PaymentTypeInEntity paymenttypein = paymenttypeintablemodel.getPaymentTypeIn(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.changeTabPanel(getIndex(), new PaymentInDetail(paymenttypein.getPaymentin().getId()));
    }

    @Override
    protected void edit() {
        detail();
    }

    public final void save(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                response = RestClient.post("savePaymentTypeInList",
                        paymenttypeintablemodel.getPaymentTypeInList());
                
                return true;
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void filter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected final void loaddata() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                String start = new SimpleDateFormat("MMddyyyy").format(startdatepicker.getModel().getValue());
                String end = new SimpleDateFormat("MMddyyyy").format(enddatepicker.getModel().getValue());
                
                response = RestClient.get("getPaymentTypeInList?startdate=" + start + "&enddate=" + end +
                        "&accepted=" + acceptedjcb.isSelected() + "&valid=" + validjcb.isSelected() + 
                        "&type=" + combobox.getSelectedIndex());
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                upperpanel.setNotifLabel(number);
                }
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentTypeInEntity.class));
                        
                        paymenttypeintablemodel.setPaymentTypeInList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }

    protected void detail(String id){
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                        getAncestorOfClass(JTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PaymentInDraftDetail(id));
    }
    
    @Override
    protected void add() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("addPaymentInDraft");
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                upperpanel.setNotifLabel(number);
                }
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);
                        
                    JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus(), 
                    JOptionPane.ERROR_MESSAGE);
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);

                    detail(output);
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void openinnewtab() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        PaymentTypeInEntity paymenttypein = paymenttypeintablemodel.getPaymentTypeIn(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.insertTab(getIndex()+1, new PaymentInDetail(paymenttypein.getPaymentin().getId()));
    }
}
