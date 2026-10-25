package com.nafiz.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.prefs.Preferences;

public final class LocalConversationService {

    private static final String MESSAGE_PREFIX = "messages.";
    private final Preferences storage = Preferences.userNodeForPackage(
            LocalConversationService.class);

    public synchronized ChatMessage send(String conversationId, String sender, String content) {
        String normalized = content == null ? "" : content.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty.");
        }
        ChatMessage message = ChatMessage.outgoing(conversationId, sender, normalized);
        List<ChatMessage> messages = read(conversationId);
        messages.add(message);
        write(conversationId, messages);
        return message;
    }

    public synchronized List<ChatMessage> history(String conversationId) {
        return List.copyOf(read(conversationId));
    }

    public synchronized void deleteMessage(String content) {
        if (content == null || content.isEmpty()) {
            return;
        }
        try {
            for (String key : storage.keys()) {
                if (key.startsWith(MESSAGE_PREFIX)) {
                    String conversationId = key.substring(MESSAGE_PREFIX.length());
                    List<ChatMessage> messages = new ArrayList<>(read(conversationId));
                    boolean changed = messages.removeIf(m -> m.content().equals(content)
                            || m.content().contains(content)
                            || content.contains(m.content()));
                    if (changed) {
                        write(conversationId, messages);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private List<ChatMessage> read(String conversationId) {
        String raw = storage.get(MESSAGE_PREFIX + conversationId, "");
        List<ChatMessage> messages = new ArrayList<>();
        if (raw.isBlank()) {
            return messages;
        }
        for (String line : raw.split("\\R")) {
            String[] fields = line.split("\\t", -1);
            if (fields.length != 5) {
                continue;
            }
            try {
                messages.add(new ChatMessage(
                        decode(fields[0]),
                        conversationId,
                        decode(fields[1]),
                        decode(fields[2]),
                        java.time.Instant.ofEpochMilli(Long.parseLong(fields[3])),
                        MessageStatus.valueOf(fields[4])));
            } catch (RuntimeException ignored) {
                // Ignore a damaged local record and keep the rest of the history usable.
            }
        }
        return messages;
    }

    private void write(String conversationId, List<ChatMessage> messages) {
        String value = messages.stream()
                .map(message -> String.join("\t",
                        encode(message.id()),
                        encode(message.sender()),
                        encode(message.content()),
                        Long.toString(message.createdAt().toEpochMilli()),
                        message.status().name()))
                .reduce((left, right) -> left + "\n" + right)
                .orElse("");
        storage.put(MESSAGE_PREFIX + conversationId, value);
    }

    private static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(Base64.getUrlDecoder().decode(value),
                java.nio.charset.StandardCharsets.UTF_8);
    }
}
