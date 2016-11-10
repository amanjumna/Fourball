package co.za.oneup.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="FourballServlet", 
			urlPatterns={"/FourballServlet"})
public class FourballServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Hashtable<String, String> playerDetails = new Hashtable<String, String>();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws IOException{
		Map<String, String[]> params = request.getParameterMap();
		String operation = "";
		// determine operation
		for(Map.Entry<String, String[]> current : params.entrySet()) {
			if (current.getKey().equals("OPERATION"))
				operation = current.getValue()[0];
		}
		
		// registerClient
		if (operation.equals("REGISTERCLIENT")) {
			String deviceID = "";
			String playerName = "";
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("DEVICEID")) {
					deviceID = current.getValue()[0];					
				}
				if (current.getKey().equals("PLAYERNAME")) {
					playerName = current.getValue()[0];					
				}
			}
			UUID uuid = UUID.randomUUID();
			playerDetails.put(uuid.toString(), deviceID+"|"+playerName);
			
			
		}
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Hello Servlet Get yay</h1>");
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Hello Servlet Post</h1>");
		out.println("</body>");
		out.println("</html>");
	}	
}
