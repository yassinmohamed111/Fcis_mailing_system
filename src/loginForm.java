import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginForm extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JTextField tfUser_id;

    public loginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_id = tfUser_id.getText();
                String email = tfEmail.getText();

                String password = String.valueOf(pfPassword.getPassword());

               user =  getAuthenticatedUser(user_id ,email, password);

                if (user != null) {

                    userLoginHolder.getInstance().setuser(user); //singleton

                    dispose();
                }
                else {


                    JOptionPane.showMessageDialog(loginForm.this,
                            "Email or Password or id Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }

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
public User user ;
    private User getAuthenticatedUser( String user_id ,  String email , String password)
    {
        User user = null ;



        try{
            DatabaseConnectionInterface connection = new DatabaseConnectionProxy();
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM users  WHERE user_id=? AND email=? AND password=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.user_id = resultSet.getString("user_id");
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }



        return user;

    }

    public static void main(String[] args) {
        loginForm loginForm = new loginForm(null);
        User user = loginForm.user;
        if (user != null) {
            System.out.println("Successful Authentication of: " + user.name);
            System.out.println("         user_id: " + user.user_id);
            System.out.println("          Email: " + user.email);
            System.out.println("          Phone: " + user.phone);
            System.out.println("          education: " + user.address);
        }
        else {
            System.out.println("Authentication canceled");
        }
    }
}
