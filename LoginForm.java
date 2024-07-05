import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton loginButton;

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        add(userLabel);

        userField = new JTextField(20);
        userField.setBounds(140, 50, 100, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 80, 80, 25);
        add(passLabel);

        passField = new JPasswordField(20);
        passField.setBounds(140, 80, 100, 25);
        add(passField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 80, 25);
        loginButton.addActionListener(this);
        add(loginButton);
    }

    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        if (authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            new MainSystem().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reservation_system", "root", "password");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username=? AND password=?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}
    

