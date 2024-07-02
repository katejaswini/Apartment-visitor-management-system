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
import java.sql.ResultSet;

import com.mysql.cj.jdbc.result.ResultSetMetaData;


public class LIST extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LIST() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/visitor_management?characterEncoding=latin1", "root", "home");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM visitors");

            out.print("<table width=80% border=1>");
            out.print("<caption>Visitor List:</caption>");

            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int total = rsmd.getColumnCount();
            out.print("<tr>");
            for (int i = 1; i <= total; i++) {
                out.print("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.print("</tr>");

            /* Printing result */
            while (rs.next()) {
                out.print("<tr><td>" + rs.getString("name") + "</td><td>" + rs.getInt("visitorId") + "</td><td>" + rs.getLong("phone_no") + "</td><td>"
                        + rs.getString("purpose") + "</td><td>" + rs.getString("address") + "</td><td>" + rs.getInt("apartment_number") + "</td><td>"
                        + rs.getDate("entry_date") + "</td><td>" + rs.getDate("exit_date") + "</td></tr>");
            }

            out.print("</table>");

            rs.close();
            ps.close();
            con.close();
            response.sendRedirect("MAIN.html");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error occurred: " + e.getMessage());
        }
    }

}
