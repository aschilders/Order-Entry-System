import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class orderEntryUpdatePanelBuilder extends JPanel {
	private JTextField timeTextField;
	private JTextField dateTextField;
	private JComboBox<String> comboField1;
	private JComboBox<String> comboField2;

	public orderEntryUpdatePanelBuilder() {
		
		JLabel customerPrompt = new JLabel("Customer First Name");
		comboField1 = new JComboBox<String>();
	    comboField1.addItem("");
	    try {
	    	orderCustomerPositionsManager getProd = new orderCustomerPositionsManager();
		    
		    ResultSet menuInfo1 = orderCustomerPositionsManager.selectPositions();
		    
		    while (menuInfo1.next()) {
		    	String position = menuInfo1.getString("customerfirstname");
		    	comboField1.addItem(position);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	    
	    JLabel employeePrompt = new JLabel("Employee First Name");
		comboField2 = new JComboBox<String>();
	    comboField2.addItem("");
	    try {
	    	orderEmployeePositionsManager getProd = new orderEmployeePositionsManager();
		    
		    ResultSet menuInfo2 = orderEmployeePositionsManager.selectPositions();
		    
		    while (menuInfo2.next()) {
		    	String position = menuInfo2.getString("employeefirstname");
		    	comboField2.addItem(position);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
		
		JLabel timePrompt = new JLabel("Order Time");
		timeTextField = new JTextField(45);
		
		
		JLabel datePrompt = new JLabel("Item Date");
		dateTextField = new JTextField(45);

		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(10,1));
		setBorder(BorderFactory.createTitledBorder("Enter Order Entry Information"));
		
		
		// add our labels and text boxes
		add(customerPrompt);
		add(comboField1);
		
		add(employeePrompt);
		add(comboField2);
		
		add(timePrompt);
		add(timeTextField);
		
		add(datePrompt);
		add(dateTextField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getTime() {
		return timeTextField.getText();
	}
 
	public String getDate() {
		return dateTextField.getText();
	}
	
	public String getCustomer() {
		return comboField1.getSelectedItem().toString();
	}
 
	public String getEmployee() {
		return comboField2.getSelectedItem().toString();
	}

	
	
	public void setTime(String time) {
		this.timeTextField.setText(time);
	}

	public void setDate(String date) {
		this.dateTextField.setText(date);
	}
	
	public void setCombo1(int position) {
		comboField1.setSelectedIndex(position);
	}

	public void setCombo2(int position) {
		comboField2.setSelectedIndex(position);
	}
	
	public void clear() {
		timeTextField.setText("");
		dateTextField.setText("");
		comboField1.setSelectedIndex(0);
		comboField2.setSelectedIndex(0);
	}
	
	
}
