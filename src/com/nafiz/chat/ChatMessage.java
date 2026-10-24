package com.nafiz.chat;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record ChatMessage(
        String id,
        String conversationId,
        String sender,
        String content,
        Instant createdAt,
        MessageStatus status) {

    public ChatMessage {
        id = Objects.requireNonNull(id);
        conversationId = Objects.requireNonNull(conversationId);
        sender = Objects.requireNonNull(sender);
        content = Objects.requireNonNull(content);
        createdAt = Objects.requireNonNull(createdAt);
        status = Objects.requireNonNull(status);
    }

    public static ChatMessage outgoing(String conversationId, String sender, String content) {
        return new ChatMessage(UUID.randomUUID().toString(), conversationId, sender,
                content, Instant.now(), MessageStatus.SENT);
    }
}
