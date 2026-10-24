package com.nafiz.server.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PasswordHasherTest {

    private final PasswordHasher hasher = new PasswordHasher();

    @Test
    void hashesAndVerifiesWithoutStoringPlaintext() {
        String encoded = hasher.hash("correct-password".toCharArray());

        assertTrue(hasher.matches("correct-password".toCharArray(), encoded));
        assertFalse(hasher.matches("wrong-password".toCharArray(), encoded));
        assertFalse(encoded.contains("correct-password"));
    }
}
