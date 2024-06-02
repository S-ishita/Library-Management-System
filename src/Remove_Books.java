import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Remove_Books {
    private JTextField input;
    private JButton deleteButton;
    private JButton backButton;
    public JPanel rembook;
    static JFrame frame = new JFrame();
    Connection con;
    PreparedStatement pst;


    public static void main(String[] args) {
        frame.setContentPane(new Remove_Books().rembook);
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


    public Remove_Books() {
        connect();
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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String val=input.getText();
                try {
                    pst=con.prepareStatement("delete from books where book_id=? or book_name=?");
                    pst.setString(1,val);
                    pst.setString(2,val);
                    int rows=pst.executeUpdate();
                    if(rows>0) {
                        JOptionPane.showMessageDialog(null, "Book Deleted Successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No Such Book Available");
                    }
                    input.setText("");
                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
