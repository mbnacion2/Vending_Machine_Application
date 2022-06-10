package com.techelevator;

import com.techelevator.view.Menu;

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
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				displayItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				Purchase();
			}else if(choice.equals(MAIN_MENU_OPTION_EXIT)){
				//program exits
				break;
			}
		}
	}
	private void Purchase(){
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (choice.equals(PURCHASE_OPTION_FEED_MONEY)) {
				// FEED MONEY

			} else if (choice.equals(PURCHASE_OPTION_SELECT_PRODUCT)) {
				// SELECT PRODUCT, update balance and return to purchase menu

			}else if(choice.equals(PURCHASE_OPTION_FINISH_TRANSACTION)){
				//FINISH TRANSACTION and return to Main menu

			}
			System.out.print("Current Money Provided: " );
		}
	}

	private void displayItems(){
		//Display the vending machine items
		for(Items item: vendingMachine.getItems()){
			System.out.print(item.getSlotLocation() + ", $" + item.getPrice());
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
