package service.auth;

import domain.User;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

public interface AuthService {

    User login(String email, String password) throws NotFoundException, BadRequestException, ConflictException;

    void register(String email, String password, String name) throws ConflictException;

    boolean isEmailTaken(String email);

}
