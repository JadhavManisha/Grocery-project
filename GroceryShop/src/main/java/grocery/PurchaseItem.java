package grocery;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/purchaseitem")
public class PurchaseItem extends HttpServlet{
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
		String qty=req.getParameter("qty");
		int itemId=Integer.parseInt(id);
		int itemQty=Integer.parseInt(qty);
				
//		declare resource
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		PrintWriter pw=resp.getWriter();
		
//		create query
		String query="select stock,price from grocery_item where id=?";
		String query1="update grocery_item set stock=? where id=?";
		try {
		pstmt=con.prepareStatement(query);
	    pstmt.setInt(1,itemId);
	    rs=pstmt.executeQuery();
	    if(rs.next()) {
	    	int dbStock=rs.getInt(1);
	    	double dbPrice=rs.getDouble(2);
	    	if( itemQty<=dbStock) {
	    		double total=itemQty*dbPrice;
	    		pw.print("<h1>TOTAL AMOUNT IS:"+total+"</h1>");
	    		pstmt=con.prepareStatement(query1);
	    		pstmt.setInt(1, dbStock- itemQty);
	    		pstmt.setInt(2, itemId);
	    		pstmt.executeUpdate();
	    	}else {
	    		pw.print("<h1>ITEM OUT OF STOCK</h1>");
	    	}
	    	{
	    		pw.print("<h1>ITEM NOT FOUND</h1>");
	    	}
	    }
		}catch(SQLException e) {
			e.printStackTrace();
		}
 
	}

}
