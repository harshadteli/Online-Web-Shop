

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

 @WebServlet("/api/process-order")
public class ProcessOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        //1. Set UTF-8 encoding (required for Marathi/Hindi names)
        request.setCharacterEncoding("UTF-8");

        // 2.All data are captured
        String name = request.getParameter("customer_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");
        String amount = request.getParameter("total_amount");
        String method = request.getParameter("payment_method");

        // 3.Database Logic
        String dbURL = "jdbc:mysql://localhost:3306/watch_shop";
        String dbUser = "root";
        String dbPass = ""; // Enter your password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass)) {
                String sql = "INSERT INTO orders (customer_name, phone, address, city, pincode, total_amount, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, phone);
                pstmt.setString(3, address);
                pstmt.setString(4, city);
                pstmt.setString(5, pincode);
                pstmt.setString(6, amount);
                pstmt.setString(7, method);

                int result = pstmt.executeUpdate();
                if (result > 0) {
                    // Redirect to the confirmation page upon success
                    response.sendRedirect(request.getContextPath() + "/success.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.html");
        }
    }
}