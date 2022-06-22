package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

public class ItemsTest {
    @Test
    public void test_items() {
        Items item = new Items();
        //Test setters and getters
        item.setSlotLocation("A1");
        item.setProductName("Potato Chips");
        item.setCount(3);
        item.setPrice(3.05);
        item.setType("Chip");
        Assert.assertEquals(item.getSlotLocation(),"A1");
        Assert.assertEquals(item.getProductName(),"Potato Chips");
        Assert.assertEquals(item.getCount(),3);
        Assert.assertEquals(item.getPrice(), 3.05, 0.0);
        Assert.assertEquals(item.getType(),"Chip");
    }

}
