import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Home extends JFrame { 
    private JTextField farmIdField;
    private int farmerId;
    public Home(int farmerId) {
    	this.farmerId=farmerId;
        setTitle("Smart Farming!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu farmerMenu = new JMenu("Farmer");
        JMenu farmMenu = new JMenu("Farm");
        JMenu cropMenu = new JMenu("Crop");

        // Create menu items for Farmer menu
        JMenuItem viewFarmsItem = new JMenuItem("View Farms");

        // Create menu items for Farm menu
        JMenuItem farmInsertItem = new JMenuItem("Insert Farm");
        JMenuItem farmUpdateItem = new JMenuItem("Update Farm");
        JMenuItem farmDeleteItem = new JMenuItem("Delete Farm");

        // Create menu item for Crop menu
        JMenuItem recommendCropItem = new JMenuItem("Recommend Crop");

        // Add action listeners to menu items
        viewFarmsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewFarmsDialog();
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

        recommendCropItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRecommendCropDialog();
            }
        });

        // Add menu items to menus
        farmerMenu.add(viewFarmsItem);

        farmMenu.add(farmInsertItem);
        farmMenu.add(farmUpdateItem);
        farmMenu.add(farmDeleteItem);

        cropMenu.add(recommendCropItem);

        // Add menus to menu bar
        menuBar.add(farmerMenu);
        menuBar.add(farmMenu);
        menuBar.add(cropMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);
    }
    private void viewFarmsDialog()
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
			ResultSet rs=stmt.executeQuery("select farm_id,name,location from farm where farmer_id="+farmerId); 
			StringBuilder sb = new StringBuilder();
			while(rs.next())
			{
				int x=rs.getInt(1);
				String m=rs.getString(2);
				String n=rs.getString(3);
            

                sb.append(x).append(": ").append(m).append(" at ").append(n).append(".\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Farms", JOptionPane.PLAIN_MESSAGE);
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
        panel.setLayout(new GridLayout(4, 2));

        // Insert Farm components
        JLabel farmIdLabel = new JLabel("Farm ID:");
        farmIdField = new JTextField(20);
        JLabel farmNameLabel = new JLabel("Farm Name:");
        JTextField farmNameField = new JTextField(20);
        JLabel farmLocationLabel = new JLabel("Farm Location:");
        JTextField farmLocationField = new JTextField(20);

        JButton addFarmButton = new JButton("Add Farm");
        addFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
				JOptionPane.showMessageDialog(null, "Farm details should be unique", "Error", JOptionPane.ERROR_MESSAGE);
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
        panel.setLayout(new GridLayout(4, 2));

        // Update Farm components
        JLabel farmIdLabel = new JLabel("Farm ID:");
        farmIdField = new JTextField(20);
        JLabel farmNameLabel = new JLabel("Farm Name:");
        JTextField farmNameField = new JTextField(20);
        JLabel farmLocationLabel = new JLabel("Farm Location:");
        JTextField farmLocationField = new JTextField(20);

        JButton updateFarmButton = new JButton("Update Farm");
        updateFarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
			int a = stmt.executeUpdate("UPDATE farm SET name='" + farmName + "',location ='" + farmLocation + "'WHERE farm_id=" + farmId ); 
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
			int a=stmt.executeUpdate("delete from farm where farm_id="+farmId+" and farmer_id="+farmerId);  
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

    private void showRecommendCropDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        // Recommend Crop components
        JLabel farmIdLabel = new JLabel("Farm ID:");
        JTextField farmIdField = new JTextField(20);
        JLabel monthLabel = new JLabel("Month:");
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthComboBox = new JComboBox<>(months);

        JButton recommendCropButton = new JButton("Recommend Crop");
        recommendCropButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String farmId = farmIdField.getText();
                String month = (String) monthComboBox.getSelectedItem();

                // Perform recommend crop logic here
                String driverClassName = "oracle.jdbc.driver.OracleDriver";

                try {
                    // Load the JDBC driver
                    Class.forName(driverClassName);
                    
                    // Establish a connection to the database
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "lekhan", "lekh");

                    // Perform database operations using the connection
            		Statement stmt=con.createStatement();
            		ResultSet rs=stmt.executeQuery("select crop from crop,farm where month='"+month+"' and farm_id="+farmId+" and farmer_id="+farmerId); 
            		if(rs.next())
            			JOptionPane.showMessageDialog(null, "Recommended crop for Farm ID " + farmId + " in " + month + ": Crop "+rs.getString(1),"recommmended", JOptionPane.INFORMATION_MESSAGE);
            		else
            			JOptionPane.showMessageDialog(null, "Farm not found", "Error", JOptionPane.ERROR_MESSAGE);
                    con.close();

                    System.out.println("Connection closed successfully.");
                } catch (ClassNotFoundException c) {
                    System.err.println("Failed to load JDBC driver: " + c.getMessage());
                } catch (SQLException c) {
                    System.err.println("Failed to connect to the database: " + c.getMessage());
                }

                // Clear the fields
                farmIdField.setText("");
                monthComboBox.setSelectedIndex(0);

                
            }
        });

        // Add components to the panel with grid constraints
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(farmIdLabel, gbc);
        gbc.gridx++;
        panel.add(farmIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(monthLabel, gbc);
        gbc.gridx++;
        panel.add(monthComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(recommendCropButton, gbc);

        JOptionPane.showOptionDialog(null, panel, "Recommend Crop", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }


    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }*/
}
