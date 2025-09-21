package utils;

import model.Product;

import java.util.ArrayList;

public class DuplicateValidator {

    // Check duplicates for products
    public static boolean isProductDuplicate(ArrayList<Product> products, String newInstance) {

        // Loop through students ArrayList
        for (Product product : products) {

            // Check for name matches
            if (product.getName().toLowerCase().contains(newInstance.toLowerCase())) {
                return true;
            }
        }

        return false;

    }

}