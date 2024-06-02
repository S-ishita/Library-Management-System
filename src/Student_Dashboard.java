import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Student_Dashboard {
    static JFrame frame=new JFrame();
    private JButton ISSUEBOOKButton;
    private JButton RETURNBOOKButton;
    public JPanel studash;
    private JButton EDITDETAILSButton;
    private JButton LOGOUTButton;

    public Student_Dashboard() {
        ISSUEBOOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(ISSUEBOOKButton);

                // Close the current frame
                currentFrame.dispose();

                // Create a new instance of JFrame
                JFrame booksIssueFrame = new JFrame("Issue Book");
                Issue_Book booksIssue = new Issue_Book();
                booksIssueFrame.setContentPane(booksIssue.issue);
                booksIssueFrame.setUndecorated(true);
                booksIssueFrame.setBounds(500,250,800,500);
                booksIssueFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                booksIssueFrame.setVisible(true);
            }
        });
        RETURNBOOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(RETURNBOOKButton);

                // Close the current frame
                currentFrame.dispose();

                // Create a new instance of JFrame
                JFrame booksReturnFrame = new JFrame("Return Book");
                Return_Book booksReturn = new Return_Book();
                booksReturnFrame.setContentPane(booksReturn.returnb);
                booksReturnFrame.setUndecorated(true);
                booksReturnFrame.setBounds(500,250,800,500);
                booksReturnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                booksReturnFrame.setVisible(true);
            }
        });
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(LOGOUTButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame loginFrame = new JFrame("Login");
                LoginPage loginp = new LoginPage();
                loginFrame.setContentPane(loginp.Login);
                loginFrame.setUndecorated(true);
                loginFrame.setBounds(500,250,800,500);
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setVisible(true);
            }
        });
        EDITDETAILSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(EDITDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame editDetailsFrame = new JFrame("Edit Details");
                Edit_Details editd = new Edit_Details();
                editDetailsFrame.setContentPane(editd.editdet);
                editDetailsFrame.setUndecorated(true);
                editDetailsFrame.setBounds(500,250,800,500);
                editDetailsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                editDetailsFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(new Student_Dashboard().studash);
        frame.setBounds(500,250,800,500);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
