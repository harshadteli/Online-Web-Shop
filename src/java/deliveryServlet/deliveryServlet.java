package deliveryServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/deliveryServlet")
public class deliveryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Getting data from a form
        String name = request.getParameter("customer_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");

        // २. XAMPP MySQL connection (Database: watch_shop)
        String url = "jdbc:mysql://localhost:3306/watch_shop";
        String user = "root";
        String password = ""; 

        try {
            // 3. Driver Loading
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            // 4. SQL Query (Table: orders)
            String sql = "INSERT INTO orders (name, phone, address, city, pincode) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, city);
            ps.setString(5, pincode);

            // 5. Execute
            int result = ps.executeUpdate();
            if(result > 0) {
                // Upon success, redirect to the payment page
                response.sendRedirect("payment.html");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Connection Error: " + e.getMessage());
        }
    }
}