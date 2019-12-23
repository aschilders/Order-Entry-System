import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inventoryTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://childers_milestone.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public inventoryTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strItemName, String strItemCount) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary

		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT"
							+ "			i.inventorykey AS ID, "
							+ "			m.menuitemname AS Name, "
							+ "			i.itemcount AS Count "
							+ "FROM inventory i "
							+ " LEFT JOIN menuitem m ON m.menuitemkey =  i.menuitemkey "
							+ "WHERE "
							+ " 		menuitemname LIKE ?"
							+ " AND 	itemcount LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strItemName + "%" );
		prepStmt.setString(2, "%" + strItemCount + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate(int empID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT"
				+ "						inventorykey AS ID		, "
				+ "						menuitemkey AS Item, "
				+ "						itemcount AS Count "
				+ " FROM inventory 								  "
				+ " WHERE inventorykey = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, empID);
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strItemCount, String pos) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
  		Integer posID = 0;
  		String ourSQLPos = "SELECT menuitemkey from menuitem where menuitemname = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("menuitemkey");
  		}
		
		String ourSQLInsert = "INSERT INTO inventory "
							+ " 		(menuitemkey, itemcount) "
							+ " VALUES 	(       ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setInt(1, posID);
		prepStmt.setString(2, strItemCount);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strItemCount, String pos, int empID) throws SQLException {
		
		int rows = 0;
		
		Integer posID = 0;
  		String ourSQLPos = "SELECT menuitemkey from menuitem where menuitemname = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("menuitemkey");
  		}
		
		String ourSQLUpdate = "UPDATE inventory " 
							+ " SET "
							+ " 	menuitemkey 	=	?	, "
							+ " 	itemcount 	=	?	 "
							+ " WHERE inventorykey    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setInt(1, posID);
		prepStmt.setString(2, strItemCount);
		prepStmt.setInt(3, empID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord(int empID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM inventory " 
							+ "WHERE inventorykey = ? " ;

		// prepare our statment
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
		
		// set our parms
		prepStmt.setInt(1, empID);
		
		// get rows from delete
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		// return the number of rows affected
		return rows;
	}
}
