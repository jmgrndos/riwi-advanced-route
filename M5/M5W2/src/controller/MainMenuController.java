package controller;

import model.Inventory;
import view.MainMenuView;

public class MainMenuController {

    public static void start() {

        // Main loop
        while (true) {
            int option = MainMenuView.showMainMenu();

            // Hande cancel button
            if (option == -1 || option == 5) {

                // Ask for confirmation
                if (MainMenuView.confirmLeave()){

                    // Print summary of bought items
                    MainMenuView.showSummary(Inventory.getArticlesBought(), Inventory.getTotalSpent());
                    break;

                }

                else continue;

            }

            // Execute selected method
            switch (option) {
                case 0 -> InventoryController.addProduct();
                case 1 -> InventoryController.listProducts();
                case 2 -> InventoryController.buyProduct();
                case 3 -> InventoryController.showStats();
                case 4 -> InventoryController.searchProduct();

            }
        }
    }
}
