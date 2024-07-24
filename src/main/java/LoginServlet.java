import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Retrieve parameters from the request
        String username = request.getParameter("fullname");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Create an instance of LoginDAO
        LoginDAO loginDAO = new LoginDAO();

        try {
            // Validate credentials
            if (loginDAO.validateUser(username, password, role)) {
                // Create a session for the user
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                // Redirect to a success page or user's dashboard
                if ("admin".equals(role)) {
                    response.sendRedirect("adminHome.jsp");
                } else {
                    response.sendRedirect("customerHome.jsp");
                }
            } else {
                // Set an error message and redirect to the login page
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log error details for debugging
            request.setAttribute("error", "An unexpected error occurred. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
