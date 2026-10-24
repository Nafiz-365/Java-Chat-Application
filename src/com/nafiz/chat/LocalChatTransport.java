package com.nafiz.chat;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public final class LocalChatTransport implements ChatTransport {

    private final LocalConversationService conversations;
    private volatile ExecutorService executor = createExecutor();
    private volatile Listener listener;
    private volatile boolean connected;

    private static ExecutorService createExecutor() {
        return Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "local-chat-transport");
        thread.setDaemon(true);
        return thread;
        });
    }

    public LocalChatTransport(LocalConversationService conversations) {
        this.conversations = Objects.requireNonNull(conversations);
    }

    @Override
    public synchronized void connect() {
        if (executor.isShutdown()) {
            executor = createExecutor();
        }
        connected = true;
        notifyListener(Listener::onConnected);
    }

    @Override
    public synchronized void disconnect() {
        connected = false;
        notifyListener(Listener::onDisconnected);
        executor.shutdownNow();
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public List<ChatMessage> history(String conversationId) {
        return conversations.history(conversationId);
    }

    @Override
    public void send(String conversationId, String sender, String content) {
        if (!connected) {
            notifyError("Chat is not connected.");
            return;
        }
        try {
            executor.execute(() -> {
                try {
                    notifyMessage(conversations.send(conversationId, sender, content));
                } catch (IllegalArgumentException exception) {
                    notifyError(exception.getMessage());
                }
            });
        } catch (RejectedExecutionException exception) {
            notifyError("Chat is not connected.");
        }
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void notifyListener(java.util.function.Consumer<Listener> callback) {
        Listener current = listener;
        if (current != null) {
            callback.accept(current);
        }
    }

    private void notifyMessage(ChatMessage message) {
        if (!connected) {
            return;
        }
        notifyListener(listener -> listener.onMessage(message));
    }

    private void notifyError(String message) {
        if (!connected) {
            return;
        }
        notifyListener(listener -> listener.onError(message));
    }
}
