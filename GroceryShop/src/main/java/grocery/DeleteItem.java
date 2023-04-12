package grocery;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/deleteitem")
public class DeleteItem extends HttpServlet{
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		fetch the value from html
		String id=req.getParameter("id");
		String name=req.getParameter("name");
		String stock=req.getParameter("stock");
		String price=req.getParameter("price");
		
//		parse values
		int id1=Integer.parseInt(id);
		int stock1=Integer.parseInt(stock);
		double price1=Double.parseDouble(price);
		
		
		
//		declare resource
		PreparedStatement pstmt=null;
		
//		create query
		String query="delete from grocery_item where id=?";
		try {
		pstmt=con.prepareStatement(query);
	    pstmt.setInt(1, id1);
	   
	    int count=pstmt.executeUpdate();
	    PrintWriter pw=resp.getWriter();
	    pw.print("<h1>"+count+" RECORD DELETE SUCCESSFULLY </h>");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
