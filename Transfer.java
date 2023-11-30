

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		 long accountnumber=Long.parseLong(request.getParameter("ac"));
		 String name=request.getParameter("n");
		 String password=request.getParameter("psw");
		 long target=Long.parseLong(request.getParameter("ac2"));
		 double am=Double.parseDouble(request.getParameter("num"));
		 Connection con=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			
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
					 if(balance>am)
					 {	
						 long account=target;
						 ps=con.prepareStatement("select amount from bank1 where accountnumber=?");
						 ps.setLong(1, account);
						 rs=ps.executeQuery();
						 if(rs.next())
						 {
							 amount=rs.getInt(1);
							 amount+=am;
							 ps=con.prepareStatement("update bank1 set amount=? where accountnumber=?");
							 ps.setInt(1, (int) amount);
							 ps.setLong(2, account);
							 int i=ps.executeUpdate();
							 if(i>0) out.print("transfer success");
							 else out.print("transfer failed");
							  ps=con.prepareStatement("select amount from bank1 where accountnumber=?");
							  ps.setLong(1, accountnumber);
							  rs=ps.executeQuery();
							  if(rs.next())
							  {
								  balance=rs.getDouble(1);
								  double amount1=balance-am;
								  ps=con.prepareStatement("update bank1 set amount=? where accountnumber=?");
								  ps.setDouble(1, amount1);
								  ps.setLong(2, accountnumber);
								  int i1=ps.executeUpdate();
								  if(i1>0) out.print(i1+"<br>"+"your account balance is updated");
								  else out.print(i1+"your account balance is not updated");
							  }
							  else out.print("check once it fail");
							      
							     
							
							 
							 
						 }
						 else out.print("details mismatch");
					 }
					 else out.print("transfer amount exceeds the withdraw amount");
	
					 }
						
					 
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
