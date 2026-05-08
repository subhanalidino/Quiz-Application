package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class QuizApp extends JFrame {

    int userId = 1;
    int score = 0;
    int total = 5;

    JButton submitBtn;

    public QuizApp() {

        setTitle("Quiz Screen");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Quiz Finished (Demo)");
        title.setBounds(100, 80, 200, 30);
        add(title);

        submitBtn = new JButton("Submit Quiz");
        submitBtn.setBounds(120, 150, 150, 40);
        add(submitBtn);

        // 👉 Jab user quiz finish kare
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Demo score (yahan tum apna real score use karogi)
                score = 3;

                SaveResult sr = new SaveResult();

                // 1. Save score
                sr.storingResult(userId, score);

                // 2. Fetch latest score
                int latestScore = sr.getLatestScore(userId);

                // 3. Close quiz window
                dispose();

                // 4. Show result (same flow)
                new ResultScreen(userId, latestScore, total);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizApp();
            }
        });
    }
}


// ================= DATABASE CLASS =================
class SaveResult {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (Exception e) {
            System.out.println("Driver Error");
        }
    }

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quiz",
                "root",
                "ali@12345#"
        );
    }

    // SAVE RESULT
    void storingResult(int userId, int score) {
        try {
            Connection conn = connect();

            String sql = "INSERT INTO result(user_id, correct_answers) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, score);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Result Saved");

        } catch (Exception e) {
            System.out.println("DB Error: " + e);
        }
    }

    // FETCH LATEST SCORE
    int getLatestScore(int userId) {
        int score = 0;

        try {
            Connection conn = connect();

            String sql = "SELECT correct_answers FROM result WHERE user_id=? ORDER BY result_id DESC LIMIT 1";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                score = rs.getInt("correct_answers");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Fetch Error: " + e);
        }

        return score;
    }
}


// ================= RESULT SCREEN =================

class ResultScreen extends JFrame {
    JLabel background;

    public ResultScreen(int userId, int correct, int total) {

        setTitle("Quiz Result");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        background = new JLabel(new ImageIcon("D:\\Project Folder OOP\\BG5.jpg"));
        background.setBounds(0,0,400,300);
        background.setLayout(null);
        setContentPane(background);


        getContentPane().setBackground(new Color(200, 162, 200));


        int wrong = total - correct;
        int percentage = (correct * 100) / total;

        JLabel l1 = new JLabel("Correct: " + correct);
        JLabel l2 = new JLabel("Wrong: " + wrong);
        JLabel l3 = new JLabel("Percentage: " + percentage + "%");

        l1.setBounds(120, 80, 200, 40);
        l2.setBounds(120, 120, 200, 40);
        l3.setBounds(120, 160, 200, 40);

        background.add(l1);
        background.add(l2);
        background.add(l3);

        setVisible(true);
        setAlwaysOnTop(true);
    }
}