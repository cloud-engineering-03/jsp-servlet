package controller.user;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.NotExistException;
import model.CommentDAO;
import model.LikeDAO;
import model.PostDAO;
import model.UserDAO;
import model.dto.CommentDTO;
import model.dto.PostDTO;
import model.dto.UserDTO;

public class Controller {

	public boolean check(String name) {
		try {
			return UserDAO.checkUser(name);
		} catch (SQLException | NotExistException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean dupCheck(String id) {
		try {
			return UserDAO.idDupCheck(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean signUp(UserDTO user) {
		try {
			return UserDAO.signUp(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserDTO login(String id, String pwd) {
		try {
			return UserDAO.login(id, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean update(UserDTO user) {
		try {
			return UserDAO.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(String name) {
		try {
			return UserDAO.delete(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public UserDTO getUserInfo(String name) {
		try {
			return UserDAO.getUserInfo(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createPost(PostDTO post) {
		try {
			return PostDAO.create(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<PostDTO> readAllPosts() {
		try {
			return PostDAO.readAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PostDTO readPost(int postId) {
		try {
			return PostDAO.read(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updatePost(PostDTO post) {
		try {
			return PostDAO.update(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePost(int postId) {
		try {
			return PostDAO.delete(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean createComment(CommentDTO comment) {
		try {
			return CommentDAO.create(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<CommentDTO> readComment(int postId) {
		try {
			return CommentDAO.read(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateComment(CommentDTO comment) {
		try {
			return CommentDAO.update(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteComment(int commentId) {
		try {
			return CommentDAO.delete(commentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void doLike(String userName, int commentId) {
		try {
			if(LikeDAO.likeCheck(userName, commentId)) {
				LikeDAO.add(userName, commentId);
			}else {
				LikeDAO.delete(userName, commentId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}