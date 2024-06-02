import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUp {
    private JComboBox usertype;
    private JTextField user;
    private JLabel pass;
    private JPasswordField passw;
    private JTextField contactr;
    private JButton GOTOLOGINPAGEButton;
    private JButton REGISTERButton;
    public JPanel sign;
    static JFrame frame=new JFrame();

    public SignUp() {
        connect();
        REGISTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String usert=usertype.getSelectedItem().toString();
                String username=user.getText();
                String passt=passw.getText();
                String contact1=contactr.getText();
                // Check if any field is empty
                if (username.isEmpty() || passt.isEmpty() || contact1.isEmpty() || usert.isEmpty()) {
                    StringBuilder errorMessage = new StringBuilder("Please fill in all fields:\n");
                    if (username.isEmpty()) errorMessage.append("- Username\n");
                    if (passt.isEmpty()) errorMessage.append("- Password\n");
                    if (contact1.isEmpty()) errorMessage.append("- Contact\n");
                    if (usert.isEmpty()) errorMessage.append("- User Type\n");

                    JOptionPane.showMessageDialog(null, errorMessage.toString(), "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without proceeding
                }
                if(usert.equals("admin")) {
                    try {
                        // Check if the staff already exists
                        String checkQuery = "SELECT COUNT(*) FROM admin WHERE name = ? AND contact = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setString(1, username);
                        checkStmt.setString(2, contact1);
                        ResultSet rs = checkStmt.executeQuery();

                        if (rs.next() && rs.getInt(1) > 0) {
                            // staff exists, update password
                            String updateQuery = "UPDATE admin SET password = ? WHERE name = ?";
                            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                            updateStmt.setString(1, passt);
                            updateStmt.setString(2, username);

                            int rowsUpdated = updateStmt.executeUpdate();
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, "Admin Details updated successfully");
                            }
                        } else {
                            // Staff doesn't exist, insert new record
                            String insertQuery = "INSERT INTO admin(name, password, contact) VALUES (?, ?, ?)";
                            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                            insertStmt.setString(1, username);
                            insertStmt.setString(2, passt);
                            insertStmt.setString(3, contact1);
                            insertStmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Admin Registered Successfully");
                        }

                        user.setText("");
                        passw.setText("");
                        contactr.setText("");
                        user.requestFocus();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
                    }
                }
                else{
                    try {
                        // Check if the staff already exists
                        String checkQuery = "SELECT COUNT(*) FROM student WHERE name = ? AND contact = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setString(1, username);
                        checkStmt.setString(2, contact1);
                        ResultSet rs = checkStmt.executeQuery();

                        if (rs.next() && rs.getInt(1) > 0) {
                            // staff exists, update password
                            String updateQuery = "UPDATE student SET password = ? WHERE name = ?";
                            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                            updateStmt.setString(1, passt);
                            updateStmt.setString(2, username);

                            int rowsUpdated = updateStmt.executeUpdate();
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(null, "User Details updated successfully");
                            }
                        } else {
                            // Staff doesn't exist, insert new record
                            String insertQuery = "INSERT INTO student(name, password, contact,type) VALUES (?, ?, ?,?)";
                            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                            insertStmt.setString(1, username);
                            insertStmt.setString(2, passt);
                            insertStmt.setString(3, contact1);
                            insertStmt.setString(4, usert);
                            insertStmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "User Registered Successfully");
                        }

                        user.setText("");
                        passw.setText("");
                        contactr.setText("");
                        user.requestFocus();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
                    }
                }

            }
        });
        GOTOLOGINPAGEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(GOTOLOGINPAGEButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame loginFrame = new JFrame("Login");
                LoginPage loginp = new LoginPage();
                loginFrame.setContentPane(loginp.Login);
                loginFrame.setBounds(500,250,800,500);
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {

        frame.setContentPane(new SignUp().sign);
        frame.setBounds(500,250,800,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;


    //establish connection with database
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","madhav251");
            System.out.println("Connected to the database successfully");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
