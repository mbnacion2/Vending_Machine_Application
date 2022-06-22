package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
    private VendingMachine vendingMachine;
    @Before
    public void setup(){
        boolean hasException= false;
        try{
            vendingMachine = new VendingMachine();
        } catch (Exception e){
            hasException = true;
        }
        Assert.assertFalse(hasException);
    }
    @Test
    public void test_feed_money_balance(){
        //test feedMoney() method
        //check if balance is initially zero
        Assert.assertTrue(vendingMachine.getCurrentBalance()==0.0);
        vendingMachine.feedMoney(1.0);
        //check if balance is 1.0
        Assert.assertTrue(vendingMachine.getCurrentBalance()==1.0);
        vendingMachine.feedMoney(2.0);
        //check if balance is 2.0
        Assert.assertTrue(vendingMachine.getCurrentBalance()==3.0);
        vendingMachine.feedMoney(5.0);
        //check if balance is 5.0
        Assert.assertTrue(vendingMachine.getCurrentBalance()==8.0);
        vendingMachine.feedMoney(10.0);
        //check if balance is 10.0
        Assert.assertTrue(vendingMachine.getCurrentBalance()==18.0);
        vendingMachine.makeChange();
    }
    @Test
    public void test_presence_of_items() {
        //check info for item "A1"
        Items item = vendingMachine.getItem("A1");
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getSlotLocation(),"A1");
        Assert.assertEquals(item.getProductName(),"Potato Crisps");
        Assert.assertEquals(item.getPrice(),3.05,0.00);
        Assert.assertEquals(item.getType(),"Chip");
        Assert.assertEquals(item.getCount(),5);
        //check info for item "C3"
        Items item2 = vendingMachine.getItem("C3");
        Assert.assertNotNull(item2);
        Assert.assertEquals(item2.getSlotLocation(),"C3");
        Assert.assertEquals(item2.getProductName(),"Mountain Melter");
        Assert.assertEquals(item2.getPrice(),1.50,0.00);
        Assert.assertEquals(item2.getType(),"Drink");
        Assert.assertEquals(item2.getCount(),5);
    }

    @Test
    public void test_dispense_give_change(){
        //test dispenseItem() method
        DecimalFormat df = new DecimalFormat("#.##");
        vendingMachine.feedMoney(5.0);
        Items item = vendingMachine.getItem("A1");
        Assert.assertTrue(item.getCount()==5); //check count initial value is 5
        vendingMachine.dispenseItem(item);
        Assert.assertTrue(item.getCount()==4); //check count updated
        Assert.assertEquals(vendingMachine.getCurrentBalance(), (Double.valueOf(df.format(5.0-item.getPrice()))),0.00);


        //test makeChange() method
        Map<String, Integer> change = vendingMachine.makeChange();
        for (Map.Entry<String, Integer> entry: change.entrySet()){
            int expectedCount = 0;
            String coinName = entry.getKey();
            if (coinName.equals("Quarters: ")) {
                expectedCount = 7;
            } else if (coinName.equals("Dimes: ")){
                expectedCount = 2;
            } else if (coinName.equals("Nickels: ")){
                expectedCount = 0;
            }
            Assert.assertTrue(entry.getValue()==expectedCount);
        }
    }
}
