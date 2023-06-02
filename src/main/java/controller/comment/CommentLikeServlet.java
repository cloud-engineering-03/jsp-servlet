package controller.comment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.CommentService;

@WebServlet("/comment/like")
public class CommentLikeServlet extends HttpServlet {
    
	private CommentService service = new CommentService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		
		try {
			service.doLike(getServletInfo(), 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
