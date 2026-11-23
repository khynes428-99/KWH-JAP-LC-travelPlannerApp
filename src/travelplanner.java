import java.awt.*;
import javax.swing.*;

public class travelplanner {

    public static void main(String[] args) {

        accountcreation accounts = new accountcreation();

         showLogin(accounts);
    }
     public static void showLogin(accountcreation accounts) {

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

    // Get the username and password from the text fields
    String username = usernameField.getText().trim().toLowerCase();
    char[] password = passwordField.getPassword();

    // Check if the username and password are correct
    boolean loginSuccessful = accounts.verifyAccount(username, password);

    // Clear the password after checking it
    java.util.Arrays.fill(password, '\0');
    passwordField.setText("");

    if (loginSuccessful) {

        
        frame.dispose();

        // Open the dashboard
        Dashboard.showDashboard(accounts, username);

    } else {

        JOptionPane.showMessageDialog(
            frame, "Incorrect username or password"
        );
    }
}
);
           

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



