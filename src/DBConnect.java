import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

		public static Connection connect()
		{
			Connection con = null;
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			}catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return con;
		}

		public static void disconnect(Connection con)
		{
			try {
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
}
