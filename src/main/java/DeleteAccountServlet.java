import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        DeleteAccountDAO dao = new DeleteAccountDAO();
        boolean isDeleted = dao.deleteAccount(accountNumber);

        if (isDeleted) {
            response.sendRedirect("success.jsp"); // Redirect to a success page
        } else {
            response.sendRedirect("error.jsp"); // Redirect to an error page
        }
    }
}
