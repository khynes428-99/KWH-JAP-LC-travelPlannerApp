
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class accountcreation {

    // Store created accounts
    private HashMap<String, Account> accounts = new HashMap<>();

    private SecureRandom random = new SecureRandom();

    // store user information
    private static class Account {

        String username;
        byte[] passwordHash;
        byte[] salt;

        Account(String username, byte[] passwordHash, byte[] salt) {
            this.username = username;
            this.passwordHash = passwordHash;
            this.salt = salt;
        }
    }

    // create an account
    public String createAccount(String username, char[] password) {

       
        if (username == null || username.isBlank()) {
            return "Username cannot be empty";
        }

        if (password == null || password.length == 0) {
            return "Password cannot be empty";
        }

        if (password.length < 8) {
            return "Password must be at least 8 characters";
        }

        username = username.trim().toLowerCase(java.util.Locale.ROOT);

        if (accounts.containsKey(username)) {
            return "Username already exists";
        }

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] passwordHash = hashPassword(password, salt);

        Account newAccount = new Account(username, passwordHash, salt);

        accounts.put(username, newAccount);

        return "Account created successfully!";
    }

    public boolean verifyAccount(String username, char[] password) {

        if (username == null || password == null) {
            return false;
        }

        username = username.trim().toLowerCase(java.util.Locale.ROOT);

        if (!accounts.containsKey(username)) {
            return false;
        }

        Account savedAccount = accounts.get(username);

        byte[] enteredHash = hashPassword(password, savedAccount.salt);

        // Compare password hashes
        try {
            return MessageDigest.isEqual(
                savedAccount.passwordHash, enteredHash
            );
        } finally {
            Arrays.fill(enteredHash, (byte) 0);
        }
    }

    private byte[] hashPassword(char[] password, byte[] salt) {

        PBEKeySpec spec = new PBEKeySpec(
            password, salt, 600000, 256
        );

        try {
            SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            return factory.generateSecret(spec).getEncoded();

        } catch (Exception e) {
            throw new IllegalStateException("Password hashing failed", e);
        } finally {
            spec.clearPassword();
        }
    }
}
