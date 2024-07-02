package visitors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/SEARCH")
public class SEARCH extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SEARCH() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String dateParam = request.getParameter("entry_date");
        if (dateParam == null || dateParam.isEmpty()) {
            out.println("<p>Entry date is required</p>");
            return;
        }
        
        Date entry_date;
        try {
            entry_date = Date.valueOf(dateParam);
        } catch (IllegalArgumentException e) {
            out.println("<p>Invalid date format</p>");
            return;
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/visitor_management?characterEncoding=latin1", "root", "home");

            String query = "SELECT * FROM visitors WHERE entry_date = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, entry_date);
            ResultSet rs = ps.executeQuery();
            
            boolean recordsFound = false;
            out.println("<h2>Visitor Details</h2>");
            out.println("<table border='1' style='width:100%; border-collapse:collapse; text-align:left;'>");
            out.println("<tr><th>Name</th><th>VisitorId</th><th>Phone</th><th>Purpose</th><th>Address</th><th>Apartment Number</th><th>Entry Date</th><th>Exit Date</th></tr>");
            while (rs.next()) {
                recordsFound = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("visitorId") + "</td>");
                out.println("<td>" + rs.getLong("phone_no") + "</td>");
                out.println("<td>" + rs.getString("purpose") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");
                out.println("<td>" + rs.getInt("apartment_number") + "</td>");
                out.println("<td>" + rs.getDate("entry_date") + "</td>");
                out.println("<td>" + rs.getDate("exit_date") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            if (!recordsFound) {
                out.println("<p>No visitors found for the given entry date</p>");
            }
            
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error occurred: " + e.getMessage());
        }
    }
}
