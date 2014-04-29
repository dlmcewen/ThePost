package thepost;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.ServletInputStream;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class AddItemServlet
 * Creates a DAO and inserts an Item into the database and saves the image to the images folder
 * @author Edward Killmeier
 */
//@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "/var/lib/tomcat6/webapps/ThePost/images";
	//private final String BACKUP_DIRECTORY = "/var/lib/tomcat6/webapps/imagesThePost";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItemServlet() {
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
	 * Receives all inputs and inserts object into Item table
	 * @author Edward Killmeier
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ThePostDAO dao = new ThePostDAO();
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("currentSessionUser");
		String type = "";
		String description = "";
		String imageLocation = "";
		double price = 0;
		String title = "";
    request.setAttribute("message", null);
		if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload( new DiskFileItemFactory()).parseRequest(request);
                String name = "";
                for(FileItem item : multiparts){
                	//read in inputs as a part
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
                   }
                }
                
               imageLocation = name;
               
               if(imageLocation.equalsIgnoreCase(String.valueOf(user.getId()))){
            	   imageLocation = "noImage.jpg";
               }
               
               boolean result = dao.addItem(user.getId(), price, type, description, imageLocation, title);
               if(result){
            	   //File uploaded successfully
            	   request.setAttribute("message", "Inserted object into database");
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
    
        request.getRequestDispatcher("UserHomePage.jsp").forward(request, response);
		
	}

}
