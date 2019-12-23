import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class productsTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://restaurant.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public productsTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectUsers(String strProduct, String catName) throws SQLException {		
		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		// week 12
		String ourSQLSelect = "SELECT 	p.dbproductid as ID, "
							+ "			p.dbproduct AS Product, "
							+ "			c.dbcategory AS Category "
							+ "FROM products p "
							+ " LEFT JOIN categories c ON c.dbcategoryid =  p.dbcategoryid "
							+ "WHERE "
							+ " 		dbproduct LIKE 	 ? "
							+ " AND 	dbcategory LIKE ? ";
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strProduct + "%" );
		prepStmt.setString(2, "%" + catName + "%" );
		
		// execute the query
		ResultSet userResults = prepStmt.executeQuery();
		return userResults;
		
	}

	public static ResultSet selectUpdate( int productID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT dbproductid as ID					, "
				+ "						dbproduct AS Product		, "
				+ "						dbcategoryid AS Category  "
				+ " FROM products 								  "
				+ " WHERE dbproductid = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, productID );
		
		ResultSet userResults = prepStmt.executeQuery();
		
		return userResults;
	}

	
	public int insertRecord(String strProduct, String pos) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
  		Integer posID = 0;
  		String ourSQLPos = "SELECT dbcategoryid from categories where dbcategory = ?";  
        
  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("dbcategoryid");
  		}
		
		String ourSQLInsert = "INSERT INTO products "
							+ " 		(dbproduct, dbcategoryid) "
							+ " VALUES 	(       ?,			 ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString( 1, strProduct);
		prepStmt.setInt(2, posID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String strProduct, String pos, int productID) throws SQLException {
		
		int rows = 0;
		
  		// Week 12
		// Created variable to hold value id of position from userpositions table
  		Integer posID = 0;
  		String ourSQLPos = "SELECT dbcategoryid from categories where dbcategory = ?";  

  		// Create a Statement object.
  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
  		prepPos.setString(1, pos);
  		ResultSet posResults = prepPos.executeQuery();
  		//Gets the result of what was returned from the database for the name passed to the method
  		while (posResults.next()) {
  			posID = posResults.getInt("dbcategoryid");
  		}
  		
		
		String ourSQLUpdate = "UPDATE products " 
							+ " SET "
							+ " 	dbproduct 	=	?	, "
							+ "     dbcategoryid =  ?     "
							+ " WHERE dbproductid    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString( 1, strProduct);
		prepStmt.setInt(2, posID);		
		prepStmt.setDouble(3, productID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord( int productID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM products " 
							+ "WHERE dbproductid = ? " ;

		// prepare our statment
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
		
		// set our parms
		prepStmt.setInt(1, productID);
		
		// get rows from delete
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		// return the number of rows affected
		return rows;
	}
}
