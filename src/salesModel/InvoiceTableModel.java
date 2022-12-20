
package salesModel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceTableModel extends AbstractTableModel{
    
    private ArrayList <Invoice> invoices;
    private String [] cols = {"No.", "Date", "Customer", "Total"};

    public InvoiceTableModel(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
    return invoices.size();
    }
    
    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
    return cols[c];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Invoice inv = invoices.get(rowIndex);
        switch (columnIndex)
        {
            case 0: return inv.getInvoiceNo();
            case 1: return inv.getInvoiceDate();
            case 2: return inv.getCustomerName();
            case 3: return inv.getInvoiceTotal();
            default: return "invalid column";
        }
            }
    
    }
