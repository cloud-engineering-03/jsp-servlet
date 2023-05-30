package model;

import java.sql.SQLException;

import exception.NotExistException;
import model.dto.UserDTO;

public class Test {

	public static void main(String[] args) {
		try {
			if(UserDAO.checkUser("김혁준")) {
				UserDAO.signUp(UserDTO.builder().id("test").password("test")
												.nickName("test").name("김혁준").build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NotExistException e) {
			e.printStackTrace();
		};
	}

}
