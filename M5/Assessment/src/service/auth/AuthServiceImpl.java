package service.auth;

import dao.user.UserDAO;
import dao.user.UserDAOImpl;
import domain.Partner;
import domain.User;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import util.Logger;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;

    public AuthServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public User login(String email, String password) {

        // Find user by email
        Optional<User> userOptional = userDAO.findUserByEmail(email);

        // Check if user exists
        if (userOptional.isEmpty()) {

            // Log error
            Logger.log(Logger.LogLevel.WARN, "Login failed: User with email '" + email + "' not found.");
            throw new NotFoundException("User doesn't exist.");
        }

        User user = userOptional.get();

        // Check if password matches
        if (!user.getPassword().equals(password)) {

            // Log error
            Logger.log(Logger.LogLevel.WARN, "Login failed: Incorrect password for user '" + email + "'.");
            throw new BadRequestException("Incorrect password.");
        }

        // If user is a partner
        if (user instanceof Partner partner) {

            // Check if the partner's account is active
            if (!partner.isActive()) {

                // Log error
                Logger.log(Logger.LogLevel.WARN, "Login failed: Partner account for '" + email + "' is not active.");
                throw new ConflictException("Partner account is not active.");
            }
        }

        // Return the user object if all checks pass
        Logger.log(Logger.LogLevel.INFO, "User '" + email + "' logged in successfully.");
        return user;
    }

    @Override
    public void register(String email, String password, String name) {

        // If email is taken.
        if (isEmailTaken(email)) {

            // Log error
            Logger.log(Logger.LogLevel.WARN, "Registration failed: Email '" + email + "' is already taken.");
            throw new ConflictException("Email is already taken");
        }

        // Create new Partner object
        Partner newPartner = new Partner(email, password, name);

        // Add partner to the database
        userDAO.create(newPartner);

        // Log success
        Logger.log(Logger.LogLevel.INFO, "User '" + email + "' registered successfully.");
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userDAO.findByEmail(email);
    }
}
