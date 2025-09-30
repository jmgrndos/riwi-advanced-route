package service;

import model.Product;
import repository.IRepository;
import repository.Repository;

import java.util.List;
import java.util.Optional;

public class InventoryService implements IInventoryService {
    private final IRepository<Product> repository;

    public InventoryService() {
        this.repository = new Repository();
    }

    @Override
    public void addProduct(Product product) throws Exception {

        repository.create(product); // Call method to add product to the database

    }

    @Override
    public void updatePrice(int id, double newPrice) throws Exception {

        // Get product
        Optional<Product> productOpt = repository.searchById(id);

        // If product exists
        if (productOpt.isPresent()) {

            // Assign product and update values
            Product product = productOpt.get();
            product.setPrice((newPrice));
            repository.update(product);

        } else {

            throw new Exception("Product with id: " + id + " not found.");

        }
    }

    @Override
    public void updateStock(int id, int newStock) throws Exception {

        // Get product
        Optional<Product> productOpt = repository.searchById(id);

        // If product exists
        if (productOpt.isPresent()) {

            // Assign product and update values
            Product product = productOpt.get();
            product.setStock(newStock);
            repository.update(product);

        } else {

            throw new Exception("Product with id: " + id + " not found.");

        }

    }

    @Override
    public void deleteProduct(int id) throws Exception {

        repository.delete(id); // Call method to remove product from database

    }

    @Override
    public Optional<Product> searchByName(String name) throws Exception {

        // Get all products
        List<Product> products = repository.listAll();

        // Filter list to find matching name product
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst(); // Return the first match
    }

    @Override
    public Optional<Product> searchById(int id) throws Exception {
        return repository.searchById(id); // Call search by id method
    }

    @Override
    public List<Product> listAll() throws Exception {
        return repository.listAll(); // Return all products
    }
}
