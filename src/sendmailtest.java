import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Properties;


public class sendmailtest extends JDialog {
    private JPanel panel1;
    private JTextField toEmail;
     private JTextArea textEmail;
     private JTextField subjectEmail;
    private JButton buttonsend;
    private JButton showInboxButton;


    public sendmailtest(JFrame parent) {
        super(parent);
        setTitle("Create a new mail");
        setContentPane(panel1);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);





        buttonsend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {





                String reciver = toEmail.getText();
                String subject = subjectEmail.getText();
                String email = textEmail.getText();
                if ( reciver.isEmpty() || subject.isEmpty() || email.isEmpty() ) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter all fields",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }


                User user = userLoginHolder.getInstance().getuser(); //singlton
               //String password = getPassword(sender);


                Properties properties = new Properties();
                properties.put("mail.smtp.auth" , "true" );
                properties.put("mail.smtp.starttls.enable" , "true");
                properties.put("mail.smtp.host","outlook.office365.com" );
                properties.put("mail.smtp.port" , "587");
                properties.put("mail.smtp.ssl.trust" , "*");
                properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user.email, user.password); //singlton
                    }
                });
                session.setDebug(true);

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(user.email));
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciver));
                    message.setSubject(subject);
                    message.setText(email);
                    Transport.send(message);

                } catch (Exception ee) {
                    System.out.println("the error is : " + ee.getMessage());
                }
                JOptionPane.showMessageDialog(null , " mail sent succesfully");


            }


        });



        showInboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reciveMail reciveMail = new reciveMail(null) ;
                dispose();

            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {

         sendmailtest sendmailtest = new sendmailtest(null);


    }


}





/*
    String sender = fromEmail.getText();
    String reciver = toEmail.getText();
    String subject = subjectEmail.getText();
    String mail = textEmail.getText();
                if (sender.isEmpty() || reciver.isEmpty() || subject.isEmpty() || mail.isEmpty() ) {
                        JOptionPane.showMessageDialog(null,
                        "Please enter all fields",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                        return;
                        }


                        String username = "c9347ed42632a2";
                        String password = "ab2a9f4a60107e";

                        Properties properties = new Properties();
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.ssl.protokls", "TLSv1.2");
                        properties.put("mail.smtp.host", "smtp.mailtrap.io");
                        properties.put("mail.smtp.port", "587");

                        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
@Override
protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
        }
        });
        session.setDebug(true);

        try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciver));
        message.setSubject(subject);
        message.setText(mail);
        Transport.send(message);

        } catch (Exception ee) {
        System.out.println("the error is : " + ee.getMessage());
        }
        JOptionPane.showMessageDialog(null , " mail sent succesfully");


        }


        });

        cancel.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
        dispose();
        }

        });
        setVisible(true);
        */
