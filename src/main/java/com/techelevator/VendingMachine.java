package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class VendingMachine {
    private List<Items> items = new ArrayList<>();
    private double currentBalance;
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

    public void feedMoney(Double money){
        this.currentBalance+=money;
        Logger.log("FEED MONEY $"+ money +" $"+this.currentBalance );
    }

    public Double getCurrentBalance(){
        return currentBalance;
    }

    public Items dispenseItem(Items item){
        double newBalance=Double.valueOf(this.currentBalance- item.getPrice());
        Logger.log(item.getProductName() + " "+ item.getSlotLocation()+ " $" + this.currentBalance +" $"+ newBalance);
        this.currentBalance= newBalance;
        int newCount = item.getCount()-1;
        item.setCount(newCount);


        return item;

    }

    public Map<String, Integer> makeChange() {
        Map<String, Integer> numberOfCoins = new HashMap<String, Integer>();
        double changeAmount=this.currentBalance;

        int numberOfQuarters = (int) (this.currentBalance / 0.25);
        this.currentBalance -= numberOfQuarters * 0.25;
        numberOfCoins.put("Quarters: ", numberOfQuarters);

        int numberOfDimes = (int) (this.currentBalance / 0.10);
        this.currentBalance -= numberOfDimes * 0.10;
        numberOfCoins.put("Dimes: ", numberOfDimes);
        int numberOfNickels;
        if (this.currentBalance > 0) {
            numberOfNickels = 1;

        } else {
            numberOfNickels = 0;
        }
        numberOfCoins.put("Nickels: ", numberOfNickels);

        this.currentBalance=0.00;
        Logger.log("GIVE CHANGE $"+ changeAmount+ " $" + this.currentBalance);
        return numberOfCoins;

    }


    public Items getItem(String slotLocation) {
        for (Items item : this.items) {
            if (item.getSlotLocation().equalsIgnoreCase(slotLocation)) {
                return item;
            }
        } return null;
    }
}
