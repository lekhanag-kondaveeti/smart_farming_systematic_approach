import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Smart_Farming extends JFrame {
    private JTextField signUpFarmerIdField;
    private JTextField signUpNameField;
    private JPasswordField signUpPasswordField;
    private JTextField loginFarmerIdField;
    private JPasswordField loginPasswordField;
    private JTextField loginFarmerIdField1;
    private JPasswordField loginPasswordField1;

    public Smart_Farming() {
        // Initialize the database
        setTitle("Farmer Sign Up/Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);

        // Create a panel for padding space
        JPanel paddingPanel = new JPanel();
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        paddingPanel.setLayout(new BorderLayout());

        // Create panels for sign up and login components
        JPanel signUpPanel = createSignUpPanel();
        JPanel loginPanel = createLoginPanel();
        JPanel adminPanel = createAdminPanel();

        // Create tabbed pane to switch between sign up and login
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sign Up", signUpPanel);
        tabbedPane.addTab("Login", loginPanel);
        tabbedPane.addTab("Admin", adminPanel);

        // Add tabbed pane to the padding panel
        paddingPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add padding panel to the frame
        getContentPane().add(paddingPanel);

    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5)); // Add gaps between components

        // Sign Up components
        JLabel signUpFarmerIdLabel = new JLabel("Farmer ID:");
        signUpFarmerIdField = new JTextField(15); // Reduced text field size
        JLabel signUpNameLabel = new JLabel("Name:");
        signUpNameField = new JTextField(15); // Reduced text field size
        JLabel signUpPasswordLabel = new JLabel("Password:");
        signUpPasswordField = new JPasswordField(15); // Reduced text field size

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        // Add components to the sign-up panel
        panel.add(signUpFarmerIdLabel);
        panel.add(signUpFarmerIdField);
        panel.add(signUpNameLabel);
        panel.add(signUpNameField);
        panel.add(signUpPasswordLabel);
        panel.add(signUpPasswordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(signUpButton);

        return panel;
    }

    private void handleSignIn() {
    	String farmerId = signUpFarmerIdField.getText();
        String name = signUpNameField.getText();
        String password = new String(signUpPasswordField.getPassword());
        
        if (farmerId.isEmpty() || name.isEmpty() || password.isEmpty() ) {
        	JOptionPane.showMessageDialog(this, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        else
        {
        	String driverClassName = "oracle.jdbc.driver.OracleDriver";
	        try {
	            // Load the JDBC driver
	            Class.forName(driverClassName);

	            // Establish a connection to the database
	            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "lekhan", "lekh");

	            // Perform database operations using the connection
				Statement stmt=con.createStatement();  
				int a=stmt.executeUpdate("insert into farmer values("+farmerId+",'"+name+"','"+password+"')");  
				if(a>0)
					JOptionPane.showMessageDialog(this, "Sign-up successful", "Message", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "Fail", "Error", JOptionPane.ERROR_MESSAGE);
	            // Close the connection
	            con.close();

	            System.out.println("Connection closed successfully.");
	        } catch (ClassNotFoundException e) {
	            System.err.println("Failed to load JDBC driver: " + e.getMessage());
	        } catch (SQLException e) {
	            System.err.println("Failed to connect to the database: " + e.getMessage());
	        }
	    }


// Clear sign in fields
//Clear the sign up fields
signUpFarmerIdField.setText("");
signUpNameField.setText("");
signUpPasswordField.setText("");

}
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5)); // Add gaps between components

        // Login components
        JLabel loginFarmerIdLabel = new JLabel("Farmer ID:");
        loginFarmerIdField1 = new JTextField(15); // Reduced text field size
        JLabel loginPasswordLabel = new JLabel("Password:");
        loginPasswordField1 = new JPasswordField(15); // Reduced text field size

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Add components to the login panel
        panel.add(loginFarmerIdLabel);
        panel.add(loginFarmerIdField1);
        panel.add(loginPasswordLabel);
        panel.add(loginPasswordField1);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        return panel;
    }
    
    @SuppressWarnings("unused")
	private void displayInfoMessage(String message) {
        Dialog infoDialog = new Dialog(this, "Info Message", true);
        infoDialog.setLayout(new FlowLayout());
        infoDialog.setSize(200, 100);

        Label messageLabel = new Label(message);
        Button closeButton = new Button("Close");

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                infoDialog.dispose();
            }
        });

        infoDialog.add(messageLabel);
        infoDialog.add(closeButton);
        infoDialog.setVisible(true);
    }
    
    private void handleLogin() {
    	String farmerId = loginFarmerIdField1.getText();
        String password = new String(loginPasswordField1.getPassword());
       

    if (farmerId.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter ID and password", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    else {
    	String driverClassName = "oracle.jdbc.driver.OracleDriver";

    	try {
        // Load the JDBC driver
        Class.forName(driverClassName);
        
        // Establish a connection to the database
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "lekhan", "lekh");

        // Perform database operations using the connection
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select password from farmer where farmer_id="+farmerId+"and password='"+password+"'");  
		if(rs.next())
		{	
			JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
        	new Home(Integer.parseInt(farmerId));
		}
		else
			JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        // Close the connection
        con.close();

        System.out.println("Connection closed successfully.");
    } catch (ClassNotFoundException e) {
        System.err.println("Failed to load JDBC driver: " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Failed to connect to the database: " + e.getMessage());
    }
}

        
    
    // Clear login fields
    loginFarmerIdField.setText("");
    loginPasswordField.setText("");
}
    
    
    
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5)); // Add gaps between components

        // Login components
        JLabel loginFarmerIdLabel = new JLabel("Admin ID:");
        loginFarmerIdField = new JTextField(15); // Reduced text field size
        JLabel loginPasswordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField(15); // Reduced text field size

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAdminLogin();
            }
        });

        // Add components to the login panel
        panel.add(loginFarmerIdLabel);
        panel.add(loginFarmerIdField);
        panel.add(loginPasswordLabel);
        panel.add(loginPasswordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        return panel;
    }
    
    private void handleAdminLogin() {
    	String adminId = loginFarmerIdField.getText();
        String password = new String(loginPasswordField.getPassword());


    if (adminId.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter ID and password", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    else {
    String driverClassName = "oracle.jdbc.driver.OracleDriver";

    try {
        // Load the JDBC driver
        Class.forName(driverClassName);
        
        // Establish a connection to the database
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "lekhan", "lekh");

        // Perform database operations using the connection
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select password from admin where admin_id='"+adminId+"'and password='"+password+"'");  
		if(rs.next())
		{	
			JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
        	new AdminPage(adminId);
		}
		else
			JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        // Close the connection
        con.close();

        System.out.println("Connection closed successfully.");
    } catch (ClassNotFoundException e) {
        System.err.println("Failed to load JDBC driver: " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Failed to connect to the database: " + e.getMessage());
    }
}

        
    
    // Clear login fields
    loginFarmerIdField.setText("");
    loginPasswordField.setText("");
}
    
    
    
    


    public static void main(String[] args) {
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Smart_Farming().setVisible(true);
            }
        });
    }
}


