package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.util.DBUtil;

public class LikeDAO {
	
	//좋아요 누르면 좋아요 정보 변경
	public static boolean likeCheck(String userName, int commentId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from `like` where name=? and commentId=?");
			pstmt.setString(1, userName);
			pstmt.setInt(2, commentId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return false;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return true;
	}
	
	public static void delete(String userName, int commentId) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("delete from `like` where name=? and commentId=?");
			pstmt.setString(1, userName);
			pstmt.setInt(2, commentId);
			pstmt.executeUpdate();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	public static void add(String userName, int commentId) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("insert into `like` values(null,?,?)");
			pstmt.setString(1, userName);
			pstmt.setInt(2, commentId);
			pstmt.executeUpdate();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	public static int count(int commentId) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select count(*) from `like` where commentId=?");
			pstmt.setInt(1, commentId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return 0;
	}
}
