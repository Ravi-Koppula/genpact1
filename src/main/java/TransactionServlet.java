import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber"); // Assuming account number is stored in session

        if (accountNumber == null) {
            response.sendRedirect("error.jsp?message=Session expired or account not found");
            return;
        }

        TransactionDAO dao = new TransactionDAO();
        List<Transaction> transactions = dao.getLast10Transactions(accountNumber);

        if (transactions != null) {
            request.setAttribute("Transactions", transactions);
            request.getRequestDispatcher("viewTransactions.jsp").forward(request, response); // Forward to a JSP to display transactions
        } else {
            response.sendRedirect("error.jsp?message=No transactions found");
        }
    }
}
