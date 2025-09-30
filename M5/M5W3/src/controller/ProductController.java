package controller;

import model.Product;
import service.IInventoryService;
import service.InventoryService;

import java.util.List;
import java.util.Optional;

public class ProductController {

    private final IInventoryService inventoryService;

    public ProductController() {
        this.inventoryService = new InventoryService();
    }

    public void addProduct (String name, double price, int stock) throws Exception {
        Product product = new Product(name, price, stock);
        inventoryService.addProduct(product);
    }

    public void updatePrice(int id, double newPrice) throws Exception {
        inventoryService.updatePrice(id, newPrice);
    }

    public void updateStock(int id, int nuevoStock) throws Exception {
        inventoryService.updateStock(id, nuevoStock);
    }

    public void deleteProduct(int id) throws Exception {
        inventoryService.deleteProduct(id);
    }

    public Optional<Product> searchByName(String name) throws Exception {
        return inventoryService.searchByName(name);
    }

    public Optional<Product> searchById(int id) throws Exception {
        return inventoryService.searchById(id);
    }

    public List<Product> listAll() throws Exception {
        return inventoryService.listAll();
    }
}
