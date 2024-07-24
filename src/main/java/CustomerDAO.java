import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi@2003";

    // Method to get customer details by account number
    public Customer getCustomerByAccountNumber(String accountNumber) {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE account_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setUserName(rs.getString("user_name"));
                customer.setDob(rs.getString("dob"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setGender(rs.getString("gender"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setJoinedDate(rs.getString("joined_date"));
                customer.setBalance(rs.getDouble("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // Method to get a connection to the database
    private Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
