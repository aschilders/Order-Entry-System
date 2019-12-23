import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.acl.Permission;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class NavigationMenu extends JFrame {
	JPanel buttonPanel;		// panel for our buttons
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;

	public NavigationMenu() {
		
		// set the title of the window
		setTitle("Select Menu Option (Scroll Wheel Not Resize)") ;
		
		setResizable(false);

		// specify the action to take when the close button clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// build our button panel
		buildButtonPanel();
		
		// create a borderlayout manager
		setLayout(new BorderLayout());
		
		JScrollPane scrPane = new JScrollPane(buttonPanel);
		scrPane.getVerticalScrollBar().setUnitIncrement(5);
		JScrollBar vertical = scrPane.getVerticalScrollBar();
		vertical.setPreferredSize( new Dimension(0,0) );
		add(scrPane);
		
		// pack and display the window
		setSize(500, 650);
		
		// set visible
		setVisible(true);
		
	
	}

	// build the button panel
	private void buildButtonPanel() {
		// construct button panel, add buttons and listeners
		buttonPanel = new JPanel();
		
		try {                
			image1 = ImageIO.read(new File("person.png"));
		} catch (IOException ex) {
		    // handle exception...
		}
		
		JLabel picLabel1 = new JLabel(new ImageIcon(image1));
		
		try {                
			image2 = ImageIO.read(new File("scroll.png"));
		} catch (IOException ex) {
		    // handle exception...
		}
		
		JLabel picLabel2 = new JLabel(new ImageIcon(image2));
		
		try {                
			image3 = ImageIO.read(new File("order.png"));
		} catch (IOException ex) {
		    // handle exception...
		}
		
		JLabel picLabel3 = new JLabel(new ImageIcon(image3));
		
		try {                
			image4 = ImageIO.read(new File("table.png"));
		} catch (IOException ex) {
		    // handle exception...
		}
		
		JLabel picLabel4 = new JLabel(new ImageIcon(image4));
		
		// create a search users button and add listener
		JButton manageCustomersButton = new JButton("Manage Customers");
		manageCustomersButton.addActionListener(new manageCustomersButtonListener());
		manageCustomersButton.setPreferredSize(new Dimension(30, 50));
		
		JButton manageEmployeesButton = new JButton("Manage Employees");
		manageEmployeesButton.addActionListener(new manageEmployeesButtonListener());
		
		JButton manageEmployeeTypesButton = new JButton("Manage Employee Types");
		manageEmployeeTypesButton.addActionListener(new manageEmployeeTypesButtonListener());
		
		JButton manageInventoryButton = new JButton("Manage Inventory");
		manageInventoryButton.addActionListener(new manageInventoryButtonListener());
		
		JButton manageMenuItemsButton = new JButton("Manage Menu Items");
		manageMenuItemsButton.addActionListener(new manageMenuItemsButtonListener());
		
		JButton manageMenuTypesButton = new JButton("Manage Menu Types");
		manageMenuTypesButton.addActionListener(new manageMenuTypesButtonListener());
		
		JButton manageOrderDetailsButton = new JButton("Manage Order Details");
		manageOrderDetailsButton.addActionListener(new manageOrderDetailsButtonListener());
		
		JButton manageOrderEntryButton = new JButton("Manage Order Entry");
		manageOrderEntryButton.addActionListener(new manageOrderEntryButtonListener());
		
		JButton manageTicketsButton = new JButton("Manage Tickets");
		manageTicketsButton.addActionListener(new manageTicketsButtonListener());
		
		JButton manageTicketDetailsButton = new JButton("Manage Ticket Details");
		manageTicketDetailsButton.addActionListener(new manageTicketDetailsButtonListener());
		
		JButton manageTablesButton = new JButton("Manage Tables");
		manageTablesButton.addActionListener(new manageTablesButtonListener());
		
		// set the layout and add the buttons
		buttonPanel.setLayout(new GridLayout(20,1));
		// add the buttons
		buttonPanel.add(picLabel1);
		buttonPanel.add(manageCustomersButton);
		buttonPanel.add(manageEmployeeTypesButton);
		buttonPanel.add(manageEmployeesButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(2, 0)));
		buttonPanel.add(picLabel2);
		buttonPanel.add(manageMenuTypesButton);
		buttonPanel.add(manageMenuItemsButton);
		buttonPanel.add(manageInventoryButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(2, 0)));
		buttonPanel.add(picLabel3);
		buttonPanel.add(manageOrderEntryButton);
		buttonPanel.add(manageOrderDetailsButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(2, 0)));
		buttonPanel.add(picLabel4);
		buttonPanel.add(manageTablesButton);
	}
	
	// handle the manage user button
	public class manageCustomersButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				customerUpdateForm ds = new customerUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageEmployeesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				employeeUpdateForm ds = new employeeUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageEmployeeTypesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				employeeTypesUpdateForm ds = new employeeTypesUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageInventoryButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				inventoryUpdateForm ds = new inventoryUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageMenuItemsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				menuItemUpdateForm ds = new menuItemUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageMenuTypesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				menuTypeUpdateForm ds = new menuTypeUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageOrderDetailsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "Under Construction");
			
			
		}

	}
	
	public class manageOrderEntryButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				orderEntryUpdateForm ds = new orderEntryUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	public class manageTicketsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "Under Construction");
			
			
		}
	}
	
	// handle the manage permission button
	public class manageTicketDetailsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "Under Construction");
			
			
		}

	}
	
	public class manageTablesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				tablesUpdateForm ds = new tablesUpdateForm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}


	
	// exit the form
	public static void main(String[] args) {

		NavigationMenu dbMenu = new NavigationMenu();
		
	}
	
}
