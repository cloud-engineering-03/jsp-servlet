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

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.NotExistException;
import model.dto.UserDTO;
import service.UserService;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	UserService service = new UserService();

	//정보 조회 -- post, review 포함
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	//login
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		
		try {
			JSONObject obj = new JSONObject(request.getReader().readLine());
			UserDTO user = service.login((String)obj.get("id"), (String)obj.get("pw"));
			map.put("status",1);
			request.getSession().setAttribute("nick", user.getNickName());
		} catch (SQLException | NotExistException e) {
			e.printStackTrace();
			map.put("status",0);
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

	//정보 수정 -- post, review 제외 user 정보만 수정 (post,review는 각 servlet에서 수정 실행)
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		
		try {
			String s = request.getReader().readLine();
			UserDTO user = mapper.readValue(s, UserDTO.class);
			if(service.update(user)) {
				map.put("status",1);
			}
		} catch (SQLException | NotExistException e) {
			e.printStackTrace();
			map.put("status",0);
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

	//회원 탈퇴 -- 작성한 post, review 포함 전부 삭제, people 데이터 바꾸기
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		
		try {
			String s = request.getReader().readLine();
			UserDTO user = mapper.readValue(s, UserDTO.class);
			service.delete(user.getName());
			map.put("status", 1);
		} catch (SQLException | NotExistException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		
		response.getWriter().print(mapper.writeValueAsString(map));
	}

}


























