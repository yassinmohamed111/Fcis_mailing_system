

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.Statement;
        import java.sql.*;

public class DashboardForm extends JFrame {
    private JPanel dashboardPanel;
    private JLabel lbAdmin;
    private JButton btnRegister;
    private JButton btnCreate;
    private JButton btnCancel;
    private JButton sendMailButton;
    private JButton btnShow;
    private JButton inboxNotWorkingButton;
    private JButton ShowGrades;


    public DashboardForm() {
        setTitle("Dashborad");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boolean hasRegistredUsers = connectToDatabase();
        if (hasRegistredUsers) {
            //show Login form
            loginForm loginForm = new loginForm(this);
            User user = loginForm.user;





            if (user != null) {
                User getuser = userLoginHolder.getInstance().getuser(); //singlton
                lbAdmin.setText("User: " + " " + getuser.name + " / "  +"id  : " + getuser.user_id); //singleton
                setLocationRelativeTo(null);
                setVisible(true);




            }
            else {
                dispose();
            }
        }
        else {
            //show Registration form
            RegistrationForm registrationForm = new RegistrationForm(this);
            User user = registrationForm.user;

            if (user != null) {
                User getuser = userLoginHolder.getInstance().getuser(); //singlton

                lbAdmin.setText("User: " + getuser.name + " id : " + getuser.user_id); //singlton
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else {
                dispose();
            }
        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(DashboardForm.this);
                User user = registrationForm.user;

                if (user != null) {
                    JOptionPane.showMessageDialog(DashboardForm.this,
                            "New user: " + user.name,
                            "Successful Registration",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContactsForm contactsForm = new ContactsForm(DashboardForm.this);
               contacts cont = contactsForm.cont;

            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                       ShowContact showContact = new ShowContact (DashboardForm.this);





            }
        });
        setVisible(true);
        sendMailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User getuser = userLoginHolder.getInstance().getuser();
                if ( getuser.name.contains("admin") )
                {

                }
                else {
                    sendmailtest sendmailtest = new sendmailtest(DashboardForm.this);
                }
            }
        });
        inboxNotWorkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                User getuser = userLoginHolder.getInstance().getuser();
                if ( getuser.name.contains("admin") )
                {

                }
                else {
                    reciveMail reciveMail = new reciveMail(DashboardForm.this);
                }
            }
        });
        ShowGrades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              showGrades showGrades = new showGrades( DashboardForm.this);
            }
        });
    }


    private boolean connectToDatabase() {
        boolean hasRegistredUsers = false;



        try{

            Connection conn = DatabaseConnection.getConnection();
            Statement statement = conn.createStatement();

            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "phone VARCHAR(200),"
                    + "education VARCHAR(200),"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);




            //check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public static void main(String[] args) {
        DashboardForm myForm = new DashboardForm();
    }
}
