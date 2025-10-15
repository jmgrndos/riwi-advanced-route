package dao.loan;

import domain.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {

    void create(Loan loan);

    List<Loan> findActiveLoansByUserId(int userId);

    Optional<Loan> findById(int loanId);

    void update(Loan loan);

    List<Loan> findAll();
}
