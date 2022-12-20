
package salesModel;

public class Item {
    
    private String itemName;
    private double itemPrice;
    private int count;
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public Item(String itemName, double itemPrice, int count, Invoice invoice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.invoice = invoice;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item(String itemName, double itemPrice, int count) {

        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getCount() {
        return count;
    }
    
    public double getTotal()
    {
        return itemPrice*count;
    }
    
        public String getAsCSV() {
        return invoice.getInvoiceNo()+ "," + itemName + "," + itemPrice + "," + count;
    }
}
