import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class employeeTypesUpdatePanelBuilder extends JPanel {
	private JTextField employeeTypeTextField; 		// name
	private JTextField employeeDescTextField;	// phone

	public employeeTypesUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel employeeTypePrompt = new JLabel("Employee Type");
		employeeTypeTextField = new JTextField(45);
		
		JLabel employeeDescPrompt = new JLabel("Description");
		employeeDescTextField = new JTextField(45);
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Employee Type Information"));
		
		
		// add our labels and text boxes
		add(employeeTypePrompt);
		add(employeeTypeTextField);
		
		add(employeeDescPrompt);
		add(employeeDescTextField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getEmployeeType() {
		return employeeTypeTextField.getText();
	}
 
	public String getEmployeeDesc() {
		return employeeDescTextField.getText();
	}

	public void setEmployeeType(String type) {
		this.employeeTypeTextField.setText(type);
	}

	public void setEmployeeDesc(String desc) {
		this.employeeDescTextField.setText(desc);
	}
	
	public void clear() {
		employeeTypeTextField.setText("");
		employeeDescTextField.setText("");
	}
	
	
}
