import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class productsUpdatePanelBuilder extends JPanel {
	private JTextField productTextField; 		// name

	//Week 12
	private JComboBox<String> catComboField;       // positions

	public productsUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel productPrompt = new JLabel("Product");
		productTextField = new JTextField(45);
		
	    JLabel catPrompt = new JLabel("Category");
	    catComboField = new JComboBox<String>();
	    catComboField.addItem("");
	    try {
	    	userPositionsManager getCat = new userPositionsManager();
		    
		    ResultSet catInfo = userPositionsManager.selectPositions();
		    
		    while (catInfo.next()) {
		    	String position = catInfo.getString("dbcategory");
		    	catComboField.addItem(position);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
		
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Product Information"));
		
		
		// add our labels and text boxes
		add(productPrompt);
		add(productTextField);
		
		// Week 12
	    add(catPrompt);
	    add(catComboField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getProduct() {
		return productTextField.getText();
	}

	public String getCat()
	{
		return catComboField.getSelectedItem().toString();
	}

	public void setCat(int pos)
	{
		catComboField.setSelectedIndex(pos);
	} 
	
	public void setProduct(String name) {
		this.productTextField.setText(name);
	}
	
	public void clear() {
		productTextField.setText("");
		catComboField.setSelectedIndex(0);
		
	}
	
	
}
