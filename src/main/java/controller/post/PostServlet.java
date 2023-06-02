package controller.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
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

@WebServlet("/post")
public class PostServlet extends HttpServlet {
	
	private PostService service = new PostService();
	
	//postID에 맞는 post 정보 호출
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			map.put("post",service.read(no));
			map.put("status",1);
		} catch (SQLException | NotExistException e) {
			map.put("status",0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}
	
	//post 등록
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = request.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		
		String line="";
		while( (line = br.readLine()) !=null) {
			sb.append(line);			
		}
		
		String s = sb.toString();
		PostDTO post = mapper.readValue(s, PostDTO.class);
		try {
			service.create(post);
			map.put("status", 1);
		} catch (SQLException e) {
			map.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}
	
	//post 수정
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = request.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		
		String line="";
		while( (line = br.readLine()) !=null) {
			sb.append(line);			
		}
		
		String s = sb.toString();
		PostDTO post = mapper.readValue(s, PostDTO.class);
		try {
			service.update(post);
			map.put("status", 1);
		} catch (SQLException e) {
			map.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}
	
	//post 삭제
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			service.delete(no);
			map.put("status",1);
		} catch (SQLException | NotExistException e) {
			map.put("status",0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

}
