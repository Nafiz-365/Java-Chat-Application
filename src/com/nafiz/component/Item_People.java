package com.nafiz.component;

import com.nafiz.event.PublicEvent;
import com.nafiz.swing.InitialsAvatar;
import com.nafiz.swing.UiTheme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Item_People extends javax.swing.JPanel {

    private static Item_People selectedItem;
    private boolean selected;
    private final boolean selectable;
    private final String name;

    public Item_People(String name) {
        this.name = name;
        this.selectable = name != null && !name.toLowerCase().startsWith("no ");
        initComponents();
        lb.setText(name);
        init();
    }

    private void init() {
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if (!selected) setBackground(UiTheme.HOVER);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if (!selected) setBackground(UiTheme.SIDEBAR);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (!selectable) return;
                select();
                if (PublicEvent.getInstance().getEventChat() != null) {
                    PublicEvent.getInstance().getEventChat().selectConversation(name);
                }
            }
        });
    }

    private void select() {
        if (selectedItem != null && selectedItem != this) {
            selectedItem.selected = false;
            selectedItem.setBackground(UiTheme.SIDEBAR);
        }
        selected = true;
        selectedItem = this;
        setBackground(UiTheme.SELECTED);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        avatar = new InitialsAvatar(name);
        lb = new JLabel();

        // Smart preview text based on item name
        String previewText;
        if (name == null || name.toLowerCase().startsWith("no ")) {
            previewText = "";
        } else if (name.toLowerCase().contains("saved")) {
            previewText = "Your personal notepad";
        } else if (name.toLowerCase().contains("new conversation")) {
            previewText = "Tap to start chatting";
        } else if (name.toLowerCase().contains("group")) {
            previewText = "Create a group chat";
        } else if (name.toLowerCase().contains("archive")) {
            previewText = "No archived chats";
        } else {
            previewText = "Local conversation";
        }

        setBackground(UiTheme.SIDEBAR);
        setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
        setLayout(new BorderLayout(10, 0));

        avatar.setPreferredSize(new Dimension(42, 42));
        add(avatar, BorderLayout.WEST);

        JPanel text = new JPanel(new BorderLayout(0, 3));
        text.setOpaque(false);
        lb.setFont(UiTheme.font(Font.BOLD, 13));
        lb.setForeground(UiTheme.TEXT);
        text.add(lb, BorderLayout.NORTH);

        if (!previewText.isEmpty()) {
            JLabel preview = new JLabel(previewText);
            preview.setFont(UiTheme.font(Font.PLAIN, 11));
            preview.setForeground(UiTheme.MUTED);
            text.add(preview, BorderLayout.SOUTH);
        }
        add(text, BorderLayout.CENTER);
    }

    private InitialsAvatar avatar;
    private JLabel lb;
}
