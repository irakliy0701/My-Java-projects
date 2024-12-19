package Registration_Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Registration_Main extends JFrame implements ActionListener {
    private  JPanel registerPanel;
    private  JPanel TailPanel;
    private  JPanel MiddlePanel;
    private  JPanel HeadPanel;

    private  JLabel JetBrains_icon;
    private JLabel QuestMarkHelp;

    private JTextField UsertextField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JLabel SignIn;
    private JLabel ForgotPassword;
    private JLabel Middle_icons;
    private JLabel Username;
    private JLabel Password;

    private JButton SupportButton;
    private JLabel Tail_icons;

    private JFrame jFrame;
    public User user;

    public static void main(String[] args) {
        Registration_Main registr = new Registration_Main();
//        User user = registr.user;
//        if (user != null){
//            System.out.println("Successful registration of: " + user.username);
//        }
    }

    public Registration_Main(){
        jFrame = new JFrame("Registration_Main");
//        Image im = new ImageIcon("img/Registration/HeadPanel/jetBrains_icon.png").getImage();
//        jFrame.setIconImage(im);
        jFrame.setTitle("https://account.jetbrains.com/oauth2/signin?login_challenge=f5729e83513b4bcaa63c965f6669f019");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(1920, 1080));
        jFrame.setResizable(true);

        // Adding main panel
        jFrame.add(registerPanel);

//        jFrame.setContentPane(new Registration_Main().registerPanel);
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();

        signInButton.setFocusable(false);
        signInButton.addActionListener(this);
        SupportButton.setFocusable(false);
        SupportButton.addActionListener(this);

        // PopUpMenu
        JPopupMenu jPopupMenu = new JPopupMenu();
//        jPopupMenu.setForeground(Color.WHITE);
//        jPopupMenu.setBackground(Color.GRAY);
//        Font font = new Font("Arial Unicode MS", Font.BOLD, 14);
//        jPopupMenu.setFont(font);
        jPopupMenu.setBorderPainted(true);

        JMenuItem j1 = jPopupMenu.add(new JMenuItem("Назад"));

        JMenuItem j2 = jPopupMenu.add(new JMenuItem("Вперед"));

        JMenuItem j3 =jPopupMenu.add(new JMenuItem("Перегрузить"));
        j3.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
        jPopupMenu.addSeparator();

        JMenuItem j4 =jPopupMenu.add(new JMenuItem("Сохранить как..."));
        j4.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        JMenuItem j5 =jPopupMenu.add(new JMenuItem("Печать..."));
        j5.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));

        JMenuItem j6 =jPopupMenu.add(new JMenuItem("Трансляция..."));
        jPopupMenu.addSeparator();

//        ImageIcon imageIcon = new ImageIcon("img/QR-code.png");
//        JMenuItem j7 =jPopupMenu.add(new JMenuItem("Создать QR-код для этой страницы",
//                imageIcon));
        JMenuItem j7 =jPopupMenu.add(new JMenuItem("Создать QR-код для этой страницы"));
        jPopupMenu.addSeparator();

        JMenuItem j8 =jPopupMenu.add(new JMenuItem("Перевести на русский"));
        jPopupMenu.addSeparator();

        JMenuItem j9 =jPopupMenu.add(new JMenuItem("Просмотр кода страницы"));
        j9.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));

        JMenuItem j10 =jPopupMenu.add(new JMenuItem("Посмотреть код"));

        registerPanel.setComponentPopupMenu(jPopupMenu);

        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signInButton){
            try {
                registerUser();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getSource() == SupportButton){
            Helpform helpform = new Helpform();
        }
    }

    private void registerUser() throws IOException {
        String username = UsertextField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Заполните пустые поля",
                    "",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.length() > 40 || username.length() < 6
                || password.length() < 4 || password.length() > 20
                || !username.contains("@") || username.equals(password)) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Incorrect username and/or password",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

//        File newfile = new File("myJetBrains.txt");
//        if (!newfile.exists()) {
//            newfile.createNewFile();
//        }
//        PrintWriter pw = new PrintWriter(newfile);
//        pw.write("username: " + username + "\n");
//        pw.write("password: " + password + "\n\n");
//        pw.flush();
//        pw.close();
//
//        System.exit(0);

        //Надо расскоментить, если хочешь чтобы програ работала с MySql DB
        user = addUserToDB(username, password);
        if (user != null){
            dispose();
        }
        System.exit(0);
    }

    //Регистрация пользователей в MySql DB (надо раскоментить, чтобы был коннект с DB)
    private User addUserToDB(String username, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "Kurd0701";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to DB successfully...
            Statement st = conn.createStatement();
            String sql = "INSERT INTO users (username, password)" +
                    "VALUES(?,?)";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, username);
            pr.setString(2, password);

            // Insert row into the table
            int addedrows = pr.executeUpdate();
            if (addedrows > 0){
                user = new User();
                user.username = username;
                user.password = password;
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
