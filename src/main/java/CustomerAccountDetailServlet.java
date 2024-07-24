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

@WebServlet("/CustomerAccountDetailServlet")
public class CustomerAccountDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi@2003";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        Account account = getAccountDetails(accountNumber);

        if (account != null) {
            request.setAttribute("account", account);
            RequestDispatcher dispatcher = request.getRequestDispatcher("accountDetails.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "Account not found.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private Account getAccountDetails(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        Account account = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccountNumber(rs.getString("account_number"));
                account.setUserName(rs.getString("user_name"));
                account.setDob(rs.getString("dob"));
                account.setEmail(rs.getString("email"));
                account.setAddress(rs.getString("address"));
                account.setPhoneNumber(rs.getString("phone_number"));
                account.setGender(rs.getString("gender"));
                account.setAccountType(rs.getString("account_type"));
                account.setJoinedDate(rs.getString("joined_date"));
                account.setBalance(rs.getDouble("balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
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
}

class Account {
    private String accountNumber;
    private String userName;
    private String dob;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private String accountType;
    private String joinedDate;
    private double balance;

    // Getters and setters for the above fields
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getJoinedDate() { return joinedDate; }
    public void setJoinedDate(String joinedDate) { this.joinedDate = joinedDate; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
