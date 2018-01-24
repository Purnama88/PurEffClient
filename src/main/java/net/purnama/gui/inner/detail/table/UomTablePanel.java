/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.UomTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UomTablePanel extends TablePanel{
    
    private final JMenuItem menuitemedit;
    
    private final UomTableModel uomtablemodel;
    
    private ArrayList<UomEntity> list;
    
    private final String parentid;
    
    public UomTablePanel(String parentid){
        
        this.parentid = parentid;
        
        list = new ArrayList<>();
        
        uomtablemodel = new UomTableModel (list);
        
        table.setModel(uomtablemodel);
        
        popupmenu.remove(menuitemdelete);
        
        menuitemedit = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"),
                new MyImageIcon().getImage("net/purnama/image/Edit_16.png"));
        
        popupmenu.add(menuitemedit);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getUomList?parentid="+parentid);
                
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
                        
                        uomtablemodel.setUomList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void addUom(UomEntity uom){
        uomtablemodel.addRow(uom);
    }
    
    public UomEntity getSelectedUom(){
        UomEntity uom = uomtablemodel.getUom(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        return uom;
    }
    
    public JMenuItem getMenuItemEdit(){
        return menuitemedit;
    }
}
