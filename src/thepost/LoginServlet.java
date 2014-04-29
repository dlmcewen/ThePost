package thepost;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 * Receives inputs and checks to make sure the User is in the User table
 * @author Edward Killmeier
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	 * Receives request parameters and checks if user is in the User table and password matches
	 * then sets currentSessionUser attribute to that user
	 * @author Edward Killmeier
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ThePostDAO dao = new ThePostDAO();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		try {
			User checkUser = new User(userName, password);
			User currentUser = dao.login(checkUser);
			HttpSession session = request.getSession(true);
			
			if(currentUser.getId() == -1){
				request.setAttribute("message", "Incorrect username and password, please try again.");
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
			else if(currentUser.getIsAdmin()){
				currentUser = dao.getUserItems(currentUser);
				session.setAttribute("currentSessionUser",currentUser);
				request.getRequestDispatcher("UserHomePage.jsp").forward(request, response);
			}
			else if(!currentUser.getIsAdmin()){
				currentUser = dao.getUserItems(currentUser);
				session.setAttribute("currentSessionUser",currentUser);
				request.getRequestDispatcher("UserHomePage.jsp").forward(request, response);
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
