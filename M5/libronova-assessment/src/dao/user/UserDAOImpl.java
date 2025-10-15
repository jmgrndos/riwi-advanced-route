package dao.user;

import config.ConnectionFactory;
import domain.Admin;
import domain.Partner;
import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public void create(Partner partner) {

        // SQL query to insert a new user
        String userQuery = "INSERT INTO users (email, password) VALUES (?, ?)";

        // SQL query to insert a new partner
        String partnerQuery = "INSERT INTO partners (user_id, name, is_active) VALUES (?, ?, ?)";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Create the user record
            try (PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, partner.getEmail());
                userStmt.setString(2, partner.getPassword());
                userStmt.executeUpdate();

                // Get the generated user ID
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        partner.setUserId(userId);

                        // Create the partner record
                        try (PreparedStatement partnerStmt = conn.prepareStatement(partnerQuery)) {
                            partnerStmt.setInt(1, userId);
                            partnerStmt.setString(2, partner.getName());
                            partnerStmt.setBoolean(3, partner.isActive());
                            partnerStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            // Consider rolling back transaction here in a real application
            throw new RuntimeException("Error creating partner: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean findByEmail(String email) {

        // SQL query to check if a user exists with the given email
        String query = "SELECT 1 FROM users WHERE email = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set the parameter
            stmt.setString(1, email);

            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {
                
                // If a record is found, return true
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        // SQL query to get user and partner details
        String query = "SELECT u.user_id, u.email, u.password, p.name, p.is_active FROM users u " +
                       "LEFT JOIN partners p ON u.user_id = p.user_id WHERE u.email = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set the parameter
            stmt.setString(1, email);
            
            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {
                
                // If a user is found, create a User object and populate it with data from the database
                if (rs.next()) {
                    
                    // If the user is a partner, create a Partner object
                    if (rs.getString("name") != null) {
                        Partner partner = new Partner();
                        partner.setUserId(rs.getInt("user_id"));
                        partner.setEmail(rs.getString("email"));
                        partner.setPassword(rs.getString("password"));
                        partner.setName(rs.getString("name"));
                        partner.setActive(rs.getBoolean("is_active"));
                        return Optional.of(partner);
                    } else {
                        // If the user is an admin, create an Admin object
                        Admin admin = new Admin();
                        admin.setUserId(rs.getInt("user_id"));
                        admin.setEmail(rs.getString("email"));
                        admin.setPassword(rs.getString("password"));
                        return Optional.of(admin);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage(), e);
        }
        
        // If no user is found, return an empty Optional
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserById(int userId) {

        // SQL query to get user and partner details
        String query = "SELECT u.user_id, u.email, u.password, p.name, p.is_active FROM users u " +
                "LEFT JOIN partners p ON u.user_id = p.user_id WHERE u.user_id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameter
            stmt.setInt(1, userId);

            // Execute the query and get the result
            try (ResultSet rs = stmt.executeQuery()) {

                // If a user is found, create a User object and populate it with data from the database
                if (rs.next()) {

                    // If the user is a partner, create a Partner object
                    if (rs.getString("name") != null) {
                        Partner partner = new Partner();
                        partner.setUserId(rs.getInt("user_id"));
                        partner.setEmail(rs.getString("email"));
                        partner.setPassword(rs.getString("password"));
                        partner.setName(rs.getString("name"));
                        partner.setActive(rs.getBoolean("is_active"));
                        return Optional.of(partner);
                    } else {
                        // If the user is an admin, create an Admin object
                        Admin admin = new Admin();
                        admin.setUserId(rs.getInt("user_id"));
                        admin.setEmail(rs.getString("email"));
                        admin.setPassword(rs.getString("password"));
                        return Optional.of(admin);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id: " + e.getMessage(), e);
        }

        // If no user is found, return an empty Optional
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {

        // Check if the user is a partner
        if (!(user instanceof Partner)) {
            // Or throw an exception, depending on desired behavior
            return;
        }

        // SQL query to update the partner's status
        Partner partner = (Partner) user;
        String query = "UPDATE partners SET is_active = ? WHERE user_id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameters
            stmt.setBoolean(1, partner.isActive());
            stmt.setInt(2, partner.getUserId());
            
            // Execute the update
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user status: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() {

        // List to store all users
        List<User> users = new ArrayList<>();

        // SQL query to get all users and their details
        String query = "SELECT u.user_id, u.email, u.password, p.name, p.is_active FROM users u " +
                       "LEFT JOIN partners p ON u.user_id = p.user_id";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate over the result set
            while (rs.next()) {
                
                // If the user is a partner, create a Partner object
                if (rs.getString("name") != null) {
                    Partner partner = new Partner();
                    partner.setUserId(rs.getInt("user_id"));
                    partner.setEmail(rs.getString("email"));
                    partner.setPassword(rs.getString("password"));
                    partner.setName(rs.getString("name"));
                    partner.setActive(rs.getBoolean("is_active"));
                    users.add(partner);

                } else {
                    // If the user is an admin, create an Admin object
                    Admin admin = new Admin();
                    admin.setUserId(rs.getInt("user_id"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPassword(rs.getString("password"));
                    users.add(admin);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all users: " + e.getMessage(), e);
        }
        
        // Return the list of users
        return users;
    }
}
