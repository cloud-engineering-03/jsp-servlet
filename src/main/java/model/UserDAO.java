package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.AddException;
import exception.DeleteException;
import exception.ModifyException;
import exception.NotExistException;
import model.dto.UserDTO;
import model.util.DBUtil;

public class UserDAO {

	// 회원가입 하는 메소드
	public static boolean signUp(UserDTO user, String salt) throws SQLException, AddException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("insert into user values(?,?,?,?,?)");
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getId());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getNickName());
			pstmt.setString(5, salt);
			if (pstmt.executeUpdate() == 1) {
				PeopleDAO.update(user.getName(), 1);
				return true;
			}

		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}

	// id 중복확인
	public static boolean idDupCheck(String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from user where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
	}

	// 명단에 이름 있는지 확인하는 메소드
	public static boolean checkUser(String name) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from people where name = ?");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt(2) == 1) {
					return false;
				} else {
					return true;
				}
			} else {
				throw new NotExistException("가입할 수 없는 이름입니다.");
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
	}
	
	public static String salt(String id) throws SQLException, NotExistException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select salt from user where id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String salt = rs.getString(1);
				return salt;
			} else {
				throw new NotExistException("소금 검색 실패!.");
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
	}
	
	
	// 사용자의 id, pwd 일치하는지 확인하는 메소드
	public static UserDTO login(String id, String pwd) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO user = null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from user where id=? and password=?");
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = UserDTO.builder().name(rs.getString(1))
										.id(rs.getString(2))
										.password(rs.getString(3))
										.nickName(rs.getString(4))
										.build();
				return user;
			} else {
				throw new NotExistException("로그인 실패!.");
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
	}

	public static boolean update(UserDTO user) throws SQLException, ModifyException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("update user set password=?, nickname=? where name = ?");
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getNickName());
			pstmt.setString(3, user.getName());
			if (pstmt.executeUpdate() == 1) {
				return true;
			} else {
				throw new ModifyException("수정 실패");
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
	}

	public static boolean delete(String name) throws SQLException, DeleteException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("delete from user where name = ?");
			pstmt.setString(1, name);
			if (pstmt.executeUpdate() == 1) {
				PeopleDAO.update(name, 0);
				return true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return false;
	}

	public static UserDTO getUserInfo(String name) throws SQLException, NotExistException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO user = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select id,name,nickname from user where name = ?");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = UserDTO.builder().name(rs.getString(2))
										.id(rs.getString(1))
										.nickName(rs.getString(3))
										.build();
//				user.setPostList(PostDAO.readByName(user.getName()));
//				user.setCommentList(CommentDAO.readByName(user.getName()));
				return user;
			}
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		throw new NotExistException("이름에 해당하는 가입 정보를 찾을 수 없습니다.");
	}
}