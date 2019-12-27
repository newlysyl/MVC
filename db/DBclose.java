package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBclose {
	
	public static void close(PreparedStatement psmt, Connection conn, ResultSet rs) {  // static 이 붙어있으면 어디서나 호출가능함

		try {
			if(psmt != null) {
				psmt.close();
			}
			if(conn != null) {
				conn.close();
			}
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
