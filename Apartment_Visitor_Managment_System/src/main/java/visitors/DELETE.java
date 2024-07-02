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

public class DELETE extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DELETE() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		        int visitorId = Integer.parseInt(request.getParameter("visitorId"));
		        
		        
		        try {
		        	
		        	Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/visitor_management?characterEncoding=latin1","root" ,"home");
		            String query = "DELETE FROM visitors WHERE visitorId = ?";
		            PreparedStatement ps = conn.prepareStatement(query);
		            ps.setInt(1, visitorId);
		            ps.executeUpdate();
		            ps.close();
		            conn.close();
		            PrintWriter out = response.getWriter();
		         
		            response.sendRedirect("MAIN.html");
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }

	}
