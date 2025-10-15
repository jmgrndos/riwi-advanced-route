package service.user;

import domain.User;
import exception.BadRequestException;
import exception.NotFoundException;

import java.util.List;

public interface UserService {
    void updateUserStatus(int userId, boolean isActive) throws NotFoundException, BadRequestException;
    List<User> getAllUsers();
    User getUserByEmail(String email) throws NotFoundException;
    User getUserById(int userId) throws NotFoundException;
}
