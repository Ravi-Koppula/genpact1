import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepositDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi@2003";

    public boolean depositAmount(String accountNumber, double amount) {
        boolean isDeposited = false;
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, amount);
            statement.setString(2, accountNumber);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                isDeposited = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }

        return isDeposited;
    }
}
