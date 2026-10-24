package com.nafiz.protocol;

import java.util.Objects;

public record ProtocolEnvelope<T>(
        int version,
        ProtocolMessageType type,
        String requestId,
        T payload,
        ProtocolError error) {

    public ProtocolEnvelope {
        if (version < 1) {
            throw new IllegalArgumentException("version must be positive");
        }
        type = Objects.requireNonNull(type, "type");
        if (requestId != null && requestId.isBlank()) {
            throw new IllegalArgumentException("requestId must not be blank");
        }
        if (payload != null && error != null) {
            throw new IllegalArgumentException("payload and error cannot both be present");
        }
    }

    public static <T> ProtocolEnvelope<T> request(
            ProtocolMessageType type, String requestId, T payload) {
        return new ProtocolEnvelope<>(1, type, requestId, payload, null);
    }

    public static <T> ProtocolEnvelope<T> response(
            ProtocolMessageType type, String requestId, T payload) {
        return new ProtocolEnvelope<>(1, type, requestId, payload, null);
    }

    public static ProtocolEnvelope<Void> failure(
            ProtocolMessageType type, String requestId, ProtocolError error) {
        return new ProtocolEnvelope<>(1, type, requestId, null,
                Objects.requireNonNull(error, "error"));
    }

    public boolean isError() {
        return error != null;
    }
}
