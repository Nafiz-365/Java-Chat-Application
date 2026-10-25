package com.nafiz.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MessageContextMenu extends JPopupMenu {

    public MessageContextMenu(String messageText, Runnable onDelete) {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        Font menuFont = UiTheme.font(Font.PLAIN, 12);

        // ── 1. Copy Action ──────────────────────────────────────────────────
        if (messageText != null && !messageText.trim().isEmpty()) {
            JMenuItem copyItem = new JMenuItem("Copy text");
            copyItem.setIcon(ModernIcons.copy(UiTheme.TEXT, 16));
            copyItem.setFont(menuFont);
            copyItem.setBackground(Color.WHITE);
            copyItem.setForeground(UiTheme.TEXT);
            copyItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
            copyItem.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 12));
            copyItem.addActionListener(e -> {
                StringSelection selection = new StringSelection(messageText);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
            });
            add(copyItem);
        }

        // ── 2. Delete Action ────────────────────────────────────────────────
        if (onDelete != null) {
            JMenuItem deleteItem = new JMenuItem("Delete message");
            deleteItem.setIcon(ModernIcons.trash(UiTheme.DANGER, 16));
            deleteItem.setFont(menuFont);
            deleteItem.setBackground(Color.WHITE);
            deleteItem.setForeground(UiTheme.DANGER);
            deleteItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteItem.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 12));
            deleteItem.addActionListener(e -> onDelete.run());
            add(deleteItem);
        }
    }
}
