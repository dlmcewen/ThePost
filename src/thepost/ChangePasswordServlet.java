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
 * Servlet implementation class ChangePasswordServlet
 * Allows user to change password
 * @author Edward Killmeier
 */
//@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
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
	 * Receives new password and checks it matches and then inputs it into the database
	 * @author Edward Killmeier
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ThePostDAO dao = new ThePostDAO();
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		HttpSession session = request.getSession(true);
		
		User user = (User) session.getAttribute("currentSessionUser");
		
		
		if(user.getIsAdmin()){
			if(password1.contentEquals(password2)){
				try {
					user.setPassword(password1);
					dao.updatePassword(user);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("message", "Password updated.");
				request.getRequestDispatcher("UserHomePage.jsp").forward(request, response);
			}
			else{
				request.setAttribute("message", "Passwords did not match.");
				request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
			}
		}
		
		else if(!user.getIsAdmin()){
			if(password1.contentEquals(password2)){
				try {
					user.setPassword(password1);
					dao.updatePassword(user);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("message", "Password updated.");
				request.getRequestDispatcher("UserHomePage.jsp").forward(request, response);
			}
			else{
				request.setAttribute("message", "Passwords did not match.");
				request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
			}
		}
		
	}

}
