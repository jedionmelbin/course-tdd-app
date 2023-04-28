package com.xprotec.app;

import com.xprotec.app.domain.Checkout;
import com.xprotec.app.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ShoppingTest {
    private Product firstProduct;
    private Product secondProduct;
    private Checkout checkout;

    @BeforeEach
    @DisplayName("Given 2 products in the shopping cart")
    public void setup() {
        firstProduct = new Product(1, "USB Drive", "128 GB USB Drive", 19, 0);
        secondProduct = new Product(2, "Hard Drive", "External GB USB Drive", 19,0);

        checkout = new Checkout();
        checkout.addToCart(firstProduct);
        checkout.addToCart(secondProduct);
    }

    @Test
    @DisplayName("Test if product added to shopping cart succesfully")
    public void testItemsAddToShoppingCart() {
        Product newProduct = new Product(3, "New external Drive", "128 GB USB Drive", 19, 0);
        checkout.addToCart(newProduct);

        Assertions.assertEquals(3, checkout.getItems().size());
        Assertions.assertEquals(59.699997F, (float) checkout.getTotalAmount());
    }

    @Test
    @DisplayName("Test if product removed from shopping cart correctly")
    public void testDueCalculation() {
        checkout.pay(90.8F);
    }

    @Test
    @DisplayName("Test product removed from shopping")
    public void removeCart(){

    }
}
