package service.loan;

import domain.Loan;
import exception.ConflictException;
import exception.NotFoundException;

import java.util.List;

public interface LoanService {

    void createLoan(int bookId, int userId) throws NotFoundException, ConflictException;

    List<Loan> getActiveLoans(int userId);

    void returnLoan(int loanId) throws NotFoundException, ConflictException;

    List<Loan> getAllLoans();

    void exportAllOverdueLoans();

    List<Loan> getOverdueLoans();
}
