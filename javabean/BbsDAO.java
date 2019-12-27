package javabean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBclose;
import db.DBconnection;
import dto.BbsDTO;

public class BbsDAO {

	private static BbsDAO dao = new BbsDAO();

	private BbsDAO() {

	}

	public static BbsDAO getInstance() {
		return dao;
	}

	public List<BbsDTO> getBbsList() {

		String sql = " SELECT SEQ, ID, TITLE, CONTENT, " 
				+ " WDATE, DEL, READCOUNT " 
				+ " FROM BBS "
				+ " ORDER BY WDATE DESC "; // ORDER BY WDATE DESC 는 최신글을 가장 위로 정렬하는 것

		Connection conn = null; // DB Connection
		PreparedStatement psmt = null; // SQL
		ResultSet rs = null; // result

		List<BbsDTO> list = new ArrayList<BbsDTO>();

		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {

				BbsDTO dto = new BbsDTO(rs.getInt(1), // seq,
						rs.getString(2), // id,
						rs.getString(3), // title,
						rs.getString(4), // content,
						rs.getString(5), // wdate,
						rs.getInt(6), // del,
						rs.getInt(7) // readcount);
				); // 생성과 동시에 데이터를 넣는다.
				list.add(dto);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, rs);
		}

		return list;

	}

	public boolean writeBBS(BbsDTO dto) {
		int count = 0;

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		String sql = "INSERT INTO BBS(SEQ, ID, TITLE, CONTENT, WDATE, DEL, READCOUNT) "
				+ " VALUES(SEQ_BBS.NEXTVAL, ?, ?, ?, SYSDATE, 0, 0) ";

		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());

			count = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, rs);
		}

		return count > 0 ? true : false;
	}

	public BbsDTO getBBS(int seq) {
		String sql = " SELECT ID, TITLE, WDATE, CONTENT, READCOUNT " 
					+ " FROM BBS " 
					+ " WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		BbsDTO dto = null;

		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, seq);
			rs = psmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				String title = rs.getString(2);
				String wdate = rs.getString(3);
				String content = rs.getString(4);
				int readcount = rs.getInt(5);

				dto = new BbsDTO(0, id, title, content, wdate, 0, readcount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, rs);
		}
		return dto;
	}

	// 조회 수 메소드
	public void readCount(int seq) {
		String sql = " UPDATE BBS SET " 
					+ " READCOUNT = READCOUNT + 1 " 
					+ " WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);

			psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, rs);
		}

	}
	

	// 수정 메소드
	public boolean updateBbs(int seq, String title, String content) {
		String sql = " UPDATE BBS SET " 
					+ " TITLE = ?, CONTENT = ? " 
					+ " WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;

		int count = 0;

		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);

			count = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, null);
		}

		return count > 0 ? true : false;
	}

	// 삭제 메소드
	public boolean deleteBbs(int seq) {
		String sql = " UPDATE BBS " 
					+ " SET DEL = 1 " 
					+ " WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;

		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);

			count = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, null);
		}

		return count > 0 ? true : false;

	}
	
	
	// 검색 메소드
	public List<BbsDTO> getSelectList(String title, String word) {
									//        항목                   검색어
		String sql = " SELECT SEQ, ID, TITLE "
				+ " FROM BBS "
				+ " WHERE 1=1 ";
		
		if(title.equals("제목")) {
			sql = sql + " AND TITLE LIKE ? ";
		} else if(title.equals("내용")) {
			sql = sql + " AND CONTENT LIKE ? ";
		} else if(title.equals("작성자")) {
			sql = sql + " AND ID = ? ";        // ID는 LIKE 쓰면 안됨 왜냐 아이디가 aa인경우 사용자가 aabb를 입력할수도 있으므로
		}
		
		sql = sql + " ORDER BY WDATE DESC "; 		// 가장 최근 글이 먼저 나오도록
	
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDTO> list = new ArrayList<BbsDTO>();
		
		try {
			conn = DBconnection.getConnection();
			psmt = conn.prepareStatement(sql);
			
			// ? 에 들어갈 데이터 넣기
			if(title.equals("작성자")) {
				psmt.setString(1, word);
			} else if(title.equals("제목") || title.equals("내용")) {
				psmt.setString(1, "%" + word + "%");	// 여기서 % 를 넣어야함. 저기 위에 AND TITLE % LIKE % 이거는 불가능하므로
			}
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BbsDTO dto = new BbsDTO(rs.getInt(1),	// seq 
										rs.getString(2),	// id 
										rs.getString(3),	// title
										null,				// content 
										null,				// wdate 
										0,					// del 
										0);					// readCount
													// 다 가져와도 되긴하는데 실질적으로 필요한 것만 가져와씀
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(psmt, conn, rs);
		}
		return list;
	
	}
	
}
