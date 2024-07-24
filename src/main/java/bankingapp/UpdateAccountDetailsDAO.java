import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAccountDetailsDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String USER = "yourusername";
    private static final String PASSWORD = "yourpassword";

    public boolean updateAccountDetails(String accountNumber, String newAccountHolderName, String newAddress, String newPhoneNumber) {
        boolean isUpdated = false;
        String query = "UPDATE accounts SET account_holder_name = ?, address = ?, phone_number = ? WHERE account_number = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newAccountHolderName);
            statement.setString(2, newAddress);
            statement.setString(3, newPhoneNumber);
            statement.setString(4, accountNumber);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        return isUpdated;
    }
}
