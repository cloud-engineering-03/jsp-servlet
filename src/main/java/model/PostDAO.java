package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exception.NotExistException;
import model.dto.PostDTO;
import model.util.DBUtil;

public class PostDAO {
	public static boolean create(PostDTO post) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("insert into post values(null,?,?,?,now(),0)");
			pstmt.setString(1, post.getUserName());
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getContent());
			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}

	public static ArrayList<PostDTO> readAll() throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PostDTO> list = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from post");
			rs = pstmt.executeQuery();
			list = new ArrayList<>();

			while (rs.next()) {
				list.add(PostDTO.builder().postId(rs.getInt(1)).userName(rs.getString(2)).title(rs.getString(3))
						.content(rs.getString(4)).createdAt(rs.getDate(5)).viewCnt(rs.getInt(6))
						.commentList(CommentDAO.read(rs.getInt(1))).build());
			}
			return list;
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
	}

	public static ArrayList<PostDTO> readByName(String name) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PostDTO> list = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from post where userName=?");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();

			while (rs.next()) {
				list.add(PostDTO.builder().postId(rs.getInt(1)).userName(rs.getString(2)).title(rs.getString(3))
						.content(rs.getString(4)).createdAt(rs.getDate(5)).viewCnt(rs.getInt(6))
						.commentList(CommentDAO.read(rs.getInt(1))).build());
			}
			return list;
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
	}

	public static PostDTO read(int postId) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from post where postId=?");
			pstmt.setInt(1, postId);
			if (!countUp(postId))
				return null;
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return PostDTO.builder().postId(rs.getInt(1)).userName(rs.getString(2)).title(rs.getString(3))
						.content(rs.getString(4)).createdAt(rs.getDate(5)).viewCnt(rs.getInt(6))
						.commentList(CommentDAO.read(rs.getInt(1))).build();
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		return null;
	}

	public static boolean update(PostDTO post) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("update post set title=?, content=?, createdTime=now()" + "where postId=?");
			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getContent());
			pstmt.setInt(3, post.getPostId());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}

	public static boolean delete(int postid) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("delete from post where postId = ?");
			pstmt.setInt(1, postid);
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
			pstmt = con.prepareStatement("delete from post where userName = ?");
			pstmt.setString(1, name);
			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}
	public static boolean countUp(int postId) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("update post set viewCnt = viewCnt+1 where postId=?");
			pstmt.setInt(1, postId);
			if(pstmt.executeUpdate()==1) {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}
}
