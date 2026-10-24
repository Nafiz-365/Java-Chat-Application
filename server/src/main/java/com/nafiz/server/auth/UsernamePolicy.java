package com.nafiz.server.auth;

import java.util.Locale;

public final class UsernamePolicy {

    private UsernamePolicy() {
    }

    public static String normalizeAndValidate(String username) {
        String normalized = username == null
                ? ""
                : username.trim().toLowerCase(Locale.ROOT);
        if (normalized.length() < 3 || normalized.length() > 32
                || !normalized.matches("[a-z0-9._-]+")) {
            throw new IllegalArgumentException(
                    "Username must be 3-32 characters using letters, numbers, '.', '_' or '-'.");
        }
        return normalized;
    }
}
