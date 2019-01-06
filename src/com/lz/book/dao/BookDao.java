package com.lz.book.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;




public class BookDao {
	
	
	private static final String DB_DEIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/customer";
	private static final String DB_NAME = "Books";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "515527";
	private static Connection connection = null;
	
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DB_DEIVER);
		connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
	}
	
	private static void getClose(Connection connection, Statement st, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (st != null) {
			st.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	
	public static String Query() throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;

		List<Book> booksList = new Arrays<>();
		getConnection();
		
		String sql = "SELECT * FROM books;";
		System.out.println(sql);
		ps = connection.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
		
			Book book = new Book();
			book.setBid(rs.getInt("Bid"));
			book.setBname(rs.getString("Bname"));
			book.setBnumber(rs.getInt("Bnumber"));
		
			booksList.add(book);
		}
		BooksBean booksBean = new BooksBean();
		// 图书的列表
		booksBean.setRows(booksList);
		// 图书的总数
		booksBean.setTotal(String.valueOf(booksList.size()));
		
		getClose(connection, null, ps, rs);
		return gson.toJson(booksBean);
	}
	}
}
