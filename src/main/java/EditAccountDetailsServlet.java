import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EditAccountDetailsServlet")
public class EditAccountDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        // Validate account number
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            response.sendRedirect("error.jsp?message=Account number is required");
            return;
        }

        EditAccountDetailsDAO dao = new EditAccountDetailsDAO();
        Account account = dao.getAccountDetails(accountNumber);

        if (account != null) {
            // Store account details in session to use in the next JSP
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            response.sendRedirect("EditAccountDetails.jsp"); // Redirect to a page to display account details
        } else {
            response.sendRedirect("error.jsp?message=Account not found");
        }
    }
}
