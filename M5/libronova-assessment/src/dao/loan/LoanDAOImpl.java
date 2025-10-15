package dao.loan;

import config.ConnectionFactory;
import domain.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanDAOImpl implements LoanDAO {

    @Override
    public void create(Loan loan) {

        // SQL query
        String sql = "INSERT INTO loans (book_id, user_id, due_date) VALUES (?, ?, ?)";
        
        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the parameters
            stmt.setInt(1, loan.getBookId());
            stmt.setInt(2, loan.getUserId());
            stmt.setDate(3, loan.getDueDate());
            
            // Execute the update
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error creating loan: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Loan> findActiveLoansByUserId(int userId) {

        // SQL query
        String sql = "SELECT l.loan_id, l.loan_date, l.due_date, l.book_id " +
                     "FROM loans l " +
                     "WHERE l.user_id = ? AND l.return_date IS NULL";
        List<Loan> loans = new ArrayList<>();
        
        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the parameter
            stmt.setInt(1, userId);
            
            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {
                
                // Iterate over the result set
                while (rs.next()) {
                    
                    // Create a Loan object and populate it with data from the database
                    Loan loan = new Loan();
                    loan.setLoanId(rs.getInt("loan_id"));
                    loan.setLoanDate(rs.getDate("loan_date"));
                    loan.setDueDate(rs.getDate("due_date"));
                    loan.setUserId(userId);
                    loan.setBookId(rs.getInt("book_id"));
                    
                    // Add the loan to the list
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active loans: " + e.getMessage(), e);
        }
        
        // Return the list of loans
        return loans;
    }

    @Override
    public Optional<Loan> findById(int loanId) {

        // SQL query
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        
        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the parameter
            stmt.setInt(1, loanId);
            
            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {
                
                // If a loan is found, create a Loan object and populate it with data from the database
                if (rs.next()) {
                    Loan loan = new Loan();
                    loan.setLoanId(rs.getInt("loan_id"));
                    loan.setLoanDate(rs.getDate("loan_date"));
                    loan.setDueDate(rs.getDate("due_date"));
                    loan.setReturnDate(rs.getDate("return_date"));
                    loan.setUserId(rs.getInt("user_id"));
                    loan.setBookId(rs.getInt("book_id"));
                    
                    // Return the loan
                    return Optional.of(loan);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding loan by id: " + e.getMessage(), e);
        }
        
        // If no loan is found, return an empty Optional
        return Optional.empty();
    }

    @Override
    public void update(Loan loan) {

        // SQL query
        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";
        
        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set the parameters
            stmt.setDate(1, loan.getReturnDate());
            stmt.setInt(2, loan.getLoanId());
            
            // Execute the update
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error updating loan: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Loan> findAll() {

        // SQL query
        String sql = "SELECT * FROM loans";
        List<Loan> loans = new ArrayList<>();
        
        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            // Iterate over the result set
            while (rs.next()) {
                
                // Create a Loan object and populate it with data from the database
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setLoanDate(rs.getDate("loan_date"));
                loan.setDueDate(rs.getDate("due_date"));
                loan.setReturnDate(rs.getDate("return_date"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setBookId(rs.getInt("book_id"));
                
                // Add the loan to the list
                loans.add(loan);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all loans: " + e.getMessage(), e);
        }
        
        // Return the list of loans
        return loans;
    }
}
