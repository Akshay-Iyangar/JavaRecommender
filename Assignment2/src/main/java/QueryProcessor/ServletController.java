package QueryProcessor;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.queryparser.classic.ParseException;
 
/**
 * It takes the request(symptoms of disease) from User and gives Medicine Advise
 *
 * @author Examples Of Java
 *
 */


public class ServletController extends HttpServlet {
 
 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
 throws ServletException, IOException {


Queryoutput qp = new Queryoutput();
try {
	if(!req.getParameter("dynamicquery").isEmpty()){
		String query_input=req.getParameter("dynamicquery");
		List<String>outputfinal=qp.queryprocessor(query_input);
		req.setAttribute("query_str",query_input);
		req.setAttribute("url",outputfinal);
	}
	else if(req.getParameter("static_query")!=null)
	{
		int index_position = Integer.parseInt(req.getParameter("static_query"));
		ExcelReader excel = new ExcelReader();
		String query_url=excel.readexcel(index_position);
		List<String>outputfinal=qp.queryprocessor(query_url);
		req.setAttribute("query_str",query_url);
		req.setAttribute("url",outputfinal);
	}
/**
 * get the requestDispatcher and forward the request/response to
 * appropriate JSP page
 */
RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
rd.forward(req, resp);
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


 }
 }
