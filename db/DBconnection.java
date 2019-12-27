package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	
	public static void initConnection() {
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");  // 현재 사용할 클래스(forname의 파라미터부분) 가 있냐없냐를 조사
				
				System.out.println("Driver Loading Success."); // Driver가 있으면 이 부분이 출력된다.
			} catch (ClassNotFoundException e) {   // Driver가 없으면 exception 이 뜸
				e.printStackTrace();
			}
		}
	
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.33:1521:xe", "hr", "hr");
		
			System.out.println("DB Connection Success.");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;    // conn은 DB와 연결하는 포인터역할
	}
}
