import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Books_Available {
    public JPanel books;
    private JTable table1;
    private JButton fetchButton;
    private JButton backButton;
    static JFrame frame=new JFrame();
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

    //table for database entries
    void table_load(){
        try {
            pst=con.prepareStatement("select * from books ");
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }


   

    public static void main(String[] args) {
        frame.setContentPane(new Books_Available().books);
        frame.setBounds(500,250,800,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }



    public Books_Available() {
        connect();
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load();
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
