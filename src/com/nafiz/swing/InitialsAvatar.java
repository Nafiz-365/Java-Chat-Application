package com.nafiz.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class InitialsAvatar extends JPanel {

    private String initials = "FC";

    public InitialsAvatar(String name) {
        setOpaque(false);
        setInitials(name);
    }

    public void setInitials(String name) {
        if (name == null || name.isBlank()) {
            initials = "FC";
        } else {
            initials = name.trim().substring(0, 1).toUpperCase();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;
        g2.setColor(UiTheme.PRIMARY);
        g2.fillOval(x, y, size, size);
        g2.setColor(Color.WHITE);
        g2.setFont(UiTheme.font(Font.BOLD, 14));
        int textWidth = g2.getFontMetrics().stringWidth(initials);
        int textAscent = g2.getFontMetrics().getAscent();
        g2.drawString(initials, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textAscent / 2 - 3);
        g2.dispose();
    }
}
