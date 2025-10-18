package dao.book;

import config.ConnectionFactory;
import domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements BookDAO {

    @Override
    public Optional<Book> findById(int bookId) {
        
        // SQL query
        String sql = "SELECT * FROM books WHERE book_id = ?";
        
        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the parameter
            stmt.setInt(1, bookId);
            
            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {
                
                // If a book is found, create a Book object and populate it with data from the database
                if (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setStock(rs.getInt("stock"));
                    
                    // Return the book
                    return Optional.of(book);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by id: " + e.getMessage(), e);
        }
        
        // If no book is found, return an empty Optional
        return Optional.empty();
    }

    @Override
    public void createBook(Book book) {

        // SQL query
        String sql = "INSERT INTO books (title, isbn, stock) VALUES (?, ?, ?)";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getIsbn());
            stmt.setInt(3, book.getStock());
            // Execute the update
            stmt.executeUpdate();

            // Get the generated key and set it to the book object
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating book: " + e.getMessage(), e);
        }
    }

    @Override
    public Book getBookByIsbn(String isbn) {

        // SQL query
        String sql = "SELECT * FROM books WHERE isbn = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameter
            stmt.setString(1, isbn);

            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {

                // If a book is found, create a Book object and populate it with data from the database
                if (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setStock(rs.getInt("stock"));
                    // Return the book
                    return book;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by isbn: " + e.getMessage(), e);
        }

        // If no book is found, return null
        return null;
    }

    @Override
    public void updateBookStock(Book book) {

        // SQL query
        String sql = "UPDATE books SET stock = ? WHERE book_id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters
            stmt.setInt(1, book.getStock());
            stmt.setInt(2, book.getBookId());

            // Execute the update
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating book stock: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Book> getAllBooks() {

        // SQL query
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate over the result set
            while (rs.next()) {

                // Create a Book object and populate it with data from the database
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                book.setStock(rs.getInt("stock"));

                // Add the book to the list
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all books: " + e.getMessage(), e);
        }

        // Return the list of books
        return books;
    }
}
