package controller.post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.NotExistException;
import model.dto.PostDTO;
import service.PostService;

@WebServlet("/post/list")
public class PostListServlet extends HttpServlet {
	
	private PostService service = new PostService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			ArrayList<PostDTO> list =service.readAll();
			map.put("list", list);
			map.put("status",1);
		} catch (SQLException | NotExistException e) {
			map.put("status",0);
			e.printStackTrace();
		}
		
		response.getWriter().print(mapper.writeValueAsString(map));
	}

}
