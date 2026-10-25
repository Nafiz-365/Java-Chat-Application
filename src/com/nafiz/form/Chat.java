/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.form;

import com.nafiz.component.Chat_Body;
import com.nafiz.component.Chat_Bottom;
import com.nafiz.component.Chat_Title;
import com.nafiz.auth.Session;
import com.nafiz.chat.ChatMessage;
import com.nafiz.chat.ChatTransport;
import com.nafiz.chat.LocalConversationService;
import com.nafiz.chat.LocalChatTransport;
import com.nafiz.event.EventChat;
import com.nafiz.event.PublicEvent;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Nafiz_365
 */
public class Chat extends javax.swing.JPanel {
    private ChatTransport transport;
    private Chat_Title chatTitle;
    private Chat_Body chatBody;
    private static final String CONVERSATION_ID = "local";
    private String conversationId = CONVERSATION_ID;

    /**
     * Creates new form Menu_Left
     */
    public Chat() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 0, wrap 1", "0[grow, fill]0",
                "0[shrink 0][grow, fill][shrink 0]0"));
        chatTitle = new Chat_Title();
        chatTitle.setUserName("Your conversation");
        chatTitle.setStatusText("Local workspace");
        chatBody = new Chat_Body();
        Chat_Bottom chatBottom = new Chat_Bottom();
        transport = new LocalChatTransport(new LocalConversationService());
        transport.setListener(new ChatTransport.Listener() {
            @Override
            public void onConnected() {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    chatBody.clearChat();
                    java.util.List<ChatMessage> history = transport.history(conversationId);
                    String currentUser = Session.getUsername();
                    for (ChatMessage msg : history) {
                        if (currentUser != null && currentUser.equalsIgnoreCase(msg.sender())) {
                            chatBody.addItemRight(msg);
                        } else {
                            chatBody.addItemLeft(msg);
                        }
                    }
                });
            }

            @Override
            public void onMessage(ChatMessage message) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    String currentUser = Session.getUsername();
                    if (currentUser != null && currentUser.equalsIgnoreCase(message.sender())) {
                        chatBody.addItemRight(message);
                    } else {
                        chatBody.addItemLeft(message);
                    }
                });
            }

            @Override
            public void onError(String message) {
                javax.swing.SwingUtilities.invokeLater(() ->
                        javax.swing.JOptionPane.showMessageDialog(Chat.this, message,
                                "Message unavailable", javax.swing.JOptionPane.WARNING_MESSAGE));
            }

            @Override
            public void onDisconnected() {
                javax.swing.SwingUtilities.invokeLater(() -> chatBody.clearChat());
            }
        });
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(String text) {
                String sender = Session.getUsername();
                if (sender == null) {
                    return;
                }
                transport.send(conversationId, sender, text);
            }

            @Override
            public void selectConversation(String name) {
                chatTitle.setUserName(name);
                chatTitle.statusActive();
                String targetId = name == null ? CONVERSATION_ID : name.trim().toLowerCase().replaceAll("[^a-z0-9_-]", "_");
                if (!conversationId.equals(targetId)) {
                    conversationId = targetId;
                    chatBody.clearChat();
                    java.util.List<ChatMessage> history = transport.history(conversationId);
                    String currentUser = Session.getUsername();
                    for (ChatMessage msg : history) {
                        if (currentUser != null && currentUser.equalsIgnoreCase(msg.sender())) {
                            chatBody.addItemRight(msg);
                        } else {
                            chatBody.addItemLeft(msg);
                        }
                    }
                }
                if (onConversationSelected != null) {
                    onConversationSelected.run();
                }
            }
        });

        add(chatTitle, "growx, wrap");
        add(chatBody, "grow, push, wrap");
        add(chatBottom, "growx");

    }

    private Runnable onConversationSelected;

    public void setOnConversationSelected(Runnable action) {
        this.onConversationSelected = action;
    }

    public void setOnBack(Runnable action) {
        if (chatTitle != null) {
            chatTitle.setOnBack(action);
        }
    }

    public void setBackVisible(boolean visible) {
        if (chatTitle != null) {
            chatTitle.setBackVisible(visible);
        }
    }

    public void setOnToggleDetails(Runnable action) {
        if (chatTitle != null) {
            chatTitle.setOnToggleDetails(action);
        }
    }

    /** Sync the Details button arrow indicator (▶/◀) with the actual slider state */
    public void setDetailsOpen(boolean open) {
        if (chatTitle != null) {
            chatTitle.setDetailsOpen(open);
        }
    }

    public void connect() {
        if (!transport.isConnected()) {
            transport.connect();
        }
    }

    public void disconnect() {
        if (transport.isConnected()) {
            transport.disconnect();
        }
        if (chatBody != null) {
            chatBody.clearChat();
        }
    }

    /**
     * Initializes the visual shell used by the generated form editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new Color(248, 250, 252));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
