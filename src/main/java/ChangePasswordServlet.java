import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Ravi@2003";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user from session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if not authenticated
            return;
        }

        // Retrieve new passwords from request
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            // Passwords do not match, send error message
            request.setAttribute("errorMessage", "Passwords do not match!");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        // Change the password
        boolean passwordChanged = changePassword(username, newPassword);

        if (passwordChanged) {
            // Redirect to a success page or login page
            response.sendRedirect("passwordChanged.jsp");
        } else {
            // Set error message and forward back to change password page
            request.setAttribute("errorMessage", "Failed to change password!");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
        }
    }

    private boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, newPassword);
            statement.setString(2, username);
            
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
