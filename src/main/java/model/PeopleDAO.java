package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.util.DBUtil;

public class PeopleDAO {
	
	// 가입시 가입여부 1로 바꾸기, 탈퇴시 가입여부 0으로 바꾸기
	public static void update(String name, int i) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("update people set isSign=? where name = ?");
			pstmt.setInt(1, i);
			pstmt.setString(2, name);
			
			pstmt.executeUpdate();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
}
