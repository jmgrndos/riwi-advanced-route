package domain;

public class Partner extends User {

    private String name;
    private boolean isActive;

    public Partner() {
    }

    public Partner(String email, String password, String name) {
        super(email, password);
        this.name = name;
        this.isActive = true; // Default to active
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "password='" + getPassword() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", userId=" + getUserId() +
                ", name='" + getName() + '\'' +
                ", isActive=" + isActive() +
                '}';
    }
}
