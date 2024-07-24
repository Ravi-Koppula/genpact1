import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi@2003";

    public boolean validateUser(String username, String password, String role) {
        boolean isValidUser = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure driver is loaded
            // Establish a connection to the database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // Prepare SQL query
            String sql;
            if ("admin".equals(role)) {
                sql = "SELECT * FROM admin WHERE fullname = ? AND password = ?";
            } else {
                sql = "SELECT * FROM customer WHERE fullname = ? AND password = ?";
            }

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                isValidUser = true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL error.");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources.");
                e.printStackTrace();
            }
        }

        return isValidUser;
    }
}
