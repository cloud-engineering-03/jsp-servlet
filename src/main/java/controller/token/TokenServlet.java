package controller.token;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.util.JToken;

@WebServlet("/token")
public class TokenServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = new HashMap<>();
		
		String s = request.getReader().readLine().replace("\"", "");
		String s2 = JToken.decode(s);
		if(s2!=null) {
			map.put("status", 1);
			map.put("name", s2);
		}else {
			map.put("status", 0);
		}
		response.getWriter().print(mapper.writeValueAsString(map));
	}

}
