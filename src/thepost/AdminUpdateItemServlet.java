package thepost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class AdminUpdateItemServlet
 */
//@WebServlet("/AdminUpdateItemServlet")
public class AdminUpdateItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "/var/lib/tomcat6/webapps/ThePost/images";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUpdateItemServlet() {
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
		Item updateItem = dao.getSingleItem(itemId);
		int userId = updateItem.getUserId();
		User admin = (User)session.getAttribute("currentSessionUser");
		admin = dao.getAllUsers(admin);
		ArrayList<User> users = admin.getUserList();
		String userName = "";
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getId() == userId){
				userName = users.get(i).getfName() + " " + users.get(i).getlName() + "'s";
			}
		}
		
		session.setAttribute("adminUpdateItem", updateItem);
		session.setAttribute("userNameUpdate", userName);
		request.getRequestDispatcher("AdminUpdateItem.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ThePostDAO dao = new ThePostDAO();
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("currentSessionUser");
		String type = "";
		String description = "";
		String imageLocation = "";
		double price = 0;
		String title = "";
		String pic = "";
		int itemId = 0;
		if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload( new DiskFileItemFactory()).parseRequest(request);
                String name = "";
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        name = user.getId() + new File(item.getName()).getName();
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                        //item.write( new File(BACKUP_DIRECTORY + File.separator + name));
                    }
                    else{
                   	 // Get the field name
                       String fieldName = item.getFieldName();
                       // Get the field value
                       String value = item.getString();
                       if(fieldName.equalsIgnoreCase("type")){
                    	   type = value;
                       }
                       else if(fieldName.equalsIgnoreCase("price")){
                    	   price = Double.parseDouble(value);
                       }
                       else if(fieldName.equalsIgnoreCase("description")){
                    	   description = value;
                       }
					   else if(fieldName.equalsIgnoreCase("title")){
						   title = value;
					   }
					   else if(fieldName.equalsIgnoreCase("itemId")){
						   itemId = Integer.parseInt(value);
					   }
					   else if(fieldName.equalsIgnoreCase("picture")){
						   pic = value;
					   }
                   }
                }
               
               Item oldItem = dao.getSingleItem(itemId);
               
               if(pic.equalsIgnoreCase("original")){
            	   String oldImage = oldItem.getImageLocation();
            	   imageLocation = oldImage.substring(7);
            	   request.setAttribute("message", imageLocation);
               }
               else if(pic.equalsIgnoreCase("new")){
            	   imageLocation = name;
               }
               
               if(imageLocation.equalsIgnoreCase(String.valueOf(user.getId()))){
            	   imageLocation = "noImage.jpg";
               }
               
               boolean result = dao.updateItem(price, type, description, imageLocation, title, itemId);
               if(result){
            	   //File uploaded successfully
            	   request.setAttribute("message", "Updated object in database");
            	   session.setAttribute("updateItem", null);
            	   user = dao.getUserItems(user);
            	   session.setAttribute("currentSessionUser", user);
               }
               else{
            	 //File uploaded successfully
                   request.setAttribute("message", "Failed to insert object into database" + imageLocation);
               }
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
         
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
        }
    
        request.getRequestDispatcher("ManageItems.jsp").forward(request, response);
	}

}
