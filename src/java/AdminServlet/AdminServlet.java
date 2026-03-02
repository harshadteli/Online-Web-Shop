import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/admin-orders")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // JSON स्ट्रिंग मॅन्युअली तयार करण्यासाठी StringBuilder वापरू
        StringBuilder json = new StringBuilder();
        json.append("["); 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/watch_shop", "root", "");
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM payments ORDER BY payment_date DESC");
            
            boolean first = true;
            while(rs.next()) {
                if (!first) json.append(","); // दोन ऑब्जेक्ट्समध्ये कॉमा देण्यासाठी
                
                json.append("{")
                    .append("\"id\":").append(rs.getInt("id")).append(",")
                    .append("\"name\":\"").append(rs.getString("customer_name")).append("\",")
                    .append("\"amount\":").append(rs.getDouble("amount")).append(",")
                    .append("\"txid\":\"").append(rs.getString("transaction_id")).append("\",")
                    .append("\"date\":\"").append(rs.getString("payment_date")).append("\"")
                    .append("}");
                
                first = false;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        out.print(json.toString()); // तयार झालेली JSON स्ट्रिंग प्रिंट करा
        out.flush();
    }
}