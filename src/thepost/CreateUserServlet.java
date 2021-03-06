package thepost;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUserServlet
 * Inserts a User object into the User database
 * @author Edward Killmeier
 */
//@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Receives inputs and creates a User object and ensures that object is not already in the table
	 * @author Edward Killmeier
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ThePostDAO dao = new ThePostDAO();
		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		
		request.setAttribute("message", null);

		String uga = "@uga.edu";
		
		String subUserName = userName.substring(userName.length() - 8, userName.length());
		
		if (!password.equals(repassword))	{
			request.setAttribute("message", "The passwords do not match.");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		else if(subUserName.equalsIgnoreCase(uga)){
			try {
				if(dao.insertUser(fName, lName, userName, password)){
					request.setAttribute("message", "Account was created, please login.");
					request.getRequestDispatcher("Login.jsp").forward(request, response);
				}
				else{
					request.setAttribute("message", "Username already exists.");
					request.getRequestDispatcher("Login.jsp").forward(request, response);
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			request.setAttribute("message", "Sorry, you must enter a valid UGA email");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		
	}

}