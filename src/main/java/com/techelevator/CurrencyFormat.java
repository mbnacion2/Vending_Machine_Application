package com.techelevator;

import java.text.NumberFormat;

public class CurrencyFormat {
    //Convert any double representing money to include "$"
    public static String toCurrencyString(double money){
        NumberFormat formatter= NumberFormat.getCurrencyInstance();
        return formatter.format(money);
    }
}
