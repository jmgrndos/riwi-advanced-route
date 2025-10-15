package controller.auth;

import domain.User;

public interface AuthController {


    User handleLogin();

    void handleSignup();

}
