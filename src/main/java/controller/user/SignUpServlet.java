package controller.user;

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
import model.dto.UserDTO;
import service.UserService;

@WebServlet("/user/new")
public class SignUpServlet extends HttpServlet {
	UserService service = new UserService();
	
	//중복 확인
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		try {
			if(service.dupCheck(request.getParameter("id"))) {
				map.put("status", 1);
			}else {
				map.put("status", 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

	//회원 가입
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		
		//JSON 문자열 받아와서 Object로 변환 시작 (여러 줄 경우)
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		InputStream is = request.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));
		
		String line="";
		while( (line = br.readLine()) !=null) {
			sb.append(line);			
		}
		
		String s = sb.toString();
		UserDTO user = mapper.readValue(s, UserDTO.class);
		//JSON 문자열 받아와서 Object로 변환 끝		
		
		try {
			//-------------이부분 트랜잭션 처리 고민해봐야함
			service.signUp(user);
			//-----------------
			
			map.put("status", 1);
		} catch (SQLException | NotExistException e) {
			e.printStackTrace();
			map.put("status", 0);
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}
	
}
