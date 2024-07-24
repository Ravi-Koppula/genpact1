import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/AccountDetailServlet")
public class AccountDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi@2003";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        AccountDetails accountDetails = getAccountDetails(accountNumber);

        if (accountDetails != null) {
            request.setAttribute("accountDetails", accountDetails);
            RequestDispatcher dispatcher = request.getRequestDispatcher("accountDetails.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Account not found.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("getAccountDetails.jsp");
            dispatcher.forward(request, response);
        }
    }

    private AccountDetails getAccountDetails(String accountNumber) {
        AccountDetails accountDetails = null;
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                accountDetails = new AccountDetails();
                accountDetails.setAccountNumber(rs.getString("account_number"));
                accountDetails.setAccountHolderName(rs.getString("account_holder_name"));
                accountDetails.setBalance(rs.getDouble("balance"));
                // Set other account details as needed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountDetails;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public class AccountDetails {
        private String accountNumber;
        private String accountHolderName;
        private double balance;
        // Other fields as needed

        // Getters and setters for each field
        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
