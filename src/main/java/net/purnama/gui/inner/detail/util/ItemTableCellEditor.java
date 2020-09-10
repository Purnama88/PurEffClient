/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellEditor;
import net.purnama.gui.library.AutoSuggestComboBox;
import net.purnama.model.ItemEntity;
import net.purnama.rest.RestClient;

/**
 *
 * @author Purnama
 */
public class ItemTableCellEditor extends AbstractCellEditor implements TableCellEditor{

    private AutoSuggestComboBox component;
    
    private final SwingWorker<Boolean, String> worker;
    
    private ArrayList<ItemEntity> list;
    
    public ItemTableCellEditor(){
        super();
        
        list = new ArrayList<>();
        
        worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getActiveItemList");
                
                return true;
                
            }
            
            @Override
            protected void done() {
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
                                        ItemEntity.class));
                        
                        component = new AutoSuggestComboBox(list.toArray());
                    }
                    catch(IOException e){
                    }
                }
            }
        };
        
        worker.execute();
        
        
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int rowIndex, int vColIndex){
        String value2 = String.valueOf(value);
        
        if(value2.isEmpty()){
            component.reset();
        }
        else{
            ComboBoxModel model = component.getModel();
        
            int size = model.getSize();

            for(int i = 0; i < size; i++){
                ItemEntity item = (ItemEntity)model.getElementAt(i);
                if(item.toString().equals(value2)){
                    model.setSelectedItem(item);
                }
            }
        }
        
        return component;
    }

    @Override
    public Object getCellEditorValue() {
        ItemEntity item = (ItemEntity)component.getSelectedItem();
        return item;
    }
}