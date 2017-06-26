package com.dgit.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class MySqlConnectionTest {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/book_ex";
	private static final String USER = "root";
	private static final String PW = "rootroot";
	
	@Test
	public void testConnection() throws ClassNotFoundException{
		Class.forName(DRIVER);
		
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
