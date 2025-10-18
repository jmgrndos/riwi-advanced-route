package controller.partner;

import domain.Loan;
import domain.User;
import exception.ConflictException;
import exception.NotFoundException;
import service.loan.LoanService;
import util.InputValidator;
import view.PartnerView;

import java.util.List;
import java.util.stream.Collectors;

public class PartnerControllerImpl implements PartnerController {

    // Properties
    private final User loggedInPartner;
    private final LoanService loanService;
    private final PartnerView partnerView;

    // Constructor
    public PartnerControllerImpl(User loggedInPartner, LoanService loanService) {
        this.loggedInPartner = loggedInPartner;
        this.loanService = loanService;
        this.partnerView = new PartnerView();
    }

    @Override
    public void viewLoans() {

        // Get active loans for the logged-in partner
        List<Loan> activeLoans = loanService.getActiveLoans(loggedInPartner.getUserId());

        // If list is empty
        if (activeLoans.isEmpty()) {
            partnerView.showSuccessMessage("My Loans", "You have no active loans.");
            return;
        }

        // Formats the list of active loans into a string for display.
        String loansStr = activeLoans.stream()
                .map(loan -> "Loan ID: " + loan.getLoanId() +
                        "\n  Book ID: " + loan.getBookId() +
                        "\n  Loan Date: " + loan.getLoanDate() +
                        "\n  Due Date: " + loan.getDueDate())
                .collect(Collectors.joining("\n\n"));

        // Show active loans
        partnerView.showLoans("My Loans", "Your active loans:\n\n" + loansStr);

    }

    @Override
    public void makeLoan() {

        // Define variables
        String bookIdStr;
        int bookId;

        // Make a loan loop
        while (true) {

            // Prompt for book id
            bookIdStr = partnerView.getBookIdInput("Make a Loan");

            // Handle cancel button
            if (bookIdStr == null) return;

            // Validate book id input
            if (InputValidator.isEmptyInput(bookIdStr)) {
                partnerView.showErrorMessage("Make a Loan Error", "Book ID cannot be empty.");
                continue;
            }

            // Validate book id format
            if (InputValidator.isInvalidInteger(bookIdStr)) {
                partnerView.showErrorMessage("Make a Loan Error", "Invalid book ID format. Please enter a positive number.");
                continue;
            }

            // Convert input to integer
            bookId = Integer.parseInt(bookIdStr);
            break;
        }

        // Call service to create loan
        try {
            loanService.createLoan(bookId, loggedInPartner.getUserId());
            partnerView.showSuccessMessage("Make a Loan", "Loan created successfully!");
        } catch (NotFoundException | ConflictException e) {
            partnerView.showErrorMessage("Make a Loan Error", e.getMessage());
        }
    }

    @Override
    public void returnLoan() {

        // Define variables
        String loanIdStr;
        int loanId;

        // Return a loan loop
        while (true) {

            // Prompt for loan id
            loanIdStr = partnerView.getLoanIdInput("Return a Loan");

            // Handle cancel button
            if (loanIdStr == null) return;

            // Validate loan id input
            if (InputValidator.isEmptyInput(loanIdStr)) {
                partnerView.showErrorMessage("Return a Loan Error", "Loan ID cannot be empty.");
                continue;
            }

            // Validate loan id format
            if (InputValidator.isInvalidInteger(loanIdStr)) {
                partnerView.showErrorMessage("Return a Loan Error", "Invalid loan ID format. Please enter a positive number.");
                continue;
            }

            // Convert input to integer
            loanId = Integer.parseInt(loanIdStr);
            break;
        }

        // Call service to return loan
        try {
            loanService.returnLoan(loanId);
            partnerView.showSuccessMessage("Return a Loan", "Loan returned successfully!");
        } catch (NotFoundException | ConflictException e) {
            partnerView.showErrorMessage("Return a Loan Error", e.getMessage());
        }
    }
}
