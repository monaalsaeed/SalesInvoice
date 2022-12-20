/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesModel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mona
 */
public class ItemsTableModel extends AbstractTableModel
{
    
    private ArrayList <Item> items;
    private String [] cols = {"NO.","Item Name","Item Price","Count", "Item Total"};

    public ItemsTableModel(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;   
    }
    
    @Override
    public String getColumnName(int c){
        return cols[c];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item i = items.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0:
                return i.getInvoice().getInvoiceNo();
            case 1:
                return i.getItemName();
            case 2: 
                return i.getItemPrice();
            case 3:
                return i.getCount();
            case 4:
                return i.getTotal();
            default:
                return "invalid line!!";
        }
    }
    
}
