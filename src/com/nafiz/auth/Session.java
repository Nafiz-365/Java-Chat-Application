package com.nafiz.auth;

public final class Session {

    private static String username;

    private Session() {
    }

    public static synchronized void start(String authenticatedUsername) {
        username = authenticatedUsername;
    }

    public static synchronized String getUsername() {
        return username;
    }

    public static synchronized boolean isActive() {
        return username != null;
    }

    public static synchronized void clear() {
        username = null;
    }
}
