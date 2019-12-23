import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;


public class customerUpdateForm extends JFrame {
	// GRID FORMATTING
	private final int GRIDWIDTH 	= 900;		// size our grid 
	private final int GRIDHEIGHT	= 300;


	customerUpdatePanelBuilder userInfoPanel;		// panel for user information
	JPanel buttonPanel;				// panel for buttons
	JPanel listPanel;				// panel for our list
	String searchString = "";		 
	JScrollPane scrollPane ;		// a scroll pane to hold our list
	JTable ourTable;				// holds the result set
	int selectedRecordID = 0;		// holds our record id from the list
	
	public customerUpdateForm() throws SQLException {
		// set the window title
		setTitle("Update Customers");
		// set our action when the user close
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// create a panel builder object
		userInfoPanel = new customerUpdatePanelBuilder();
		
		// build the button panel object
		buildButtonPanel();
		
		// build the list panel object
		buildListPanel();
		
		// create a BorderManager layout
		setLayout(new BorderLayout());
		
		// add the panels to the content pane
		add(userInfoPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);

		// pack and display the window
		pack();
		setVisible(true);
	
	}
	
	
	private void buildListPanel() throws SQLException {
		// Create the panel
		listPanel = new JPanel();
		
		// add a titled border to the panel
		listPanel.setBorder(BorderFactory.createTitledBorder("Customer Information"));
		
		// create an object to access the db
		customerTableManager um = new customerTableManager();
		
		// create a resultset to hold the blank search for when first startup
		ResultSet userInfo = um.selectUsers("", "", "", "", "", "", "", "", "", "");
		
		// user rs2xml to display our resultset
		ourTable = new JTable(DbUtils.resultSetToTableModel(userInfo));
		
		// GRID FORMATTING
		formatList();

		// scroll pane so we scroll
		scrollPane = new JScrollPane(ourTable);
		listPanel.add(scrollPane);
		
		// add a listener that will update our selectedRecordID with the currently selected record
		ourTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
				
				public void valueChanged(ListSelectionEvent event) {
					// check to mae sure table is begin reloaded
					if (ourTable.getSelectedRow() >= 0) {
						
						
						try {
							// get the id of the row
							selectedRecordID = (int) ourTable.getValueAt(ourTable.getSelectedRow(),0);
							ResultSet updateInfo = customerTableManager.selectUpdate(selectedRecordID);

							while(updateInfo.next()) {
								userInfoPanel.setFirstName(updateInfo.getString("Firstname"));
								userInfoPanel.setLastName(updateInfo.getString("Lastname"));
								userInfoPanel.setAddress(updateInfo.getString("Address"));
								userInfoPanel.setCity(updateInfo.getString("City"));
								userInfoPanel.setState(updateInfo.getString("State"));
								userInfoPanel.setZip(updateInfo.getString("Zip"));
								userInfoPanel.setEmail(updateInfo.getString("Email"));
								userInfoPanel.setPassword(updateInfo.getString("Password"));
								userInfoPanel.setPhone(updateInfo.getString("Phone"));
								userInfoPanel.setCombo(updateInfo.getInt("List"));
							}
						
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			});
		
	}

	
	// GRID FORMATTING
	private void formatList() {
		// This method formats our columns to size
		// Add this line below where you place the result set to format the column
		TableColumnModel columnModel = ourTable.getColumnModel();

	    columnModel.getColumn(0).setPreferredWidth(50);
	    columnModel.getColumn(1).setPreferredWidth(80);
	    columnModel.getColumn(2).setPreferredWidth(80);
	    columnModel.getColumn(3).setPreferredWidth(100);
	    columnModel.getColumn(4).setPreferredWidth(100);
	    columnModel.getColumn(5).setPreferredWidth(40);
	    columnModel.getColumn(6).setPreferredWidth(100);
	    columnModel.getColumn(7).setPreferredWidth(100);
	    columnModel.getColumn(8).setPreferredWidth(100);
	    columnModel.getColumn(9).setPreferredWidth(100);
	    columnModel.getColumn(10).setPreferredWidth(50);
	   
		ourTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ourTable.setPreferredScrollableViewportSize(new Dimension(GRIDWIDTH,GRIDHEIGHT));
		//ourTable.setFillsViewportHeight(true);
		
	}

	
	private void buildButtonPanel() {
		// create a panel for the buttons
		buttonPanel = new JPanel();
		
		// create a Search button and add an action listener
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new SearchButtonListener());
				
		// create a update button and add an action listener
		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new InsertButtonListener());

		
		// create a update button and add an action listener
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdateButtonListener());
		
		// create a delete button and add an action listener
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());

		
		// create a clear button and add an action listener
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());

		// create an exit button to exit the application
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener());
		
		// add buttons to the panel
		buttonPanel.add(searchButton);
		buttonPanel.add(insertButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);
		
		// set a default button
		getRootPane().setDefaultButton(searchButton);
		
	}
	
	private void resetForm() throws SQLException {
		
		// clear all the input boxes
		userInfoPanel.clear();
		
		// refresh the gird
		ResultSet searchInfo = null;
		
		// create an object to instantiate userTableManager class
		customerTableManager um = new customerTableManager();
		
		// call our selectUsers with no filters
		searchInfo = customerTableManager.selectUsers("", "", "", "", "", "", "", "", "", "");

		// reset the table to new search criteria
		ourTable.setModel(DbUtils.resultSetToTableModel(searchInfo));
		
		// GRID FORMATTING
		formatList();

		// reinitialize our selected record
		selectedRecordID = 0;

	}

	// Handle search, filter button
		public class SearchButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e1) {
				// Get the user information from the text fields
				String strFirst = userInfoPanel.getFirstName();
				String strLast = userInfoPanel.getLastName();
				String strAddress = userInfoPanel.getAddress();
				String strCity = userInfoPanel.getCity();
				String strState = userInfoPanel.getState();
				String strZip = userInfoPanel.getZip();
				String strEmail = userInfoPanel.getEmail();
				String strPassword = userInfoPanel.getPassword();
				String strPhone = userInfoPanel.getPhone();
				String strCombo = userInfoPanel.getCombo();

				// week 12
				
				// create a ResultSet variable to hold our search info
				ResultSet searchInfo = null;
				
				try {
					// create an object to instantiate the Connection to the table	
					customerTableManager getInfo = new customerTableManager();
					// create a blank search of the table
					searchInfo = customerTableManager.selectUsers(strFirst, strLast, strAddress, strCity, strState, strZip, strEmail, strPassword, strPhone, strCombo);

					// reset the table to new search criteria
					ourTable.setModel(DbUtils.resultSetToTableModel(searchInfo));

					// GRID FORMATTING
					formatList();
					
					
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}

		}


	public class InsertButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String strFirst = userInfoPanel.getFirstName();
			String strLast = userInfoPanel.getLastName();
			String strAddress = userInfoPanel.getAddress();
			String strCity = userInfoPanel.getCity();
			String strState = userInfoPanel.getState();
			String strZip = userInfoPanel.getZip();
			String strEmail = userInfoPanel.getEmail();
			String strPassword = userInfoPanel.getPassword();
			String strPhone = userInfoPanel.getPhone();
			String strCombo = userInfoPanel.getCombo();
			
			
			if (strFirst.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strLast.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strAddress.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strCity.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strState.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strZip.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strEmail.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strPassword.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strPhone.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			if (strCombo.equals("")){
				JOptionPane.showMessageDialog(null, "Error, missing parameter.");
				return;
			}
			
			
			try {

				int rows = 0;
				
				// instantiate our userTableManager
				customerTableManager userManager = new customerTableManager();
				
				// insert the record
				rows = userManager.insertRecord(strFirst, strLast, strAddress, strCity, strState, strZip, strEmail, strPassword, strPhone, strCombo);
				
				if (rows > 0) {
					// let the user know the record was added
					JOptionPane.showMessageDialog(null,"Record successfully added!");

					// rest the form
					resetForm();
					
					
				}
				
				else {
					JOptionPane.showMessageDialog(null,"Record insert failed!");
				}
					
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	
	}

	public class UpdateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e1) {
			if (selectedRecordID < 1) {
				JOptionPane.showMessageDialog(null,"Please select a record to update.");
				return;
			}
			
			// Get the user information from the text fields
			String strFirst = userInfoPanel.getFirstName();
			String strLast = userInfoPanel.getLastName();
			String strAddress = userInfoPanel.getAddress();
			String strCity = userInfoPanel.getCity();
			String strState = userInfoPanel.getState();
			String strZip = userInfoPanel.getZip();
			String strEmail = userInfoPanel.getEmail();
			String strPassword = userInfoPanel.getPassword();
			String strPhone = userInfoPanel.getPhone();
			String strCombo = userInfoPanel.getCombo();
			
			int rows = 0;
			customerTableManager updater;
			
			
			try {
				// create an object to instantiate the Connection to the table	
				updater = new customerTableManager();
				rows = updater.updateRecord(strFirst, strLast, strAddress, strCity, strState, strZip, strEmail, strPassword, strPhone, strCombo, selectedRecordID);
				
				if (rows > 0) {
					JOptionPane.showMessageDialog(null, "Record updated successfully");
					
					// clear all the input boxes
					userInfoPanel.clear();
					
					// refresh the gird
					
					ResultSet searchInfo = null;
					
					// create an object to0 instantiate userTableManager class
					customerTableManager getInfo = new customerTableManager();
					
					// call our selectUsers with no filters
					searchInfo = customerTableManager.selectUsers("", "", "", "", "", "", "", "", "", "");

					// reset the table to new search criteria
					ourTable.setModel(DbUtils.resultSetToTableModel(searchInfo));

					// GRID FORMATTING
					formatList();


					// reinitialize our selected record
					selectedRecordID = 0;

			

					
				} else {
					JOptionPane.showMessageDialog(null, "Record Update Failed");
				}
				
				
				
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	public class ClearButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e1) {
			
			try {
				// reset the form
				resetForm();
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	public class DeleteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e1) {
		
			// Verify we have a record selected
			if (selectedRecordID < 1) {
				JOptionPane.showMessageDialog(null,"Please select a record to delete");
				return;
			}
				
				// verify they want to delete
			if( JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record, "
					+ "and all connected records?\nThis action cannot be undone.", "Confirm Delete", 
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {  
					return;
			}
			
			// initialize variable to hold records affected
			int rows = 0;
			customerTableManager Deleter;
			
			
			try {
				// create an object to instantiate the Connection to the table	
				Deleter = new customerTableManager();
				rows = Deleter.deleteRecord(selectedRecordID);
				
				if (rows > 0) {
					//reset the form
					resetForm();

					// inform user
					JOptionPane.showMessageDialog(null, "Record Deleted successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Record Delete Failed");
				}
				
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	// exit the form
	public class ExitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// exit the app
			dispose();

		}

	}

	// test driver for form
	public static void main(String[] arg0) throws SQLException {
		customerUpdateForm dbgs = new customerUpdateForm();
	}
}
