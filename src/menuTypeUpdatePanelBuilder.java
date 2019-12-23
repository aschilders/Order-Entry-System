
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class menuTypeUpdatePanelBuilder extends JPanel {
	private JTextField menuTypeTextField; 		// name

	public menuTypeUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel menuTypePrompt = new JLabel("menuType");
		menuTypeTextField = new JTextField(45);

		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Menu Type Information"));
		
		
		// add our labels and text boxes
		add(menuTypePrompt);
		add(menuTypeTextField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getMenuType() {
		return menuTypeTextField.getText();
	}


	public void setMenuType(String menuType) {
		this.menuTypeTextField.setText(menuType);
	}


	public void clear() {
		menuTypeTextField.setText("");
	}
	
	
}
