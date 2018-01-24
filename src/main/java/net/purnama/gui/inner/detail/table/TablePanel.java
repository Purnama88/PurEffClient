/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyTable;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class TablePanel extends JScrollPane{
    
    protected final MyTable table;
    
    protected final JPopupMenu popupmenu;
    
    protected final JMenuItem menuitemdelete, menuitemcopy;
    
    
    public TablePanel(){
        super();
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        table = new MyTable();
        setBorder(null);
        getViewport().add(table);
        
        popupmenu = new JPopupMenu();
        
        menuitemdelete = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_DELETE"),
                new MyImageIcon().getImage("net/purnama/image/Delete_16.png"));
        menuitemcopy = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_COPY"),
                new MyImageIcon().getImage("net/purnama/image/Copy_16.png"));
        
        popupmenu.add(menuitemcopy);
        popupmenu.addSeparator();
        popupmenu.add(menuitemdelete);
        
        table.setComponentPopupMenu(popupmenu);
        
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Point p = e.getPoint();
		if(SwingUtilities.isRightMouseButton(e)){
                    int rowNumber = table.rowAtPoint( p );
                    ListSelectionModel model = table.getSelectionModel();
                    model.setSelectionInterval(rowNumber, rowNumber);
		}
            }
        });
        
        menuitemcopy.addActionListener((ActionEvent e) -> {
            try{
                StringSelection stringselection = new StringSelection(table.
                        getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());

                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringselection,
                        null);
            }
            catch(HeadlessException exp){
                exp.printStackTrace();
            }
        });
        
    }
    
    public MyTable getTable(){
        return table;
    }
}
