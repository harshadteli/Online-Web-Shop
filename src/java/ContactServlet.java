import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieving data
        String name = request.getParameter("fullname");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        // Database Details
        String dbUrl = "jdbc:mysql://localhost:3306/watch_shop";
        String dbUser = "root"; 
        String dbPass = ""; // Enter your MySQL password here
        Connection conn = null;

        try {
            // 1. Loading the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establishing a connection
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            
            // ३. Creating the SQL query
            String sql = "INSERT INTO contact_inquiries (fullname, email, subject, message) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, subject);
            ps.setString(4, message);

            // 4. Running
            int row = ps.executeUpdate();
            
            if (row > 0) {
                response.sendRedirect("contact.html?status=success");
            } else {
                response.sendRedirect("contact.html?status=error");
            }

        } catch (Exception e) {
            e.printStackTrace(); // This will show an error on your console
            response.sendRedirect("contact.html?status=error");
        } finally {
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}