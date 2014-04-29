package thepost;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ManageUsersServlet
 */
//@WebServlet("/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ThePostDAO dao = new ThePostDAO();
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("currentSessionUser");
		user.clearUsers();
		user = dao.getAllUsers(user);
		session.setAttribute("currentSessionUser", user);
		request.getRequestDispatcher("ManageUsers.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int userId = Integer.parseInt(request.getParameter("userId"));
		String option = request.getParameter("updateSelection");
		ThePostDAO dao = new ThePostDAO();
		if(option.equalsIgnoreCase("remove")){
			//Send to RemoveUser.jsp
			User user = dao.getSingleUser(userId);
			user = dao.getUserItems(user);
			session.setAttribute("removeUser", user);
			request.getRequestDispatcher("RemoveUser.jsp").forward(request, response);
		}
		
		else if(option.equalsIgnoreCase("reset")){
			//Send to ResetUserPassword.jsp
			User user = dao.getSingleUser(userId);
			session.setAttribute("resetUser", user);
			request.getRequestDispatcher("ResetUserPassword.jsp").forward(request, response);
		}
		
	}

}
