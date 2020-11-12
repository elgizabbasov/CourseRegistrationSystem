import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 * Class DBConnection is responsible for making the connection between the database and the program
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */
 
public class DBConnection {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/student";
			
	static final String USERNAME = "root";
	static final String PASSWORD = "root";
	
	public static Connection initConnection() {
		try { 
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		return conn;
	
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}

