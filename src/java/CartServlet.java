import java.io.IOException;
import java.io.PrintWriter;
// javax ऐवजी jakarta वापरा
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/cart")
public class CartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // JSON response settings
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // CORS settings
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        
        // JSON Data
        String jsonResponse = "[" +
            "{\"name\": \"Grand Classic Silver\", \"price\": 1200.00, \"quantity\": 1}," +
            "{\"name\": \"Midnight Leather\", \"price\": 850.00, \"quantity\": 2}" +
            "]";
            
        out.print(jsonResponse);
        out.flush();
    }
}