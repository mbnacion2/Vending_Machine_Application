package com.techelevator;

import com.techelevator.view.Menu;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT= "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	private static final String PURCHASE_OPTION_FEED_MONEY= "Feed Money";
	private static final String PURCHASE_OPTION_SELECT_PRODUCT= "Select Product";
	private static final String PURCHASE_OPTION_FINISH_TRANSACTION= "Finish Transaction";
	private static String[] PURCHASE_MENU_OPTIONS= {PURCHASE_OPTION_FEED_MONEY, PURCHASE_OPTION_SELECT_PRODUCT, PURCHASE_OPTION_FINISH_TRANSACTION};
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
		while (true) {
			System.out.println("\n\r\n\rMAIN MENU");
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				displayItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchaseMenu();
			}else if(choice.equals(MAIN_MENU_OPTION_EXIT)){
				//program exits
				break;
			}
		}
	}
	private void purchaseMenu(){

		//System.out.print("Current Money Provided: " + vendingMachine.getCurrentBalance() +"\n\r");
		while (true) {
			System.out.println("\n\rPURCHASE MENU");
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

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
			System.out.println("\n\r Current Money Provided: " + vendingMachine.getCurrentBalance()+"\n\r");

		}

	}

	private void displayItems(){
		//Display the vending machine items
		System.out.println("\n\r\n\rITEMS");
		for(Items item: vendingMachine.getItems()){
			System.out.println(item.getSlotLocation() +" "+ item.getProductName()+ ", $" + item.getPrice() + " count: "+ item.getCount());
		}
	}
	 private void enterMoney() {
         Double money = 0.00;
         boolean answerYes = true;
         Scanner scanner = new Scanner(System.in);
         while (answerYes) {

			 System.out.println("\n\r\n\r FEED MONEY");
			 System.out.println("Accepts: $1, $2, $5, $10 only. \n\r");

             System.out.print("Enter dollar amount: ");
             String userInput = scanner.nextLine();
             int moneyInput = 0;

             try {
                 moneyInput = Integer.parseInt(userInput);
                 if(moneyInput==1 || moneyInput==2 || moneyInput==5 || moneyInput==10){
                     money= (double)moneyInput;

                     vendingMachine.feedMoney(money);
					 System.out.println("Current money provided : $" + vendingMachine.getCurrentBalance());
                 }else{
					 System.out.println("Invalid input.");
				 }
				 System.out.print("\n\r Do you want to enter money again? Y or N: ");
                 String yesNo=scanner.nextLine();
                 if(yesNo.equalsIgnoreCase("Y")){
                 	answerYes=true;
				 }else if (yesNo.equalsIgnoreCase("N")) {
					 answerYes = false;
				 } else {
					 System.out.println("Invalid Input");
					 break;
				 }


             } catch (Exception e) {

             }


         }
     }

     private void selectProduct(){

		displayItems();// Displays items
		boolean yes= true;
		while(yes){
			System.out.print("\n\r Enter product code: ");
			Items itemChoice= getProductChoice();// calls getProductChoice() to refer to users choice
			if(itemChoice.getCount()==0){
				System.out.println("ITEM SOLD OUT");
			}else if( itemChoice.getPrice()> this.vendingMachine.getCurrentBalance()){
				System.out.println("Insufficient funds.");
				yes=false;

			}else{
				Items dispenseItem= this.vendingMachine.dispenseItem(itemChoice);
				System.out.println("Item dispensed: "+ dispenseItem.getProductName() + " $"+ dispenseItem.getPrice());
				System.out.println("Balance remaining: $"+ vendingMachine.getCurrentBalance());
				//Messages for each type
				if(dispenseItem.getType().equalsIgnoreCase("Chip")){
					System.out.println("Crunch Crunch, Yum!");
				}else if(dispenseItem.getType().equalsIgnoreCase("Candy")){
					System.out.println("Munch Munch, Yum!");
				}else if(dispenseItem.getType().equalsIgnoreCase("Drink")){
					System.out.println("Glug Glug, Yum!");
				}else if(dispenseItem.getType().equalsIgnoreCase("Gum")){
					System.out.println("Chew Chew, Yum!");
				}
				yes=false;
			}
		}


	 }

	 private Items getProductChoice(){
		Scanner scanner= new Scanner(System.in);
		while(true){
			String userInput= scanner.nextLine();
			Items item= this.vendingMachine.getItem(userInput.trim());
			if(item==null){
				System.out.println("Product not found");
				break;
			}else{
				return item;
			}
		}
		return null;
	 }

	 private void finishTransaction(){
		 System.out.println("\n\r\n\rYour change is: $"+ vendingMachine.getCurrentBalance());
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
