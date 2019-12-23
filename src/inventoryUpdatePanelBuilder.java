import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class inventoryUpdatePanelBuilder extends JPanel {
	private JTextField itemTextField;
	private JComboBox<String> comboField;

	public inventoryUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel itemPrompt = new JLabel("Item Count");
		itemTextField = new JTextField(45);
		
		JLabel typePrompt = new JLabel("Item Name");
		comboField = new JComboBox<String>();
	    comboField.addItem("");
	    try {
	    	InventoryPositionsManager getProd = new InventoryPositionsManager();
		    
		    ResultSet menuInfo = InventoryPositionsManager.selectPositions();
		    
		    while (menuInfo.next()) {
		    	String position = menuInfo.getString("menuitemname");
		    	comboField.addItem(position);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(10,1));
		setBorder(BorderFactory.createTitledBorder("Enter Inventory Information"));
		
		
		// add our labels and text boxes
		
		add(typePrompt);
		add(comboField);
		
		add(itemPrompt);
		add(itemTextField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getItem() {
		return itemTextField.getText();
	}
 
	public String getCombo() {
		return comboField.getSelectedItem().toString();
	}

	
	
	public void setItem(String fname) {
		this.itemTextField.setText(fname);
	}

	public void setCombo(int position) {
		comboField.setSelectedIndex(position);
	}
	
	public void clear() {
		itemTextField.setText("");
		comboField.setSelectedIndex(0);
	}
	
	
}
