package grocery;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/displayitem")
public class DisplayItem extends HttpServlet{
	Connection con;
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/1ejm9/?","root","sql@123");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		declare resource
		Statement stmt=null;
		ResultSet rs=null;
		PrintWriter pw=resp.getWriter();
		
//		create query
		String query="select * from grocery_item";
		try {
		stmt=con.createStatement();
	   rs=stmt.executeQuery(query);
	   pw.print("<table border='2'>");
	   pw.print("<tr>");
	   pw.print("<th>ITEM ID</th>");
	   pw.print("<th>ITEM NAME</th>");
	   pw.print("<th>ITEM STOCK</th>");
	   pw.print("<th>ITEM PRICE</th>");
	   pw.print("</tr>");
	   while(rs.next()) {
		   pw.print("<tr>");
		   pw.print("<td>"+rs.getInt(1)+"</td>");
		   pw.print("<td>"+rs.getString(2)+"</td>");
		   pw.print("<td>"+rs.getInt(3)+"</td>");
		   pw.print("<td>"+rs.getDouble(4)+"<td>");
		   pw.print("</tr>");
		   
	   }
		pw.print("</table>");  
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
