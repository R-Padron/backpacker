package com.backpacker.resources.utility;

public class Item {

    private String itemName;
    private Integer quantity;

    public Item(String i, int q){
        this.itemName = i;
        this.quantity = q;
    }

    public String getItemName(){
        return this.itemName;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public void setItem(String i){
        this.itemName = i;
    }
    public void setQuantity(int q){
        this.quantity = q;
    }
}
