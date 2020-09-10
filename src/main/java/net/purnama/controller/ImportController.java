/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import net.purnama.model.ItemEntity;
import net.purnama.model.ItemGroupEntity;
import net.purnama.model.ItemWarehouseEntity;
import net.purnama.model.PartnerEntity;
import net.purnama.model.PartnerTypeEntity;
import net.purnama.model.SellPriceEntity;
import net.purnama.model.UomEntity;
import net.purnama.util.GlobalFields;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author purnama
 */
public class ImportController {
    
    private final String FILE_NAME;
    
    public ImportController(String FILE_NAME){
        this.FILE_NAME = FILE_NAME;
    }
    
    public ArrayList<ItemWarehouseEntity> importItemWarehouse(){
        ArrayList<ItemWarehouseEntity> list = new ArrayList<>();
        
        return list;
    }
    
    public ArrayList<SellPriceEntity> importSellPrice(){
        ArrayList<SellPriceEntity> list = new ArrayList<>();
        
        return list;
    }
    
    public ArrayList<ItemGroupEntity> importItemGroup(){
        ArrayList<ItemGroupEntity> list = new ArrayList<>();
        
        try{
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
//                Iterator<Cell> cellIterator = currentRow.iterator();

                ItemGroupEntity itemgroup = new ItemGroupEntity();
                
                Cell code = currentRow.getCell(0);
                if(code.getStringCellValue().equals("")){
                    
                }
                else{
                    itemgroup.setCode(code.getStringCellValue());

                    Cell name = currentRow.getCell(1);
                    itemgroup.setName(name.getStringCellValue());

                    Cell note = currentRow.getCell(2);
                    itemgroup.setNote(note.getStringCellValue());

                    itemgroup.setStatus(true);

    //                while (cellIterator.hasNext()) {
    //
    //                    Cell currentCell = cellIterator.next();
    //                    
    //                    if (currentCell.getCellTypeEnum()== CellType.STRING) {
    //                        System.out.print(currentCell.getStringCellValue() + "--");
    //                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
    //                        System.out.print(currentCell.getNumericCellValue() + "--");
    //                    }
    //
    //                }

                    list.add(itemgroup);
                }
            }
        } 
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_FILENOTFOUND"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_IMPORT"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        }
        catch (IllegalStateException e){
            JOptionPane.showMessageDialog(null, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_IMPORT"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        }
        
        return list;
    }
    
    public ArrayList<PartnerEntity> importPartner(){
        ArrayList<PartnerEntity> list = new ArrayList<>();
        
        try{
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();

                PartnerEntity partner = new PartnerEntity();
                
                Cell code = currentRow.getCell(0);
                if(code.getStringCellValue().equals("")){
                    
                }
                else{
                    partner.setCode(code.getStringCellValue());

                    Cell name = currentRow.getCell(1);
                    partner.setName(name.getStringCellValue());

                    Cell contactname = currentRow.getCell(2);
                    partner.setContactname(contactname.getStringCellValue());

                    Cell partnertypename = currentRow.getCell(3);
                    PartnerTypeEntity partnertype = new PartnerTypeEntity();
                    partnertype.setName(partnertypename.getStringCellValue());
                    partner.setPartnertype(partnertype);

                    Cell address = currentRow.getCell(4);
                    partner.setAddress(address.getStringCellValue());

                    Cell phonenumber = currentRow.getCell(5);
                    partner.setPhonenumber(phonenumber.getStringCellValue());

                    Cell phonenumber2 = currentRow.getCell(6);
                    partner.setPhonenumber2(phonenumber2.getStringCellValue());

                    Cell mobilenumber = currentRow.getCell(7);
                    partner.setMobilenumber(mobilenumber.getStringCellValue());

                    Cell faxnumber = currentRow.getCell(8);
                    partner.setFaxnumber(faxnumber.getStringCellValue());

                    Cell email = currentRow.getCell(9);
                    partner.setEmail(email.getStringCellValue());

                    Cell maxdiscount = currentRow.getCell(10);
                    partner.setMaximumdiscount(maxdiscount.getNumericCellValue());

                    Cell maxbalance = currentRow.getCell(11);
                    partner.setMaximumbalance(maxbalance.getNumericCellValue());

                    Cell paymentdue = currentRow.getCell(12);
                    partner.setPaymentdue((int) paymentdue.getNumericCellValue());

                    Cell note = currentRow.getCell(13);
                    partner.setNote(note.getStringCellValue());

                    partner.setBalance(0);
                    partner.setStatus(true);

                    list.add(partner);
                }
            }
        } 
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_FILENOTFOUND"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        } 
        catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_IMPORT"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        }
        catch (IllegalStateException e){
            JOptionPane.showMessageDialog(null, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_IMPORT"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        }
        
        return list;
    }
    
    public ArrayList<ItemEntity> importItem(){
        ArrayList<ItemEntity> list = new ArrayList<>();
        
        try{
            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();

                ItemEntity item = new ItemEntity();
                
                Cell code = currentRow.getCell(0);
                if(code.getStringCellValue().equals("")){
                    
                }
                else{
                    item.setCode(code.getStringCellValue());

                    Cell name = currentRow.getCell(1);
                    item.setName(name.getStringCellValue());

                    Cell itemgroupcode = currentRow.getCell(2);
                    ItemGroupEntity itemgroup = new ItemGroupEntity();
                    itemgroup.setCode(itemgroupcode.getStringCellValue());
                    item.setItemgroup(itemgroup);

                    Cell buyuomname = currentRow.getCell(3);
                    UomEntity buyuom = new UomEntity();
                    buyuom.setName(buyuomname.getStringCellValue());
                    item.setBuyuom(buyuom);

                    Cell selluomname = currentRow.getCell(4);
                    UomEntity selluom = new UomEntity();
                    selluom.setName(selluomname.getStringCellValue());
                    item.setSelluom(selluom);

                    Cell cost = currentRow.getCell(5);
                    item.setCost(cost.getNumericCellValue());

                    Cell note = currentRow.getCell(6);
                    item.setNote(note.getStringCellValue());

                    item.setStatus(true);

                    list.add(item);
                }
            }
        } 
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_FILENOTFOUND"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_IMPORT"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        }
        catch (IllegalStateException e){
            JOptionPane.showMessageDialog(null, 
                    GlobalFields.PROPERTIES.getProperty("ERROR_IMPORT"), "", 
                    JOptionPane.ERROR_MESSAGE);
            
            return null;
        }
        
        return list;
    }
}
