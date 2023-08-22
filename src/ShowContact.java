import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ShowContact extends JDialog {
    private JTextField tfUserid;
    private JLabel user_id;
    private JButton buttonOk;
    private JPanel showPanel;
    private JLabel lbname;
    private JLabel lbemial;
    private JLabel relationLabel;
    private JLabel userIdlabel;
    private JLabel lbphone;

    public ShowContact(JFrame parent) {
        super(parent);
        setTitle("show contact");
        setContentPane(showPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String user_id = tfUserid.getText();
                getCont(user_id);


            }
        });
        setVisible(true);
    }

    public contacts cont ;
    private contacts getCont( String user_id )
    {
        contacts cont = null ;

        try{
             Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM contacts WHERE email=?  ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user_id);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
               cont = new contacts();

                cont.name = resultSet.getString("name");
                cont.email = resultSet.getString("email");
                cont.phone = resultSet.getString("phone");
                cont.relation = resultSet.getString("relation");
                cont.user_id = resultSet.getString("user_id");

               // System.out.println( " name : " + cont.name + "\t " +" email :" + cont.email +" \t"+ "phone :" + cont.phone +" \t"+" relation :" + cont.relation + " \t" + "contacts of user_id : "+ cont.user_id);

              lbname.setText("Name : " + cont.name);
                lbemial.setText("Email: " + cont.email);
                lbphone.setText("Phone : " + cont.phone);
                relationLabel.setText("Relation : " + cont.relation);
                userIdlabel.setText("contact of User_id  : " + cont.user_id);



            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }



        return cont;

    }


    public  static void main(String[] args) {
        ShowContact showContact = new ShowContact(null);
        contacts cont = showContact.cont;

        }
        }


