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

import exception.AddException;
import exception.DeleteException;
import exception.ModifyException;
import exception.NotExistException;
import model.dto.CommentDTO;
import model.dto.PostDTO;
import service.CommentService;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

	private CommentService service = new CommentService();

	// postId에 해당되는 모든 comment 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		int no = Integer.parseInt(request.getParameter("no"));
		try {
			ArrayList<CommentDTO> list = service.read(no);
			map.put("list", list);
			map.put("status", 1);
		} catch (SQLException | NotExistException e) {
			map.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

	// comment 등록
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = request.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		String s = sb.toString();
		CommentDTO comment = mapper.readValue(s, CommentDTO.class);
		try {
			service.create(comment);
			map.put("status", 1);
		} catch (SQLException | AddException e) {
			map.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

	// comment 수정
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = request.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		String s = sb.toString();
		CommentDTO comment = mapper.readValue(s, CommentDTO.class);
		try {
			service.update(comment);
			map.put("status", 1);
		} catch (SQLException | ModifyException e) {
			map.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

	// comment 삭제
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		try {
			int no = Integer.parseInt(request.getParameter("no"));
			service.delete(no);
			map.put("status", 1);
		} catch (SQLException | DeleteException e) {
			map.put("status", 0);
			e.printStackTrace();
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

}
