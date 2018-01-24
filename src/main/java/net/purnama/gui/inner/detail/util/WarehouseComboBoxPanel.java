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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.WarehouseEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class WarehouseComboBoxPanel extends JPanel{
    
    private ArrayList<WarehouseEntity> list;
    
    private final MyLabel label, loadinglabel;
    private final JComboBox combobox;
    
    public WarehouseComboBoxPanel(){
        super(new FlowLayout(FlowLayout.LEFT));
        
        list = new ArrayList<>();
        combobox = new JComboBox();
        
        label = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_WAREHOUSE"), 100);
        combobox.setPreferredSize(new Dimension(200, 27));
        loadinglabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        
        add(label);
        add(combobox);
        add(loadinglabel);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveWarehouseList");
                
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
                                        WarehouseEntity.class));
                        
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
    
    public WarehouseEntity getComboBoxValue(){
        WarehouseEntity warehouse = (WarehouseEntity)combobox.getSelectedItem();
        return warehouse;
    }
    
    public void setComboBoxValue(WarehouseEntity warehouse){
        if(warehouse != null){
            for(int i = 0; i < combobox.getItemCount(); i++){
                WarehouseEntity temp = (WarehouseEntity)combobox.getItemAt(i);
                if(warehouse.getId().equals(temp.getId())){
                    combobox.setSelectedIndex(i);
                }
            }
        }
    }
    
    public void refresh(){
        SwingWorker<Boolean, String> refreshworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            WarehouseEntity warehouse = getComboBoxValue();
            
            @Override
            protected Boolean doInBackground(){
                
                loadinglabel.setVisible(true);
                
                response = RestClient.get("getActiveWarehouseList");
                
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
                                        WarehouseEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                        
                        setComboBoxValue(warehouse);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        refreshworker.execute();
    }
    
    public void setComboBoxEnabled(boolean value){
        combobox.setEnabled(value);
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
}
