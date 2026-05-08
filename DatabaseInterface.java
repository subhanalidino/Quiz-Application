package Project;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
class DatabaseInterface{
    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Load Successfully");}
        catch(ClassNotFoundException e){
            System.out.println("Something Missing(driver)");
        }}
    public static Connection connect() throws Exception {
        String url="jdbc:mysql://localhost:3306/quiz";
        String userName="root";
        String passwordDb="ali@12345#";
        return DriverManager.getConnection(url,userName,passwordDb);
    }

}
// logic for showing questions on screen
abstract class DBQuestionLogic extends JFrame{
    int userId;
    int getScore;
    Connection con;
    String query;
    ResultSet Result;
    //JLabel label//
    JTextField getAnswer;
    JButton nextButton;
    String correctAnswer;
    int score;

    JLabel labelQuestion;
    JLabel opt1 ;
    JLabel opt2 ;
    JLabel opt3 ;

    JLabel background;

    public DBQuestionLogic() {

        background = new JLabel(new ImageIcon("D:\\Project Folder OOP\\BG4.jpeg"));
        background.setBounds(0, 0, 400, 300);
        background.setLayout(null);
        setContentPane(background);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labelQuestion = new JLabel();
        opt1 = new JLabel();
        opt2 = new JLabel();
        opt3 = new JLabel();

        getAnswer = new JTextField("Your Choice");
        nextButton = new JButton("Next");

        labelQuestion.setBounds(420, 120, 400, 100);
        opt1.setBounds(420, 200, 300, 20);
        opt2.setBounds(420, 250, 300, 20);
        opt3.setBounds(420, 300, 300, 20);

        getAnswer.setBounds(420, 350, 150, 30);
        nextButton.setBounds(420, 400, 100, 30);

        background.add(labelQuestion);
        background.add(opt1);
        background.add(opt2);
        background.add(opt3);
        background.add(getAnswer);
        background.add(nextButton);

        nextButton.addActionListener(e -> checkAnswer());
    }

void checkAnswer() {

    String userInput = getAnswer.getText().trim();

    if (userInput.equalsIgnoreCase(correctAnswer)) {
        score++;
        JOptionPane.showMessageDialog(this, "This Choice is Correct");
    } else {
        JOptionPane.showMessageDialog(this,
                "This Choice is inCorrect And Correct Answer is : " + correctAnswer);
    }

    getAnswer.setText(null);
    loadQuestions();
}

    void loadQuestions() {
        try {
            if (Result.next()) {

                int qId = Result.getInt("question_id");

                labelQuestion.setText(Result.getString("question_name"));

                PreparedStatement ps = con.prepareStatement(
                        "SELECT choice_name FROM quiz.choice WHERE question_id=? AND is_correct=1"
                );

                ps.setInt(1, qId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    correctAnswer = rs.getString("choice_name");
                }

                PreparedStatement ps2 = con.prepareStatement(
                        "SELECT choice_name FROM quiz.choice WHERE question_id=?"
                );

                ps2.setInt(1, qId);
                ResultSet rs2 = ps2.executeQuery();

                int i = 0;

                while (rs2.next()) {

                    String option = rs2.getString("choice_name");

                    if (i == 0) opt1.setText("A) " + option);
                    else if (i == 1) opt2.setText("B) " + option);
                    else if (i == 2) opt3.setText("C) " + option);

                    i++;
                }

            } else {
                JOptionPane.showMessageDialog(this, "Quiz Finished!");
                ResultScreen sr = new ResultScreen(1 , score , 5);
                this.dispose();
            }

        } catch (Exception e) {
            System.out.println("Exception Case");
        }
    }

    abstract void loadData();
}

class DatabaseQuestionLogic extends DBQuestionLogic {

        String subTopicName;
    public DatabaseQuestionLogic(String subTopicName) {

        this.subTopicName = subTopicName;

        if (subTopicName.equals("Tables")) {
            showTables();
        } else {
            showConstraints();
        }

        setVisible(true);
    }

    private void showTables() {
        try {
            con = Jdbc.connect();

            String query = "SELECT question_name, question_id FROM quiz.question WHERE subtopic_id=3";

            PreparedStatement prep = con.prepareStatement(query);
            Result = prep.executeQuery();

            loadQuestions();

        } catch (Exception e) {
            System.out.println("Exception Case");
        }
    }

    private void showConstraints() {
        try {
            con = Jdbc.connect();

            String query = "SELECT question_name, question_id FROM quiz.question WHERE subtopic_id=4";

            PreparedStatement prep = con.prepareStatement(query);
            Result = prep.executeQuery();

            loadQuestions();

        } catch (Exception e) {
            System.out.println("Exception Case");
        }
    }

    @Override
    void loadData() {
        // intentionally unused (kept for structure only)
    }
}

// for database quiz background
class Database extends JFrame{
    public Database(){

        setSize(900,700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel pic = new JLabel(new ImageIcon("D:\\Project Folder OOP\\BG3.jpeg"));
        pic.setBounds(200,0 ,900,700);
//        pic.setLayout(null);
        setContentPane(pic);

        // for setting background
//        getContentPane().setBackground(new Color(200,162,200));
        JLabel l=new JLabel("Choose Any Topic!");
        l.setBounds(400,25,600,400);
        l.setForeground(Color.BLACK);
        l.setFont(new Font("Brush Script MT",Font.PLAIN,60));
        add(l);

        // for making verb button
        JButton Tables=new JButton("Tables");
        Tables.setBackground(new Color(80,160,150));
        Tables.setFont(new Font("Arial",Font.BOLD,20));
        Tables.setBounds(400,300,200,90);


        // for making tenses button
        JButton Constraints=new JButton("DB Constraints");
        Constraints.setBackground(new Color(80,160,150));
        Constraints.setFont(new Font("Arial",Font.BOLD,20));
        Constraints.setBounds(650,300,200,90);

        Tables.addActionListener(input ->{
            new DatabaseQuestionLogic("Tables");
        });
        Constraints.addActionListener(input1 ->{
            new DatabaseQuestionLogic("Constraints");
        });

//        setLayout(null);
        add(Tables);
        add(Constraints);
//        setSize(300,150);
        setVisible(true);
    }

}