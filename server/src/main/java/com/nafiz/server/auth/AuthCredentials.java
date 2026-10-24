package com.nafiz.server.auth;

public record AuthCredentials(String username, char[] password) {

    public AuthCredentials {
        username = UsernamePolicy.normalizeAndValidate(username);
        if (password == null || password.length < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters.");
        }
    }
}
