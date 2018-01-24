/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import net.purnama.controller.ItemInvoicePurchaseDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.model.BuyPriceEntity;
import net.purnama.model.ItemEntity;
import net.purnama.model.UomEntity;
import net.purnama.model.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoicePurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemInvoicePurchaseDraftTableModel extends AbstractTableModel {
    
    private final ItemInvoicePurchaseDraftController iteminvoicepurchasedraftController;
    private final InvoicePurchaseDraftEntity invoicepurchasedraft;
    private ArrayList<ItemInvoicePurchaseDraftEntity> iteminvoicepurchasedraftlist;
    private final ArrayList<ItemInvoicePurchaseDraftEntity> deletediteminvoicepurchasedraftlist;
    
    private final DiscountSubtotalPanel totaltable;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PRICE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PERCENTAGE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DISCOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL")
    };
    
    public ItemInvoicePurchaseDraftTableModel(ArrayList<ItemInvoicePurchaseDraftEntity> iteminvoicepurchasedraftlist,
            InvoicePurchaseDraftEntity invoicepurchasedraft,
            DiscountSubtotalPanel totaltable){
        
        super();
        iteminvoicepurchasedraftController = new ItemInvoicePurchaseDraftController();
        deletediteminvoicepurchasedraftlist = new ArrayList<>();
        this.iteminvoicepurchasedraftlist = iteminvoicepurchasedraftlist;
        this.invoicepurchasedraft = invoicepurchasedraft;
        this.totaltable = totaltable;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicepurchasedraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        ItemInvoicePurchaseDraftEntity iis = iteminvoicepurchasedraftlist.get(row);
        
        if(col == 1){
            ItemEntity item = (ItemEntity)value;
            if(item == null){
                
            }
            else{
                iis.setItem(item);
                iis.setUom(item.getBuyuom());

                SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                        ClientResponse response;

                    @Override
                    protected Boolean doInBackground(){

                        response = RestClient.get("getBuyPrice?itemid=" + item.getId() + "&uomid=" + iis.getUom().getId());

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
                                BuyPriceEntity buyprice = mapper.
                                        readValue(output, BuyPriceEntity.class);
                                iis.setPrice(buyprice.getValue());
                                totaltable.setSubtotal(totaltable.getSubtotal() + iis.getSubtotal());
                            }
                            catch(IOException e){
                                iis.setPrice(0);
                            }
                        }

                        fireTableCellUpdated(row, 3);
                        fireTableCellUpdated(row, 4);
                        fireTableCellUpdated(row, 7);
                    }
                };
                worker.execute();
            }
        }
        else if(col == 2){
            totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
            iis.setQuantity(GlobalFunctions.convertToQuantity(value.toString()));
            fireTableCellUpdated(row, 7);
            totaltable.setSubtotal(totaltable.getSubtotal() + iis.getSubtotal());
        }
        else if(col == 3){
            UomEntity uom = (UomEntity)value;
            iis.setUom(uom);
            
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                    ClientResponse response;

                @Override
                protected Boolean doInBackground(){

                    response = RestClient.get("getBuyPrice?itemid=" + iis.getItem().getId() +
                            "&uomid=" + iis.getUom().getId());

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
                            totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
                            BuyPriceEntity buyprice = mapper.
                                    readValue(output, BuyPriceEntity.class);
                            iis.setPrice(buyprice.getValue());
                            totaltable.setSubtotal(totaltable.getSubtotal() + iis.getSubtotal());
                        }
                        catch(IOException e){
                            iis.setPrice(0);
                        }
                    }
                    
                    fireTableCellUpdated(row, 3);
                    fireTableCellUpdated(row, 4);
                    fireTableCellUpdated(row, 7);
                }
            };
            worker.execute();
        }
        else if(col == 4){
            totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
            iis.setPrice(GlobalFunctions.convertToDouble(value.toString()));
            fireTableCellUpdated(row, 7);
            totaltable.setSubtotal(totaltable.getSubtotal() + iis.getSubtotal());
        }
        else if(col == 5){
            totaltable.setDiscount(totaltable.getDiscount() - iis.getDiscount());
            double percentage = GlobalFunctions.convertToDouble(value.toString());
            iis.setDiscount(iis.getSubtotal() * percentage / 100);
            
            fireTableCellUpdated(row, 6);
            fireTableCellUpdated(row, 7);
            totaltable.setDiscount(totaltable.getDiscount() + iis.getDiscount());
        }
        else if(col == 6){
            totaltable.setDiscount(totaltable.getDiscount() - iis.getDiscount());
            iis.setDiscount(GlobalFunctions.convertToDouble(value.toString()));
            fireTableCellUpdated(row, 5);
            fireTableCellUpdated(row, 7);
            totaltable.setDiscount(totaltable.getDiscount() + iis.getDiscount());
        }
        
        
        if(row+1 == getRowCount()){
            iteminvoicepurchasedraftlist.add(iteminvoicepurchasedraftController.
                    createEmptyItemInvoicePurchaseDraft(invoicepurchasedraft));
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoicePurchaseDraftEntity iis = iteminvoicepurchasedraftlist.get(rowIndex);
        
        String description = "";
        String uom = "";
        
        if(iis.getItem() != null){
            description = iis.getItem().getCode() + " - " + iis.getItem().getName();
        }
        
        if(iis.getUom() != null){
            uom = iis.getUom().toString();
        }
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            description, 
            iis.getFormattedQuantity(), 
            uom,
            iis.getFormattedPrice(),
            iis.getFormattedDiscount_percentage(),
            iis.getFormattedDiscount(),
            iis.getFormattedTotal(),
            
        };
        return values[columnIndex];
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        if(getItemInvoicePurchaseDraftList().get(row).getItem() != null){
            return col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 6;
        }
        else{
            return col == 1;
        }
    }
    
    public void addRow(ItemInvoicePurchaseDraftEntity iis) {
        iteminvoicepurchasedraftlist.add(iis);
    }
    
    public void deleteRow(int rownum){
          
        ItemInvoicePurchaseDraftEntity iis = iteminvoicepurchasedraftlist.get(rownum);
        
        deletediteminvoicepurchasedraftlist.add(iis);
        
        iteminvoicepurchasedraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
        
        totaltable.setDiscount(totaltable.getDiscount() - iis.getDiscount());
        
        if(getRowCount() == 0){
            addRow(iteminvoicepurchasedraftController.createEmptyItemInvoicePurchaseDraft(invoicepurchasedraft));
        }
    }
    
    public List<ItemInvoicePurchaseDraftEntity> getItemInvoicePurchaseDraftList(){
        return iteminvoicepurchasedraftlist;
    }
    
    public List<ItemInvoicePurchaseDraftEntity> getDeletedItemInvoicePurchaseDraftList(){
        return deletediteminvoicepurchasedraftlist;
    }
    
    public void setItemInvoicePurchaseDraftList(ArrayList<ItemInvoicePurchaseDraftEntity> 
            iteminvoicepurchasedraftlist){
        this.iteminvoicepurchasedraftlist = iteminvoicepurchasedraftlist;
        fireTableDataChanged();
    }
    
    public boolean isEmpty(){
        boolean status = true;
        
        for(ItemInvoicePurchaseDraftEntity id : iteminvoicepurchasedraftlist){
            if(id.getItem() != null){
                status = false;
            }
        }
        
        return status;
    }
    
    public ItemInvoicePurchaseDraftEntity getItemInvoicePurchaseDraft(int index){
        return iteminvoicepurchasedraftlist.get(index);
    }
}
