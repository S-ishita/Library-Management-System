import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Remove_Staff {
    private JTextField inputstaff;
    private JButton deleteButton;
    private JButton backButton;
    public JPanel remstaff;
    static JFrame frame = new JFrame();
    Connection con;
    PreparedStatement pst;

    public static void main(String[] args) {
        frame.setContentPane(new Remove_Staff().remstaff);
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

    public Remove_Staff() {
        connect();
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val=inputstaff.getText();
                try {
                    pst=con.prepareStatement("delete from staff where staff_id=? or name=?");
                    pst.setString(1,val);
                    pst.setString(2,val);
                    int rows=pst.executeUpdate();
                    if(rows>0) {
                        JOptionPane.showMessageDialog(null, "Staff Deleted Successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No Staff with given details available");
                    }
                    inputstaff.setText("");
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
