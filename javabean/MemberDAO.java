package javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBclose;
import db.DBconnection;
import dto.MemberDTO;

public class MemberDAO {

	private static MemberDAO mem = null;
	
	private String loginId;
	
	private MemberDAO() {
	}
	
	public static MemberDAO getInstance() {
		if(mem == null) {
			mem = new MemberDAO();
		}
		return mem;
	}
	
	public boolean getId(String id) {
		String sql = " SELECT ID "
				+ " FROM MEMBER "
				+ " WHERE ID = ? ";
		
		Connection conn = null;			// DB Connection
		PreparedStatement psmt = null;	// SQL
		ResultSet rs = null;			// result
		
		boolean findId = false;
		
		System.out.println("sql:" + sql);
		
		
		try {
				conn = DBconnection.getConnection();
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, id);
				
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					findId = true;				// 회원 데이터가 있을 때
			}		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			DBclose.close(psmt, conn, rs);
		}
		
		return findId;
	}
	
	
	
	// DB에 추가
	public boolean addMember(MemberDTO dto) {
		String sql = " INSERT INTO MEMBER(ID, PWD, NAME, EMAIL, AUTH) "
				+ " VALUES(?, ?, ?, ?, 3) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		System.out.println("sql = " + sql);
		
		int count = 0;
		
		try {
			
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			
			count = psmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, null);
		}
		
		return count > 0 ? true : false;
	}
	
	
	// 로그인 메소드
	public MemberDTO login(String id, String pwd) {
		 
		// MEMBER 테이블에서 아이디, 패스워드 검사 후 ID, NAME, EMAIL, AUTH 를 가져옴
		String sql = " SELECT ID, NAME, EMAIL, AUTH "
				+ " FROM MEMBER "
				+ " WHERE ID = ? AND PWD = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDTO mem = null;
		
		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id.trim());
			psmt.setString(2, pwd.trim());
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				String _id = rs.getString(1);		// id
				String _name = rs.getString(2);		// name
				String _email = rs.getString(3);	// email
				int auth = rs.getInt(4);			// auth
				
				mem = new MemberDTO(_id, null, _name, _email, auth);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, rs);
		}
		
		return mem;
		
	}
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public static MemberDAO getMem() {
		return mem;
	}

	public static void setMem(MemberDAO mem) {
		MemberDAO.mem = mem;
	}	
	
}