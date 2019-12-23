import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class employeeTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://childers_milestone.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public employeeTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strFirstName, String strLastName, String strPhone, String strAddress, String strState, String strZip, 
															String strEmail, String strPassword, String strType) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary

		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT"
							+ "			e.employeekey AS ID, "
							+ "			e.employeefirstname AS Firstname, "
							+ "			e.employeelastname AS Lastname, "
							+ "			e.employeephone AS Phone, "
							+ "			e.employeeaddress AS Address, "
							+ "			e.employeestate AS State, "
							+ "			e.employeezip AS Zip, "
							+ "			e.employeeemail AS Email, "
							+ "			e.employeepassword AS Password, "
							+ "			t.employeetype AS Permission "
							+ "FROM employee e "
							+ " LEFT JOIN employeetype t ON t.employeetypekey =  e.employeetypekey "
							+ "WHERE "
							+ " 		employeefirstname LIKE ?"
							+ " AND 	employeelastname LIKE ? "
							+ " AND 	employeephone LIKE ? "
							+ " AND 	employeeaddress LIKE ? "
							+ " AND 	employeestate LIKE ? "
							+ " AND 	employeezip LIKE ? "
							+ " AND 	employeeemail LIKE ? "
							+ " AND 	employeepassword LIKE ? "
							+ " AND 	employeetype LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strFirstName + "%" );
		prepStmt.setString(2, "%" + strLastName + "%" );
		prepStmt.setString(3, "%" + strPhone + "%" );
		prepStmt.setString(4, "%" + strAddress + "%" );
		prepStmt.setString(5, "%" + strState + "%" );
		prepStmt.setString(6, "%" + strZip + "%" );
		prepStmt.setString(7, "%" + strEmail + "%" );
		prepStmt.setString(8, "%" + strPassword + "%" );
		prepStmt.setString(9, "%" + strType + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate(int empID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT"
				+ "						employeekey AS ID		, "
				+ "						employeefirstname AS Firstname, "
				+ "						employeelastname AS Lastname, "
				+ "						employeephone AS Phone, "
				+ "						employeeaddress AS Address, "
				+ "						employeestate AS State, "
				+ "						employeezip AS Zip, "
				+ "						employeeemail AS Email, "
				+ "						employeepassword AS Password, "
				+ "						employeetypekey AS Permission "
				+ " FROM employee 								  "
				+ " WHERE employeekey = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, empID);
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strFirstName, String strLastName, String strPhone, String strAddress, String strState, String strZip, 
													String strEmail, String strPassword, String pos) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
  		Integer posID = 0;
  		String ourSQLPos = "SELECT employeetypekey from employeetype where employeetype = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("employeetypekey");
  		}
		
		String ourSQLInsert = "INSERT INTO employee "
							+ " 		(employeefirstname, employeelastname, employeephone, employeeaddress, employeestate, employeezip, "
							+ "				employeeemail, employeepassword, employeetypekey) "
							+ " VALUES 	(       ?,			 ?,			 ?,			 ?,			 ?,"
							+ "			 			?,			 ?,			 ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString(1, strFirstName);
		prepStmt.setString(2, strLastName);
		prepStmt.setString(3, strPhone);
		prepStmt.setString(4, strAddress);
		prepStmt.setString(5, strState);
		prepStmt.setString(6, strZip);
		prepStmt.setString(7, strEmail);
		prepStmt.setString(8, strPassword);
		prepStmt.setInt(9, posID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strFirstName, String strLastName, String strPhone, String strAddress, String strState, String strZip, 
											String strEmail, String strPassword, String pos, int empID) throws SQLException {
		
		int rows = 0;
		
		Integer posID = 0;
  		String ourSQLPos = "SELECT employeetypekey from employeetype where employeetype = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("employeetypekey");
  		}
		
		String ourSQLUpdate = "UPDATE employee " 
							+ " SET "
							+ " 	employeefirstname 	=	?	, "
							+ " 	employeelastname 	=	?	, "
							+ " 	employeephone 	=	?	, "
							+ " 	employeeaddress 	=	?	, "
							+ " 	employeestate 	=	?	, "
							+ " 	employeezip 	=	?	, "
							+ " 	employeeemail 	=	?	, "
							+ " 	employeepassword 	=	?	, "
							+ " 	employeetypekey 	=	?	"
							+ " WHERE employeekey    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString(1, strFirstName);
		prepStmt.setString(2, strLastName);
		prepStmt.setString(3, strPhone);
		prepStmt.setString(4, strAddress);
		prepStmt.setString(5, strState);
		prepStmt.setString(6, strZip);
		prepStmt.setString(7, strEmail);
		prepStmt.setString(8, strPassword);
		prepStmt.setInt(9, posID);
		prepStmt.setInt(10, empID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord(int empID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM employee " 
							+ "WHERE employeekey = ? " ;

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
