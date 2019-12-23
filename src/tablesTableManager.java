
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tablesTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://childers_milestone.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public tablesTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strCategory) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary

		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT"
							+ "			t.tableid AS ID, "
							+ "			t.tablename AS Category "
							+ "FROM tables t "
							+ "WHERE "
							+ " 		tablename LIKE ?";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strCategory + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate(int catID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT"
				+ "						tableid AS ID		, "
				+ "						tablename AS Category	"
				+ " FROM tables 								  "
				+ " WHERE tableid = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, catID);
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strCategory) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
		String ourSQLInsert = "INSERT INTO tables "
							+ " 		(tablename) "
							+ " VALUES 	(       ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString( 1, strCategory);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strCategory, int catID) throws SQLException {
		
		int rows = 0;
		
		String ourSQLUpdate = "UPDATE tables " 
							+ " SET "
							+ " 	tablename 	=	?	"
							+ " WHERE tableid    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString( 1, strCategory);
		prepStmt.setInt(2, catID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord(int catID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM tables " 
							+ "WHERE tableid = ? " ;

		// prepare our statment
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
		
		// set our parms
		prepStmt.setInt(1, catID);
		
		// get rows from delete
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		// return the number of rows affected
		return rows;
	}
}
