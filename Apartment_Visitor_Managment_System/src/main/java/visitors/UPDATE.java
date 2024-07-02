package visitors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class UPDATE extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UPDATE() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String visitorIdStr = request.getParameter("visitorId");
        String phoneNoStr = request.getParameter("phone_no");
        String purpose = request.getParameter("purpose");
        String address = request.getParameter("address");
        String apartmentNoStr = request.getParameter("apartment_no");
        String entryDateStr = request.getParameter("entry_date");
        String exitDateStr = request.getParameter("exit_date");

        if (name == null || visitorIdStr == null || phoneNoStr == null || purpose == null || address == null || apartmentNoStr == null || entryDateStr == null || exitDateStr == null) {
            out.println("All fields are required.");
            return;
        }

        try {
            int visitorId = Integer.parseInt(visitorIdStr);
            int phoneNo = Integer.parseInt(phoneNoStr);
            int apartmentNo = Integer.parseInt(apartmentNoStr);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/visitor_management?characterEncoding=latin1", "root", "home");
            
            String query = "UPDATE visitors SET name=?, phone_no=?, purpose=?, address=?, apartment_number=? WHERE visitorId=?";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, name);
            ps.setInt(2, phoneNo);
            ps.setString(3, purpose);
            ps.setString(4, address);
            ps.setInt(5, apartmentNo);
            ps.setInt(6, visitorId);
            
            int rowsUpdated = ps.executeUpdate();
            
            if (rowsUpdated > 0) {
                out.println("Visitor updated successfully.");
            } else {
                out.println("No visitor found with the given ID.");
            }
            
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error occurred: " + e.getMessage());
        }
    }
}
