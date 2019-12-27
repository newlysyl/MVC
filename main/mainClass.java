package main;

import db.DBconnection;
import view.loginView;

public class mainClass {

	public static void main(String[] args) {
		
		DBconnection.initConnection();
		new loginView();
	}

}
