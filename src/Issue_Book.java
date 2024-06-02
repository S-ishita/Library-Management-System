import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Issue_Book {
    public JPanel issue;
    private JTextField user;
    private JTextField bname;
    private JFormattedTextField issueda;
    private JFormattedTextField returnda;
    private JTable table1;
    private JButton issueBookButton;
    private JButton backButton;
    static JFrame frame=new JFrame();
    Connection con;
    PreparedStatement pst;

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


    //table for database entries
    void table_load(){
        try {
            pst=con.prepareStatement("select * from books where copies!=0");
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    void dateset(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        Calendar returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DAY_OF_MONTH, 7);

        returnda.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(dateFormat)));
        returnda.setValue(returnDate.getTime());
        returnda.setEditable(false);
    }
    public static void main(String[] args) {
        frame.setContentPane(new Issue_Book().issue);
        frame.setBounds(500,250,800,500);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public Issue_Book() {
        connect();
        table_load();
        dateset();



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backButton);

                // Close the current frame
                currentFrame.dispose();

                // Create a new instance of JFrame for the Dashboard
                JFrame dashboardFrame = new JFrame("Student/Lecturer Dashboard");
                Student_Dashboard dashboard = new Student_Dashboard();
                dashboardFrame.setContentPane(dashboard.studash);
                dashboardFrame.setUndecorated(true);
                dashboardFrame.setBounds(500,250,800,500);
                dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboardFrame.setVisible(true);
            }
        });
        issueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookname = bname.getText();
                String userid = user.getText();
                try {
                    Date issueDate = (Date) issueda.getValue();
                    Date returnDate = (Date) returnda.getValue();

                    // Convert the dates to java.sql.Date for database insertion
                    java.sql.Date sqlIssueDate = new java.sql.Date(issueDate.getTime());
                    java.sql.Date sqlReturnDate = new java.sql.Date(returnDate.getTime());

                    // Update the books table to decrease the number of copies
                    String updateSql = "UPDATE books SET copies = copies - 1 WHERE copies != 0 AND book_name = ?";
                    pst = con.prepareStatement(updateSql);
                    pst.setString(1, bookname);
                    int updatedRows = pst.executeUpdate();

                    if (updatedRows > 0) {
                        // Insert data into the issuedbooks table
                        String insertSql = "INSERT INTO issuedbooks (userid, bookname, issuedate, returndate) VALUES (?, ?, ?, ?)";
                        pst = con.prepareStatement(insertSql);
                        pst.setString(1, userid);
                        pst.setString(2, bookname);
                        pst.setDate(3, sqlIssueDate);
                        pst.setDate(4, sqlReturnDate);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(frame, "Book Issued Successfully");
                        user.setText("");
                        bname.setText("");
                        table_load();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book not available or incorrect book name");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();

                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        Calendar currentDate = Calendar.getInstance();
        issueda = new JFormattedTextField(dateFormat);
        issueda.setValue(currentDate.getTime());
        issueda.setEditable(false);
    }
}
