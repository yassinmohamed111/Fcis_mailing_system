import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class showGrades extends  JDialog {
  //  private JTextField codeofCourse;
    private JButton showBtn;
    private JLabel lbGrade;
    private JLabel lbPractical;
    private JLabel lbYear;
    private JPanel ShowPanel;
    private JLabel LbCousrsName;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel LbGpa;


    public showGrades(JFrame parent) {

        super(parent);
        setTitle("show grades");
        setContentPane(ShowPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               String code = comboBox1.getSelectedItem().toString().toLowerCase();

              if ( comboBox2.getSelectedItem() == "2020-2021 1st-term")
              {
                   LbGpa.setText(" gpa of 2020-2021 1st-term : 2.43");
              }
              else if (comboBox2.getSelectedItem() == "2020-2021 2nd-term" )
              {
                  LbGpa.setText(" gpa  of 2020-2021 2nd-term : 2.29");
              }
              else if (comboBox2.getSelectedItem() == "2021-2022 1st-term" )
              {
                  LbGpa.setText(" gpa of 2021-2022 1st-term : 1.98");
              }
              else if ( comboBox2.getSelectedItem() == "2021-2022 2nd-term")
              {
                  LbGpa.setText(" gpa of 2021-2022 1st-term: 1.50");
              }
              else if ( comboBox2.getSelectedItem() == "2022-2023 1st-term")
              {
                  LbGpa.setText(" gpa of 2022-2023 1st-term: 1.90");
              }
              else if ( comboBox2.getSelectedItem() == "2022-2023 2nd-term")
              {
                  LbGpa.setText(" gpa of 2022-2023 2nd-term: 2.35");
              }
                getGrade(code);


            }
        });
        setVisible(true);
    }

    public grades grade;

    public grades getGrade(String coursename) {
        grades grade = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM grades WHERE Coursename=?  ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, coursename);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
              grade = new grades();

                grade.grade = resultSet.getString("grade");
                grade.practicalgrade = resultSet.getString("practicalgrade");
                grade.year = resultSet.getString("year");

                grade.courseName = resultSet.getString("Coursename");

                // System.out.println( " name : " + cont.name + "\t " +" email :" + cont.email +" \t"+ "phone :" + cont.phone +" \t"+" relation :" + cont.relation + " \t" + "contacts of user_id : "+ cont.user_id);

                LbCousrsName.setText("Course name : " + grade.courseName);
                if ( grade.grade.contains("null") ) {
                     lbGrade.setText(" Grade : Not out yet !");
                }
                else
                {lbGrade.setText("Grade: " + grade.grade);

                }
                lbPractical.setText("Yearworks : " + grade.practicalgrade);
                lbYear.setText("Year : " + grade.year);


            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return  grade ;

    }


    public  static void main(String[] args) {
       showGrades showGrades = new showGrades(null);
       grades grade = new grades();

    }
}
