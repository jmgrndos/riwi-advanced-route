package model;

import interfaces.ProductType;

import java.util.ArrayList;

public abstract class Product implements ProductType {

    // Declare fields
    private final String name;
    private final double price;

    // Create constructor
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters for each field
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Get current product
    public static Product getProduct(ArrayList<Product> products, String productName) {

        Product currentProduct;

        // Loop through students ArrayList
        for (Product product : products) {

            // Check if student already exists
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {

                // Update currentStudent value to match found student
                currentProduct = product;
                return currentProduct;

            }

        }

        // If no student is found, return null
        return null;

    }

}
