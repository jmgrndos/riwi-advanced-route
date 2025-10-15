package controller.admin;

import domain.Book;
import domain.Loan;
import exception.BadRequestException;
import exception.NotFoundException;
import service.book.BookService;
import service.loan.LoanService;
import service.user.UserService;
import util.InputValidator;
import view.AdminView;

import java.util.List;
import java.util.stream.Collectors;

public class AdminControllerImpl implements AdminController {

    // Properties
    private final AdminView adminView;
    private final BookService bookService;
    private final LoanService loanService;
    private final UserService userService;

    // Constructor
    public AdminControllerImpl(BookService bookService, LoanService loanService, UserService userService) {
        this.adminView = new AdminView();
        this.bookService = bookService;
        this.loanService = loanService;
        this.userService = userService;
    }

    @Override
    public void viewAllBooks() {

        // Get all books
        List<Book> books = bookService.getAllBooks();

        // If list is empty
        if (books.isEmpty()) {
            adminView.showSuccessMessage("All Books", "No books found.");
            return;
        }

        // Formats the list of books into a string for display.
        String booksText = books.stream()
                .map(book -> "ID: " + book.getBookId() +
                        ", Title: " + book.getTitle() +
                        ", ISBN: " + book.getIsbn() +
                        ", Stock: " + book.getStock())
                .collect(Collectors.joining("\n"));

        // Show all books
        adminView.showSuccessMessage("All Books", booksText);
    }

    @Override
    public void createBook() {

        // Define variables
        String title, isbn, stockStr;
        int stock;

        // Create book loop
        while (true) {

            // Prompt for title
            title = adminView.getTitleInput("Create Book");

            // Handle cancel button
            if (title == null) return;

            // Validate title input
            if (InputValidator.isEmptyInput(title)) {
                adminView.showErrorMessage("Create Book Error", "Title cannot be empty.");
                continue;
            }
            break;
        }

        while (true) {

            // Prompt for isbn
            isbn = adminView.getBookIdInput("Create Book");

            // Handle cancel button
            if (isbn == null) return;

            // Validate isbn input
            if (InputValidator.isEmptyInput(isbn)) {
                adminView.showErrorMessage("Create Book Error", "ISBN cannot be empty.");
                continue;
            }
            break;
        }

        while (true) {

            // Prompt for stock
            stockStr = adminView.getStockInput("Create Book");

            // Handle cancel button
            if (stockStr == null) return;

            // Validate stock input
            if (InputValidator.isEmptyInput(stockStr)) {
                adminView.showErrorMessage("Create Book Error", "Stock cannot be empty.");
                continue;
            }

            // Validate stock format
            if (InputValidator.isInvalidInteger(stockStr)) {
                adminView.showErrorMessage("Create Book Error", "Invalid stock format. Please enter a positive number.");
                continue;
            }
            stock = Integer.parseInt(stockStr);
            break;
        }

        // Create new book object
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setStock(stock);

        // Call service to create book
        bookService.createBook(book);
        adminView.showSuccessMessage("Create Book", "Book created successfully!");
    }

    @Override
    public void updateBookStock() {

        // Define variables
        String bookIdStr, newStockStr;
        int bookId, newStock;

        // Update book stock loop
        while (true) {

            // Prompt for bookId
            bookIdStr = adminView.getBookIdInput("Update Stock");

            // Handle cancel button
            if (bookIdStr == null) return;

            // Validate bookId input
            if (InputValidator.isEmptyInput(bookIdStr)) {
                adminView.showErrorMessage("Update Stock Error", "Book ID cannot be empty.");
                continue;
            }

            if (InputValidator.isInvalidInteger(bookIdStr)) {
                adminView.showErrorMessage("Update Stock Error", "Invalid Book ID format. Please enter a positive number.");
                continue;
            }
            bookId = Integer.parseInt(bookIdStr);
            break;
        }

        while (true) {

            // Prompt for new stock
            newStockStr = adminView.getStockInput("Update Stock");

            // Handle cancel button
            if (newStockStr == null) return;

            // Validate new stock input
            if (InputValidator.isEmptyInput(newStockStr)) {
                adminView.showErrorMessage("Update Stock Error", "New stock cannot be empty.");
                continue;
            }

            // Validate new stock format
            if (InputValidator.isInvalidInteger(newStockStr)) {
                adminView.showErrorMessage("Update Stock Error", "Invalid stock format. Please enter a positive number.");
                continue;
            }
            newStock = Integer.parseInt(newStockStr);
            break;
        }

        try {
            // Call service to update book stock
            bookService.updateBookStock(bookId, newStock);
            adminView.showSuccessMessage("Update Stock", "Book stock updated successfully!");
        } catch (NotFoundException | BadRequestException e) {
            adminView.showErrorMessage("Update Stock Error", e.getMessage());
        }
    }

    @Override
    public void viewAllLoans() {

        // Get all loans
        List<Loan> loans = loanService.getAllLoans();

        // If list is empty
        if (loans.isEmpty()) {
            adminView.showSuccessMessage("All Loans", "No loans found.");
            return;
        }

        // Formats the list of loans into a string for display.
        String loansText = loans.stream()
                .map(loan -> "Loan ID: " + loan.getLoanId() +
                        ", Book ID: " + loan.getBookId() +
                        ", User ID: " + loan.getUserId() +
                        ", Loan Date: " + loan.getLoanDate() +
                        ", Due Date: " + loan.getDueDate() +
                        ", Returned: " + (loan.getReturnDate() != null ? loan.getReturnDate() : "No"))
                .collect(Collectors.joining("\n"));

        // Show all loans
        adminView.showSuccessMessage("All Loans", loansText);
    }

    @Override
    public void updatePartnerStatus() {

        // Define variables
        String userIdStr;
        int userId;

        // Update partner status loop
        while (true) {

            // Prompt for user id
            userIdStr = adminView.getUserIdInput("Update Partner Status");

            // Handle cancel button
            if (userIdStr == null) return;

            // Validate email input
            if (InputValidator.isEmptyInput(userIdStr)) {
                adminView.showErrorMessage("Update Partner Error", "User ID cannot be empty.");
                continue;
            }

            // Validate email format
            if (InputValidator.isInvalidInteger(userIdStr)) {
                adminView.showErrorMessage("Update Partner Error", "Invalid user ID format.");
                continue;
            }

            userId = Integer.parseInt(userIdStr);
            break;
        }

        // Get new partner status
        boolean isActive = adminView.getPartnerStatusInput("Update Partner Status");

        try {
            // Call service to update partner status
            userService.updateUserStatus(userId, isActive);
            adminView.showSuccessMessage("Update Partner Status", "Partner status updated successfully!");
        } catch (NotFoundException | BadRequestException e) {
            adminView.showErrorMessage("Update Partner Error", e.getMessage());
        }
    }

    @Override
    public void exportAllBooks() {

        // Get all books
        List<Book> books = bookService.getAllBooks();

        // If list is empty
        if (books.isEmpty()) {
            adminView.showErrorMessage("Export Error", "No books to export.");
            return;
        }

        // Export all books
        bookService.exportAllBooks();
        adminView.showSuccessMessage("Export Success", "All books exported to books.csv");
    }

    @Override
    public void exportAllOverdueLoans() {

        // Get all books
        List<Loan> overdueLoans = loanService.getOverdueLoans();

        // If list is empty
        if (overdueLoans.isEmpty()) {
            adminView.showErrorMessage("Export Error", "No overdue loans to export.");
            return;
        }

        // Export all overdue loans
        loanService.exportAllOverdueLoans();
        adminView.showSuccessMessage("Export Success", "All overdue loans exported to overdue_loans.csv");
    }
}
