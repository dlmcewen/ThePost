package thepost;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveItemServlet
 * Creates a DAO to interact with database to remove and Item
 * @author Edward Killmeier
 */
//@WebServlet("/RemoveItemServlet")
public class RemoveItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Gets Item id from request parameter and removes that item from the database
	 * @author Edward Killmeier
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ThePostDAO dao = new ThePostDAO();
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		if(itemId < 0){
			request.getRequestDispatcher("RemoveItem.jsp").forward(request, response);
		}
		else{
			dao.removeItem(itemId);
			User user = (User) session.getAttribute("currentSessionUser");
			user.removeItem(itemId);
			session.setAttribute("currentSessionUser", user);
			request.setAttribute("message", "Listing was removed");
			request.getRequestDispatcher("RemoveItem.jsp").forward(request, response);
		}
	}

}
