package domain;

public class Admin extends User {

    public Admin() {
    }

    public Admin(String email, String password) {
        super(email, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + getUserId() +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
