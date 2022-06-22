package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalesReport {
    private List<Items> items = new ArrayList<>();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String filePath = "";

    public SalesReport(List<Items> items) {
        this.items = items;
        //write a new sales report to a new file
        this.filePath = "salesReport-"+ LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) +".txt";
        File file = new File(this.filePath);
        try (PrintWriter reportWriter = new PrintWriter(new FileOutputStream(file, false));){
            Double totalSales=0.0;
            for(Items item: items) {
                reportWriter.println(item.getProductName() + "|"+item.getSalesCount());
                totalSales+= item.getPrice() * item.getSalesCount();
            }
            reportWriter.println("");
            reportWriter.println("TOTAL SALES " + CurrencyFormat.toCurrencyString(totalSales));
            reportWriter.flush();
        } catch (Exception e) {
            Logger.log("Error generating sales report: " + e.getMessage());
        }
    }
}
