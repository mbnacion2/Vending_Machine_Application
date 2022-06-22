package com.techelevator;
/**
 *
 * The Items class represent a single item in the product list and has the following properties
 * Slot Location, Product Name, Price, Type (Chip, Drink or Candy), Count (Items remaining)
 *
 */
public class Items {
    private String slotLocation= " ";
    private String productName= " ";
    private double price=0.00;
    private String type= " ";
    private int count= 5;
    private int salesCount=0;

    public int getSalesCount() {
        return salesCount;
    }

    public void decrement() {
        if(this.count>0) {
            this.count--;
            this.salesCount++;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String slotLocation) {
        this.slotLocation = slotLocation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
