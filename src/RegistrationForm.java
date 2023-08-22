import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog {
    private JTextField tfphone;
    private JTextField tfEdu;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirm;
    private JButton btnRegister;
    private JButton btnCancel;
    private JTextField tfname;
    private JTextField tfEmail;
    private JPanel registerPanel;
    private JTextField tfUser_id;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);

    }

    private void registerUser() {
        String user_id = tfUser_id.getText();
        String name = tfname.getText();
        String email = tfEmail.getText();
        String phone = tfphone.getText();
        String education = tfEdu.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirm.getPassword());


        if ( user_id.isEmpty() ||name.isEmpty() || email.isEmpty() || phone.isEmpty() || education.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDatabase(user_id , name, email, phone, education, password);
        if (user != null) {

            userLoginHolder.getInstance().setuser(user);

            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    public User user;
    private User addUserToDatabase(String user_id , String name, String email, String phone, String education, String password)
    {
        User user = null;

        try{
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO users (user_id , name, email, phone, education, password ) " +
                    "VALUES (?,?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5,education);
            preparedStatement.setString(6, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.user_id = user_id;
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.education = education;
                user.password = password;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

        public static void main(String[] args) {

            RegistrationForm myForm = new RegistrationForm(null);
            User user = myForm.user;
            if (user != null) {
                System.out.println("Successful registration of: " + user.name);
            }
            else {
                System.out.println("Registration canceled");
            }
        }
    }

