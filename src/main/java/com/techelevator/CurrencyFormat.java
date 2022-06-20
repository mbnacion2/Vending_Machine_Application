package com.techelevator;

import java.text.NumberFormat;

public class CurrencyFormat {
    public static String toCurrencyString(double money){
        NumberFormat formatter= NumberFormat.getCurrencyInstance();
        return formatter.format(money);
    }
}
