package com.techelevator;

import java.text.NumberFormat;

/**
 *
 * The CurrencyFormat class is a utility class for formatting numbers
 * to the local currency format
 */
public class CurrencyFormat {
    //Convert any double representing money to include "$"
    public static String toCurrencyString(double money){
        NumberFormat formatter= NumberFormat.getCurrencyInstance();
        return formatter.format(money);
    }
}
