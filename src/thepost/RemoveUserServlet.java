package thepost;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveUserServlet
 */
//@WebServlet("/RemoveUserServlet")
public class RemoveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveUserServlet() {
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
		User user = (User)session.getAttribute("removeUser");
		int userId = user.getId();
		ThePostDAO dao = new ThePostDAO();
		if(dao.removeUser(userId)){
			request.setAttribute("message", "Successfully Removed User");
			User admin = (User)session.getAttribute("currentSessionUser");
			admin.clearUsers();
			admin = dao.getAllUsers(admin);
			request.getRequestDispatcher("ManageUsers.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "Did NOT Successfully Remove " + user.getfName() + " " + user.getlName());
			request.getRequestDispatcher("ManageUsers.jsp").forward(request, response);
		}
	}

}
