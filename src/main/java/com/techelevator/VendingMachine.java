package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private List<Items> items = new ArrayList<>();

    public List<Items> getItems() {
        return items;
    }

    public VendingMachine() throws Exception{
        File file = new File("vendingmachine.csv");
        try (Scanner inputFile = new Scanner(file)) {
            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                String[] itemInfo =line.split("\\|");
                Items newItems= new Items();
                newItems.setSlotLocation(itemInfo[0]);
                newItems.setProductName(itemInfo[1]);
                newItems.setPrice(Double.parseDouble(itemInfo[2]));
                newItems.setType(itemInfo[3]);
                newItems.getCount();
                items.add(newItems);
            }

        }catch(FileNotFoundException e){
            System.err.print("File not found.");
        }
    }

    public Items getItem(String slotLocation) {
        for (Items item : this.items) {
            if (item.getSlotLocation().equalsIgnoreCase(slotLocation)) {
                return item;
            }
        } return null;
    }
}
