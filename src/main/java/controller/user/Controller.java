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