package service;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.NotExistException;
import model.PostDAO;
import model.dto.PostDTO;

public class PostService {

	public boolean create(PostDTO post) throws SQLException {
		return PostDAO.create(post);
	}

	public ArrayList<PostDTO> readAll() throws SQLException, NotExistException {
		return PostDAO.readAll();
	}

	public PostDTO read(int postId) throws SQLException, NotExistException {
		PostDAO.countUp(postId);
		return PostDAO.read(postId);
	}

	public boolean update(PostDTO post) throws SQLException {
		return PostDAO.update(post);
	}

	public boolean delete(int postId) throws SQLException, NotExistException {
		return PostDAO.delete(postId);
	}
}
