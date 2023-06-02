package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exception.AddException;
import exception.DeleteException;
import exception.ModifyException;
import exception.NotExistException;
import model.dto.CommentDTO;
import model.util.DBUtil;

public class CommentDAO {
	public static boolean create(CommentDTO comment) throws SQLException, AddException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("insert into comment values(null,?,?,?,now())");
			pstmt.setString(1, comment.getUserName());
			pstmt.setInt(2, comment.getPostId());
			pstmt.setString(3, comment.getContent());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}

		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}

	public static ArrayList<CommentDTO> read(int postId) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CommentDTO> list = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from comment where postId=?");
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();

			while (rs.next()) {
				CommentDTO comment = CommentDTO.builder().commentId(rs.getInt(1)).userName(rs.getString(2))
						.postId(rs.getInt(3)).content(rs.getString(4)).createdAt(rs.getDate(5)).build();
				comment.setLikeCnt(LikeDAO.count(comment.getCommentId()));
				list.add(comment);
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		return list;
	}

	public static ArrayList<CommentDTO> readByName(String name) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CommentDTO> list = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from comment where userName=?");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();

			while (rs.next()) {
				CommentDTO comment = CommentDTO.builder().commentId(rs.getInt(1)).userName(rs.getString(2))
						.postId(rs.getInt(3)).content(rs.getString(4)).createdAt(rs.getDate(5)).build();
				comment.setLikeCnt(LikeDAO.count(rs.getInt(1)));
				list.add(comment);
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		return list;
	}

	public static boolean update(CommentDTO comment) throws SQLException, ModifyException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("update comment set content=? where commentId=?");
			pstmt.setString(1, comment.getContent());
			pstmt.setInt(2, comment.getCommentId());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}

	public static boolean delete(int commentId) throws SQLException, DeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("delete from comment where commentId = ?");
			pstmt.setInt(1, commentId);
			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}
	public static boolean deleteByName(String name) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("delete from comment where userName = ?");
			pstmt.setString(1, name);
			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}
}
