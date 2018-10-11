package redone.game.database;

import java.sql.*;

public class MySQLDatabase
{
	public static Connection con;
	// Constructor
	public MySQLDatabase()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/runescape", "root", "");
			
			System.out.println("Connected to mysql server!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet runQuery(String query)
	{
		Statement statement;
		ResultSet rs = null;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static MySQLDatabase getInstance()
	{
		if(mInstance == null)
			mInstance = new MySQLDatabase();
		
		return mInstance;
	}

	private static MySQLDatabase mInstance = null;
}
