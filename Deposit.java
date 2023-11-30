

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Deposit
 */
@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "resource" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		 long accountnumber=Long.parseLong(request.getParameter("ac"));
		 String name=request.getParameter("n");
		 String password=request.getParameter("psw");
		 double dm=Double.parseDouble(request.getParameter("num"));
		 Connection con=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			@SuppressWarnings("unused")
			ResultSetMetaData rsmd=null;
			
			double balance;
			double amount;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","uday","welcome");
				ps=con.prepareStatement("select amount from bank1 where accountnumber=? and name=? and password=?");
				ps.setLong(1, accountnumber);
				ps.setString(2, name);
				ps.setString(3, password);
				rs=ps.executeQuery();
				if(rs.next())
				{
					 balance=rs.getInt(1);

						amount=balance+dm;
						ps=con.prepareStatement("update bank1 set amount=? where accountnumber=?");
						ps.setDouble(1, amount);
						ps.setLong(2, accountnumber);
						int i=ps.executeUpdate();
					   if(i>0)
						   {
						   	out.print("deposited successfully"+"<br>");
						   out.print("available balance is "+amount+" rupees");
						   }
					   else out.print("deposite unsuccessfull");
				}  
				else out.print("invalid details");	   
					}
					
				catch (Exception e) {
					out.print(e);
				}
				finally {
					try {
						con.close();
						ps.close();
						rs.close();
						
						
					} catch (Exception e2) {
						out.print(e2);
						
					}
				}
				
					
					
				
	}

}
