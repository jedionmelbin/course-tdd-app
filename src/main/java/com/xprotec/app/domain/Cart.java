package com.xprotec.app.domain;

import java.util.ArrayList;
import java.util.List;


public class Cart {
    private Float totalAmount = 0F;
    private List<Product> items = new ArrayList<>();

    public void addToCart(Product product){
        items.add(product);
       // totalAmount = totalAmount + product.getPrice();
    }
    public void removeCart(Product product){
        items.remove(product);
    }

    public void emptyCart(){
        items.clear();
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public void printCartDetails(){
        System.out.println("Here are the item your shopping");
    }
}
