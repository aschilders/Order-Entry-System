import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ordersUpdatePanelBuilder extends JPanel {
	private JTextField amountTextField; 		// name
	private JTextField timeTextField;
	
	//Week 12
	private JComboBox<String> productComboField;       // positions

	public ordersUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel timePrompt = new JLabel("Time");
		timeTextField = new JTextField(45);
		
		JLabel amountPrompt = new JLabel("Amount");
		amountTextField = new JTextField(45);
		
	    JLabel productPrompt = new JLabel("Product");
	    productComboField = new JComboBox<String>();
	    productComboField.addItem("");
	    try {
	    	productPositionsManager getProd = new productPositionsManager();
		    
		    ResultSet prodInfo = productPositionsManager.selectPositions();
		    
		    while (prodInfo.next()) {
		    	String position = prodInfo.getString("dbproduct");
		    	productComboField.addItem(position);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
		
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Order Information"));
		
		
		// add our labels and text boxes
		add(timePrompt);
		add(timeTextField);
		
		add(amountPrompt);
		add(amountTextField);
		
		// Week 12
	    add(productPrompt);
	    add(productComboField);
		
	}

	// create our getters.  
	// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getTime() {
		return timeTextField.getText();
	}
	
	public String getAmount() {
		return amountTextField.getText();
	}

	public String getProduct()
	{
		return productComboField.getSelectedItem().toString();
	}

	public void setProduct(int product)
	{
		productComboField.setSelectedIndex(product);
	} 
	
	public void setTime(String time) {
		this.timeTextField.setText(time);
	}
	
	public void setAmount(String amount) {
		this.amountTextField.setText(amount);
	}
	
	public void clear() {
		timeTextField.setText("");
		amountTextField.setText("");
		productComboField.setSelectedIndex(0);
		
	}
	
	
}
