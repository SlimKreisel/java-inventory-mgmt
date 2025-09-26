package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Handles connection to MySQL database.
 * Centralized here so DAO classes don’t repeat connection logic.
 */
public class DatabaseConnection {

        private static final String URL =
                "jdbc:mysql://127.0.0.1:3306/inventorydb?useSSL=false&serverTimezone=UTC";
        private static final String USER = "root";
        private static final String PASS = "NewStrongPassword123!";
    // Open a connection
        public static Connection getConnection() throws SQLException {
            try {
                return DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) {
                throw new SQLException("Failed to connect to DB: " + e.getMessage(), e);
            }
        }
    }


