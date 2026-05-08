package Project;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Jdbc {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Load Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Something Missing(driver)");
        }
    }

    public static Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/quiz";
        String userName = "root";
        String passwordDb = "ali@12345#";
        return DriverManager.getConnection(url, userName, passwordDb);
    }
}

abstract class QuizBase extends JFrame {
    int userId;
    int getScore;
    Connection conn;
    ResultSet result;
    String correctAnswer;
    int score;

    JTextField getAnswer;
    JButton nextButton;

    JLabel labelQuestion;
    JLabel opt1, opt2, opt3;

    JLabel background;

    public QuizBase() {
        background = new JLabel(new ImageIcon("D:\\Project Folder OOP\\BG4.jpeg"));
        background.setBounds(0, 0, 900, 700);
        setContentPane(background);
        setSize(900, 700);
        setLayout(null);
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

    // SAME LOGIC (UNCHANGED)
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

    // SAME LOGIC (ONLY CLEANED STRUCTURE)
    void loadQuestions() {
        try {
            if (result.next()) {

                int qId = result.getInt("question_id");

                labelQuestion.setText(result.getString("question_name"));

                PreparedStatement ps = conn.prepareStatement(
                        "SELECT choice_name FROM quiz.choice WHERE question_id=? AND is_correct=1"
                );

                ps.setInt(1, qId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    correctAnswer = rs.getString("choice_name");
                }

                PreparedStatement ps2 = conn.prepareStatement(
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

class EngQuestionsLogic extends QuizBase {

    String subTopicName;

    public EngQuestionsLogic(String subTopicName) {

        this.subTopicName = subTopicName;

        if (subTopicName.equals("Verb")) {
            showVerb();
        } else {
            showTenses();
        }

        setVisible(true);
    }

    private void showVerb() {
        try {
            conn = Jdbc.connect();

            String query = "SELECT question_name, question_id FROM quiz.question WHERE subtopic_id=1";

            PreparedStatement prep = conn.prepareStatement(query);
            result = prep.executeQuery();

            loadQuestions();

        } catch (Exception e) {
            System.out.println("Exception Case");
        }
    }

    private void showTenses() {
        try {
            conn = Jdbc.connect();

            String query = "SELECT question_name, question_id FROM quiz.question WHERE subtopic_id=2";

            PreparedStatement prep = conn.prepareStatement(query);
            result = prep.executeQuery();

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

class English extends JFrame {

    public English() {

        setSize(900, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel pic = new JLabel(new ImageIcon("D:\\Project Folder OOP\\BG3.jpeg"));
        pic.setBounds(200, 0, 900, 700);
        setContentPane(pic);

        JLabel l = new JLabel("Choose Any Topic!");
        l.setBounds(400, 25, 600, 400);
        l.setFont(new Font("Brush Script MT", Font.PLAIN, 60));
        add(l);

        JButton verb = new JButton("Verb");
        JButton tense = new JButton("Tenses");

        verb.setBounds(400, 300, 200, 90);
        tense.setBounds(650, 300, 200, 90);

        verb.addActionListener(e -> new EngQuestionsLogic("Verb"));
        tense.addActionListener(e -> new EngQuestionsLogic("Tenses"));

        add(verb);
        add(tense);

        setVisible(true);
    }
}