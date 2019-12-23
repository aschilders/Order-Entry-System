import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class orderEntryTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://childers_milestone.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public orderEntryTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strCustomerFirst, String strEmployeeFirst, String strOrderDate, String strOrderTime) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary

		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT"
							+ "			o.orderkey AS ID, "
							+ "			c.customerfirstname AS CFirst, "
							+ "			e.employeefirstname AS EFirst, "
							+ "			o.orderdate AS Date, "
							+ "			o.ordertime AS Time "
							+ "FROM orderentry o "
							+ " LEFT JOIN customer c ON c.customerkey =  o.customerkey "
							+ " LEFT JOIN employee e ON e.employeekey =  o.employeekey "
							+ "WHERE "
							+ " 		customerfirstname LIKE ?"
							+ " AND 	employeefirstname LIKE ? "
							+ " AND 	orderdate LIKE ? "
							+ " AND 	ordertime LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strCustomerFirst + "%" );
		prepStmt.setString(2, "%" + strEmployeeFirst + "%" );
		prepStmt.setString(3, "%" + strOrderDate + "%" );
		prepStmt.setString(4, "%" + strOrderTime + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate(int empID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT"
				+ "						orderkey AS ID		, "
				+ "						customerkey AS CFirst, "
				+ "						employeekey AS EFirst, "
				+ "						orderdate AS Date, "
				+ "						ordertime AS Time "
				+ " FROM orderentry 								  "
				+ " WHERE orderkey = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, empID);
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strOrderDate, String strOrderTime, String pos1, String pos2) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
  		Integer posID1 = 0;
  		String ourSQLPos1 = "SELECT customerkey from customer where customerfirstname = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos1 = conn.prepareStatement(ourSQLPos1);
  		prepPos1.setString(1, pos1);
  		ResultSet posResults = prepPos1.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID1 = posResults.getInt("customerkey");
  		}
  		
  		Integer posID2 = 0;
  		String ourSQLPos2 = "SELECT employeekey from employee where employeefirstname = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos2 = conn.prepareStatement(ourSQLPos2);
  		prepPos2.setString(1, pos2);
  		ResultSet posResults2 = prepPos2.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults2.next()) {
  			posID2 = posResults2.getInt("employeekey");
  		}
		
		String ourSQLInsert = "INSERT INTO orderentry "
							+ " 		(customerkey, employeekey, orderdate, ordertime) "
							+ " VALUES 	(       ?,			 ?,			 ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setInt(1, posID1);
		prepStmt.setInt(2, posID2);
		prepStmt.setString(3, strOrderDate);
		prepStmt.setString(4, strOrderTime);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strOrderDate, String strOrderTime, String pos1, String pos2, int empID) throws SQLException {
		
		int rows = 0;
		
		Integer posID1 = 0;
  		String ourSQLPos1 = "SELECT customerkey from customer where customerfirstname = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos1 = conn.prepareStatement(ourSQLPos1);
  		prepPos1.setString(1, pos1);
  		ResultSet posResults = prepPos1.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID1 = posResults.getInt("customerkey");
  		}
  		
  		Integer posID2 = 0;
  		String ourSQLPos2 = "SELECT employeekey from employee where employeefirstname = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos2 = conn.prepareStatement(ourSQLPos2);
  		prepPos2.setString(1, pos2);
  		ResultSet posResults2 = prepPos2.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults2.next()) {
  			posID2 = posResults2.getInt("employeekey");
  		}
		
		String ourSQLUpdate = "UPDATE orderentry " 
							+ " SET "
							+ " 	customerkey 	=	?	, "
							+ " 	employeekey 	=	?	, "
							+ " 	orderdate 	=	?	, "
							+ " 	ordertime 	=	?	"
							+ " WHERE orderkey    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setInt(1, posID1);
		prepStmt.setInt(2, posID2);
		prepStmt.setString(3, strOrderDate);
		prepStmt.setString(4, strOrderTime);
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
		String ourSQLDelete = "DELETE FROM orderentry " 
							+ "WHERE orderkey = ? " ;

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
