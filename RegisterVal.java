package org.tcs.Mywebsite;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class RegisterVal {
	static String Dmail;
	protected static String doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
  	  Connection con=null;
  	  PreparedStatement pstmt=null;
  	  ResultSet rs =null;
  	  String query="Select Gmail from btm.Website_users where Gmail=?";
  	 try {
			Class.forName("com.jdbc.mysql.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=admin");
			pstmt=con.prepareStatement(query);
	          String Rmail=req.getParameter("Rml");
	          
			// Set the data for the Palceholder
	          
			pstmt.setString(1, Rmail);
			
			//Execute the Query or Statement
			   rs=pstmt.executeQuery();
			   if(rs.next())
			   {
				  Dmail=rs.getString(1);
			   }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
  	       finally
  	       {
  	    	   if(rs!=null)
  	    	   {
  	    		   try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
  	    	   }
  	    	   if(pstmt!=null)
  	    	   {
  	    		   try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
  	    	   }
  	    	   if(con!=null)
  	    	   {
  	    		   try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
  	    	   }
  	       }
  	 System.out.println(Dmail);
	    return Dmail;
      }

}
