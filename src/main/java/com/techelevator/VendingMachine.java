package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

import static com.techelevator.CurrencyFormat.toCurrencyString;

public class VendingMachine {
    private List<Items> items = new ArrayList<>();
    private double currentBalance;
    private static final String QUARTERS= "Quarters";
    private static final String DIMES= "Dimes";
    private static final String NICKELS= "Nickels";

    public List<Items> getItems() {
        return items;
    }



    public VendingMachine() throws Exception{
        //Read through vendingmachine.csv file to stock inventory information
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
        //Add user input money to current balance each time
        this.currentBalance+=money;
        Logger.log("FEED MONEY "+ toCurrencyString(money)+ " "+ toCurrencyString(this.currentBalance));
    }

    public Double getCurrentBalance(){
        return currentBalance;
    }

    public Items dispenseItem(Items item){
        //Dispense item when user balance is enough and is not out of stock
        DecimalFormat df = new DecimalFormat("#.##");
        double newBalance=Double.valueOf(df.format(this.currentBalance- item.getPrice()));// subtract the price from users balance
        Logger.log(item.getProductName() + " "+ item.getSlotLocation()+ " " + toCurrencyString(this.currentBalance) +" "+ toCurrencyString(newBalance));
        this.currentBalance= newBalance; //balance is updated
        int newCount = item.getCount()-1;// item count is one less
        item.setCount(newCount); //update item count


        return item;//finish dispensing the item

    }

    public Map<String, Integer> makeChange() {
        //make remaining balance into change using least amount of coins
        DecimalFormat df = new DecimalFormat("#.##");
        Map<String, Integer> numberOfCoins = new HashMap<String, Integer>();
        Double changeAmount=this.currentBalance;
        //divide balance into quarters first, remainder passed to the next step
        int numberOfQuarters = (int) (this.currentBalance / 0.25);
        this.currentBalance = Double.valueOf(df.format((this.currentBalance - (numberOfQuarters * 0.25))));
        numberOfCoins.put(QUARTERS+ ": ", numberOfQuarters);
        //divide remainder balance into dimes, remainder is passed to the next step again
        int numberOfDimes = (int) (this.currentBalance / 0.10);
        this.currentBalance = Double.valueOf(df.format((this.currentBalance - (numberOfDimes * 0.10))));
        numberOfCoins.put(DIMES+ ": ", numberOfDimes);
        //remainder is a nickel
        int numberOfNickels;
        if (this.currentBalance == 0.05) {
            numberOfNickels = 1;
        //there is no remainder so no nickels necessary
        } else {
            numberOfNickels = 0;
        }
        numberOfCoins.put(NICKELS+ ": ", numberOfNickels);

        this.currentBalance=0.00;
        Logger.log("GIVE CHANGE "+ toCurrencyString(changeAmount)+ " " + toCurrencyString(this.currentBalance));
        return numberOfCoins;

    }


    public Items getItem(String slotLocation) {
        //identify item using slot location
        for (Items item : this.items) {
            if (item.getSlotLocation().equalsIgnoreCase(slotLocation)) {
                return item;
            }
        } return null;
    }
}
