

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		long accountnumber=Long.parseLong(request.getParameter("ac"));
		String name=request.getParameter("n");
		
		String pass=request.getParameter("psw");
		String pass2=request.getParameter("cpsw");
		double amount=Double.parseDouble(request.getParameter("a"));
		String address=request.getParameter("ad");
		long mobilenumber=Long.parseLong(request.getParameter("num"));
		String password=null;
		String status="active";
		if(pass.equals(pass2))
		{ 
			password=pass;
		}
		else
		{
			out.print("password mismatch");
		}

		Connection con=null;
		PreparedStatement ps=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","uday","welcome");
			ps=con.prepareStatement("insert into bank1 values(?,?,?,?,?,?,?)");
			ps.setLong(1, accountnumber);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setDouble(4, amount);
			ps.setString(5, address);
			ps.setLong(6, mobilenumber);
			ps.setString(7, status);
			
			int i=ps.executeUpdate();
			if(i>0) out.print("NEW USER REGISTRATION SUCCESSFULLY");
			else  out.print("NEW USER REGISTRATION FAILED");
			
		} catch (Exception e) {
			out.print(e);
		}
		finally {
			
			try {
				if(con!=null) con.close();
				if(ps!=null) ps.close();
			} catch (Exception e2) {
				out.print(e2);
			}
	}
		
	}

}
