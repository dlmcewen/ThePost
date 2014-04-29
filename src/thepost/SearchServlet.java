package thepost;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class SearchServlet
 * Queries database based on user requests
 * @author Cameron Day
 */
//@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ThePostDAO dao = new ThePostDAO();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Receives requests parameters from user and queries database and forwards to ItemListing.jsp
	 * @author Cameron Day
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		
		String hasPic="no";
		if (request.getParameter("hasPic") != null){
			hasPic = request.getParameter("hasPic");
		}
		
		String sorting = "";
		if (request.getParameter("sorting") != null){
			sorting = (String)request.getParameter("sorting");
		}
		String sortCat = "date";
		String sortDir = "DESC";
		if (sorting.equals("Date: Oldest")){
			sortDir = "ASC";
		}
		else if (sorting.equals("Price: Highest First")){
			sortCat = "price";
		}
		else if (sorting.equals("Price: Lowest First")){
			sortCat = "price";
			sortDir = "ASC";
		}
		
		if (request.getParameter("category") != null && request.getParameter("searchString") != null){
			String category = (String)request.getParameter("category");
			String searchString = (String)request.getParameter("searchString");
			
			if (category.equals("All Categories") && searchString.equals("")){
				String action = (String)request.getParameter("action");
				if (action.equals("list")){
					try{
						int startIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
						int itemsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
						ArrayList<Item> allItems = dao.getAllItems(sortCat, sortDir, startIndex, itemsPerPage);
						int itemCount = dao.getItemCount(category, searchString, -1.0, -1.0, hasPic);
						Gson gson = new Gson();
						JsonElement element = gson.toJsonTree(allItems, new TypeToken<ArrayList<Item>>() {}.getType());
						JsonArray jsonArray = element.getAsJsonArray();
						String listItems = jsonArray.toString();
						listItems = "{\"Result\":\"OK\",\"Records\":"+listItems+",\"TotalRecordCount\":"+itemCount+"}";						
						response.setContentType("application/json");
						response.getWriter().print(listItems);
						
					}
					catch (Exception e){
						String error="{\"Result\":\"ERROR\",\"Message\":"+e.getStackTrace()+"}";
						response.getWriter().print(error);
					}
				}
			}
			else {
				try{
					int startIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
					int itemsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
					ArrayList<Item> searchItems = dao.getSearchItems(category, searchString, sortCat, sortDir, startIndex, itemsPerPage);
					int itemCount = dao.getItemCount(category, searchString, -1.0, -1.0, hasPic);
					Gson gson = new Gson();
					JsonElement element = gson.toJsonTree(searchItems, new TypeToken<ArrayList<Item>>() {}.getType());
					JsonArray jsonArray = element.getAsJsonArray();
					String listItems = jsonArray.toString();
					listItems = "{\"Result\":\"OK\",\"Records\":"+listItems+",\"TotalRecordCount\":"+itemCount+"}";
					response.setContentType("application/json");
					response.getWriter().print(listItems);
				}
				catch (Exception e)
				{
					String error="{\"Result\":\"ERROR\",\"Message\":"+e.getStackTrace()+"}";
					response.getWriter().print(error);
				}
		}
	}
		else if(request.getParameter("action")!=null){
			String action = (String)request.getParameter("action");
			if (action.equals("list")){
				try{
					int startIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
					int itemsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
					ArrayList<Item> allItems = dao.getAllItems(sortCat, sortDir, startIndex, itemsPerPage);
					int itemCount = dao.getItemCount(sortCat, "", -1.0, -1.0, hasPic);
					Gson gson = new Gson();
					JsonElement element = gson.toJsonTree(allItems, new TypeToken<ArrayList<Item>>() {}.getType());
					JsonArray jsonArray = element.getAsJsonArray();
					String listItems = jsonArray.toString();
					listItems = "{\"Result\":\"OK\",\"Records\":"+listItems+",\"TotalRecordCount\":"+itemCount+"}";
					response.setContentType("application/json");
					response.getWriter().print(listItems);
					
				}
				catch (Exception e){
					String error="{\"Result\":\"ERROR\",\"Message\":"+e.getStackTrace()+"}";
					response.getWriter().print(error);
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Receives new requests from user and forwards to ItemListing.jsp
	 * @author Cameron Day
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		
		String hasPic="no";
		if (request.getParameter("hasPic") != null){
			hasPic = request.getParameter("hasPic");
		}

		String category = (String)request.getParameter("category");
		String searchString = (String)request.getParameter("searchString");
		
		String sorting = "";
		if (request.getParameter("sorting") != null){
			sorting = (String)request.getParameter("sorting");
		}		
		String sortCat = "date";
		String sortDir = "DESC";
		if (sorting.equals("Date: Oldest")){
			sortDir = "ASC";
		}
		else if (sorting.equals("Price: Highest First")){
			sortCat = "price";
		}
		else if (sorting.equals("Price: Lowest First")){
			sortCat = "price";
			sortDir = "ASC";
		}

		if (request.getParameter("lowPrice") != null && request.getParameter("highPrice") != null && !request.getParameter("lowPrice").equals("") && !request.getParameter("highPrice").equals("")){
//			if (!category.equals("") && !searchString.equals("")){
			if (!category.equals("")){
				double lowPrice = Double.parseDouble(request.getParameter("lowPrice").replaceAll("[^\\d.]", ""));
				double highPrice = Double.parseDouble(request.getParameter("highPrice").replaceAll("[^\\d.]", ""));
				int startIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
				int itemsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
				ArrayList<Item> priceItems = dao.modifySearch(category, searchString, lowPrice, highPrice, sortCat, sortDir, startIndex, itemsPerPage, hasPic);
//				ArrayList<Item> priceItems = dao.getItemsOfPrice(category, searchString, lowPrice, highPrice, sortCat, sortDir, startIndex, itemsPerPage);
				int itemCount = dao.getItemCount(category, searchString, lowPrice, highPrice, hasPic);
				Gson gson = new Gson();
				JsonElement element = gson.toJsonTree(priceItems, new TypeToken<ArrayList<Item>>() {}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				String listItems = jsonArray.toString();
				listItems = "{\"Result\":\"OK\",\"Records\":"+listItems+",\"TotalRecordCount\":"+itemCount+"}";
				response.setContentType("application/json");
				response.getWriter().print(listItems);
			}
		}
		else if(hasPic.equals("yes")){
			try{
				int startIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
				int itemsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
				ArrayList<Item> searchItems = dao.modifySearch(category, searchString, -1.0, -1.0, sortCat, sortDir, startIndex, itemsPerPage, hasPic);
//				ArrayList<Item> searchItems = dao.getSearchItems(category, searchString, sortCat, sortDir, startIndex, itemsPerPage);
				int itemCount = dao.getItemCount(category, searchString, -1.0, -1.0, hasPic);
				Gson gson = new Gson();
				JsonElement element = gson.toJsonTree(searchItems, new TypeToken<ArrayList<Item>>() {}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				String listItems = jsonArray.toString();
				listItems = "{\"Result\":\"OK\",\"Records\":"+listItems+",\"TotalRecordCount\":"+itemCount+"}";
				response.setContentType("application/json");
				response.getWriter().print(listItems);
			}
			catch (Exception e)
			{
				String error="{\"Result\":\"ERROR\",\"Message\":"+e.getStackTrace()+"}";
				response.getWriter().print(error);
			}
		}
		else{
			doGet(request,response);
		}
	}

}
