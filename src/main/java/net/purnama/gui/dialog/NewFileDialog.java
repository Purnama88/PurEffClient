/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import net.purnama.gui.library.MyDialog;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.MenuEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NewFileDialog extends MyDialog{
    private final JPanel titlepanel, descriptionpanel, leftdescriptionpanel, rightdescriptionpanel;
    
    private final JTree tree;
    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode("PurEff");
    
    public NewFileDialog(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_NEWFILE"), 450, 430);
        
        titlepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlepanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        titlepanel.add(new MyLabel("Choose Invoice"));
        
        tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        descriptionpanel = new JPanel(new GridLayout(1, 2));
        descriptionpanel.setPreferredSize(new Dimension(435, 420));
        descriptionpanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        leftdescriptionpanel = new JPanel(new GridLayout(1, 1));
        leftdescriptionpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), " Menu :"));
        rightdescriptionpanel = new JPanel();
        rightdescriptionpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), " Description :"));
        
        descriptionpanel.add(leftdescriptionpanel);
        descriptionpanel.add(rightdescriptionpanel);
        
        leftdescriptionpanel.add(tree);
        
        box.add(titlepanel);
        box.add(descriptionpanel);
        box.add(submitpanel);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getTransactionalMenuList");
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                }
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
                        ArrayList<MenuEntity> menulist = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        MenuEntity.class));
                        
                        for(MenuEntity menu : menulist){
                            DefaultMutableTreeNode master = 
                                new DefaultMutableTreeNode(menu.getName());
                            root.add(master);
                        }
                        
                        tree.expandRow(0);
                        tree.setRootVisible(false);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void showDialog(){
        setVisible(true);
    }
}
