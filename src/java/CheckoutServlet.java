import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Check the URL mapping carefully—this exact name must match the one in cart.html
@WebServlet("/api/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 1. Order processing logic (e.g., making an entry in the database)
            System.out.println("Processing SWAMI Order...");

            // 2. Redirection Logic
            // Success Message(success.html) For sending to:
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/success.html");

        } catch (Exception e) {
            e.printStackTrace();
            // In case of an error, redirect back to the cart page.
            response.sendRedirect("Cart.html?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // If someone accesses the URL directly, redirect them back to the cart
        response.sendRedirect("Cart.html");
    }
}