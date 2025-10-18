package controller.auth;

import domain.User;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import service.auth.AuthService;
import service.auth.AuthServiceImpl;
import util.InputValidator;
import view.AuthView;

public class AuthControllerImpl implements AuthController {

    private final AuthView authView;
    private final AuthService authService;

    public AuthControllerImpl() {
        this.authView = new AuthView();
        this.authService = new AuthServiceImpl();
    }

    @Override
    public User handleLogin() {

        // Declare variables
        String email, password;

        // Ask for email
        while (true) {

            // Prompt for email
            email = authView.getEmailInput("Log in");

            // Handle cancel button
            if (email == null) return null;

            // Check if email is empty
            if (InputValidator.isEmptyInput(email)) {
                authView.showErrorMessage("Log in Error", "Email cannot be empty.");
                continue;
            }

            // Check if email is invalid
            if (InputValidator.isInvalidEmail(email)) {
                authView.showErrorMessage("Log in Error", "Invalid email format.");
                continue;
            }

            // Email is valid
            break;
        }

        // Ask for password
        while (true) {

            // Prompt for password
            password = authView.getPasswordInput("Log in");

            // Handle cancel button
            if (password == null) return null;

            // Check if password is empty
            if (InputValidator.isEmptyInput(password)) {
                authView.showErrorMessage("Log in Error", "Password cannot be empty.");
                continue;
            }

            // Password is valid
            break;
        }

        try {
            // Get user from the service
            User user = authService.login(email, password);

            // Show success message
            authView.showSuccessMessage("Log in Successful", "Welcome!");
            return user;

        } catch (NotFoundException | BadRequestException | ConflictException e) {
            authView.showErrorMessage("Log in Error", e.getMessage());
            return null;
        }

    }

    @Override
    public void handleSignup() {

        // Declare variables
        String email, password, name;

        // Ask for email
        while (true) {

            // Prompt for email
            email = authView.getEmailInput("Sign up");

            // Handle cancel button
            if (email == null) return;

            // Check if email is empty
            if (InputValidator.isEmptyInput(email)) {
                authView.showErrorMessage("Sign up Error", "Email cannot be empty.");
                continue;
            }

            // Check if email is invalid
            if (InputValidator.isInvalidEmail(email)) {
                authView.showErrorMessage("Sign up Error", "Invalid email format.");
                continue;
            }

            // Email is valid and available
            break;
        }

        // Ask for password
        while (true) {

            // Prompt for password
            password = authView.getPasswordInput("Sign up");

            // Handle cancel button
            if (password == null) return;

            // Check if password is empty
            if (InputValidator.isEmptyInput(password)) {
                authView.showErrorMessage("Sign up Error", "Password cannot be empty.");
                continue;
            }

            // Password is valid
            break;
        }

        // Ask for name
        while (true) {

            // Prompt for name
            name = authView.getNameInput("Sign up");

            // Handle cancel button
            if (name == null) return;

            // Check if name is empty
            if (InputValidator.isEmptyInput(name)) {
                authView.showErrorMessage("Sign up Error", "Name cannot be empty.");
                continue;
            }

            // Name is valid
            break;
        }

        try {
            // Register user via the service
            authService.register(email, password, name);

            // Show success message
            authView.showSuccessMessage("Sign up Successful", "Registration complete! You can now log in.");

        } catch (ConflictException e) {
            authView.showErrorMessage("Sign up Error", e.getMessage());
        }

    }
}
