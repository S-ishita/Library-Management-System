import net.proteanit.sql.DbUtils;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Return_Book {
    public JPanel returnb;
    private JTextField user;
    private JTextField bname;
    private JFormattedTextField returnda;
    private JTextField lfine;
    private JTable table1;
    private JButton payFineAndReturnButton;
    private JButton backButton;
    private com.toedter.calendar.JDateChooser dateChooser;
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
            pst=con.prepareStatement("select * from issuedbooks");
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        frame.setContentPane(new Return_Book().returnb);
        frame.setBounds(500,250,800,500);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public Return_Book() {
        connect();
        table_load();
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
        payFineAndReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = user.getText();
                String name = bname.getText();
                String actualReturnDateStr = returnda.getText();

                if (id.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both User ID and Book Name.");
                    return;
                }

                if ("yyyy-MM-dd".equals(actualReturnDateStr)) {
                    JOptionPane.showMessageDialog(frame, "Please enter the actual return date.");
                    return;
                }

                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    java.util.Date actualReturnDate = dateFormat.parse(actualReturnDateStr);
                    java.sql.Date sqlActualReturnDate = new java.sql.Date(actualReturnDate.getTime());

                    // First, check if the book is actually issued to this user
                    String checkSql = "SELECT returndate FROM issuedbooks WHERE userid = ? AND bookname = ?";
                    pst = con.prepareStatement(checkSql);
                    pst.setString(1, id);
                    pst.setString(2, name);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Book is issued to this user, get the expected return date
                        java.sql.Date sqlExpectedReturnDate = rs.getDate("returndate");
                        Calendar expectedReturnCal = Calendar.getInstance();
                        expectedReturnCal.setTime(sqlExpectedReturnDate);

                        // Set currentCal to the actual return date
                        Calendar actualReturnCal = Calendar.getInstance();
                        actualReturnCal.setTime(actualReturnDate);

                        // Calculate the number of days overdue
                        long daysOverdue = (actualReturnCal.getTimeInMillis() - expectedReturnCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                        double fine = Math.max(0, daysOverdue * 10); // $10 per day, minimum 0

                        // Update fine label and show message
                        lfine.setText(String.format("%.2f", fine));
                        String message = daysOverdue > 0
                                ? String.format("Book is %d days overdue. Fine: $%.2f", daysOverdue, fine)
                                : "Book is not overdue. No fine.";
                        JOptionPane.showMessageDialog(frame, message);

                        // Start a transaction for database updates
                        con.setAutoCommit(false);

                        try {
                            // Update the book copies in the books table
                            String updateSql = "UPDATE books SET copies = COALESCE(copies, 0) + 1 WHERE book_name = ?";
                            pst = con.prepareStatement(updateSql);
                            pst.setString(1, name);
                            int updatedRows = pst.executeUpdate();

                            if (updatedRows > 0) {
                                // Delete the record from issuedbooks table
                                String deleteSql = "DELETE FROM issuedbooks WHERE userid = ? AND bookname = ?";
                                pst = con.prepareStatement(deleteSql);
                                pst.setString(1, id);
                                pst.setString(2, name);
                                pst.executeUpdate();



                                // Commit the transaction
                                con.commit();
                                JOptionPane.showMessageDialog(frame, "Book returned successfully. " + (fine > 0 ? "Please pay the fine of $" + String.format("%.2f", fine) : ""));

                                // Clear the input fields and reload the table
                                user.setText("");
                                bname.setText("");
                                returnda.setText("yyyy-MM-dd");
                                returnda.setForeground(Color.GRAY);
                                lfine.setText("0.00");
                                table_load();
                            } else {
                                con.rollback();
                                JOptionPane.showMessageDialog(frame, "Error updating book copies. Transaction rolled back.");
                            }
                        } catch (SQLException ex) {
                            con.rollback();
                            throw ex; // Rethrow to be caught by the outer catch block
                        } finally {
                            con.setAutoCommit(true); // Reset auto-commit
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "This book is not issued to this user.");
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format in the return date field. Please use yyyy-MM-dd.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }
            }
        });
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        // Create the JFormattedTextField for returnda
        returnda = new JFormattedTextField(dateFormat);
        returnda.setEditable(true); // Allow user to edit
        returnda.setToolTipText("Enter date in yyyy-MM-dd format");

        // Optional: Set a placeholder text
        returnda.setValue(null);
        returnda.setText("yyyy-MM-dd");
        returnda.setForeground(Color.GRAY);

        // Add focus listeners to handle placeholder text
        returnda.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("yyyy-MM-dd".equals(returnda.getText())) {
                    returnda.setText("");
                    returnda.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (returnda.getText().isEmpty()) {
                    returnda.setText("yyyy-MM-dd");
                    returnda.setForeground(Color.GRAY);
                }
            }
        });

        // Create a JDateChooser as a helper
        dateChooser = new com.toedter.calendar.JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        // Set up a PropertyChangeListener to update returnda when a date is selected
        dateChooser.getDateEditor().addPropertyChangeListener(
                e -> {
                    if ("date".equals(e.getPropertyName())) {
                        java.util.Date selectedDate = dateChooser.getDate();
                        if (selectedDate != null) {
                            // Format the date and set it in the text field
                            String formattedDate = dateFormat.format(selectedDate);
                            returnda.setText(formattedDate);
                            returnda.setForeground(Color.BLACK);
                        }
                    }
                }
        );

        // Add both components to the frame or panel
        frame.add(returnda);
        frame.add(dateChooser);

    }
}
