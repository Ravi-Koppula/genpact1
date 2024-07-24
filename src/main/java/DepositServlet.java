import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String depositAmountStr = request.getParameter("depositAmount");
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber"); // Assuming account number is stored in session

        try {
            double depositAmount = Double.parseDouble(depositAmountStr);
            
            // Validate deposit amount
            if (depositAmount <= 0) {
                response.sendRedirect("error.jsp?message=Invalid deposit amount");
                return;
            }

            DepositDAO dao = new DepositDAO();
            boolean isDeposited = dao.depositAmount(accountNumber, depositAmount);

            if (isDeposited) {
                response.sendRedirect("success.jsp"); // Redirect to a success page
            } else {
                response.sendRedirect("error.jsp"); // Redirect to an error page
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?message=Invalid amount format");
        }
    }
}
