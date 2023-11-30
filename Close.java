

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
 * Servlet implementation class Close
 */
@WebServlet("/Close")
public class Close extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Close() {
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
		 String status="deactive";
		 Connection con=null;
			PreparedStatement ps=null;
			
			
			
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","uday","welcome");
				ps=con.prepareStatement(" update bank1 set status=? where accountnumber=? and name=? and password=?");
				ps.setString(1, status);
				ps.setLong(2, accountnumber);
				ps.setString(3, name);
				ps.setString(4, password);
				int i=ps.executeUpdate();
				if(i>0) out.print("your account is closed");
				else out.print("try again");
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					con.close();
					ps.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
	}

}
