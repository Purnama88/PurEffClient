/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.login.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
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
import net.purnama.model.WarehouseEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class WarehouseComboBoxPanel extends JPanel{
    
    private ArrayList<WarehouseEntity> list;
    
    private final MyLabel warehouseicon, loadinglabel;
    private final JComboBox combobox;
    
    public WarehouseComboBoxPanel(){
        super(new FlowLayout(FlowLayout.CENTER));
        
        list = new ArrayList<>();
        combobox = new JComboBox();
        
        warehouseicon = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Warehouse_16.png"), 20, 20);
        loadinglabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        
        add(warehouseicon);
        add(combobox);
        add(loadinglabel);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                loadinglabel.setVisible(true);
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.getNonApi("getWarehouse_IdCode_List");
                
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
                        combobox.setVisible(true);
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
        ComboBoxModel model = combobox.getModel();
        
        int size = model.getSize();
        
        for(int i = 0; i < size; i++){
            WarehouseEntity temp = (WarehouseEntity)model.getElementAt(i);
            if(warehouse.getId().equals(temp.getId())){
                model.setSelectedItem(warehouse);
            }
        }
    }
    
    public void setComboBoxEnabled(boolean value){
        combobox.setEnabled(value);
    }
}
