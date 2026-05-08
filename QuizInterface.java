package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class QuizInterface {

    static int score = 0;
    static int total = 10;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
    }

    public static Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/quiz?useSSL=false&serverTimezone=UTC";
        String userName = "root";
        String passwordDb = "ali@12345#";
        return DriverManager.getConnection(url, userName, passwordDb);
    }

    // ---------------- LOGIN SCREEN (MERGED WITH InterfaceStart) ----------------
    public void Login() {

        JFrame frame = new JFrame("Main Frame");
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel background = new JLabel(new ImageIcon("D:\\Project Folder OOP\\image.jpg"));
        background.setBounds(0, 0, 800, 600);
        background.setLayout(null);
        frame.setContentPane(background);

        JLabel title = new JLabel("Quiz Application");
        title.setBounds(400, 200, 500, 80);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        background.add(title);

        JTextField input = new JTextField();
        input.setText("User ID");
        input.setBounds(450, 300, 300, 50);
        input.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(input);

        JButton enter = new JButton("Enter");
        enter.setBounds(450, 380, 200, 60);
        enter.setFont(new Font("Arial", Font.BOLD, 20));
        enter.setBackground(new Color(200, 162, 200));
        background.add(enter);

        enter.addActionListener(e -> {
            String text = input.getText().trim();
            try {
                Integer.parseInt(text); // validation
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid User ID");
                return;
            }

            frame.dispose();
            new QuizInterface().SubjectSelector();
        });

        frame.setVisible(true);
    }

    // ---------------- SUBJECT SELECTION ----------------
    public void SubjectSelector() {

        JFrame f1 = new JFrame("Subject Selection");
        f1.setSize(900, 600);
        f1.setLayout(null);

        JLabel bg = new JLabel(new ImageIcon("D:\\Project Folder OOP\\BG1.jpg"));
        bg.setBounds(0, 0, 900, 600);
        bg.setLayout(null);
        f1.setContentPane(bg);

        JLabel l = new JLabel("Select  Subject");
        l.setBounds(500, 250, 400, 60);
        l.setFont(new Font("Brush Script MT", Font.BOLD, 50));
        l.setForeground(Color.BLACK);
        bg.add(l);

        JButton englishButton = new JButton("English");
        englishButton.setBounds(500, 350, 150, 60);
        englishButton.setBackground(new Color(173, 173, 139));
        bg.add(englishButton);

        JButton databaseButton = new JButton("Database");
        databaseButton.setBounds(700, 350, 150, 60);
        databaseButton.setBackground(new Color(173, 173 , 139));
        bg.add(databaseButton);

        englishButton.addActionListener(e -> {
            f1.dispose();
            new English();
        });

        databaseButton.addActionListener(e -> {
            f1.dispose();
            new Database();
        });

//        resultButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(f1, "Score: " + score + "/" + total);
//        });

        f1.setVisible(true);
    }
}

// ---------------- MAIN CLASS ----------------
class choice {
    public static void main(String[] args) {
        new QuizInterface().Login();
    }
}