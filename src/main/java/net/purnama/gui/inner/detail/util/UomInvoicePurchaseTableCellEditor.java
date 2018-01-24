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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellEditor;
import net.purnama.model.UomEntity;
import net.purnama.model.transactional.draft.ItemInvoicePurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoicePurchaseDraftTableModel;

/**
 *
 * @author Purnama
 */
public class UomInvoicePurchaseTableCellEditor extends AbstractCellEditor implements TableCellEditor{

    private JComboBox component;
    
    private final SwingWorker<Boolean, String> worker;
    
    private ArrayList<UomEntity> list;
    
    public UomInvoicePurchaseTableCellEditor(){
        super();
        
        worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getUomList");
                
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
                                        UomEntity.class));
                    }
                    catch(IOException e){
                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    public Object getCellEditorValue() {
        UomEntity uom = (UomEntity)component.getSelectedItem();
        
        return uom;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ItemInvoicePurchaseDraftTableModel tm = (ItemInvoicePurchaseDraftTableModel)table.getModel();
        
        ItemInvoicePurchaseDraftEntity iisde = tm.getItemInvoicePurchaseDraft(table.
                    convertRowIndexToModel(row));
        
        ArrayList<UomEntity> returnls = new ArrayList<>();
        
        UomEntity puom;
        if(iisde.getUom().getParent() != null){
            puom = iisde.getUom().getParent();
        }
        else{
            puom = iisde.getUom();
        }
        
        for(UomEntity uom : list){
            if(uom.getId().equals(puom.getId()) ||
                    (uom.getParent() != null && uom.getParent().getId().equals(puom.getId()))){
                returnls.add(uom);
            }
        }
        
        component = new JComboBox(returnls.toArray());
        
        
        ComboBoxModel model = component.getModel();
            
        int size = model.getSize();

        for(int i = 0; i < size; i++){
            UomEntity uom = (UomEntity)model.getElementAt(i);
            if(uom.getId().equals(iisde.getUom().getId())){
                model.setSelectedItem(uom);
            }
        }
        
        return component;
    }
    
}
