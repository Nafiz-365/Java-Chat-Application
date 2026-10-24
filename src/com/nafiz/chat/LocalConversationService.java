package com.nafiz.chat;

import java.util.ArrayList;
import java.util.List;

public final class LocalConversationService {

    private final List<ChatMessage> messages = new ArrayList<>();

    public synchronized ChatMessage send(String conversationId, String sender, String content) {
        String normalized = content == null ? "" : content.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty.");
        }
        ChatMessage message = ChatMessage.outgoing(conversationId, sender, normalized);
        messages.add(message);
        return message;
    }

    public synchronized List<ChatMessage> history(String conversationId) {
        return messages.stream()
                .filter(message -> message.conversationId().equals(conversationId))
                .toList();
    }
}
