package org.tcs.Mywebsite;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
public class MyWebsite extends HttpServlet {
	static String Rname;
	static String Rmail;
	static String Rpassword;
	static String Lmail;
	static String Lpassword;
	static String Dname;
	static String Dmail=null;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// Fetch the UI/Form data

		Rname = req.getParameter("Rnm");// User name
		Rmail = req.getParameter("Rml");// Mail ID
		Rpassword = req.getParameter("Rps");// password

		// Persistence Logic

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=admin");

			PrintWriter out = resp.getWriter();
			String query2 = "insert into btm.Website_users values(?,?,?)";
			pstmt = con.prepareStatement(query2);

			// Setting data to the "query2" placegholders

			pstmt.setString(1, Rname);
			pstmt.setString(2, Rmail);
			pstmt.setString(3, Rpassword);

			// Presentation Logic
              Dmail=RegisterVal.doGet(req, resp);
              //System.out.println(Dmail);
			if (Dmail!= null) {
				// Execute
				pstmt.executeUpdate();

				out.println("<html><center><body bgcolor='orange'>" + "<h1>Hello " + Rmail
						+ " is Alredy exist, Please LogIn :)" + "</h1>" + "</body></center></html>");
				out.close();

			} else if(Dmail==null){
				// Execute
				pstmt.executeUpdate();

				out.println("<html><center><body bgcolor='orange'>" + "<h1>Hello " + Rname
						+ " Your Registration was succesfull :)" + "</h1>" + "</body></center></html>");
				out.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Lmail = req.getParameter("ml");
		Lpassword = req.getParameter("ps");
		// Persistence Logic

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from btm.Website_users where Gmail=? and password=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=admin");
			pstmt = con.prepareStatement(query);

			// set data to the Place holder

			pstmt.setString(1, Lmail);
			pstmt.setString(2, Lpassword);

			// Execute the Sql query or Statement

			rs = pstmt.executeQuery();

			// Getting the Data from the Data Base

			if (rs.next()) {
				Dname = rs.getString(1);

				// Presentation Logic

				PrintWriter out = resp.getWriter();
				out.println("<html><center><body bgcolor='orange'>" + "<h1>Hello " + Dname + " Welcome to my website :)"
						+ "</h1>" + "</body></center></html>");
				out.close();
			} else {
				// Presentation Logic

				PrintWriter out = resp.getWriter();
				out.println("<html><center><body bgcolor='orange'>" + "<h1>Invalid Login Credentials / Please Register"
						+ "</h1>" + "</body></center></html>");
				out.close();
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
