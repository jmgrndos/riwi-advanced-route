package service;

import model.Product;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {

    void addProduct(Product product) throws Exception;

    void updatePrice(int id, double newPrice) throws Exception;

    void updateStock(int id, int newStock) throws Exception;

    void deleteProduct (int id) throws Exception;

    Optional<Product> searchByName(String name) throws Exception;

    Optional<Product> searchById(int id) throws Exception;

    List<Product> listAll() throws Exception;

}
