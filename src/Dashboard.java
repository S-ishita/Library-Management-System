import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard {
    private JButton BOOKSAVAILABLEButton;
    private JButton STAFFDETAILSButton;
    private JButton ADDBOOKSButton;
    private JButton ADDSTAFFButton;
    private JButton REMOVEBOOKSButton;
    private JButton REMOVESTAFFButton;
    private JButton EDITADMINButton;
    public JPanel Dash;
    private JButton LOGOUTButton;
    static JFrame frame=new JFrame();

    public Dashboard() {
        BOOKSAVAILABLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(BOOKSAVAILABLEButton);

                // Close the current frame
                currentFrame.dispose();

                // Create a new instance of JFrame for the Books_Available panel
                JFrame booksAvailableFrame = new JFrame("Books Available");
                Books_Available booksAvailable = new Books_Available();
                booksAvailableFrame.setContentPane(booksAvailable.books);
                booksAvailableFrame.setUndecorated(true);
                booksAvailableFrame.setBounds(500,250,800,500);
                booksAvailableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                booksAvailableFrame.setVisible(true);

            }
        });
        ADDBOOKSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(BOOKSAVAILABLEButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame addBooksFrame = new JFrame("Add Books");
                Add_Books addBooks = new Add_Books();
                addBooksFrame.setContentPane(addBooks.add);
                addBooksFrame.setUndecorated(true);
                addBooksFrame.setBounds(500,250,800,500);
                addBooksFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                addBooksFrame.setVisible(true);
            }
        });
        STAFFDETAILSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(STAFFDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame viewstaffFrame = new JFrame("Staff Details");
                Staff_Details viewstaff = new Staff_Details();
                viewstaffFrame.setContentPane(viewstaff.staffdets);
                viewstaffFrame.setUndecorated(true);
                viewstaffFrame.setBounds(500,250,800,500);
                viewstaffFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                viewstaffFrame.setVisible(true);
            }
        });
        ADDSTAFFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(STAFFDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame addstaffFrame = new JFrame("Add Staff");
                Add_Staff addstaff1 = new Add_Staff();
                addstaffFrame.setContentPane(addstaff1.addstaffdets);
                addstaffFrame.setUndecorated(true);
                addstaffFrame.setBounds(500,250,800,500);
                addstaffFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                addstaffFrame.setVisible(true);
            }
        });
        REMOVEBOOKSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(STAFFDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame removebookFrame = new JFrame("Remove Books");
                Remove_Books removebooks= new Remove_Books();
                removebookFrame.setContentPane(removebooks.rembook);
                removebookFrame.setUndecorated(true);
                removebookFrame.setBounds(500,250,800,500);
                removebookFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                removebookFrame.setVisible(true);
            }
        });
        REMOVESTAFFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(STAFFDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame removestaffFrame = new JFrame("Remove Staff");
                Remove_Staff removestaff = new Remove_Staff();
                removestaffFrame.setContentPane(removestaff.remstaff);
                removestaffFrame.setUndecorated(true);
                removestaffFrame.setBounds(500,250,800,500);
                removestaffFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                removestaffFrame.setVisible(true);
            }
        });
        EDITADMINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(STAFFDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame editadminFrame = new JFrame("Edit Admin");
                Edit_Admin editadmin = new Edit_Admin();
                editadminFrame.setContentPane(editadmin.edit);
                editadminFrame.setUndecorated(true);
                editadminFrame.setBounds(500,250,800,500);
                editadminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                editadminFrame.setVisible(true);
            }
        });
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(STAFFDETAILSButton);

                // Close the current frame
                currentFrame.dispose();
                JFrame loginFrame = new JFrame("Login");
                LoginPage loginp = new LoginPage();
                loginFrame.setContentPane(loginp.Login);
                loginFrame.setBounds(500,250,800,500);
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setVisible(true);
            }
        });
    }
    public static void main(String[] args) {
        frame.setContentPane(new Dashboard().Dash);
        frame.setBounds(500,250,800,500);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
