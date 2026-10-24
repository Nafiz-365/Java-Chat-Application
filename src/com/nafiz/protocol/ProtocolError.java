package com.nafiz.protocol;

import java.util.Objects;

public record ProtocolError(String code, String message, boolean retryable) {

    public ProtocolError {
        code = requireText(code, "code");
        message = requireText(message, "message");
    }

    private static String requireText(String value, String field) {
        Objects.requireNonNull(value, field);
        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value;
    }
}
