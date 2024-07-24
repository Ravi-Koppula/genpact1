import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get session and check if user is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("customer") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve the customer object from session
        Customer customer = (Customer) session.getAttribute("customer");

        // Get the withdrawal amount from request
        double amount = 0;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount format");
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
            return;
        }

        // Perform validation checks
        if (amount <= 0) {
            request.setAttribute("error", "Withdrawal amount must be greater than zero");
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
            return;
        }
        if (amount > customer.getBalance()) {
            request.setAttribute("error", "Insufficient balance");
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
            return;
        }

        // Process withdrawal
        customer.setBalance(customer.getBalance() - amount);

        // Update customer information in the session or database (if necessary)

        // Set success message and forward to the balance view page
        request.setAttribute("success", "Withdrawal successful. New balance is: $" + customer.getBalance());
        request.getRequestDispatcher("viewBalance.jsp").forward(request, response);
    }
}
