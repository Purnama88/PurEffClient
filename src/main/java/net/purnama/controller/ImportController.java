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
import java.util.List;
import net.purnama.model.ItemEntity;
import net.purnama.model.ItemGroupEntity;
import net.purnama.model.PartnerEntity;
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
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public List<PartnerEntity> importPartner(){
        List<PartnerEntity> list = new ArrayList<>();
        
        return list;
    }
    
    public List<ItemEntity> importItem(){
        List<ItemEntity> list = new ArrayList<>();
        
        return list;
    }
}
