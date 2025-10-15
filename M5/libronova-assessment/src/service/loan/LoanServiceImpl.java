package service.loan;

import dao.book.BookDAO;
import dao.loan.LoanDAO;
import domain.Book;
import domain.Loan;
import exception.ConflictException;
import exception.NotFoundException;
import util.CSVExporter;
import util.Logger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {

    // Properties
    private final LoanDAO loanDAO;
    private final BookDAO bookDAO;

    // Constructor
    public LoanServiceImpl(LoanDAO loanDAO, BookDAO bookDAO) {
        this.loanDAO = loanDAO;
        this.bookDAO = bookDAO;
    }

    @Override
    public void createLoan(int bookId, int userId) {

        // Get book
        Book book = bookDAO.findById(bookId)
                .orElseThrow(() -> {

                    // Log error
                    Logger.log(Logger.LogLevel.WARN, "Create loan failed: Book with id '" + bookId + "' not found.");
                    return new NotFoundException("Book not found");
                });

        // Check if book is out of stock
        if (book.getStock() <= 0) {

            // Log error
            Logger.log(Logger.LogLevel.WARN, "Create loan failed: Book with id '" + bookId + "' is out of stock.");
            throw new ConflictException("Book is out of stock");
        }

        // Create and save the loan
        Loan loan = new Loan();
        loan.setBookId(book.getBookId());
        loan.setUserId(userId);
        loan.setLoanDate(new java.sql.Date(new Date().getTime()));

        // Set due date to 15 months from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 15);
        loan.setDueDate(new java.sql.Date(cal.getTime().getTime()));

        // Save the loan
        loanDAO.create(loan);

        // Update book stock
        book.setStock(book.getStock() - 1);

        // Update book stock
        bookDAO.updateBookStock(book);

        // Log success
        Logger.log(Logger.LogLevel.INFO, "Loan for book with id '" + bookId + "' created successfully for user with id '" + userId + "'.");
    }

    @Override
    public List<Loan> getActiveLoans(int userId) {
        return loanDAO.findActiveLoansByUserId(userId);
    }

    @Override
    public void returnLoan(int loanId) {

        // Get loan
        Loan loan = loanDAO.findById(loanId)
                .orElseThrow(() -> {

                    // Log error
                    Logger.log(Logger.LogLevel.WARN, "Return loan failed: Loan with id '" + loanId + "' not found.");
                    return new NotFoundException("Loan not found");
                });

        // Check if loan has already been returned
        if (loan.getReturnDate() != null) {

            // Log error
            Logger.log(Logger.LogLevel.WARN, "Return loan failed: Loan with id '" + loanId + "' has already been returned.");
            throw new ConflictException("Loan has already been returned");
        }

        // Update loan with return date
        loan.setReturnDate(new java.sql.Date(new Date().getTime()));
        loanDAO.update(loan);

        // Get book
        Book book = bookDAO.findById(loan.getBookId())
                .orElseThrow(() -> {

                    // Log error
                    Logger.log(Logger.LogLevel.ERROR, "Return loan failed: Book with id '" + loan.getBookId() + "' not found for loan with id '" + loanId + "'.");
                    return new NotFoundException("Book not found for loan");
                });

        // Update book stock
        book.setStock(book.getStock() + 1);
        bookDAO.updateBookStock(book);

        // Log success
        Logger.log(Logger.LogLevel.INFO, "Loan with id '" + loanId + "' returned successfully.");
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanDAO.findAll();
    }

    @Override
    public void exportAllOverdueLoans() {

        // Get all overdue loans
        List<Loan> overdueLoans = getOverdueLoans();

        // Define headers
        List<String> headers = Arrays.asList("Loan ID", "Book ID", "User ID", "Loan Date", "Due Date");

        // Export to CSV
        CSVExporter.exportToCSV("overdue_loans.csv", headers, overdueLoans, (loan) -> Arrays.asList(
                String.valueOf(loan.getLoanId()),
                String.valueOf(loan.getBookId()),
                String.valueOf(loan.getUserId()),
                loan.getLoanDate().toString(),
                loan.getDueDate().toString()
        ));

        // Log success
        Logger.log(Logger.LogLevel.INFO, "All overdue loans exported to overdue_loans.csv");
    }

    @Override
    public List<Loan> getOverdueLoans() {

        // Get all loans
        return loanDAO.findAll().stream()
                .filter(loan -> loan.getReturnDate() == null && loan.getDueDate().before(new Date()))
                .collect(Collectors.toList());
    }
}
