import java.awt.*;
import javax.swing.*;

public class travelplanner {

    public static void main(String[] args) {

        accountcreation accounts = new accountcreation();

        JFrame frame = new JFrame("Login");
        frame.setSize(450, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Text fields
        JPanel fieldsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        usernameField.setBorder(
            BorderFactory.createTitledBorder("Username")
        );

        passwordField.setBorder(
            BorderFactory.createTitledBorder("Password")
        );

        fieldsPanel.add(usernameField);
        fieldsPanel.add(passwordField);

        // Centered button underneath
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton loginButton = new JButton("Login");

        JButton createAccountButton = new JButton("Create Account");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });

        createAccountButton.addActionListener(e -> {

            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            String result = accounts.createAccount(username, password);

            JOptionPane.showMessageDialog(frame, result);
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);


        frame.add(fieldsPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


/* Please add the following:
Authentication (Verify username and password) 
Obfuscate passwords
Open new window on successful login
*/ 
