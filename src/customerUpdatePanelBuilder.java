import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class customerUpdatePanelBuilder extends JPanel {
	private JTextField fnameTextField;
	private JTextField lnameTextField;
	private JTextField addressTextField;
	private JTextField cityTextField;
	private JTextField stateTextField;
	private JTextField zipTextField;
	private JTextField emailTextField;
	private JTextField passwordTextField;
	private JTextField phoneTextField;
	private String[] choices = {"Yes", "No"};
	JComboBox comboMessage = new JComboBox(choices);

	public customerUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel fnamePrompt = new JLabel("First Name");
		fnameTextField = new JTextField(45);
		
		JLabel lnamePrompt = new JLabel("Last Name");
		lnameTextField = new JTextField(45);
		
		JLabel addressPrompt = new JLabel("Address");
		addressTextField = new JTextField(45);
		
		JLabel cityPrompt = new JLabel("City");
		cityTextField = new JTextField(45);
		
		JLabel statePrompt = new JLabel("State");
		stateTextField = new JTextField(45);
		
		JLabel zipPrompt = new JLabel("Zip Code");
		zipTextField = new JTextField(45);
		
		JLabel emailPrompt = new JLabel("Email");
		emailTextField = new JTextField(45);
		
		JLabel passwordPrompt = new JLabel("Password");
		passwordTextField = new JTextField(45);
		
		JLabel phonePrompt = new JLabel("Phone Number");
		phoneTextField = new JTextField(45);
		
		JLabel comboPrompt = new JLabel("Mailing List");

		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(10,1));
		setBorder(BorderFactory.createTitledBorder("Enter Customer Information"));
		
		
		// add our labels and text boxes
		add(fnamePrompt);
		add(fnameTextField);
		
		add(lnamePrompt);
		add(lnameTextField);
		
		add(addressPrompt);
		add(addressTextField);
		
		add(cityPrompt);
		add(cityTextField);
		
		add(statePrompt);
		add(stateTextField);
		
		add(zipPrompt);
		add(zipTextField);
		
		add(emailPrompt);
		add(emailTextField);
		
		add(passwordPrompt);
		add(passwordTextField);
		
		add(phonePrompt);
		add(phoneTextField);
		
		add(comboPrompt);
		add(comboMessage);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getFirstName() {
		return fnameTextField.getText();
	}
 
	public String getLastName() {
		return lnameTextField.getText();
	}
	
	public String getAddress() {
		return addressTextField.getText();
	}
 
	public String getCity() {
		return cityTextField.getText();
	}
	
	public String getState() {
		return stateTextField.getText();
	}
 
	public String getZip() {
		return zipTextField.getText();
	}
	
	public String getEmail() {
		return emailTextField.getText();
	}
 
	public String getPassword() {
		return passwordTextField.getText();
	}
	
	public String getPhone() {
		return phoneTextField.getText();
	}
 
	public String getCombo() {
		return comboMessage.getSelectedItem().toString();
	}

	
	
	public void setFirstName(String fname) {
		this.fnameTextField.setText(fname);
	}

	public void setLastName(String lname) {
		this.lnameTextField.setText(lname);
	}
	
	public void setAddress(String address) {
		this.addressTextField.setText(address);
	}

	public void setCity(String city) {
		this.cityTextField.setText(city);
	}
	
	public void setState(String state) {
		this.stateTextField.setText(state);
	}

	public void setZip(String zip) {
		this.zipTextField.setText(zip);
	}
	
	public void setEmail(String email) {
		this.emailTextField.setText(email);
	}

	public void setPassword(String password) {
		this.passwordTextField.setText(password);
	}
	
	public void setPhone(String phone) {
		this.phoneTextField.setText(phone);
	}

	public void setCombo(int position) {
		comboMessage.setSelectedIndex(position);
	}
	
	public void clear() {
		fnameTextField.setText("");
		lnameTextField.setText("");
		addressTextField.setText("");
		cityTextField.setText("");
		stateTextField.setText("");
		zipTextField.setText("");
		emailTextField.setText("");
		passwordTextField.setText("");
		phoneTextField.setText("");
		comboMessage.setSelectedIndex(0);
	}
	
	
}
