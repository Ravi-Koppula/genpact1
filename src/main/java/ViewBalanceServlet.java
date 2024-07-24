import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewBalanceServlet")
public class ViewBalanceServlet extends HttpServlet {
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
        
        // Fetch the balance (assuming a method getBalance in Customer class)
        double balance = customer.getBalance();
        
        // Set balance as request attribute and forward to JSP
        request.setAttribute("balance", balance);
        request.getRequestDispatcher("ViewBalance.jsp").forward(request, response);
    }
}
