package service;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.AddException;
import exception.DeleteException;
import exception.ModifyException;
import exception.NotExistException;
import model.CommentDAO;
import model.LikeDAO;
import model.dto.CommentDTO;

public class CommentService {

	public boolean create(CommentDTO comment) throws SQLException, AddException {
		return CommentDAO.create(comment);
	}

	public ArrayList<CommentDTO> read(int postId) throws SQLException, NotExistException {
		return CommentDAO.read(postId);
	}

	public boolean update(CommentDTO comment) throws SQLException, ModifyException {
		return CommentDAO.update(comment);
	}

	public boolean delete(int commentId) throws SQLException, DeleteException {
		return CommentDAO.delete(commentId);
	}

	public void doLike(String userName, int commentId) throws SQLException {
		if (LikeDAO.likeCheck(userName, commentId)) {
			LikeDAO.add(userName, commentId);
		} else {
			LikeDAO.delete(userName, commentId);
		}
	}
	
	public ArrayList<Integer> getLike(String userName) throws SQLException {
		return LikeDAO.getLikeNum(userName);
	}
}
