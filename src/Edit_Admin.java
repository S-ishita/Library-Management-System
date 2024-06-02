import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Edit_Admin {
    private JComboBox columnval;
    private JTextField updatedval;
    private JButton updateButton;
    private JButton backButton;
    public JPanel edit;
    static JFrame frame = new JFrame();
    Connection con;
    PreparedStatement pst;

    public static void main(String[] args) {
        frame.setContentPane(new Edit_Admin().edit);
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


    public Edit_Admin() {
        connect();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val=updatedval.getText();
                String col=columnval.getSelectedItem().toString();
                try {
                    String query = "UPDATE admin SET " + col + " = ?";
                    pst = con.prepareStatement(query);
                    pst.setString(1, val);
                    int rows=pst.executeUpdate();
                    if(rows>0) {
                        JOptionPane.showMessageDialog(null, "Admin Credentials updated Successfully");
                    }

                    updatedval.setText("");
                }
                catch (SQLException ex){
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
