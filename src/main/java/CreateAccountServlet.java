import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/CreateAccountServlet")
@MultipartConfig
public class CreateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/ravi";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravi2003";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String accountType = request.getParameter("accountType");
        String joinedDate = request.getParameter("joinedDate");
        double balance = Double.parseDouble(request.getParameter("balance"));
        Part idProof = request.getPart("idProof");
        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        boolean isAccountCreated = createAccount(userName, dob, email, address, phoneNumber, gender, accountType, joinedDate, balance, idProof, accountNumber, password);

        if (isAccountCreated) {
            request.setAttribute("message", "Account successfully created.");
        } else {
            request.setAttribute("message", "Error creating account. Please try again.");
        }

        request.getRequestDispatcher("CreateAccountResult.jsp").forward(request, response);
    }

    private boolean createAccount(String userName, String dob, String email, String address, String phoneNumber, String gender, String accountType, String joinedDate, double balance, Part idProof, String accountNumber, String password) {
        String sql = "INSERT INTO accounts (account_number, user_name, dob, email, address, phone_number, gender, account_type, joined_date, balance, id_proof, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            stmt.setString(2, userName);
            stmt.setString(3, dob);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, phoneNumber);
            stmt.setString(7, gender);
            stmt.setString(8, accountType);
            stmt.setString(9, joinedDate);
            stmt.setDouble(10, balance);
            stmt.setBlob(11, idProof.getInputStream());
            stmt.setString(12, password);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
