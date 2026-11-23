
import java.awt.*;
import javax.swing.*;

public class AccountPage {

    public static void showAccountPage(accountcreation accounts, String username) {

        
        JFrame frame = new JFrame("Account Settings");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        
        JLabel title = new JLabel("Account Settings");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(JLabel.CENTER);

        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(30, 70, 30, 70)
        );

        
        JButton updateButton = new JButton("Update Account");
        JButton deleteButton = new JButton("Delete Account");
        JButton logoutButton = new JButton("Log Out");
        JButton backButton = new JButton("Back to Dashboard");

        
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(backButton);

       
updateButton.addActionListener(e -> {


    JPasswordField currentPasswordField = new JPasswordField();
    JTextField newUsernameField = new JTextField();
    JPasswordField newPasswordField = new JPasswordField();

    
    Object[] fields = {
        "Current Password:", currentPasswordField,
        "New Username:", newUsernameField,
        "New Password:", newPasswordField
    };


    int choice = JOptionPane.showConfirmDialog(
        frame,
        fields,
        "Update Account",
        JOptionPane.OK_CANCEL_OPTION
    );

    
    if (choice == JOptionPane.OK_OPTION) {

        String newUsername = newUsernameField.getText();
        char[] currentPassword = currentPasswordField.getPassword();
        char[] newPassword = newPasswordField.getPassword();

        
        String result;

        try {
            result = accounts.updateAccount(
                username,
                currentPassword,
                newUsername,
                newPassword
            );
        } finally {
            java.util.Arrays.fill(currentPassword, '\0');
            java.util.Arrays.fill(newPassword, '\0');
            currentPasswordField.setText("");
            newPasswordField.setText("");
        }


        JOptionPane.showMessageDialog(frame, result);

        
        if (result.equals("Account updated successfully!")) {

            frame.dispose();
            travelplanner.showLogin(accounts);
        }
    }
}
);


        
deleteButton.addActionListener(e -> {


    int confirmation = JOptionPane.showConfirmDialog(
        frame,
        "Are you sure you want to delete your account?",
        "Delete Account",
        JOptionPane.YES_NO_OPTION
    );

    
    if (confirmation == JOptionPane.YES_OPTION) {

        
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "Enter your current password:",
            passwordField
        };

        int choice = JOptionPane.showConfirmDialog(
            frame,
            fields,
            "Confirm Password",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (choice == JOptionPane.OK_OPTION) {

            char[] password = passwordField.getPassword();

            
            String result;

            try {
                result = accounts.deleteAccount(username, password);
            } finally {
                java.util.Arrays.fill(password, '\0');
                passwordField.setText("");
            }

            JOptionPane.showMessageDialog(frame, result);

        
            if (result.equals("Account deleted successfully!")) {
                frame.dispose();
                travelplanner.showLogin(accounts);
            }
        }
    }
}
);


        
        logoutButton.addActionListener(e -> {
            frame.dispose();
            travelplanner.showLogin(accounts);
        });

        
        backButton.addActionListener(e -> {
            frame.dispose();
            Dashboard.showDashboard(accounts, username);
        });

        
        frame.add(title, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
