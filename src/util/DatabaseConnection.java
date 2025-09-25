package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

        private static final String URL =
                "jdbc:mysql://127.0.0.1:3306/?user=root";
        private static final String USER = "root";      // <-- your MySQL username
        private static final String PASS = "Khalidneymar11";  // <-- your MySQL password

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASS);
        }
    }


