package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 *
 * The menu class handles the display of menu options
 * and retrieving user selection
 *
 */
public class Menu {

	private PrintWriter out;
	private Scanner in;
	/**
	 * The menu class has a single constructor that requires the following parameters
	 * @param input an InputStream for getting menu selection
	 * @param output an OutputStream for displaying menu options
	 */
	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	/**
	 * The getChoiceFromOptions accepts an array of menu options which it displays in the menu OutputStream
	 * and returns the selected option from the menu InputStream
	 * @param options an Array of objects containing the options for the menu
	 * @return Object that represents the option selected from the available menu options
	 */
	public Object getChoiceFromOptions(Object[] options){
		return getChoiceFromOptions(options, null, null);
	}

	public Object getChoiceFromOptions(Object[] options, String title){
		return getChoiceFromOptions(options, title, null);
	}

	public Object getChoiceFromOptions(Object[] options, String title, String message) {
		Object choice = null;

		while (choice == null) {
			if(title!=null) {
				out.println(System.lineSeparator() + title); //Title
			}
			displayMenuOptions(options, message);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options, String message) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			if(!options[i].equals("")) {
				int optionNum = i + 1;
				out.println(optionNum + ") " + options[i]);
			}
		}
		if(message!=null){
			out.println(System.lineSeparator() + message);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
}
