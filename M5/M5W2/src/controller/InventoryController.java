package controller;

import model.Appliance;
import model.Food;
import model.Inventory;
import model.Product;
import utils.DuplicateValidator;
import utils.InputValidator;
import view.InventoryView;

public class InventoryController {

    // Add new product
    public static void addProduct() {

        // Define window title
        String title = "Add Product";

        // Check if there's available spaces for products
        if (Inventory.isFull()) {

            // Print error message
            InventoryView.errorMessage("Inventory is full.", title);

            // Exit method if product limit is reached
            return;
        }

        // Declare variables
        String productName, input;
        double productPrice = 0;
        int productAmount = 0, productType;
        Product newProduct;

        // Product name input loop
        do {

            // Ask for product name
            productName = InventoryView.askForInput("Introduce the name of the product: ", title);

            // Handle cancel button
            if (productName == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(productName)){
                InventoryView.errorMessage("Please introduce a name.", title);
            }

        } while (!InputValidator.isValidString(productName));

        // Check for duplicates
        if (DuplicateValidator.isProductDuplicate(Inventory.getProducts(), productName)) {

            // If duplicates are found, stop the method
            InventoryView.errorMessage("Product already exists", title);

            return;
        }

        // Product price input loop
        do {

            // Ask for product name
            input = InventoryView.askForInput("Introduce the price of the product: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                InventoryView.errorMessage("Please introduce a price.", title);
                continue;
            }

            // Check if input is a valid Double
            if (!InputValidator.isValidDouble(input)) {
                InventoryView.errorMessage("Please introduce a valid price.", title);
                continue;
            }

            // Update product price value
            productPrice = Double.parseDouble(input);

        } while (!InputValidator.isValidDouble(input));

        // Product stock input loop
        do {

            // Ask for product name
            input = InventoryView.askForInput("Introduce the amount of stock for the product: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                InventoryView.errorMessage("Please introduce a stock amount.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                InventoryView.errorMessage("Please introduce a valid amount.", title);
                continue;
            }

            // Update product amount value
            productAmount = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Ask for product type
        productType = InventoryView.typeInput();

        // Handle cancel button
        if (productType == 2) return;

        // Create an instance depending on product type
        newProduct = (productType == 0) ? new Food(productName, productPrice) : new Appliance(productName, productPrice);

        // Create an instance of the new product
        Inventory.newProduct(newProduct, productAmount);

        // Print success message
        InventoryView.infoMessage("Product added successfully", title);


    }

    // List all added products
    public static void listProducts() {

        // Set window title
        String title = "List Products";

        // Check if there's products in the inventory
        if (Inventory.getProducts().isEmpty()) {

            // Print message
            InventoryView.infoMessage("No products added.", title);

            // Exit the method
            return;
        }

        // Declare variables
        String productName, productType;
        double productPrice;
        int productAmount;

        // Declare a String to print product details
        StringBuilder message = new StringBuilder();

        // Get products data
        for (Product product : Inventory.getProducts()) {

            // Retrieve product details
            productName = product.getName();
            productPrice = Inventory.getPrices()[Inventory.getProducts().indexOf(product)];
            productAmount = Inventory.getStock().get(productName);
            productType = product.getDescription();

            // Format product details
            message.append(String.format("Product: %s - Price: $%.2f - Stock: %d units - Type: %s\n", productName, productPrice, productAmount, productType));

        }

        // Print full list
        InventoryView.infoMessage(message.toString(), title);

    }

    // Buy products
    public static void buyProduct() {

        // Set window title
        String title = "Buy Product";

        // Declare variables
        String productName, message, input;
        int productBought = 0, productAmount, articlesBought = 0;
        double totalSpent = 0;
        Product currentProduct;

        // Product name input loop
        do {

            // Ask for product name
            productName = InventoryView.askForInput("Introduce the name of the product: ", title);

            // Handle cancel button
            if (productName == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(productName)){
                InventoryView.errorMessage("Please introduce a name.", title);
            }

        } while (!InputValidator.isValidString(productName));

        // Get product
        currentProduct = Product.getProduct(Inventory.getProducts(), productName);

        // Handle product not found
        if (currentProduct == null) {

            // Print fail message
            InventoryView.errorMessage("Product not found.", title);
            return;

        }

        // Products to buy input loop
        do {

            // Ask for product name
            input = InventoryView.askForInput("Introduce the amount of products you want to buy: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                InventoryView.errorMessage("Please introduce an amount.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                InventoryView.errorMessage("Please introduce a valid amount.", title);
                continue;
            }

            // Update product amount value
            productBought = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Update values
        productName = currentProduct.getName();
        productAmount = Inventory.getStock().get(currentProduct.getName());

        // Check if there's stock available
        if (productBought <= productAmount) {

            // Update purchase statistics
            Inventory.setArticlesBought(articlesBought + productBought);
            Inventory.setTotalSpent(totalSpent + productBought * Inventory.getPrices()[Inventory.getProducts().indexOf(currentProduct)]);

            // Update stock
            Inventory.getStock().put(productName, (productAmount - productBought));

            // Print success message
            message = String.format("%d units of %s bought successfully.", productBought, productName);
            InventoryView.infoMessage(message, title);

        } else {

            // Print error message
            InventoryView.errorMessage("There's not enough stock.", title);

        }

    }

    // Show highest - lowest priced products
    public static void showStats() {

        // Set window title
        String title = "Show statistics";

        // Declare variables
        double maxPrice = Double.MIN_VALUE, minPrice = Double.MAX_VALUE, productPrice;
        Product maxPriceProduct = null, minPriceProduct = null;

        // Check if there are products
        if (Inventory.getProducts().isEmpty()) {

            // Print message
            InventoryView.infoMessage("No products added.", title);

            // Exit method if no products have been added
            return;

        }

        // Find the product with the highest and lowest price
        for (Product product : Inventory.getProducts()) {

            // Get product values
            productPrice = Inventory.getPrices()[Inventory.getProducts().indexOf(product)];

            // Update max price
            if (productPrice > maxPrice) {
                maxPrice = productPrice;
                maxPriceProduct = product;
            }

            // Update min price
            if (productPrice < minPrice) {
                minPrice = productPrice;
                minPriceProduct = product;
            }
        }

        // Print both values
        assert maxPriceProduct != null;
        InventoryView.showProductDetails(maxPriceProduct.getName(), maxPriceProduct.getPrice(), Inventory.getStock().get(maxPriceProduct.getName()), title + " - Highest priced product");

        assert minPriceProduct != null;
        InventoryView.showProductDetails(minPriceProduct.getName(), minPriceProduct.getPrice(), Inventory.getStock().get(minPriceProduct.getName()), title + " - Lowest priced product");

    }

    // Print specified product details
    public static void searchProduct() {

        // Set window title
        String title = "Search Product";

        // Declare variables
        String productName;
        int productAmount;
        double productPrice;
        Product currentProduct;

        // Product name input loop
        do {

            // Ask for product name
            productName = InventoryView.askForInput("Introduce the name of the product: ", title);

            // Handle cancel button
            if (productName == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(productName)){
                InventoryView.errorMessage("Please introduce a name.", title);
            }

        } while (!InputValidator.isValidString(productName));

        // Get current product
        currentProduct = Product.getProduct(Inventory.getProducts(), productName);

        // If product is not found
        if (currentProduct == null) {

            // Print fail message
            InventoryView.errorMessage("Product not found.", title);
            return;

        }

        // Update values
        productName = currentProduct.getName();
        productPrice = Inventory.getPrices()[Inventory.getProducts().indexOf(currentProduct)];
        productAmount = Inventory.getStock().get(productName);

        // Print product details
        InventoryView.showProductDetails(productName, productPrice, productAmount, title);

    }

}
