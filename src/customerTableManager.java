import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://childers_milestone.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public customerTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strFirstName, String strLastName, String strAddress, String strCity, String strState, String strZip, 
															String strEmail, String strPassword, String strPhone, String strList) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary

		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT"
							+ "			c.customerkey AS ID, "
							+ "			c.customerfirstname AS Firstname, "
							+ "			c.customerlastname AS Lastname, "
							+ "			c.customeraddress AS Address, "
							+ "			c.customercity AS City, "
							+ "			c.customerstate AS State, "
							+ "			c.customerzip AS Zip, "
							+ "			c.customeremail AS Email, "
							+ "			c.customerpassword AS Password, "
							+ "			c.customerphone AS Phone, "
							+ "			c.customermailinglist AS List "
							+ "FROM customer c "
							+ "WHERE "
							+ " 		customerfirstname LIKE ?"
							+ " AND 	customerlastname LIKE ? "
							+ " AND 	customeraddress LIKE ? "
							+ " AND 	customercity LIKE ? "
							+ " AND 	customerstate LIKE ? "
							+ " AND 	customerzip LIKE ? "
							+ " AND 	customeremail LIKE ? "
							+ " AND 	customerpassword LIKE ? "
							+ " AND 	customerphone LIKE ? "
							+ " AND 	customermailinglist LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strFirstName + "%" );
		prepStmt.setString(2, "%" + strLastName + "%" );
		prepStmt.setString(3, "%" + strAddress + "%" );
		prepStmt.setString(4, "%" + strCity + "%" );
		prepStmt.setString(5, "%" + strState + "%" );
		prepStmt.setString(6, "%" + strZip + "%" );
		prepStmt.setString(7, "%" + strEmail + "%" );
		prepStmt.setString(8, "%" + strPassword + "%" );
		prepStmt.setString(9, "%" + strPhone + "%" );
		prepStmt.setString(10, "%" + strList + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate(int empID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT"
				+ "						customerkey AS ID		, "
				+ "						customerfirstname AS Firstname, "
				+ "						customerlastname AS Lastname, "
				+ "						customeraddress AS Address, "
				+ "						customercity AS City, "
				+ "						customerstate AS State, "
				+ "						customerzip AS Zip, "
				+ "						customeremail AS Email, "
				+ "						customerpassword AS Password, "
				+ "						customerphone AS Phone, "
				+ "						customermailinglist AS List "
				+ " FROM customer 								  "
				+ " WHERE customerkey = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, empID);
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strFirstName, String strLastName, String strAddress, String strCity, String strState, String strZip, 
												String strEmail, String strPassword, String strPhone, String strList) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
		String ourSQLInsert = "INSERT INTO customer "
							+ " 		(customerfirstname, customerlastname, customeraddress, customercity, customerstate, customerzip, "
							+ "				customeremail, customerpassword, customerphone, customermailinglist) "
							+ " VALUES 	(       ?,			 ?,			 ?,			 ?,			 ?,"
							+ "			 			?,			 ?,			 ?,			 ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString(1, strFirstName);
		prepStmt.setString(2, strLastName);
		prepStmt.setString(3, strAddress);
		prepStmt.setString(4, strCity);
		prepStmt.setString(5, strState);
		prepStmt.setString(6, strZip);
		prepStmt.setString(7, strEmail);
		prepStmt.setString(8, strPassword);
		prepStmt.setString(9, strPhone);
		prepStmt.setString(10, strList);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strFirstName, String strLastName, String strAddress, String strCity, String strState, String strZip, 
										String strEmail, String strPassword, String strPhone, String strList, int empID) throws SQLException {
		
		int rows = 0;
		
		String ourSQLUpdate = "UPDATE customer " 
							+ " SET "
							+ " 	customerfirstname 	=	?	, "
							+ " 	customerlastname 	=	?	, "
							+ " 	customeraddress 	=	?	, "
							+ " 	customercity 	=	?	, "
							+ " 	customerstate 	=	?	, "
							+ " 	customerzip 	=	?	, "
							+ " 	customeremail 	=	?	, "
							+ " 	customerpassword 	=	?	, "
							+ " 	customerphone 	=	?	, "
							+ " 	customermailinglist 	=	?	"
							+ " WHERE customerkey    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString(1, strFirstName);
		prepStmt.setString(2, strLastName);
		prepStmt.setString(3, strAddress);
		prepStmt.setString(4, strCity);
		prepStmt.setString(5, strState);
		prepStmt.setString(6, strZip);
		prepStmt.setString(7, strEmail);
		prepStmt.setString(8, strPassword);
		prepStmt.setString(9, strPhone);
		prepStmt.setString(10, strList);
		prepStmt.setInt(11, empID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord(int empID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM customer " 
							+ "WHERE customerkey = ? " ;

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
