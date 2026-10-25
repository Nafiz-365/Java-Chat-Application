package com.nafiz.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

    private final int arc;
    private Color stroke;
    private boolean glassy = false;

    public RoundedPanel(LayoutManager layout, int arc) {
        super(layout);
        this.arc = arc;
        this.stroke = UiTheme.BORDER;
        setOpaque(false);
    }

    public void setStroke(Color stroke) {
        this.stroke = stroke;
        repaint();
    }

    /** Enable glassmorphism / frosted-glass look */
    public void setGlassy(boolean glassy) {
        this.glassy = glassy;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth() - 1;
        int h = getHeight() - 1;

        if (glassy) {
            // ── Layer 1: deep semi-transparent base ──────────────────────
            g2.setColor(new Color(255, 255, 255, 30));
            g2.fillRoundRect(0, 0, w, h, arc, arc);

            // ── Layer 2: frosted glass body fill ─────────────────────────
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, w, h, arc, arc);

            // ── Layer 3: top shimmer gradient (glass highlight) ───────────
            GradientPaint shimmer = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 90),
                    0, h * 0.45f, new Color(255, 255, 255, 8));
            g2.setPaint(shimmer);
            g2.fillRoundRect(0, 0, w, h / 2, arc, arc);

            // ── Layer 4: bottom edge inner glow ───────────────────────────
            GradientPaint bottomGlow = new GradientPaint(
                    0, h * 0.75f, new Color(255, 255, 255, 0),
                    0, h, new Color(255, 255, 255, 22));
            g2.setPaint(bottomGlow);
            g2.fillRoundRect(0, h / 2, w, h / 2, arc, arc);

            // ── Layer 5: left specular highlight strip ────────────────────
            GradientPaint leftGlow = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 55),
                    w * 0.08f, 0, new Color(255, 255, 255, 0));
            g2.setPaint(leftGlow);
            g2.fillRoundRect(0, 0, w, h, arc, arc);

            // ── Layer 6: glassy border ────────────────────────────────────
            if (stroke != null) {
                g2.setColor(new Color(255, 255, 255, 110));
                g2.drawRoundRect(0, 0, w, h, arc, arc);
                // inner glow border
                g2.setColor(new Color(255, 255, 255, 35));
                g2.drawRoundRect(1, 1, w - 2, h - 2, arc - 2, arc - 2);
            }
        } else {
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, w, h, arc, arc);
            if (stroke != null) {
                g2.setColor(stroke);
                g2.drawRoundRect(0, 0, w, h, arc, arc);
            }
        }

        g2.dispose();
        super.paintComponent(graphics);
    }
}
