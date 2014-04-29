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
 * Servlet implementation class ResetUserPasswordServlet
 */
//@WebServlet("/ResetUserPasswordServlet")
public class ResetUserPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetUserPasswordServlet() {
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
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ThePostDAO dao = new ThePostDAO();
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");		
		User user = (User) session.getAttribute("resetUser");
		
		if(password1.contentEquals(password2)){
			try {
				user.setPassword(password1);
				dao.updatePassword(user);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			request.setAttribute("message", "Password updated.");
			request.getRequestDispatcher("ManageUsers.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "The passwords do not match.");
			request.getRequestDispatcher("ResetUserPassword.jsp").forward(request, response);
		}
		
	}

}
