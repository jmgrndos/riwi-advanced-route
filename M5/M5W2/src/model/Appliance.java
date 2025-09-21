package model;

import interfaces.ProductType;

public class Appliance extends Product implements ProductType {

    // Subclass constructor
    public Appliance(String productName, double productPrice) {
        super(productName, productPrice);
    }

    // Subclass methods
    @Override
    public String getDescription() {
        return "Appliance";
    }

}
