package com.techelevator;

import com.techelevator.view.Menu;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Scanner;

import static com.techelevator.CurrencyFormat.toCurrencyString;
/**
 * The VendingMachineCLI class is the main class of the Vending Machine Application and
 * handle all the user interface. It uses the Menu class to display the menu and get user selection.
 * It also uses the VendingMachine class to process the user selection.
 */
public class VendingMachineCLI {
	//Main menu options
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT= "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT= "";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT };
	//Purchase menu options
	private static final String PURCHASE_OPTION_FEED_MONEY= "Feed Money";
	private static final String PURCHASE_OPTION_SELECT_PRODUCT= "Select Product";
	private static final String PURCHASE_OPTION_FINISH_TRANSACTION= "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS= {PURCHASE_OPTION_FEED_MONEY, PURCHASE_OPTION_SELECT_PRODUCT, PURCHASE_OPTION_FINISH_TRANSACTION};
	private Menu menu;

	private VendingMachine vendingMachine;
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		try{
		    this.vendingMachine=new VendingMachine();
        }catch(Exception e){
            System.out.println("Error during start up"+ e.getMessage());

        }
	}

	public void run() {
		//Displays a main menu with the options to view items, make a purchase and exit the program
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, "MAIN MENU");

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// Display vending machine items
				displayItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// Go to purchase menu
				purchaseMenu();
			}else if(choice.equals(MAIN_MENU_OPTION_SALES_REPORT)){
				SalesReport salesReport = new SalesReport(vendingMachine.getItems());
				System.out.println("Sales report generated!");
			}else if(choice.equals(MAIN_MENU_OPTION_EXIT)){
				// Program exits
				break;
			}
		}
	}

	private void purchaseMenu(){
		//Display purchase menu with the options to add to balance, select items and finish transaction

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, "PURCHASE MENU", "Current Money Provided: "+ toCurrencyString(vendingMachine.getCurrentBalance()));

			if (choice.equals(PURCHASE_OPTION_FEED_MONEY)) {
				// FEED MONEY
				enterMoney();
			} else if (choice.equals(PURCHASE_OPTION_SELECT_PRODUCT)) {
				// SELECT PRODUCT, update balance and return to purchase menu
				selectProduct();
			}else if(choice.equals(PURCHASE_OPTION_FINISH_TRANSACTION)){
				//FINISH TRANSACTION and return to Main menu
				finishTransaction();
				break;
			}
		}
	}

	private void displayItems() {
		// Display the items in the inventory
		System.out.println("\n\r\n\rITEMS"); //Title
		for (Items item : vendingMachine.getItems()) {
			//check item count is zero, display "SOLD OUT" message
			if (item.getCount() == 0) {
				System.out.println(item.getSlotLocation() + " "
						+ item.getProductName()
						+ ", Price: " + toCurrencyString(item.getPrice())
						+ " SOLD OUT");
			} else { //Display item information
				System.out.println(item.getSlotLocation() + " "
						+ item.getProductName()
						+ ", Price: " + toCurrencyString(item.getPrice())
						+ " Count: " + item.getCount());
			}
		}
	}

	 private void enterMoney() {
		//As user enters money, the balance updates accordingly
         Double money = 0.00;
         boolean answerYes = true;
         Scanner scanner = new Scanner(System.in);
         while (answerYes) {
			 System.out.println("\n\r\n\r FEED MONEY"); //Title
			 // Let user know what is only accepted beforehand
			 System.out.println("Accepts in dollar: 1, 2, 5, 10 only. \n\r");

             System.out.print("Enter dollar amount: "); //Prompt user to enter money amount
             String userInput = scanner.nextLine();
             int moneyInput = 0;

			 moneyInput = Integer.parseInt(userInput);
			 if(moneyInput==1 || moneyInput==2 || moneyInput==5 || moneyInput==10){
				 money= (double)moneyInput;
				 vendingMachine.feedMoney(money);
				 System.out.println("Current money provided: "
						 + toCurrencyString(vendingMachine.getCurrentBalance()));
			 }else{//When user inputs anything other than $1, $2, $5, and $10
				//Let user know they have entered an invalid amount
				 System.out.println("Invalid input.");
			 }
			 //Check if user wants to add more to their balance
			 System.out.print("\n\r Do you want to enter money again? Y or N: ");
			 String yesNo=scanner.nextLine();
			 if(yesNo.equalsIgnoreCase("Y")){
				//User selected yes, prompt user to input more money
				answerYes=true;
			 }else if (yesNo.equalsIgnoreCase("N")) {
				//User selected no, return to purchase menu
				 answerYes = false;
			 } else {
				//When user input is something else than Y and N
				 System.out.println("Invalid Input");// Let user know input is invalid
				 break;// Returns user to purchase menu
			 }
         }
     }

     private void selectProduct(){
		// 3. Vending Machine Purchase
		displayItems();// Displays items
		boolean yes= true;
		while(yes) {
			System.out.print("\n\r Enter product code: ");// Prompt user to select an item
			Items itemChoice = getProductChoice();// Calls getProductChoice() to refer to users choice input
				if (itemChoice == null) {
					//Invalid item selected
					System.out.println("Product not found");
					yes = false;//Return user to purchase menu
				} else if (itemChoice.getCount() == 0) {
					//If item count is zero, let user know it is sold out
					System.out.println("ITEM SOLD OUT");
					yes = false;//Return user to purchase menu
				} else if (itemChoice.getPrice() > this.vendingMachine.getCurrentBalance()) {
					//If users current balance is less than the price, then let user know
					System.out.println("Insufficient funds.");
					yes = false; //Return user to purchase menu

				} else {
					//Call the dispenseItem() method
					Items dispenseItem = this.vendingMachine.dispenseItem(itemChoice);//Item successfully dispensed
					System.out.println("Item dispensed: " + dispenseItem.getProductName() + " "
							+ CurrencyFormat.toCurrencyString(dispenseItem.getPrice()));
					System.out.println("Balance remaining: " + CurrencyFormat.toCurrencyString(vendingMachine.getCurrentBalance()));
					//Messages for each type of product
					if (dispenseItem.getType().equalsIgnoreCase("Chip")) {
						System.out.println("Crunch Crunch, Yum!");
					} else if (dispenseItem.getType().equalsIgnoreCase("Candy")) {
						System.out.println("Munch Munch, Yum!");
					} else if (dispenseItem.getType().equalsIgnoreCase("Drink")) {
						System.out.println("Glug Glug, Yum!");
					} else if (dispenseItem.getType().equalsIgnoreCase("Gum")) {
						System.out.println("Chew Chew, Yum!");
					}
					yes = false;// return to purchase menu after dispensing item
				}
			}
	 }

	 private Items getProductChoice(){
		//User inputs desired item
		Scanner scanner= new Scanner(System.in);
		while(true){
			String userInput= scanner.nextLine();
			Items item= this.vendingMachine.getItem(userInput.trim());
			if(item==null){ //Item not present in inventory was entered
				break;
			}else{
				//Item is present in inventory
				return item;
			}
		}
		return null;
	 }

	 private void finishTransaction(){
		//call makeChange() method from VendingMachine
		// 5.1 handle change back from vending machine
		 System.out.println("\n\r\n\rYour change is: "+ toCurrencyString(vendingMachine.getCurrentBalance()));
		 Map<String, Integer> changeGiven= this.vendingMachine.makeChange();
		 for(Map.Entry<String, Integer> moneyOut: changeGiven.entrySet()){
			 System.out.println(moneyOut.getKey() + " "+ moneyOut.getValue());
		 }
	 }

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
