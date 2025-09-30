import util.ConnectionFactory;
import view.InventoryView;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

        // Check connection
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            System.out.println("Connected successfully!");
        } else {
            System.out.println("Connection failed.");
        }

        // Run Menu
        InventoryView view = new InventoryView();
        view.showMenu();


    }
}
