package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

import static com.techelevator.CurrencyFormat.toCurrencyString;
/**
 *
 * The VendingMachine class contains all the data and methods that represent the vending machine
 * including the list of products, the price for each product, the number of items remaining, amount of money put in, etc.
 * The class also has the method that calculates the amount of change to return to the user in quarters, dimes and nickels.
 */
public class VendingMachine {
    private List<Items> items = new ArrayList<>();
    private double currentBalance;
    //constants for money used for change in makeChange()method
    private static final String QUARTERS= "Quarters";
    private static final String DIMES= "Dimes";
    private static final String NICKELS= "Nickels";
    public List<Items> getItems() {
        return items;
    }
    public VendingMachine() throws Exception{
        //constructor which reads through the vendingmachine.csv file to convert into items in the
        // inventory, represented by Items property which is a List of Items
        //enclosed in a try and catch block to capture any error in reading the inventory file
        File file = new File("vendingmachine.csv");
        try (Scanner inputFile = new Scanner(file)) {
            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                //converting a line from input file
                String[] itemInfo =line.split("\\|");
                Items newItems= new Items();
                newItems.setSlotLocation(itemInfo[0]);
                newItems.setProductName(itemInfo[1]);
                newItems.setPrice(Double.parseDouble(itemInfo[2]));
                newItems.setType(itemInfo[3]);
                newItems.getCount();
                items.add(newItems);
            }
        }catch(Exception e){
            //error in reading vendingmachine.csv
            System.err.print("Error reading Inventory File");
        }
    }

    public void feedMoney(Double money){
        //Add user input money to current balance each time
        this.currentBalance+=money;
        Logger.log("FEED MONEY "+ toCurrencyString(money)+ " "+ toCurrencyString(this.currentBalance));//log transaction
    }

    public Double getCurrentBalance(){
        return currentBalance;
    }

    public Items dispenseItem(Items item){
        //Dispense item when user balance is enough and is not out of stock
        DecimalFormat df = new DecimalFormat("#.##");
        // subtract the price from users balance
        double newBalance=Double.valueOf(df.format(this.currentBalance- item.getPrice()));
        //log transaction
        Logger.log(item.getProductName() + " "
                + item.getSlotLocation()+ " "
                + toCurrencyString(this.currentBalance) +" "
                + toCurrencyString(newBalance));
        // 4.Balance and item count are updated after purchase and carried over to purchase menu
        this.currentBalance= newBalance;
        item.decrement(); //item count less 1 and sales count + 1 (for sales report)
        return item;//finish dispensing the item
    }

    public Map<String, Integer> makeChange() {
        // 5. make remaining balance into change using least amount of coins
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
        //log transaction
        Logger.log("GIVE CHANGE "+ toCurrencyString(changeAmount)+ " "
                + toCurrencyString(this.currentBalance));
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
