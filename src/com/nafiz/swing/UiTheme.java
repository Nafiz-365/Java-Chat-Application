package com.nafiz.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.text.JTextComponent;

public final class UiTheme {

    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color PRIMARY_HOVER = new Color(29, 78, 216);
    public static final Color TITLE_BAR = new Color(15, 23, 42);
    public static final Color SURFACE = new Color(248, 250, 252);
    public static final Color SURFACE_WHITE = Color.WHITE;
    public static final Color SIDEBAR = new Color(241, 245, 249);
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color LABEL = new Color(71, 85, 105);
    public static final Color SUCCESS = new Color(22, 163, 74);
    public static final Color DANGER = new Color(185, 28, 28);
    public static final Color DANGER_BORDER = new Color(254, 202, 202);
    public static final Color HOVER = new Color(226, 232, 240);
    public static final Color SELECTED = new Color(219, 234, 254);
    public static final Color BUBBLE_OUT = new Color(37, 99, 235);
    public static final Color BUBBLE_IN = new Color(241, 245, 249);
    public static final Color WINDOW = new Color(51, 65, 85);

    private UiTheme() {
    }

    public static Font font(int style, int size) {
        return new Font("Segoe UI", style, size);
    }

    public static void styleField(JTextComponent field) {
        field.setOpaque(true);
        field.setBackground(SURFACE);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setFont(font(Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    public static void stylePrimaryButton(JButton button) {
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(font(Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        Color normal = PRIMARY;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                button.setBackground(PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                button.setBackground(normal);
            }
        });
    }

    public static void styleLinkButton(JButton button) {
        button.setFont(font(Font.PLAIN, 12));
        button.setForeground(PRIMARY);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
