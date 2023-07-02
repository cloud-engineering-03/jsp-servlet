package controller.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import model.dto.LikeDTO;
import service.CommentService;

@WebServlet("/comment/like")
public class CommentLikeServlet extends HttpServlet {
    
	private CommentService service = new CommentService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ArrayList<Integer> list = new ArrayList<>();
		
		try {
			String name = request.getParameter("name");
			list = service.getLike(name);
			map.put("likes", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		ArrayList<Integer> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = request.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		
		String line="";
		while( (line = br.readLine()) !=null) {
			sb.append(line);			
		}
		
		String s = sb.toString();
		LikeDTO like = mapper.readValue(s, LikeDTO.class);
		try {
			service.doLike(like.getUserName(), like.getCommentId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
