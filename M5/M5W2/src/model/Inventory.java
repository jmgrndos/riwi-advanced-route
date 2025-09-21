package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {

    // Declare fields
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final HashMap<String, Integer> stock = new HashMap<>();
    private static final double[] prices = new double[5];

    private static int articlesBought;
    private static double totalSpent;

    // Getters for each field
    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static HashMap<String, Integer> getStock() {
        return stock;
    }

    public static double[] getPrices() {
        return prices;
    }

    public static int getArticlesBought() {
        return articlesBought;
    }

    public static double getTotalSpent() {
        return totalSpent;
    }

    // Setters
    public static void setArticlesBought(int articlesBought) {
        Inventory.articlesBought = articlesBought;
    }

    public static void setTotalSpent(double totalSpent) {
        Inventory.totalSpent = totalSpent;
    }

    // Methods
    public static boolean isFull() {
        return products.size() >= prices.length;
    }

    public static void newProduct (Product product, int amount){

        products.add(product);
        stock.put(product.getName(), amount);
        prices[products.size() - 1] = product.getPrice();

    }


}
