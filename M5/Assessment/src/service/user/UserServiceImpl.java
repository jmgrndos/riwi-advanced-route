package service.user;

import dao.user.UserDAO;
import domain.Partner;
import domain.User;
import exception.BadRequestException;
import exception.NotFoundException;
import util.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void updateUserStatus(int userId, boolean isActive) {

        // Find user by id
        User user = userDAO.findUserById(userId)
                .orElseThrow(() -> {

                    // Log error
                    Logger.log(Logger.LogLevel.WARN, "Update user status failed: User with id '" + userId + "' not found.");
                    return new NotFoundException("User with id " + userId + " not found");
                });

        // If user is a partner, update status
        if (user instanceof Partner) {
            Partner partner = (Partner) user;
            partner.setActive(isActive);
            userDAO.updateUser(partner);

            // Log success
            Logger.log(Logger.LogLevel.INFO, "Status for user with id '" + userId + "' updated successfully.");
        } else {

            // Log error
            Logger.log(Logger.LogLevel.WARN, "Update user status failed: User with id '" + userId + "' is not a partner.");
            throw new BadRequestException("User is not a partner and cannot be updated.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email)
                .orElseThrow(() -> {

                    // Log error
                    Logger.log(Logger.LogLevel.WARN, "Get user by email failed: User with email '" + email + "' not found.");
                    return new NotFoundException("User with email " + email + " not found");
                });
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.findUserById(userId)
                .orElseThrow(() -> {

                    // Log error
                    Logger.log(Logger.LogLevel.WARN, "Get user by id failed: User with id '" + userId + "' not found.");
                    return new NotFoundException("User with id " + userId + " not found");
                });
    }
}
