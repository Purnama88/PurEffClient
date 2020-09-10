/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.PartnerEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PartnerComboBoxPanel extends JPanel{
    
    private ArrayList<PartnerEntity> list;
    
    private final MyLabel label, loadinglabel;
    private final JComboBox combobox;
    
    private final boolean customer, supplier, nontrade;
    
    public PartnerComboBoxPanel(boolean customer, boolean supplier, boolean nontrade){
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.customer = customer;
        this.supplier = supplier;
        this.nontrade = nontrade;
        
        list = new ArrayList<>();
        combobox = new JComboBox();
        
        label = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_PARTNER"), 100);
        combobox.setPreferredSize(new Dimension(200, 27));
        loadinglabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        
        add(label);
        add(combobox);
        add(loadinglabel);
        
        load();
    }
    
    public JComboBox getComboBox(){
        return combobox;
    }
    
    public final void load(){
        if(customer && supplier && nontrade){
            loadall();
        }
        else{
            if(customer){
                loadcustomer();
            }
            if(supplier){
                loadvendor();
            }
            if(nontrade){
                loadnontrade();
            }
        }
    }
    
    public final void loadall(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActivePartnerList");
                
                return true;
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PartnerEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadcustomer(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveCustomerList");
                
                return true;
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PartnerEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadvendor(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveVendorList");
                
                return true;
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PartnerEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadnontrade(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveNonTradeList");
                
                return true;
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PartnerEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void refresh(){
        SwingWorker<Boolean, String> refreshworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            PartnerEntity partner = getComboBoxValue();
            
            @Override
            protected Boolean doInBackground(){
                
                loadinglabel.setVisible(true);
                
                if(customer && supplier && nontrade){
                    response = RestClient.get("getActivePartnerList");
                }
                else{
                    if(customer){
                        response = RestClient.get("getActiveCustomerList");
                    }
                    if(supplier){
                        response = RestClient.get("getActiveVendorList");
                    }
                    if(nontrade){
                        response = RestClient.get("getActiveNonTradeList");
                    }
                }
                
                return true;
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PartnerEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                        
                        setComboBoxValue(partner);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        refreshworker.execute();
    }
    
    public PartnerEntity getComboBoxValue(){
        PartnerEntity partner = (PartnerEntity)combobox.getSelectedItem();
        return partner;
    }
    
    public void setComboBoxValue(PartnerEntity partner){
        if(partner != null){
            for(int i = 0; i < combobox.getItemCount(); i++){
                PartnerEntity temp = (PartnerEntity)combobox.getItemAt(i);
                if(partner.getId().equals(temp.getId())){
                    combobox.setSelectedIndex(i);
                }
            }
        }
    }
    
    public void setComboBoxEnabled(boolean value){
        combobox.setEnabled(value);
    }
}
