package model;

import interfaces.ProductType;

public class Food extends Product implements ProductType {

    // Subclass constructor
    public Food(String productName, double productPrice) {
        super(productName, productPrice);
    }

    // Subclass methods
    @Override
    public String getDescription() {
        return "Food";
    }
}
