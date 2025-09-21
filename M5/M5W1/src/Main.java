    import javax.swing.JOptionPane;
    import java.util.ArrayList;
    import java.util.HashMap;

    public class Main {

        // Declare Arrays & HashMap|
        static HashMap<String, Integer> productStock = new HashMap<>();
        static ArrayList<String> productNames = new ArrayList<>();
        static double[] productPrices = new double[5];

        static int articlesBought = 0;
        static double totalSpent = 0;

        public static void main(String[] args) {

            // Main menu
            printMenu();

        }

        public static void printMenu() {

            // Initialize menu options
            int option;
            Object[] options = {"Add a product", "List products", "Buy product",  "Expand prices", "Show stats", "Search product", "Leave"};

            // Main loop
            while (true) {

                // Declare input option message
                option = JOptionPane.showOptionDialog(
                        null,
                        "Select an option: ",
                        "Main menu",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                // Close button
                if (option == -1 || option == 6){
                    if (confirmLeave()) {
                        return;
                    }
                }

                // Check user's selected option
                switch (option) {
                    case 0 -> addProduct();
                    case 1 -> listInventory();
                    case 2 -> buyProduct();
                    case 3 -> expandPrices();
                    case 4 -> showStats();
                    case 5 -> searchProduct();
                }
            }

        }

        public static void addProduct() {

            // Set window title
            String title = "Add Product";

            // Check if there's available spaces for products
            if (productNames.size() >= productPrices.length) {

                // Print error message
                JOptionPane.showMessageDialog(null, "Can't add more products, please expand the prices array.", title, JOptionPane.ERROR_MESSAGE);

                // Exit method if product limit is reached
                return;
            }

            // Declare variables
            String productName, productPriceInput, productQuantityInput;
            double productPrice;
            int productQuantity;

            // Validate product name input
            while (true) {

                // Ask the user to prompt the product name
                productName = JOptionPane.showInputDialog(null, "Introduce the name of the product: ", title, JOptionPane.QUESTION_MESSAGE);

                // Cancel option
                if (productName == null){
                    return;
                }

                // Ensure name is not empty
                if (productName.isEmpty()) {

                    // Print error message
                    JOptionPane.showMessageDialog(null, "Please introduce a valid name.", title, JOptionPane.ERROR_MESSAGE);

                } else {

                    // Break loop if the name is valid
                    break;
                }
            }

            // Check for existing product names
            for (String product : productNames) {

                // Check if the product exists
                if (product.equalsIgnoreCase(productName.trim())) {

                    // Print message
                    JOptionPane.showMessageDialog(null, "Product already exists.", title, JOptionPane.ERROR_MESSAGE);

                    // If the product exists, exit the method
                    return;
                }
            }

            // Ask the user to prompt the product price
            while (true) {

                // Validate and parse input
                try {

                    // Input product price
                     productPriceInput = JOptionPane.showInputDialog(null, "Introduce the price of the product: ", title, JOptionPane.QUESTION_MESSAGE);

                    // Cancel button
                    if (productPriceInput == null){
                        return;
                    }

                    // Convert input to double
                    productPrice = Double.parseDouble(productPriceInput);

                    // If the input is valid, exit the loop
                    if (productPrice <= 0){

                        // Print error message
                        JOptionPane.showMessageDialog(null, "Please enter a valid price.", title, JOptionPane.ERROR_MESSAGE);

                    } else {
                        break;
                    }

                } catch (NumberFormatException e) {

                    // Print error message
                    JOptionPane.showMessageDialog(null, "Please enter a valid price.", title, JOptionPane.ERROR_MESSAGE);

                }
            }

            // Ask the user to prompt the product quantity
            while (true) {

                // Validate and parse input
                try {

                    // Input product quantity
                    productQuantityInput = JOptionPane.showInputDialog(null, "Introduce the quantity of the product: ", title, JOptionPane.QUESTION_MESSAGE);

                    // Cancel button
                    if (productQuantityInput == null){
                        return;
                    }

                    // Convert input to integer
                    productQuantity = Integer.parseInt(productQuantityInput);

                    // If the input is valid, exit the loop
                    if (productQuantity <= 0){

                        // Print error message
                        JOptionPane.showMessageDialog(null, "Please enter a valid amount.", title, JOptionPane.ERROR_MESSAGE);

                    } else {
                        break;
                    }

                } catch (NumberFormatException e) {

                    // Print error message
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.", title, JOptionPane.ERROR_MESSAGE);

                }
            }

            // Add values to the Arrays & Lists
            productNames.add(productName);
            productPrices[productNames.size() - 1] = productPrice;
            productStock.put(productName, productQuantity);

            // Print success message
            JOptionPane.showMessageDialog(null, "Product added successfully.", title, JOptionPane.INFORMATION_MESSAGE);

        }

        public static void listInventory() {

            // Set window title
            String title = "List Products";

            // Check if there's products in the inventory
            if (productNames.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No products added.", title, JOptionPane.INFORMATION_MESSAGE);

                // Exit the method
                return;
            }

            // Declare variables
            String productName, message;
            double productPrice;
            int productQuantity;
            ArrayList<String> messages = new ArrayList<>();

            // Get products data
            for (int i = 0; i < productNames.size(); i++) {

                // Retrieve product details
                productName = productNames.get(i);
                productPrice = productPrices[i];
                productQuantity = productStock.get(productName);

                // Format product details
                message = String.format("Product: %s - Price: $%.2f. - Stock: %d units\n", productName, productPrice, productQuantity);

                // Push message to the list
                messages.add(message);
            }

            // Print full list
            JOptionPane.showMessageDialog(null, String.join("\n", messages), title, JOptionPane.INFORMATION_MESSAGE);

        }

        public static void buyProduct() {

            // Set window title
            String title = "Buy Product";

            // Declare name variable
            String productName, message, productBoughtInput;
            int productBought, productQuantity;

            // Ask the user to prompt the product name
            while (true) {

                // Input product name
                productName = JOptionPane.showInputDialog(null, "Introduce the name of the product you want to buy: ", title, JOptionPane.QUESTION_MESSAGE);

                // Cancel option
                if (productName == null){
                    return;
                }

                // Check if input is valid
                if (productName.isEmpty()) {

                    // Print error message
                    JOptionPane.showMessageDialog(null, "Please introduce a valid name.", title, JOptionPane.ERROR_MESSAGE);

                } else {

                    // Exit loop if the name is valid
                    break;
                }
            }

            // Iterate through the array
            for (String product : productNames) {

                // Check if the product exists
                if (product.equalsIgnoreCase(productName)) {

                    // Ask the user to prompt the quantity of the product
                    while (true) {

                        // Validate and parse input
                        try {
                            // Input amount of bought products
                            productBoughtInput = JOptionPane.showInputDialog(null, "Introduce the amount of the product you want to buy: ", title, JOptionPane.QUESTION_MESSAGE);


                            // Cancel button
                            if (productBoughtInput == null){
                                return;
                            }

                            // Convert input to integer
                            productBought = Integer.parseInt(productBoughtInput);

                            // If the input is valid and greater than zero, exit the loop
                            if (productBought > 0){
                                break;
                            }

                        } catch (NumberFormatException e) {

                            // Print error message
                            JOptionPane.showMessageDialog(null, "Please enter a valid amount.", title, JOptionPane.ERROR_MESSAGE);

                        }
                    }

                    productQuantity = productStock.get(productName);

                    // Check if there's stock available
                    if (productBought <= productQuantity) {

                        // Update purchase statistics
                        articlesBought += productBought;
                        totalSpent += productBought * productPrices[productNames.indexOf(productName)];

                        // Update stock
                        productStock.put(productName, (productQuantity - productBought));

                        // Print success message
                        message = String.format("%d units of %s bought successfully.", productBought, productName);
                        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);

                    } else {

                        // Print error message
                        JOptionPane.showMessageDialog(null, "There's not enough stock.", title, JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
            }

            // Print fail message
            JOptionPane.showMessageDialog(null, "Product not found.", title, JOptionPane.INFORMATION_MESSAGE);

        }

        public static void expandPrices() {

            // Set window title
            String title = "Expand prices";

            // Declare new Array
            double[] newPrices = new double[productPrices.length * 2];

            // Add the values of the old Array to the new one
            System.arraycopy(productPrices, 0, newPrices, 0, productPrices.length);

            // Update productPrices to the new Array
            productPrices = newPrices;

            // Print results
            JOptionPane.showMessageDialog(null, "Expanded prices list succesfully.", title, JOptionPane.INFORMATION_MESSAGE);

            System.out.println(productPrices.length);

        }

        public static void showStats() {

            // Set window title
            String title = "Show statistics";

            // Declare variables
            String productName, maxMessage = "", minMessage = "";
            int productQuantity;
            double maxPrice = Double.MIN_VALUE, minPrice = Double.MAX_VALUE, productPrice;

            // Check if there are products
            if (productNames.isEmpty()){
                JOptionPane.showMessageDialog(null, "No products added.", title, JOptionPane.INFORMATION_MESSAGE);

                // Exit method if no products have been added
                return;
            }

            // Find the product with the highest and lowest price
            for (int i = 0; i < productNames.size(); i++) {

                // Get product values
                productName = productNames.get(i);
                productPrice = productPrices[i];
                productQuantity = productStock.get(productName);

                // Update max price
                if (productPrice > maxPrice) {
                    maxPrice = productPrice;
                    maxMessage = String.format("The product with the highest price is: \nProduct name: %s\nProduct price: %.2f\nProduct stock: %d", productName, productPrice, productQuantity);
                }

                // Update min price
                if (productPrice < minPrice) {
                    minPrice = productPrice;
                    minMessage = String.format("The product with the lowest price is: \nProduct name: %s\nProduct price: %.2f\nProduct stock: %d", productName, productPrice, productQuantity);
                }

            }

            // Print both values
            JOptionPane.showMessageDialog(null, maxMessage, title, JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, minMessage, title, JOptionPane.INFORMATION_MESSAGE);

        }

        public static void searchProduct() {

            // Set window title
            String title = "Search Product";

            // Declare variables
            String productName, message;
            int productQuantity, itemIndex;
            double productPrice;

            // Validate product name input
            while (true) {

                // Ask the user to prompt the product name
                productName = JOptionPane.showInputDialog(null, "Introduce the name of the product you want to search: ", title, JOptionPane.QUESTION_MESSAGE);

                // Cancel option
                if (productName == null){
                    return;
                }

                // Ensure name is not empty
                if (productName.isEmpty()) {

                    // Print error message
                    JOptionPane.showMessageDialog(null, "Please introduce a valid name.", title, JOptionPane.ERROR_MESSAGE);

                } else {

                    // Exit loop if the name is valid
                    break;
                }
            }

            // Check for existing product names
            for (String product : productNames) {

                // Verify the product exists
                if (product.toLowerCase().contains(productName.toLowerCase())) {

                    // Retrieve found product index
                    itemIndex = productNames.indexOf(product);

                    // Retrieve product details
                    productName = productNames.get(itemIndex);
                    productPrice = productPrices[itemIndex];
                    productQuantity = productStock.get(productName);

                    // Format product details
                    message = String.format("Product: %s - Price: $%.2f. - Stock: %d units\n", productName, productPrice, productQuantity);

                    // Print success message
                    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
                    return;

                }

            }

            // Print fail message
            JOptionPane.showMessageDialog(null, "Product not found.", title, JOptionPane.INFORMATION_MESSAGE);

        }

        public static boolean confirmLeave() {

            // Set window title
            String title = "Confirm Leave", message;

            // Print confirm message
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?", title, JOptionPane.YES_NO_OPTION);

            // Define total bought message
            message = String.format("Articles bought: %d.\nTotal spent: %.2f$. ",articlesBought,totalSpent);

            // Print total bought articles if there are
            if (!productNames.isEmpty()){
                JOptionPane.showMessageDialog(null, message, "Total", JOptionPane.INFORMATION_MESSAGE);
            }

            return confirm == JOptionPane.YES_OPTION;

        }

    }