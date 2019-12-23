import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ordersTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://restaurant.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public ordersTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strTime, String strAmount, String prodName) throws SQLException {		
		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT 	o.dborderid as ID, "
							+ "			o.dbordertime AS Time, "
							+ "			o.dborderamount AS Amount, "
							+ "			p.dbproduct AS Product "
							+ "FROM orders o "
							+ " LEFT JOIN products p ON p.dbproductid =  o.dbproductid "
							+ "WHERE "
							+ " 		dbordertime LIKE 	 ? "
							+ " AND		dborderamount LIKE 	 ? "
							+ " AND 	dbproduct LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strTime + "%" );
		prepStmt.setString(2, "%" + strAmount + "%" );
		prepStmt.setString(3, "%" + prodName + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate( int orderID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT dborderid as ID					, "
				+ "						dbordertime AS Time		, "
				+ "						dborderamount AS Amount		, "
				+ "						dbproductid AS Product  "
				+ " FROM orders 								  "
				+ " WHERE dborderid = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, orderID );
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strTime, String strAmount, String pos) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
  		Integer posID = 0;
  		String ourSQLPos = "SELECT dbproductid from products where dbproduct = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("dbproductid");
  		}
		
		String ourSQLInsert = "INSERT INTO orders "
							+ " 		(dbordertime, dborderamount, dbproductid) "
							+ " VALUES 	(       ?,			 ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString( 1, strTime);
		prepStmt.setString( 2, strAmount);
		prepStmt.setInt(3, posID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strTime, String strAmount, String pos, int orderID) throws SQLException {
		
		int rows = 0;
		
  		// Week 12
		// Created variable to hold value id of position from userpositions table
  		Integer posID = 0;
  		String ourSQLPos = "SELECT dbproductid from products where dbproduct = ?";  

  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("dbproductid");
  		}
  		
		
		String ourSQLUpdate = "UPDATE orders " 
							+ " SET "
							+ " 	dbordertime 	=	?	, "
							+ " 	dborderamount 	=	?	, "
							+ "     dbproductid =  ?     "
							+ " WHERE dborderid    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString( 1, strTime);
		prepStmt.setString( 2, strAmount);
		prepStmt.setInt(3, posID);		
		prepStmt.setDouble(4, orderID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord( int orderID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM orders " 
							+ "WHERE dborderid = ? " ;

		// prepare our statment
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
		
		// set our parms
		prepStmt.setInt(1, orderID);
		
		// get rows from delete
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		// return the number of rows affected
		return rows;
	}
}
