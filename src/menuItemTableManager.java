import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class menuItemTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://childers_milestone.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public menuItemTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strItemName, String strItemType, String strItemPrice, String strItemDesc) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary

		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT"
							+ "			m.menuitemkey AS ID, "
							+ "			m.menuitemname AS Itemname, "
							+ "			t.menutype AS Type, "
							+ "			m.menuitemprice AS Price, "
							+ "			m.menuitemdescription AS Description "
							+ "FROM menuitem m "
							+ " LEFT JOIN menutype t ON t.menutypekey =  m.menutypekey "
							+ "WHERE "
							+ " 		menuitemname LIKE ?"
							+ " AND 	menutype LIKE ? "
							+ " AND 	menuitemprice LIKE ? "
							+ " AND 	menuitemdescription LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strItemName + "%" );
		prepStmt.setString(2, "%" + strItemType + "%" );
		prepStmt.setString(3, "%" + strItemPrice + "%" );
		prepStmt.setString(4, "%" + strItemDesc + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate(int empID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT"
				+ "						menuitemkey AS ID		, "
				+ "						menuitemname AS Itemname, "
				+ "						menutypekey AS Type, "
				+ "						menuitemprice AS Price, "
				+ "						menuitemdescription AS Description "
				+ " FROM menuitem 								  "
				+ " WHERE menuitemkey = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, empID);
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strItemName, String strItemPrice, String strItemDesc, String pos) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
  		Integer posID = 0;
  		String ourSQLPos = "SELECT menutypekey from menutype where menutype = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("menutypekey");
  		}
		
		String ourSQLInsert = "INSERT INTO menuitem "
							+ " 		(menuitemname, menutypekey, menuitemprice, menuitemdescription) "
							+ " VALUES 	(       ?,			 ?,			 ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString(1, strItemName);
		prepStmt.setInt(2, posID);
		prepStmt.setString(3, strItemPrice);
		prepStmt.setString(4, strItemDesc);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strItemName, String strItemPrice, String strItemDesc, String pos, int empID) throws SQLException {
		
		int rows = 0;
		
		Integer posID = 0;
  		String ourSQLPos = "SELECT menutypekey from menutype where menutype = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("menutypekey");
  		}
		
		String ourSQLUpdate = "UPDATE menuitem " 
							+ " SET "
							+ " 	menuitemname 	=	?	, "
							+ " 	menutypekey 	=	?	, "
							+ " 	menuitemprice 	=	?	, "
							+ " 	menuitemdescription 	=	?	"
							+ " WHERE menuitemkey    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString(1, strItemName);
		prepStmt.setInt(2, posID);
		prepStmt.setString(3, strItemPrice);
		prepStmt.setString(4, strItemDesc);
		prepStmt.setInt(5, empID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord(int empID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM menuitem " 
							+ "WHERE menuitemkey = ? " ;

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
