import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminPage extends JFrame { 
    private JTextField farmerIdField;
    private JTextField farmIdField;
    private String adminId;
    public AdminPage(String adminId) {
    	this.adminId=adminId;
        setTitle("Admin interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu farmerMenu = new JMenu("Farmer");
        JMenu farmMenu = new JMenu("Farm");
        JMenu viewMenu = new JMenu("View");
        
        // Create menu items for View menu
        JMenuItem viewDetailsItem = new JMenuItem("View details");

        // Create menu items for Farm menu
        JMenuItem farmInsertItem = new JMenuItem("Insert Farm");
        JMenuItem farmUpdateItem = new JMenuItem("Update Farm");
        JMenuItem farmDeleteItem = new JMenuItem("Delete Farm");
        
        //
        JMenuItem farmerInsertItem = new JMenuItem("Insert Farmer");
        JMenuItem farmerUpdateItem = new JMenuItem("Update Farmer");
        JMenuItem farmerDeleteItem = new JMenuItem("Delete Farmer");
        
        // Add action listeners to menu items
        viewDetailsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewDetailsDialog();
            }
        });

        farmInsertItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInsertFarmDialog();
            }
        });

        farmUpdateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUpdateFarmDialog();
            }
        });

        farmDeleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDeleteFarmDialog();
            }
        });
        
        farmerInsertItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInsertFarmerDialog();
            }
        });

        farmerUpdateItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUpdateFarmerDialog();
            }
        });

        farmerDeleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDeleteFarmerDialog();
            }
        });
        
        
        
        
        
        
        farmMenu.add(farmInsertItem);
        farmMenu.add(farmUpdateItem);
        farmMenu.add(farmDeleteItem);
        
        farmerMenu.add(farmerInsertItem);
        farmerMenu.add(farmerUpdateItem);
        farmerMenu.add(farmerDeleteItem);

        viewMenu.add(viewDetailsItem);

        // Add menus to menu bar
        menuBar.add(farmerMenu);
        menuBar.add(farmMenu);
        menuBar.add(viewMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);
        
        
        
        
        
        
        
        
    }
    
    private void viewDetailsDialog()
    {
    	String driverClassName = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "lekhan";
        String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);
            
            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select farmer_id,farm_id,name,location from farm"); 
			StringBuilder sb = new StringBuilder();
			while(rs.next())
			{
				int x=rs.getInt(1);
				int y=rs.getInt(2);
				String m=rs.getString(3);
				String n=rs.getString(4);
            

                sb.append(x).append(" : ").append(y).append(" : ").append(m).append(" at ").append(n).append(".\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Farmer details", JOptionPane.PLAIN_MESSAGE);
            con.close();

        System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException e) {
        	System.err.println("Failed to load JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
        	System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }
    
    private void showInsertFarmDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        // Insert Farm components
        JLabel farmerIdLabel = new JLabel("Farmer ID:");
        farmerIdField = new JTextField(20);
        JLabel farmIdLabel = new JLabel("Farm ID:");
        farmIdField = new JTextField(20);
        JLabel farmNameLabel = new JLabel("Farm Name:");
        JTextField farmNameField = new JTextField(20);
        JLabel farmLocationLabel = new JLabel("Farm Location:");
        JTextField farmLocationField = new JTextField(20);

        JButton addFarmButton = new JButton("Add Farm");
        addFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int farmerId = Integer.parseInt(farmerIdField.getText());
                String farmId = farmIdField.getText();
                String farmName = farmNameField.getText();
                String farmLocation = farmLocationField.getText();

                // Perform insertion logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "lekhan";
                String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();  
			int a=stmt.executeUpdate("insert into farm values("+farmId+",'"+farmName+"',"+farmerId+",'"+farmLocation+"')");  
			if(a>0)
				JOptionPane.showMessageDialog(null, "Farm added successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Invalid details", "Error", JOptionPane.ERROR_MESSAGE);
            // Close the connection
            con.close();

            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException s) {
            System.err.println("Failed to load JDBC driver: " + s.getMessage());
        } catch (SQLException s) {
            System.err.println("Failed to connect to the database: " + s.getMessage());
        }
                // Clear the fields
                farmIdField.setText("");
                farmNameField.setText("");
                farmLocationField.setText("");

            }
        });

        // Add components to the panel
        panel.add(farmerIdLabel);
        panel.add(farmerIdField);
        panel.add(farmIdLabel);
        panel.add(farmIdField);
        panel.add(farmNameLabel);
        panel.add(farmNameField);
        panel.add(farmLocationLabel);
        panel.add(farmLocationField);
        panel.add(new JLabel());
        panel.add(addFarmButton);

        JOptionPane.showOptionDialog(null, panel, "Insert Farm", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private void showUpdateFarmDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        // Update Farm components
        JLabel farmerIdLabel = new JLabel("Farmer ID:");
        farmerIdField = new JTextField(20);
        JLabel farmIdLabel = new JLabel("Farm ID:");
        farmIdField = new JTextField(20);
        JLabel farmNameLabel = new JLabel("Farm Name:");
        JTextField farmNameField = new JTextField(20);
        JLabel farmLocationLabel = new JLabel("Farm Location:");
        JTextField farmLocationField = new JTextField(20);

        JButton updateFarmButton = new JButton("Update Farm");
        updateFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int farmerId = Integer.parseInt(farmerIdField.getText());
                String farmId = farmIdField.getText();
                String farmName = farmNameField.getText();
                String farmLocation = farmLocationField.getText();

                // Perform update logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "lekhan";
                String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();  
			int a = stmt.executeUpdate("UPDATE farm SET farmer_id="+farmerId +"name='" + farmName + "',location ='" + farmLocation + "'WHERE farm_id=" + farmId ); 
			if(a>0)
				JOptionPane.showMessageDialog(null, "Farm updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Farm not found", "Error", JOptionPane.ERROR_MESSAGE);
            // Close the connection
            con.close();
            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException s) {
            System.err.println("Failed to load JDBC driver: " + s.getMessage());
        } catch (SQLException s) {
            System.err.println("Failed to connect to the database: " + s.getMessage());
        }
                // Clear the fields
                farmIdField.setText("");
                farmNameField.setText("");
                farmLocationField.setText("");

            }
        });

        // Add components to the panel
        panel.add(farmerIdLabel);
        panel.add(farmerIdField);
        panel.add(farmIdLabel);
        panel.add(farmIdField);
        panel.add(farmNameLabel);
        panel.add(farmNameField);
        panel.add(farmLocationLabel);
        panel.add(farmLocationField);
        panel.add(new JLabel());
        panel.add(updateFarmButton);

        JOptionPane.showOptionDialog(null, panel, "Update Farm", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private void showDeleteFarmDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        // Delete Farm components
        JLabel farmIdLabel = new JLabel("Farm ID:");
        farmIdField = new JTextField(20);

        JButton deleteFarmButton = new JButton("Delete Farm");
        deleteFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String farmId = farmIdField.getText();

                // Perform delete logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "lekhan";
                String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();  
			int a=stmt.executeUpdate("delete from farm where farm_id="+farmId);  
			if(a>0)
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Farm not found", "Error", JOptionPane.ERROR_MESSAGE);
            // Close the connection
            con.close();
            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException s) {
            System.err.println("Failed to load JDBC driver: " + s.getMessage());
        } catch (SQLException s) {
            System.err.println("Failed to connect to the database: " + s.getMessage());
        }
                // Clear the field
                farmIdField.setText("");

            }
        });

        // Add components to the panel
        panel.add(farmIdLabel);
        panel.add(farmIdField);
        panel.add(new JLabel());
        panel.add(deleteFarmButton);

        JOptionPane.showOptionDialog(null, panel, "Delete Farm", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
    
    private void showInsertFarmerDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Insert Farm components
        JLabel farmerIdLabel = new JLabel("Farmer ID:");
        farmerIdField = new JTextField(20);
        JLabel farmerNameLabel = new JLabel("Farmer Name:");
        JTextField farmerNameField = new JTextField(20);
        JLabel farmerPasswordLabel = new JLabel("Farmer Password:");
        JTextField farmerPasswordField = new JTextField(20);

        JButton addFarmButton = new JButton("Add Farmer");
        addFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int farmerId = Integer.parseInt(farmerIdField.getText());
                String farmerName = farmerNameField.getText();
                String farmerPassword = farmerPasswordField.getText();

                // Perform insertion logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "lekhan";
                String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();  
			int a=stmt.executeUpdate("insert into farmer values("+farmerId+",'"+farmerName+"','"+farmerPassword+"')");  
			if(a>0)
				JOptionPane.showMessageDialog(null, "Farmer added successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Invalid details", "Error", JOptionPane.ERROR_MESSAGE);
            // Close the connection
            con.close();

            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException s) {
            System.err.println("Failed to load JDBC driver: " + s.getMessage());
        } catch (SQLException s) {
            System.err.println("Failed to connect to the database: " + s.getMessage());
        }
                // Clear the fields
                farmerIdField.setText("");
                farmerNameField.setText("");
                farmerPasswordField.setText("");

            }
        });

        // Add components to the panel
        panel.add(farmerIdLabel);
        panel.add(farmerIdField);
        panel.add(farmerNameLabel);
        panel.add(farmerNameField);
        panel.add(farmerPasswordLabel);
        panel.add(farmerPasswordField);
        panel.add(new JLabel());
        panel.add(addFarmButton);

        JOptionPane.showOptionDialog(null, panel, "Insert Farmer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private void showUpdateFarmerDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Update Farm components
        JLabel farmerIdLabel = new JLabel("Farmer ID:");
        farmerIdField = new JTextField(20);
        JLabel farmerNameLabel = new JLabel("Farmer Name:");
        JTextField farmerNameField = new JTextField(20);
        JLabel farmerPasswordLabel = new JLabel("Farmer Password:");
        JTextField farmerPasswordField = new JTextField(20);

        JButton updateFarmButton = new JButton("Update Farmer");
        updateFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int farmerId = Integer.parseInt(farmerIdField.getText());
                String farmerName = farmerNameField.getText();
                String farmerPassword = farmerPasswordField.getText();

                // Perform update logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "lekhan";
                String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();  
			int a = stmt.executeUpdate("UPDATE farmer SET name='" + farmerName + "',password ='" + farmerPassword + "'WHERE farmer_id=" + farmerId ); 
			if(a>0)
				JOptionPane.showMessageDialog(null, "Farmer updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Farmer not found", "Error", JOptionPane.ERROR_MESSAGE);
            // Close the connection
            con.close();
            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException s) {
            System.err.println("Failed to load JDBC driver: " + s.getMessage());
        } catch (SQLException s) {
            System.err.println("Failed to connect to the database: " + s.getMessage());
        }
                // Clear the fields
                farmerIdField.setText("");
                farmerNameField.setText("");
                farmerPasswordField.setText("");

            }
        });

        // Add components to the panel
        panel.add(farmerIdLabel);
        panel.add(farmerIdField);
        panel.add(farmerNameLabel);
        panel.add(farmerNameField);
        panel.add(farmerPasswordLabel);
        panel.add(farmerPasswordField);
        panel.add(new JLabel());
        panel.add(updateFarmButton);

        JOptionPane.showOptionDialog(null, panel, "Update Farmer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private void showDeleteFarmerDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        // Delete Farm components
        JLabel farmerIdLabel = new JLabel("Farmer ID:");
        farmerIdField = new JTextField(20);

        JButton deleteFarmButton = new JButton("Delete Farmer");
        deleteFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int farmerId = Integer.parseInt(farmerIdField.getText());

                // Perform delete logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String username = "lekhan";
                String pass = "lekh";

        try {
            // Load the JDBC driver
            Class.forName(driverClassName);

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(url, username, pass);

            // Perform database operations using the connection
			Statement stmt=con.createStatement();
			int b=stmt.executeUpdate("delete from farm where farmer_id="+farmerId);
			int a=stmt.executeUpdate("delete from farmer where farmer_id="+farmerId);  
			if(a>0)
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Farmer not found", "Error", JOptionPane.ERROR_MESSAGE);
            // Close the connection
            con.close();
            System.out.println("Connection closed successfully.");
        } catch (ClassNotFoundException s) {
            System.err.println("Failed to load JDBC driver: " + s.getMessage());
        } catch (SQLException s) {
            System.err.println("Failed to connect to the database: " + s.getMessage());
        }
                // Clear the field
                farmerIdField.setText("");

            }
        });

        // Add components to the panel
        panel.add(farmerIdLabel);
        panel.add(farmerIdField);
        panel.add(new JLabel());
        panel.add(deleteFarmButton);

        JOptionPane.showOptionDialog(null, panel, "Delete Farmer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
}