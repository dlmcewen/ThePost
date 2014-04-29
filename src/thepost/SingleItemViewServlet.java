package thepost;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SingleItemViewServlet
 */
//@WebServlet("/SingleItemViewServlet")
public class SingleItemViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleItemViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Single View
		HttpSession session = request.getSession(true);
		ThePostDAO dao = new ThePostDAO();
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		Item item = dao.getSingleItem(itemId);
		String email = dao.getUsername(item.getUserId());
		request.setAttribute("email", email);
		session.setAttribute("singleItemView", item);
		request.getRequestDispatcher("SingleItemView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Update Item
		HttpSession session = request.getSession(true);
		ThePostDAO dao = new ThePostDAO();
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		Item item = dao.getSingleItem(itemId);
		session.setAttribute("updateItem", item);
		request.getRequestDispatcher("UpdateItem.jsp").forward(request, response);
	}

}
