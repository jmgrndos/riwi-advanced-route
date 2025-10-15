package dao.user;

import domain.Partner;
import domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    void create(Partner partner);

    boolean findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(int userId);

    void updateUser(User user);

    List<User> getAllUsers();
}
