package com.nafiz.chat;

import java.util.List;

public interface ChatTransport {

    void connect();

    void disconnect();

    boolean isConnected();

    List<ChatMessage> history(String conversationId);

    void send(String conversationId, String sender, String content);

    void setListener(Listener listener);

    interface Listener {

        void onConnected();

        void onMessage(ChatMessage message);

        void onError(String message);

        void onDisconnected();
    }
}
