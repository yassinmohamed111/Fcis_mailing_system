import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ContactsForm extends JDialog {
    private JTextField tfName;
    private JTextField tfNumber;
    private JTextField tfEmail;
    private JTextField tfRelation;
    private JPanel contactspanel;
    private JButton btnOK;
    private JButton btnCancel;
    private JTextField tfUser_id;
    private JLabel Userin;


    public ContactsForm(JFrame parent) {
        super(parent);
        setTitle("Create a new contact");
        setContentPane(contactspanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createContact();
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


    private void createContact() {

        String name = tfName.getText();
        String email = tfEmail.getText();
        String phone = tfNumber.getText();
        String relation = tfRelation.getText();
        String user_id = tfUser_id.getText();

        User user = userLoginHolder.getInstance().getuser();
        Userin.setText(" User_id : " + user.user_id);

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || relation.isEmpty() || user_id.isEmpty() ) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        cont = addcontactToDatabase(name, email, phone, relation , user_id) ;
        if (cont != null) {




            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add new contact",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public contacts cont;

    private contacts addcontactToDatabase(String name, String email, String phone, String relation , String user_id) {
        contacts cont = null;


        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();


            String sql = "INSERT INTO contacts (name, email, phone, relation , user_id )" +

                    "VALUES (?, ?, ?, ? , ? )";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, relation);
            preparedStatement.setString(5, user_id);



            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                cont = new contacts();
                cont.name = name;
                cont.email = email;
                cont.phone = phone;
                cont.relation = relation;
                cont.user_id = user_id ;


            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cont;


    }

    public static void main(String[] args) {

        ContactsForm myForm = new ContactsForm(null);
        contacts cont = myForm.cont;
        if (cont != null) {
            System.out.println("Successful add of contact: " + cont.name);
        } else {
            System.out.println("Registration of contact canceled");
        }
    }


}
