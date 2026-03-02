import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 1. HTML Form se data nikalna
        String user = request.getParameter("username");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");

        // 2. Password Match check karna
        if (!pass.equals(confirmPass)) {
            out.println("<script>alert('Error: Passwords do not match!'); window.history.back();</script>");
            return;
        }

        // 3. Database mein save karna
        try {
            // MySQL Driver load karna
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connection establish karna (Database name: watch_shop)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/watch_shop", "root", "");

            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, email);
            ps.setString(3, pass);

            int result = ps.executeUpdate();

            if (result > 0) {
                // Success message
                out.println("<div style='text-align:center; margin-top:50px; font-family:Arial;'>");
                out.println("<h2 style='color:#d4af37;'>Registration Successful!</h2>");
                out.println("<p>Welcome to SWAMI exclusive circle.</p>");
                out.println("<a href='Login.html' style='color:#0f0f0f; font-weight:bold;'>Click here to Login</a>");
                out.println("</div>");
            }

            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            out.println("<script>alert('Email already registered!'); window.history.back();</script>");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}