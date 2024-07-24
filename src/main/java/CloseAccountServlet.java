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

@WebServlet("/CloseAccountServlet")
public class CloseAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi@2003";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        if (closeAccount(accountNumber)) {
            request.setAttribute("message", "Account successfully closed.");
        } else {
            request.setAttribute("message", "Error closing account. Please check if the balance is zero.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("CloseAccount.jsp");
        dispatcher.forward(request, response);
    }

    private boolean closeAccount(String accountNumber) {
        boolean isClosed = false;
        String checkBalanceSql = "SELECT balance FROM accounts WHERE account_number = ?";
        String closeAccountSql = "DELETE FROM accounts WHERE account_number = ? AND balance = 0";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql);
             PreparedStatement closeStmt = conn.prepareStatement(closeAccountSql)) {

            checkStmt.setString(1, accountNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance == 0) {
                    closeStmt.setString(1, accountNumber);
                    int rowsAffected = closeStmt.executeUpdate();
                    isClosed = rowsAffected > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isClosed;
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
