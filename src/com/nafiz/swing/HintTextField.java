package com.nafiz.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

public class HintTextField extends JTextField {

    private String hint = "";

    public HintTextField(String hint) {
        this.hint = hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (getText().isEmpty() && hint != null && !hint.isEmpty()) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(UiTheme.MUTED);
            g2.setFont(getFont());
            int baseline = getHeight() / 2 + g2.getFontMetrics().getAscent() / 2 - 2;
            g2.drawString(hint, getInsets().left, baseline);
            g2.dispose();
        }
    }
}
