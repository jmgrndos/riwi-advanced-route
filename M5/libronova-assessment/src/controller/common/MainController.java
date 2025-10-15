package controller.common;

import controller.admin.AdminController;
import controller.admin.AdminControllerImpl;
import controller.auth.AuthController;
import controller.auth.AuthControllerImpl;
import controller.partner.PartnerController;
import controller.partner.PartnerControllerImpl;
import dao.book.BookDAO;
import dao.book.BookDAOImpl;
import dao.loan.LoanDAO;
import dao.loan.LoanDAOImpl;
import dao.user.UserDAO;
import dao.user.UserDAOImpl;
import domain.Admin;
import domain.Partner;
import domain.User;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.loan.LoanService;
import service.loan.LoanServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.AdminView;
import view.MainView;
import view.PartnerView;

public class MainController {

    private final MainView mainView;
    private final AuthController authController;
    private final BookService bookService;
    private final LoanService loanService;
    private final UserService userService;

    public MainController() {
        this.mainView = new MainView();
        this.authController = new AuthControllerImpl();
        BookDAO bookDAO = new BookDAOImpl();
        this.bookService = new BookServiceImpl(bookDAO);
        LoanDAO loanDAO = new LoanDAOImpl();
        this.loanService = new LoanServiceImpl(loanDAO, bookDAO);
        UserDAO userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO);
    }

    // Main loop
    public void run() {
        while (true) {

            // Show main menu and get the selected option
            MainView.MainMenuOption option = mainView.showMainMenu();

            // Initialize logged user on null
            User loggedInUser = null;

            // Handle the selected option
            switch (option) {
                case REGISTER:
                    authController.handleSignup();
                    break;
                case LOG_IN:
                    loggedInUser = authController.handleLogin();
                    break;
                case EXIT:
                    return;
            }

            // If user is logged, handle partner or admin session
            if (loggedInUser != null) {
                if (loggedInUser instanceof Partner) {
                    handlePartnerSession(loggedInUser);

                } else if (loggedInUser instanceof Admin) {
                    handleAdminSession(loggedInUser);
                }
            }
        }
    }

    // Partner session
    private void handlePartnerSession(User loggedInUser) {

        // Initialize views and controllers
        PartnerView partnerView = new PartnerView();
        PartnerController partnerController = new PartnerControllerImpl(loggedInUser, loanService);

        // Handle partner options
        while (true) {

            // Show user menu and get the selected option
            PartnerView.UserMenuOption partnerOption = partnerView.showUserMenu(loggedInUser.getEmail());

            // Handle log out and close button
            if (partnerOption == PartnerView.UserMenuOption.LOG_OUT) {
                break;
            }

            // Handle selected option
            switch (partnerOption) {
                case VIEW_LOANS:
                    partnerController.viewLoans();
                    break;
                case MAKE_LOAN:
                    partnerController.makeLoan();
                    break;
                case RETURN_LOAN:
                    partnerController.returnLoan();
                    break;
            }
        }
    }

    private void handleAdminSession(User loggedInUser) {

        // Initialize views and controllers
        AdminController adminController = new AdminControllerImpl(bookService, loanService, userService);
        AdminView adminView = new AdminView();

        // Handle admin options
        while (true) {

            // Show admin menu and get the selected option
            AdminView.AdminMenuOption adminOption = adminView.showAdminMenu(loggedInUser.getEmail());

            // Handle log out and close button
            if (adminOption == AdminView.AdminMenuOption.LOG_OUT) {
                break;
            }

            // Handle selected option
            switch (adminOption) {
                case VIEW_BOOKS:
                    adminController.viewAllBooks();
                    break;
                case CREATE_BOOK:
                    adminController.createBook();
                    break;
                case UPDATE_BOOK_STOCK:
                    adminController.updateBookStock();
                    break;
                case VIEW_ALL_LOANS:
                    adminController.viewAllLoans();
                    break;
                case UPDATE_PARTNER_STATUS:
                    adminController.updatePartnerStatus();
                    break;
                case EXPORT_ALL_BOOKS:
                    adminController.exportAllBooks();
                    break;
                case EXPORT_ALL_OVERDUE_LOANS:
                    adminController.exportAllOverdueLoans();
                    break;
            }
        }
    }
}
