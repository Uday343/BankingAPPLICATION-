

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
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
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
		 String password=request.getParameter("psw");
		 Connection con=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			ResultSetMetaData rsmd=null;
			
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","uday","welcome");
				
				ps=con.prepareStatement("select * from bank1 where accountnumber=? and name=? and password=?");
				ps.setLong(1, accountnumber);
				ps.setString(2, name);
				ps.setString(3, password);
				rs=ps.executeQuery();
				rsmd=rs.getMetaData();
				out.print("<table border='1'>");
				int n=rsmd.getColumnCount();
				for(int i=1;i<=n;i++)
				{
					out.print("<td><font color=blue size=3>"+rsmd.getColumnName(i));
				}
				out.print("<tr>");
				while(rs.next())
				{
					for(int i=1;i<=n;i++)
					{
						out.print("<td>"+rs.getString(i));
						
					}
					out.print("<tr>");
				}
				out.print("</table");
			}
			catch (Exception e) {
				out.print(e);
			}
			finally {
				try {
					con.close();
					ps.close();
					rs.close();
					((PrintWriter) rsmd).close();
					
				} catch (Exception e2) {
					out.print(e2);
					
				}
			}
	}

}
