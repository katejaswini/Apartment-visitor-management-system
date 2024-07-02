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

public class INSERT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public INSERT() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
        	int c=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/visitor_management?characterEncoding=latin1","root" ,"home");
            
            String query = "INSERT INTO visitors VALUES (?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            
    
		     ps.setString(1, request.getParameter("name"));
		     ps.setInt(2, Integer.valueOf(request.getParameter("visitorId")));
		     ps.setLong(3, Long.valueOf(request.getParameter("phone_no")));
		     ps.setString(4,request.getParameter("purpose"));
		     ps.setString(5,request.getParameter("address"));
		     ps.setInt(6, Integer.valueOf(request.getParameter("apartment_number")));
		     ps.setDate(7, java.sql.Date.valueOf(request.getParameter("entry_date")));
	         ps.setDate(8, java.sql.Date.valueOf(request.getParameter("exit_date")));
	            
            // Execute the statement
            c=ps.executeUpdate();
            
            // Close resources
            ps.close();
            conn.close();
            if (c!=0) {
            response.sendRedirect("MAIN.html");
		    }
            
		
        } catch (Exception e) {
        	e.printStackTrace();
            response.getWriter().println("Error occurred: " + e.getMessage());
            } 
        }
    }
