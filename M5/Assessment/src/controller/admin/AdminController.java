package controller.admin;

public interface AdminController {

    void viewAllBooks();

    void createBook();

    void updateBookStock();

    void viewAllLoans();

    void updatePartnerStatus();

    void exportAllBooks();

    void exportAllOverdueLoans();
}
