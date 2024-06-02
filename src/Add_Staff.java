import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Add_Staff {
    public JPanel addstaffdets;
    private JTextField id;
    private JTextField namestaff;
    private JTextField contactstaff;
    private JButton addButton;
    private JButton backButton;
    static JFrame frame = new JFrame();
    Connection con;
    PreparedStatement pst;


    public static void main(String[] args) {
        frame.setContentPane(new Add_Staff().addstaffdets);
        frame.setBounds(500, 250, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    //establish connection with database
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "madhav251");
            System.out.println("Connected to the database successfully");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Add_Staff() {
        connect();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sid = id.getText();
                String sname = namestaff.getText();
                String scontact = contactstaff.getText();
                try {
                    // Check if the staff already exists
                    String checkQuery = "SELECT COUNT(*) FROM staff WHERE name = ?";
                    PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                    checkStmt.setString(1, sname);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // staff exists,update contact
                        String updateQuery = "UPDATE staff SET contact =" + scontact + " WHERE name = ? ";
                        PreparedStatement updateStmt = con.prepareStatement(updateQuery);

                        updateStmt.setString(1, sname);
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Staff Details updated successfully");
                        }
                    } else {
                        // Book doesn't exist, insert new record
                        String insertQuery = "INSERT INTO staff  VALUES (?, ?, ?)";
                        PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                        insertStmt.setString(1, sid);
                        insertStmt.setString(2, sname);
                        insertStmt.setString(3, scontact);
                        insertStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Staff Added Successfully");
                    }

                    id.setText("");
                    namestaff.setText("");
                    contactstaff.setText("");
                    id.requestFocus();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backButton);

                // Close the current frame
                currentFrame.dispose();

                // Create a new instance of JFrame for the Dashboard
                JFrame dashboardFrame = new JFrame("Dashboard");
                Dashboard dashboard = new Dashboard();
                dashboardFrame.setContentPane(dashboard.Dash);
                dashboardFrame.setUndecorated(true);
                dashboardFrame.setBounds(500,250,800,500);
                dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboardFrame.setVisible(true);
            }
        });
    }
}

