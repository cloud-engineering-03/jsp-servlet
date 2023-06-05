package service;

import java.sql.SQLException;

import exception.AddException;
import exception.DeleteException;
import exception.ModifyException;
import exception.NotExistException;
import model.CommentDAO;
import model.PeopleDAO;
import model.PostDAO;
import model.UserDAO;
import model.dto.UserDTO;
import model.util.Boan;

public class UserService {
	
	public boolean check(String name) throws SQLException, NotExistException {
		return UserDAO.checkUser(name);
	}
	
	public boolean dupCheck(String id) throws SQLException {
		return UserDAO.idDupCheck(id);
	}
	
	public boolean signUp(UserDTO user, String salt) throws SQLException, AddException {
		boolean u = UserDAO.signUp(user,salt);
		if(u) {
			PeopleDAO.update(user.getName(), 1);			
		}
		return u;
	}
	
	public UserDTO login(String id, String pwd) throws SQLException, NotExistException {
		pwd = Boan.change(pwd+UserDAO.salt(id));
		return UserDAO.login(id, pwd);
	}
	
	public boolean update(UserDTO user) throws SQLException, ModifyException, NotExistException {
		user.setPassword(Boan.change(user.getPassword()+UserDAO.salt(user.getId())));
		return UserDAO.update(user);
	}
	
	public boolean delete(String name) throws SQLException, DeleteException, NotExistException {
		CommentDAO.deleteByName(name);
		PostDAO.deleteByName(name);
		PeopleDAO.update(name, 0);
		return UserDAO.delete(name);
	}
	
	public UserDTO getUserInfo(String name) throws SQLException, NotExistException {
		UserDTO user = UserDAO.getUserInfo(name);
		user.setCommentList(CommentDAO.readByName(name));
		user.setPostList(PostDAO.readByName(name));
		return user;
	}
	
}
