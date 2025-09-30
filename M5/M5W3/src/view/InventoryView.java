package view;

import controller.ProductController;
import model.Product;
import util.InputValidator;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class InventoryView {

    private final ProductController controller;

    public InventoryView() {
        this.controller = new ProductController();
    }

    public void showMenu() {

        // Define menu options
        String[] options = {"Add product", "Update price", "Update stock", "Delete product", "Search by name", "List all products", "Leave"};

        // Menu loop
        while (true) {

            // Print Menu
            int option = JOptionPane.showOptionDialog(null, "Select an option: ", "Main Menu", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            // Handle close menu
            if (option == -1) break;

            // Execute menu options
            try {

                switch (option) {
                    case 0 -> addProduct();
                    case 1 -> updatePrice();
                    case 2 -> updateStock();
                    case 3 -> deleteProduct();
                    case 4 -> SearchByName();
                    case 5 -> listAllProducts();
                    case 6 -> {
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        return;

                    }

                    default -> JOptionPane.showMessageDialog(null, "Invalid option");

                }
            } catch (Exception e) {

                // Print error message
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

            }
        }
    }

    private void addProduct() throws Exception {

        String title = "Add Product"; // Set window title

        // Declare variables
        String productName, input;
        double productPrice = 0;
        int productStock = 0;

        // Product name input loop
        do {

            // Ask for product name
            productName = MenuMessage.askForInput("Introduce the name of the product: ", title);

            // Handle cancel button
            if (productName == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(productName)) {
                MenuMessage.errorMessage("Please introduce a name.", title);
            }

        } while (!InputValidator.isValidString(productName));

        // Product price input loop
        do {

            // Ask for product name
            input = MenuMessage.askForInput("Introduce the price of the product: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce a price.", title);
                continue;
            }

            // Check if input is a valid Double
            if (!InputValidator.isValidDouble(input)) {
                MenuMessage.errorMessage("Please introduce a valid price.", title);
                continue;
            }

            // Update product price value
            productPrice = Double.parseDouble(input);

        } while (!InputValidator.isValidDouble(input));

        // Product stock input loop
        do {

            // Ask for product name
            input = MenuMessage.askForInput("Introduce the amount of stock for the product: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce a stock amount.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                MenuMessage.errorMessage("Please introduce a valid amount.", title);
                continue;
            }

            // Update product amount value
            productStock = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Add product
        controller.addProduct(productName, productPrice, productStock);

        // Print success message
        JOptionPane.showMessageDialog(null, "Product added successfully.");

    }

    private void updatePrice() throws Exception {

        String title = "Update Price"; // Set window title

        // Declare variables
        String input;
        double productPrice = 0;
        int productId = 0;

        // Product id input loop
        do {

            // Ask for product id
            input = MenuMessage.askForInput("Introduce the ID of the product you want to update price:", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce an id.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                MenuMessage.errorMessage("Please introduce a valid id.", title);
                continue;
            }

            // Update product id
            productId = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Check if product exists
        if (controller.searchById(productId).isEmpty()) {
            MenuMessage.errorMessage("Product with ID " + productId + " not found.", title);
            return;
        }

        // New price input loop
        do {

            // Ask for product name
            input = MenuMessage.askForInput("Introduce the new price of the product: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce a price.", title);
                continue;
            }

            // Check if input is a valid Double
            if (!InputValidator.isValidDouble(input)) {
                MenuMessage.errorMessage("Please introduce a valid price.", title);
                continue;
            }

            // Update product price value
            productPrice = Double.parseDouble(input);

        } while (!InputValidator.isValidDouble(input));
        
        // Update values
        controller.updatePrice(productId, productPrice);

        // Print success message
        MenuMessage.infoMessage("Price updated successfully!", title);

    }

    private void updateStock() throws Exception {

        String title = "Update Stock"; // Set window title

        // Declare variables
        String input;
        int productId = 0, productStock = 0;

        // Product id input loop
        do {

            // Ask for product id
            input = MenuMessage.askForInput("Introduce the ID of the product you want to update price:", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce an id.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                MenuMessage.errorMessage("Please introduce a valid id.", title);
                continue;
            }

            // Update product id
            productId = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Check if product exists
        if (controller.searchById(productId).isEmpty()) {
            MenuMessage.errorMessage("Product with ID " + productId + " not found.", title);
            return;
        }

        // Product stock input loop
        do {

            // Ask for product name
            input = MenuMessage.askForInput("Introduce the amount of stock for the product: ", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce a stock amount.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                MenuMessage.errorMessage("Please introduce a valid amount.", title);
                continue;
            }

            // Update product stock value
            productStock = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Update values
        controller.updateStock(productId, productStock);

        // Print success message
        MenuMessage.infoMessage("Stock updated successfully!", title);

    }

    private void deleteProduct() throws Exception {

        String title = "Delete Product"; // Set window title

        // Declare variables
        String input;
        int productId = 0;
        
        // Product id input loop
        do {

            // Ask for product id
            input = MenuMessage.askForInput("Introduce the ID of the product you want to update price:", title);

            // Handle cancel button
            if (input == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(input)) {
                MenuMessage.errorMessage("Please introduce an id.", title);
                continue;
            }

            // Check if input is a valid Integer
            if (!InputValidator.isValidInteger(input)) {
                MenuMessage.errorMessage("Please introduce a valid id.", title);
                continue;
            }

            // Update product id
            productId = Integer.parseInt(input);

        } while (!InputValidator.isValidInteger(input));

        // Check if product exists
        if (controller.searchById(productId).isEmpty()) {
            MenuMessage.errorMessage("Product with ID " + productId + " not found.", title);
            return;
        }

        // Delete product
        controller.deleteProduct(productId);

        // Print success message
        MenuMessage.infoMessage("Product deleted successfully!", title);

    }

    private void SearchByName() throws Exception {

        String title = "Search by name"; // Set window title

        // Declare variables
        String productName;

        // Product name input loop
        do {

            // Ask for product name
            productName = MenuMessage.askForInput("Introduce the name of the product: ", title);

            // Handle cancel button
            if (productName == null) return;

            // Check if input is empty
            if (!InputValidator.isValidString(productName)) {
                MenuMessage.errorMessage("Please introduce a name.", title);
            }

        } while (!InputValidator.isValidString(productName));

        // Get product
        Optional<Product> productOpt = controller.searchByName(productName);

        // Check if product exists
        if (productOpt.isPresent()) {

            MenuMessage.infoMessage("Product: " + productOpt.get(), title);

        } else {

            MenuMessage.errorMessage("Product not found" ,title);

        }
    }

    private void listAllProducts() throws Exception {

        String title = "List all Products"; // Set window title

        // Get all products
        List<Product> products = controller.listAll();

        // If there's no products
        if (products.isEmpty()) {

            MenuMessage.errorMessage("There are no added products", title);
            return;

        }

        // Create string builder to print products list
        StringBuilder message = new StringBuilder("List of products:\n");

        for (Product product : products) {
            message.append("ID: ")
                    .append(product.getId())
                    .append("\nProduct: ")
                    .append(product.getName())
                    .append("\nPrice: ")
                    .append(product.getPrice())
                    .append("\nStock: ")
                    .append(product.getStock())
                    .append("\n");
        }

        // Print list
        MenuMessage.infoMessage(message.toString(), title);

    }

}
