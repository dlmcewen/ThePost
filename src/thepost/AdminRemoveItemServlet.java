package thepost;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminRemoveItemServlet
 */
//@WebServlet("/AdminRemoveItemServlet")
public class AdminRemoveItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRemoveItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ThePostDAO dao = new ThePostDAO();
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		Item removeItem = dao.getSingleItem(itemId);
		session.setAttribute("adminRemoveItem", removeItem);
		request.getRequestDispatcher("AdminRemoveItem.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ThePostDAO dao = new ThePostDAO();
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		dao.removeItem(itemId);
		request.setAttribute("message", "Removed item from database");
		request.getRequestDispatcher("ManageItems.jsp").forward(request, response);
	}

}
