import javax.mail.*;
import javax.mail.search.FlagTerm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class reciveMail extends  JDialog{
    private JButton showButton;
    private JPanel recivePanel;
    private JLabel subjectlb;
    private JLabel fromLb;
    private JLabel inboxName;
    private JLabel senddate;
    private JLabel mailLabel;


    public reciveMail(JFrame parent) {
        super(parent);
        setTitle("inbox");
        setContentPane(recivePanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);





               // String sender = GetMail.getText();
                //String pass =  getPassword(sender);

               User user = userLoginHolder.getInstance().getuser(); //singleton


                if ( user.email.isEmpty() ) {



                    JOptionPane.showMessageDialog(null,
                            "Please enter all fields",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }


                try
                {
                    Properties properties = new Properties();
                    properties.put("mail.pop3.auth" , "true" );
                    properties.put("mail.store.protocol" ,"pop3");
                    properties.put("mail.pop3.starttls.enable" , "true");
                    properties.put("mail.pop3.ssl.enable" , "true");
                    properties.put("mail.debug" , "true");
                    properties.setProperty("mail.pop3.host", "outlook.office365.com");
                    properties.setProperty("mail.pop3.port" , "995");
                    properties.put("mail.pop3.ssl.protocols", "TLSv1.2");
                    properties.put("mail.pop3.ssl.trust" , "*");

                    Session emailSession = Session.getInstance(properties);
                    emailSession.setDebug(true);

                    Store store = emailSession.getStore("pop3");
                    store.connect("outlook.office365.com" , user.email , user.password); //singlton

                    Folder emailFolder = store.getFolder("INBOX");
                    emailFolder.open(Folder.READ_ONLY);
                    Message messages[] = emailFolder.getMessages();

                    int i = ((messages.length)-1);
                    Message message = messages[i];

                    System.out.println("EMAIL NUMBER " + (i+1));
                    subjectlb.setText( "Subject  :" + message.getSubject());
                    fromLb.setText( "From :"+ String.valueOf(message.getFrom() [0]));
                    inboxName.setText("inbox of user : " + user.name + " /  id: " + user.user_id + "/ Email : " + user.email); //singlton
                    senddate.setText( " Date of messsage :" + message.getSentDate().toString());
                    mailLabel.setText(" Body : " + message.getContent());





                    emailFolder.close(true);
                    store.close();



                }
                catch (NoSuchProviderException noSuchProviderException) {
                    noSuchProviderException.printStackTrace();
                } catch (MessagingException messagingException) {
                    messagingException.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendmailtest sendmailtest = new sendmailtest(null);
                dispose();

            }
        });
        setVisible(true);
    }



                public static void main(String[] args) {
                    reciveMail reciveMail = new reciveMail(null);
                }
            }




















/*


        */



