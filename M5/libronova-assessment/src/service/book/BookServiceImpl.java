package service.book;

import dao.book.BookDAO;
import domain.Book;
import exception.BadRequestException;
import exception.NotFoundException;
import util.CSVExporter;
import util.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    // Properties
    private final BookDAO bookDAO;

    // Constructor
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public Optional<Book> findById(int bookId) {
        return bookDAO.findById(bookId);
    }

    @Override
    public void createBook(Book book) {
        bookDAO.createBook(book);

        // Log success
        Logger.log(Logger.LogLevel.INFO, "Book '" + book.getTitle() + "' created successfully.");
    }

    @Override
    public void updateBookStock(int bookId, int newStock) {

        // Get book by id
        Optional<Book> bookOptional = bookDAO.findById(bookId);

        // Update stock
        if (bookOptional.isPresent()) {
            if (newStock < 0) {

                // Log error
                Logger.log(Logger.LogLevel.WARN, "Update stock failed: New stock for book with id '" + bookId + "' cannot be negative.");
                throw new BadRequestException("New stock cannot be negative");
            }

            // Update stock
            Book book = bookOptional.get();
            book.setStock(newStock);
            bookDAO.updateBookStock(book);

            // Log success
            Logger.log(Logger.LogLevel.INFO, "Stock for book with id '" + bookId + "' updated successfully.");

        } else {
            // Log error
            Logger.log(Logger.LogLevel.WARN, "Update stock failed: Book with id '" + bookId + "' not found.");
            throw new NotFoundException("Book with id " + bookId + " not found");
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public void exportAllBooks() {

        // Get all books
        List<Book> books = getAllBooks();

        // Define headers
        List<String> headers = Arrays.asList("ID", "Title", "ISBN", "Stock");

        // Export to CSV
        CSVExporter.exportToCSV("books.csv", headers, books, (book) -> Arrays.asList(
                String.valueOf(book.getBookId()),
                book.getTitle(),
                book.getIsbn(),
                String.valueOf(book.getStock())
        ));

        // Log success
        Logger.log(Logger.LogLevel.INFO, "All books exported to books.csv");
    }
}
