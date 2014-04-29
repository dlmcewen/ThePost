package thepost;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InitialSearchServlet
 * Queries database based on request parameters
 * @author Cameron Day
 */
//@WebServlet("/InitialSearchServlet")
public class InitialSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitialSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Receives parameters and forwards query results to ItemListings.jsp
	 * @author Cameron Day
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		
		if (request.getParameter("itemCategory")!=null && request.getParameter("searchItem")!=null){

			String category = (String)request.getParameter("itemCategory");
			if(category.equals("unknown")){category = "All Categories";}
			String searchString = (String)request.getParameter("searchItem");
			String sortingString = "Date: Newest";
			RequestDispatcher dispatcher = context.getRequestDispatcher("/ItemListings.jsp");
			request.setAttribute("itemCategory", category);
			request.setAttribute("searchItem", searchString);
			request.setAttribute("sortingCat", sortingString);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
