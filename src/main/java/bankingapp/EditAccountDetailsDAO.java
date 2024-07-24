import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditAccountDetailsDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String USER = "yourusername";
    private static final String PASSWORD = "yourpassword";

    public Account getAccountDetails(String accountNumber) {
        Account account = null;
        String query = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setAccountNumber(resultSet.getString("account_number"));
                account.setAccountHolderName(resultSet.getString("account_holder_name"));
                account.setBalance(resultSet.getDouble("balance"));
                // Set other account details if needed
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return account;
    }
}
