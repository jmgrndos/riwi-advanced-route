package repository;

import model.Product;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository implements IRepository <Product> {

    // Declare SQL queries
    private static final String SQL_INSERT = "INSERT INTO product (name, price, stock) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM product";
    private static final String SQL_UPDATE = "UPDATE product SET name = ?, price = ?, stock = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM product WHERE id = ?";

    @Override
    public void create(Product product) throws Exception {

        // Connection with the database
        try (Connection conn = ConnectionFactory.getConnection();

             // Statement
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the SQL query
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());

            // Execute the SQL query
            int affectedRows = stmt.executeUpdate();

            // If create fails
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                // Set the generated id on the product
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));

                } else {
                    throw new SQLException("Error creating product.");

                }
            }
        }
    }

    @Override
    public Optional<Product> searchById(int id) throws Exception {

        // Connection with the database
        try (Connection conn = ConnectionFactory.getConnection();

             // Statement
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            // Set the value for the SQL query
            stmt.setInt(1, id);

            // Execute the SQL query and get the ResultSet
            try (ResultSet rs = stmt.executeQuery()) {

                // If object is found
                if (rs.next()) {

                    // Convert the result row into a Product object
                    Product product = mapRowToProduct(rs);

                    return Optional.of(product); // Return product
                }
            }
        }
        return Optional.empty(); // No product found
    }

    @Override
    public List<Product> listAll() throws Exception {

        // Declare product List
        List<Product> product = new ArrayList<>();

        // Connection with the database
        try (Connection conn = ConnectionFactory.getConnection();

             // Statement
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);

             // Execute SQL query and get the Result Set
             ResultSet rs = stmt.executeQuery()) {

            // Iterate through all result rows
            while (rs.next()) {
                product.add(mapRowToProduct(rs)); // Convert each row to a Product and add to the list

            }
        }
        return product;  // Return the full list of products
    }

    @Override
    public void update(Product product) throws Exception {

        // Connection with the database
        try (Connection conn = ConnectionFactory.getConnection();

             // Statement
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            // Set the values for the SQL query
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getId());

            // Execute SQL query
            int affectedRows = ps.executeUpdate();

            // If no rows are updated
            if (affectedRows == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {

        // Connection with the database
        try (Connection conn = ConnectionFactory.getConnection();

             // Statement
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {

            // Set values for the SQL statement and execute it
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            // If no rows are affected
            if (affectedRows == 0) {
                throw new SQLException("Deleting product failed, no rows affected.");
            }
        }
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {

        // Extract column values from the current row
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int stock = rs.getInt("stock");

        // Create and return a Product object with the extracted values
        return new Product(id, name, price, stock);
    }

}
