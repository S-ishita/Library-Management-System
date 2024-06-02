import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField user;
    private JPasswordField password;
    private JButton loginButton;
    public JPanel Login;
    private JLabel pass;
    private JPasswordField passw;
    private JComboBox usertype;
    private JButton signUpButton;
    static JFrame frame=new JFrame();

    public static void main(String[] args) {

        frame.setContentPane(new LoginPage().Login);
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

    public LoginPage() {
        connect();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = user.getText();
                String newpassword= passw.getText();
                String usert=usertype.getSelectedItem().toString();
                if (username.isEmpty() || newpassword.isEmpty() ||  usert.isEmpty()) {
                    StringBuilder errorMessage = new StringBuilder("Please fill in all fields:\n");
                    if (username.isEmpty()) errorMessage.append("- Username\n");
                    if (newpassword.isEmpty()) errorMessage.append("- Password\n");
                    if (usert.isEmpty()) errorMessage.append("- User Type\n");

                    JOptionPane.showMessageDialog(null, errorMessage.toString(), "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without proceeding
                }
                if(usert.equals("Admin")) {
                    String query = ("select password from admin where name='" + username + "';");
                    try {

                        Statement stm = con.createStatement();
                        ResultSet rs = stm.executeQuery(query);
                        if (rs.next()) {
                            String password1 = rs.getString("password");
                            if (password1.equals(newpassword)) {
                                JOptionPane.showMessageDialog(null, "Login Successful");
                                // Dashboard ds = new Dashboard();
                                frame.setContentPane(new Dashboard().Dash);
                                frame.setBounds(500, 250, 800, 500);
                                frame.setVisible(true);

                            } else {
                                JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Username");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                else{
                    String query = ("select password from student where name='" + username + "';");
                    try {

                        Statement stm = con.createStatement();
                        ResultSet rs = stm.executeQuery(query);
                        if (rs.next()) {
                            String password1 = rs.getString("password");
                            if (password1.equals(newpassword)) {
                                JOptionPane.showMessageDialog(null, "Login Successful");
                                // Dashboard ds = new Dashboard();
                                frame.dispose();
                                frame.setContentPane(new Student_Dashboard().studash);
                                frame.setBounds(500, 250, 800, 500);
                                frame.setVisible(true);

                            } else {
                                JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Username");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(signUpButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame signupFrame = new JFrame("Sign Up");
                SignUp signup = new SignUp();
                signupFrame.setContentPane(signup.sign);
                signupFrame.setBounds(500,250,800,500);
                signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                signupFrame.setVisible(true);
            }
        });
    }

}

