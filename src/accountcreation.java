
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.*;
import java.util.Base64;
import java.util.Properties;

public class accountcreation {

    // Store created accounts
    private HashMap<String, Account> accounts = new HashMap<>();

    private SecureRandom random = new SecureRandom();

    private static final Path ACCOUNT_FILE =
    Path.of("accounts.properties");

public accountcreation() {

    loadAccounts();
}

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

        try {
            saveAccounts();

            } catch (IOException e) {
                 accounts.remove(username);
             return "Error: Account could not be saved";
    }

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


private void saveAccounts() throws IOException {

    Properties saved = new Properties();

    for (Account account : accounts.values()) {

        String salt = Base64.getEncoder()
            .encodeToString(account.salt);

        String hash = Base64.getEncoder()
            .encodeToString(account.passwordHash);

        saved.setProperty(account.username, salt + ":" + hash);
    }

    Path temporaryFile = Path.of("accounts.properties.tmp");

    try (OutputStream output = Files.newOutputStream(temporaryFile)) {
        saved.store(output, "Travel Planner accounts");
    }

    Files.move(
        temporaryFile,
        ACCOUNT_FILE,
        StandardCopyOption.REPLACE_EXISTING
    );
}

private void loadAccounts() {

    if (Files.notExists(ACCOUNT_FILE)) {
        return;
    }

    Properties saved = new Properties();

    try (InputStream input = Files.newInputStream(ACCOUNT_FILE)) {

        saved.load(input);

        for (String username : saved.stringPropertyNames()) {

            String[] values = saved.getProperty(username).split(":", 2);

            if (values.length != 2) {
                throw new IOException("Invalid account record");
            }

            byte[] salt = Base64.getDecoder().decode(values[0]);
            byte[] hash = Base64.getDecoder().decode(values[1]);

            accounts.put(username, new Account(username, hash, salt));
        }

    } catch (IOException | IllegalArgumentException e) {
        throw new IllegalStateException("Unable to load accounts", e);
    }
}

}
