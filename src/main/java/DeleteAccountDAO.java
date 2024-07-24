import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAccountDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/yourdatabase";
    private static final String USER = "yourusername";
    private static final String PASSWORD = "yourpassword";

    public boolean deleteAccount(String accountNumber) {
        boolean isDeleted = false;
        String query = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a real application
        }
        
        return isDeleted;
    }
}
