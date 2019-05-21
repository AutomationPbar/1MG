package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager1 {

	private Connection con = null;
	private Statement stmt = null;
	ResultSet res;

	public void DBConnection(String DBPath, String Username, String Password) throws Exception {
		try {

			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

			System.out.println("Driver Registered");
			con = DriverManager.getConnection(DBPath, Username, Password);
			System.out.println("Database Connected");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println("Stm created");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ResultSet GetResultSet(String DBQuery) throws Exception {

		try {

			res = stmt.executeQuery(DBQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return res;
	}

	public void UpdateQueryExecutor(String DBQuery) throws Exception {

		try {

			stmt.executeUpdate(DBQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void SetPractoLabData(String city, String lab, String json, String remarks, String tableName)
			throws SQLException {

		try {
			String datam = "('" + city + "','" + lab + "','" + json + "','" + remarks + "');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}

		stmt.executeUpdate("INSERT INTO " + tableName + "(City, Lab, JSON, Remarks)  values ('" + city + "','" + lab
				+ "','" + json + "','" + remarks + "');");
		tear();

	}
	
	public int UpdateQuery(String DBQuery) throws Exception {

		int i = 0;
		try {
			i = stmt.executeUpdate(DBQuery);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return i;
	}

	public void tear() {
		con = null;
	}

}
