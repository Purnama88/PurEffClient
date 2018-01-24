/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.purnama.model.ItemWarehouseEntity;
import net.purnama.model.SellPriceEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author purnama
 */
public class ExportController {
    
    private final String FILE_NAME;
    
    public ExportController(String FILE_NAME){
        this.FILE_NAME = FILE_NAME;
    }
    
    public boolean exportItemSellPriceTemplateToExcel(List<SellPriceEntity> list){
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        XSSFSheet sheet = workbook.createSheet("Item Sell Price");
        
        int numofuom = 0;
        String temp = list.get(0).getItem().toString();
        
        List<String> uomnames = new ArrayList<>();
        
        for(SellPriceEntity sellprice : list){
            if(temp.equals(sellprice.getItem().toString())){
                uomnames.add(sellprice.getUom().toString());
                numofuom++;
            }
            else{
                break;
            }
        }
        
        int numofitem = list.size() /  numofuom;
        
        Object[][] datatypes = new Object[numofitem + 1][numofuom+1];
        
        int rowindex = 0;
        int colindex = 1;
        
        for(String codes : uomnames){
            datatypes[rowindex][colindex] = codes;
            colindex++;
        }
        
        temp = "";
        rowindex = 0;
        colindex = 0;
        for(SellPriceEntity sellprice : list){
            if(temp.equals(sellprice.getItem().toString())){
                colindex += 1;
                datatypes[rowindex][colindex] = sellprice.getValue();
            }
            else{
                rowindex += 1;
                colindex = 0;
                temp = sellprice.getItem().toString();
                
                datatypes[rowindex][colindex] = sellprice.getItem().toString();
                
                colindex += 1;
                datatypes[rowindex][colindex] = sellprice.getValue();
            }
        }

        int rowNum = 0;

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            
            return true;
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
            
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public boolean exportItemStockTemplateToExcel(List<ItemWarehouseEntity> list){
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        XSSFSheet sheet = workbook.createSheet("ItemStock");
        
        int numofwarehouse = 0;
        String temp = list.get(0).getItem().toString();
        
        List<String> warehousecodes = new ArrayList<>();
        
        for(ItemWarehouseEntity itemwarehouse : list){
            if(temp.equals(itemwarehouse.getItem().toString())){
                warehousecodes.add(itemwarehouse.getWarehouse().getCode());
                numofwarehouse++;
            }
            else{
                break;
            }
        }
        
        int numofitem = list.size() /  numofwarehouse;
        
        Object[][] datatypes = new Object[numofitem + 1][numofwarehouse+1];
        
        int rowindex = 0;
        int colindex = 1;
        
        for(String codes : warehousecodes){
            datatypes[rowindex][colindex] = codes;
            colindex++;
        }
        
        temp = "";
        rowindex = 0;
        colindex = 0;
        for(ItemWarehouseEntity itemwarehouse : list){
            if(temp.equals(itemwarehouse.getItem().toString())){
                colindex += 1;
                datatypes[rowindex][colindex] = itemwarehouse.getStock();
            }
            else{
                rowindex += 1;
                colindex = 0;
                temp = itemwarehouse.getItem().toString();
                
                datatypes[rowindex][colindex] = itemwarehouse.getItem().toString();
                
                colindex += 1;
                datatypes[rowindex][colindex] = itemwarehouse.getStock();
            }
        }

        int rowNum = 0;

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            
            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public boolean exportItemTemplateToExcel(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Item");
        Object[][] datatypes = {
                {"CODE", "NAME", "ITEM GROUP CODE", "BUY UOM", "SELL UOM", 
                "COST", "NOTE"}
        };

        int rowNum = 0;

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            
            return true;
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
            
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public boolean exportItemGroupTemplateToExcel(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Item Group");
        Object[][] datatypes = {
                {"CODE", "NAME", "NOTE"}
        };

        int rowNum = 0;

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            
            return true;
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
            
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
    public boolean exportPartnerTemplateToExcel(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Partner");
        Object[][] datatypes = {
                {"CODE", "NAME", "CONTACT NAME", "PARTNER TYPE", "ADDRESS", 
                "PHONE NUMBER", "PHONE NUMBER 2", "MOBILE NUMBER", "FAX NUMBER",
                "EMAIL", "MAX DISCOUNT", "MAX BALANCE", "PAYMENT DUE (DAYS)", "NOTE"}
        };

        int rowNum = 0;

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            
            return true;
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
            
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    
}
