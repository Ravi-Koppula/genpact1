import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateAccountDetailsServlet")
public class UpdateAccountDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber"); // Assuming account number is stored in session

        if (accountNumber == null) {
            response.sendRedirect("error.jsp?message=Session expired or account not found");
            return;
        }

        String newAccountHolderName = request.getParameter("accountHolderName");
        String newAddress = request.getParameter("address");
        String newPhoneNumber = request.getParameter("phoneNumber");

        // Validate inputs (basic example)
        if (newAccountHolderName == null || newAccountHolderName.trim().isEmpty() ||
            newAddress == null || newAddress.trim().isEmpty() ||
            newPhoneNumber == null || newPhoneNumber.trim().isEmpty()) {
            response.sendRedirect("error.jsp?message=All fields are required");
            return;
        }

        // Update account details in database
        UpdateAccountDetailsDAO dao = new UpdateAccountDetailsDAO();
        boolean isUpdated = dao.updateAccountDetails(accountNumber, newAccountHolderName, newAddress, newPhoneNumber);

        if (isUpdated) {
            response.sendRedirect("success.jsp"); // Redirect to a success page
        } else {
            response.sendRedirect("error.jsp?message=Failed to update account details");
        }
    }
}
