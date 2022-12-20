
package salesController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import salesModel.Invoice;
import salesModel.InvoiceTableModel;
import salesModel.Item;
import salesModel.ItemsTableModel;
import salesView.InvoiceDialog;
import salesView.InvoiceFrame;
import salesView.ItemDialog;

public class Controller implements ActionListener, ListSelectionListener {

    private InvoiceFrame invF;
    private InvoiceDialog invDial;
    private ItemDialog itmDial;
    public Controller (InvoiceFrame invF){
        this.invF = invF;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);
        
        switch (actionCommand)
        {
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Add Item":
                addItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "crtInvOK":
                createInvoiceOK();
                break;
            case "ctrInvCancle":
                createInvoiceCancle();
                break;
            case "addItmOk":
                addItemOk();
                break;
            case "addItmCancel":
                addItemCancle();
                break;
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = invF.getInvoiceTable().getSelectedRow();
        if(selectedIndex != -1)
        {
        Invoice inv = invF.getInvoices().get(selectedIndex);
        invF.getInvoiceNoLable().setText(inv.getInvoiceNo()+"");
        invF.getInvoiceDateLable().setText(inv.getInvoiceDate());
        invF.getCustomerNameLable().setText(inv.getCustomerName());
        invF.getInvoiceTotalLable().setText(inv.getInvoiceTotal() + "");
        
        ItemsTableModel itmTM = new ItemsTableModel(inv.getItems());
        invF.getItemTable().setModel(itmTM);
        itmTM.fireTableDataChanged();
        }
    }
    
    private void openFile() 
    {
        JFileChooser fc = new JFileChooser();
        //invoice header file
        int choice = fc.showOpenDialog(invF);
        try{
            if (choice == JFileChooser.APPROVE_OPTION) 
            {
            File invoiceHeaderFile = fc.getSelectedFile();
            Path invoiceHeaderPath = Paths.get(invoiceHeaderFile.getAbsolutePath());
            List <String> invoiceHeaderLines = Files.readAllLines(invoiceHeaderPath);
            ArrayList <Invoice> invoices = new ArrayList<>();
            
            for(String invoicesHeaderLine : invoiceHeaderLines )
            {
               String [] invoiceHeaderParts = invoicesHeaderLine.split(",");
               int invoiceNo = Integer.parseInt(invoiceHeaderParts[0]);
               String invoiceDate = invoiceHeaderParts[1];
               String customerName = invoiceHeaderParts[2];
               
               Invoice inv = new Invoice(invoiceNo, invoiceDate, customerName);
               invoices.add(inv);
            }

            //invoice lines file
            choice = fc.showOpenDialog(invF);
            if (choice == JFileChooser.APPROVE_OPTION)
            {
                File invoiceItemFile = fc.getSelectedFile();
                Path invoiceItemPath = Paths.get(invoiceItemFile.getAbsolutePath());
                List <String> invoiceItemLines = Files.readAllLines(invoiceItemPath);
                
                for(String invoiceItemLine : invoiceItemLines )
                {
                   String [] invoiceItemParts = invoiceItemLine.split(",");
                   int invoiceNo = Integer.parseInt(invoiceItemParts[0]);
                   String itemName = invoiceItemParts[1];
                   double itemPrice = Double.parseDouble(invoiceItemParts[2]);
                   int count = Integer.parseInt(invoiceItemParts[3]);
                   
                   Invoice inv = null;
                   for(Invoice i :invoices)
                   {
                       if(i.getInvoiceNo()==invoiceNo) 
                       {
                           inv=i;
                           break;
                       }
                   }
                   
                   Item itm = new Item(itemName, itemPrice, count, inv);
                   inv.getItems().add(itm);
                }
            }
            
            invF.setInvoices(invoices);
            InvoiceTableModel invTM = new InvoiceTableModel(invoices);
            invF.setInvTM(invTM);
            invF.getInvoiceTable().setModel(invTM);
            invF.getInvTM().fireTableDataChanged();
            }
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }

    private void saveFile() 
    {
        ArrayList<Invoice> invoices = invF.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoice invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (Item itm : invoice.getItems()) {
                String lineCSV = itm.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(invF);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(invF);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() 
    {
        invDial = new InvoiceDialog(invF);
        invDial.setVisible(true);
    }

    private void deleteInvoice() 
    {
       int row = invF.getInvoiceTable().getSelectedRow();
       if(row != -1) 
       {
           invF.getInvoices().remove(row);
           invF.getInvTM().fireTableDataChanged();
       }
    }

    private void addItem() 
    {
        ItemDialog itmDial = new ItemDialog(invF);
        itmDial.setVisible(true);
    }

    private void deleteItem() 
    {
        int invNo = invF.getInvoiceTable().getSelectedRow();
        int row = invF.getItemTable().getSelectedRow();
        if(row != -1 && invNo != -1) 
        {
        Invoice inv = invF.getInvoices().get(invNo);
        inv.getItems().remove(row);
        ItemsTableModel itmTM = new ItemsTableModel(inv.getItems());
        invF.getItemTable().setModel(itmTM);
        itmTM.fireTableDataChanged();
        }
    }

    private void createInvoiceOK() {
        String date = invDial.getInvDateField().getText();
        String cust = invDial.getCustNameField().getText();
        
        int num=0;
        for(Invoice inv : invF.getInvoices())
        {
            if(inv.getInvoiceNo()>num)
            {
                num=inv.getInvoiceNo();
            }
            else
            {
                num+=1;
            }
        }
        
        Invoice invoice = new Invoice(num, date, cust);
        invF.getInvoices().add(invoice);
        invF.getInvTM().fireTableDataChanged();
    }

    private void createInvoiceCancle() {
        invDial.setVisible(false);
        invDial.dispose();
        invDial = null;
    }
    
    private void addItemOk() {
        
       String item = itmDial.getItemNameField().getText();
        String countStr = itmDial.getItemCountField().getText();
        String priceStr = itmDial.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = invF.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Invoice invoice = invF.getInvoices().get(selectedInvoice);
            Item itm = new Item(item, price, count, invoice);
            invoice.getItems().add(itm);
            ItemsTableModel itmTM = (ItemsTableModel) invF.getItemTable().getModel();
            itmTM.getItems().add(itm);
            itmTM.fireTableDataChanged();
            invF.getInvTM().fireTableDataChanged();
        }
        itmDial.setVisible(false); 
        itmDial.dispose();
        itmDial = null;
    }

    private void addItemCancle() {
        itmDial.setVisible(false);
        itmDial.dispose();
        itmDial = null;
    }
}