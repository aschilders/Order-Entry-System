import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class menuItemUpdatePanelBuilder extends JPanel {
	private JTextField itemTextField;
	private JTextField priceTextField;
	private JTextField descTextField;
	private JComboBox<String> comboField;

	public menuItemUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel itemPrompt = new JLabel("Item Name");
		itemTextField = new JTextField(45);
		
		JLabel typePrompt = new JLabel("Item Type");
		comboField = new JComboBox<String>();
	    comboField.addItem("");
	    try {
	    	menuPositionsManager getProd = new menuPositionsManager();
		    
		    ResultSet menuInfo = menuPositionsManager.selectPositions();
		    
		    while (menuInfo.next()) {
		    	String position = menuInfo.getString("menutype");
		    	comboField.addItem(position);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
		
		JLabel pricePrompt = new JLabel("Item Price");
		priceTextField = new JTextField(45);
		
		JLabel descPrompt = new JLabel("Item Description");
		descTextField = new JTextField(45);

		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(10,1));
		setBorder(BorderFactory.createTitledBorder("Enter Menu Item Information"));
		
		
		// add our labels and text boxes
		add(itemPrompt);
		add(itemTextField);
		
		add(typePrompt);
		add(comboField);
		
		add(pricePrompt);
		add(priceTextField);
		
		add(descPrompt);
		add(descTextField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getItem() {
		return itemTextField.getText();
	}
 
	public String getPrice() {
		return priceTextField.getText();
	}
	
	public String getDesc() {
		return descTextField.getText();
	}
 
	public String getCombo() {
		return comboField.getSelectedItem().toString();
	}

	
	
	public void setItem(String fname) {
		this.itemTextField.setText(fname);
	}

	public void setPrice(String lname) {
		this.priceTextField.setText(lname);
	}
	
	public void setDesc(String address) {
		this.descTextField.setText(address);
	}

	public void setCombo(int position) {
		comboField.setSelectedIndex(position);
	}
	
	public void clear() {
		itemTextField.setText("");
		priceTextField.setText("");
		descTextField.setText("");
		comboField.setSelectedIndex(0);
	}
	
	
}
