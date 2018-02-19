/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyDialog;
import net.purnama.gui.library.MyTable;
import net.purnama.model.ItemGroupEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemGroupTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class ItemGroupImportDialog extends MyDialog implements ActionListener{
    
    private final MyTable table;
    private final ItemGroupTableModel itemgrouptablemodel;
    private final JScrollPane scrollpane;
    
    private ArrayList<ItemGroupEntity> list;
    
    public ItemGroupImportDialog(ArrayList<ItemGroupEntity> list){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_IMPORT"), 900, 500);
        
        this.list = list;
        
        table = new MyTable();
        itemgrouptablemodel = new ItemGroupTableModel(list);
        
        scrollpane = new JScrollPane();
        scrollpane.setBorder(null);
        scrollpane.getViewport().add(table);
        
        table.setModel(itemgrouptablemodel);
        
        box.add(scrollpane);
        box.add(submitpanel);
        
        submitpanel.getSubmitButton().addActionListener(this);
    }
    
    public void showDialog(){
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                response = RestClient.post("addItemGroupList", list);
                    
                submitpanel.loading();
                
                return true;
            }
            
            @Override
            protected void done() {
                
                submitpanel.finish();
                
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
                                        ItemGroupEntity.class));
                        
                        if(list.isEmpty()){
                            JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_IMPORT_SUCCESS"), "",
                            JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                        
                        itemgrouptablemodel.setItemGroupList(list);
                    }
                    catch(IOException e){
                        dispose();
                    }
                }
            }
        };
        
        worker.execute();
    }
}
