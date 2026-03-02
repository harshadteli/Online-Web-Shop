
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/api/process-payment")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8"); 

        // 1. Get parameters
        String name = request.getParameter("customer_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");
        String amount = request.getParameter("total_amount");
        String method = request.getParameter("payment_method");

        // 2. JDBC Connection and Database INSERT operation
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/watch_shop", "root", "")) {
                String query = "INSERT INTO orders (name, phone, address, city, pincode, amount, method) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, phone);
                pstmt.setString(3, address);
                pstmt.setString(4, city);
                pstmt.setString(5, pincode);
                pstmt.setString(6, amount);
                pstmt.setString(7, method);
                
                pstmt.executeUpdate();
                response.sendRedirect(request.getContextPath() + "/success.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}