package controller.user;

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

import exception.NotExistException;
import service.UserService;

@WebServlet("/people")
public class PeopleServlet extends HttpServlet {
	private UserService service = new UserService();
	
	//회원가입 전 이름 확인 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		try {
			if (service.check(request.getParameter("name"))) {
				map.put("status", 1);
				map.put("name", request.getParameter("name"));
			} else {
				map.put("status", 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("status", 0);
		} catch (NotExistException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}
	
}
