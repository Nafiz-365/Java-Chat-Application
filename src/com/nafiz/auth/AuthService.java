package com.nafiz.auth;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class AuthService {

    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private final Preferences users = Preferences.userNodeForPackage(AuthService.class).node("users");
    private final SecureRandom random = new SecureRandom();

    public AuthResult register(String username, char[] password) {
        String normalized = normalizeUsername(username);
        validate(normalized, password);
        if (users.get(normalized, null) != null) {
            return AuthResult.failure("That username is already registered.");
        }
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        users.put(normalized, encode(salt, hash(password, salt)));
        return AuthResult.success("Account created. You can now sign in.");
    }

    public AuthResult authenticate(String username, char[] password) {
        String normalized = normalizeUsername(username);
        validate(normalized, password);
        String stored = users.get(normalized, null);
        if (stored == null) {
            return AuthResult.failure("Incorrect username or password.");
        }
        String[] parts = stored.split(":", 2);
        if (parts.length != 2) {
            return AuthResult.failure("The saved account data is invalid.");
        }
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] expected = Base64.getDecoder().decode(parts[1]);
        boolean matches = MessageDigest.isEqual(expected, hash(password, salt));
        return matches
                ? AuthResult.success("Signed in.")
                : AuthResult.failure("Incorrect username or password.");
    }

    private static String normalizeUsername(String username) {
        return username == null ? "" : username.trim().toLowerCase(Locale.ROOT);
    }

    private static void validate(String username, char[] password) {
        if (username.length() < 3 || username.length() > 32
                || !username.matches("[a-z0-9._-]+")) {
            throw new IllegalArgumentException(
                    "Username must be 3-32 characters using letters, numbers, '.', '_' or '-'.");
        }
        if (password == null || password.length < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters.");
        }
    }

    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Password hashing is unavailable.", exception);
        } finally {
            spec.clearPassword();
        }
    }

    private static String encode(byte[] salt, byte[] hash) {
        return Base64.getEncoder().encodeToString(salt)
                + ":" + Base64.getEncoder().encodeToString(hash);
    }

    public record AuthResult(boolean successful, String message) {
        public static AuthResult success(String message) {
            return new AuthResult(true, message);
        }

        public static AuthResult failure(String message) {
            return new AuthResult(false, message);
        }
    }
}
