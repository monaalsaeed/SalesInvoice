
package salesModel;

import java.util.ArrayList;


public class Invoice {
   private int invoiceNo;
   private  String invoiceDate;
   private String customerName;
   private ArrayList <Item> items; 
   
   public double getInvoiceTotal ()
   {
       double total = 0;
       for(Item i : getItems())
       {
           total+=i.getTotal();
       }
       return total;
   }
    public ArrayList<Item> getItems() {
        if(items == null) items = new ArrayList<>();
        return items;
    }

    public Invoice() {
    }

    public Invoice(int invoiceNo, String invoiceDate, String customerName) {
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getAsCSV() {
        return invoiceNo + "," + invoiceDate + "," + customerName;
    }
    
}
