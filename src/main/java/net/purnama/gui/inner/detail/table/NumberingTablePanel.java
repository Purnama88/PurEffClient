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
import net.purnama.model.NumberingEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.NumberingTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NumberingTablePanel extends TablePanel{
    private final JMenuItem menuitemedit, menuitemdefault;
    
    private final NumberingTableModel numberingtablemodel;
    
    private ArrayList<NumberingEntity> list;
    
    private final int menuid;
    
    public NumberingTablePanel(int menuid){
        
        this.menuid = menuid;
        
        list = new ArrayList<>();
        
        numberingtablemodel = new NumberingTableModel (list);
        
        table.setModel(numberingtablemodel);
        
        popupmenu.remove(menuitemdelete);
        
        menuitemedit = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"),
                new MyImageIcon().getImage("net/purnama/image/Edit_16.png"));
        
        menuitemdefault = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_DEFAULT"),
                new MyImageIcon().getImage("net/purnama/image/Default_16.png"));
        
        popupmenu.add(menuitemedit);
        popupmenu.addSeparator();
        popupmenu.add(menuitemdefault);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getNumberingList?menuid="+menuid);
                
                return true;
            }
            
            @Override
            protected void done() {
                
                if(response == null){
                    System.out.println("null");
                }
                else if(response.getStatus() != 200) {
                    System.out.println("error");
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        NumberingEntity.class));
                        
                        numberingtablemodel.setNumberingList(list);
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void addNumbering(NumberingEntity numbering){
        numberingtablemodel.addRow(numbering);
    }
    
    public NumberingEntity getSelectedNumbering(){
        NumberingEntity numbering = numberingtablemodel.getNumbering(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        return numbering;
    }
    
    public NumberingEntity getNumbering(){
        return numberingtablemodel.getNumbering(table.
                    convertRowIndexToModel(table.getSelectedRow()));
    }
    
    public JMenuItem getMenuItemEdit(){
        return menuitemedit;
    }
    
    public JMenuItem getMenuItemDefault(){
        return menuitemdefault;
    }
}
