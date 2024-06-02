import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Add_Books {
    public JPanel add;
    private JButton bookIDButton;
    private JButton categoryButton;
    private JButton nameButton;
    private JButton authorButton;
    private JButton copiesButton;
    private JTextField id;
    private JTextField categ;
    private JTextField name1;
    private JTextField auth;
    private JTextField copy;
    private JButton backButton;
    private JButton addButton;
    static JFrame frame=new JFrame();
    Connection con;
    PreparedStatement pst;


    public static void main(String[] args) {
        frame.setContentPane(new Add_Books().add);
        frame.setBounds(500,250,800,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

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



    public Add_Books() {
        connect();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bookid1=id.getText();
                String category1=categ.getText();
                String names=name1.getText();
                String author1=auth.getText();
                int copies1=Integer.parseInt(copy.getText()) ;
                try {
                    // Check if the book already exists
                    String checkQuery = "SELECT COUNT(*) FROM books WHERE book_name = ? AND author = ? AND category = ?";
                    PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                    checkStmt.setString(1, names);
                    checkStmt.setString(2, author1);
                    checkStmt.setString(3, category1);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // Book exists, update copies
                        String updateQuery = "UPDATE books SET copies =copies+"+copies1+" WHERE book_name = ? AND author = ? AND category = ?";
                        PreparedStatement updateStmt = con.prepareStatement(updateQuery);

                        updateStmt.setString(1, names);
                        updateStmt.setString(2, author1);
                        updateStmt.setString(3, category1);
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Book copies updated successfully");
                        }
                    } else {
                        // Book doesn't exist, insert new record
                        String insertQuery = "INSERT INTO books (book_id, category, book_name, author, copies) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                        insertStmt.setString(1, bookid1);
                        insertStmt.setString(2, category1);
                        insertStmt.setString(3, names);
                        insertStmt.setString(4, author1);
                        insertStmt.setString(5, Integer.toString(copies1));
                        insertStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Book Added Successfully");
                    }

                    id.setText("");
                    categ.setText("");
                    name1.setText("");
                    auth.setText("");
                    copy.setText("");
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
                dashboardFrame.setBounds(500,250,800,500);
                dashboardFrame.setUndecorated(true);
                dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboardFrame.setVisible(true);
            }
        });
    }

}
