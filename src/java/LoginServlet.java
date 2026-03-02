import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/watch_shop", "root", "");

            // 1. प्रथम ईमेल तपासा की युझर रजिस्टर आहे का?
            String checkQuery = "SELECT * FROM users WHERE email=?";
            PreparedStatement psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, email);
            ResultSet rsCheck = psCheck.executeQuery();

            if (!rsCheck.next()) {
                // जर ईमेल सापडला नाही तर रजिस्ट्रेशनचा मेसेज द्या
                out.println("<script type='text/javascript'>");
                out.println("alert('Account सापडले नाही! कृपया आधी Register करा.');");
                out.println("window.location.href='register.html';"); // रजिस्ट्रेशन पेजवर पाठवा
                out.println("</script>");
            } else {
                // २. जर ईमेल असेल, तर पासवर्ड बरोबर आहे का तपासा
                String dbPassword = rsCheck.getString("password");
                
                if (dbPassword.equals(password)) {
                    // लॉगिन यशस्वी
                    HttpSession session = request.getSession();
                    session.setAttribute("user", rsCheck.getString("username"));
                    response.sendRedirect("index.html"); // होम पेजवर पाठवा
                } else {
                    // चुकीचा पासवर्ड
                    out.println("<script type='text/javascript'>");
                    out.println("alert('पासवर्ड चुकीचा आहे!');");
                    out.println("window.history.back();");
                    out.println("</script>");
                }
            }
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}