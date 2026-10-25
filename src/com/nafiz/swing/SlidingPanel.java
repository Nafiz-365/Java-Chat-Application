package com.nafiz.swing;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A wrapper panel that can animate sliding open and closed horizontally.
 * Usage: wrap your content panel inside SlidingPanel, then call slideOpen() / slideClose().
 */
public class SlidingPanel extends JPanel {

    private static final int ANIM_STEPS = 12;   // number of timer ticks
    private static final int ANIM_DELAY_MS = 14; // ~70fps

    private int targetWidth;
    private int currentWidth;
    private boolean open;
    private Timer timer;
    private boolean fullWidthMode = false;

    /**
     * @param content     The panel to slide in/out
     * @param targetWidth The fully-expanded width in pixels
     * @param startOpen   Whether to start fully open
     */
    public SlidingPanel(Component content, int targetWidth, boolean startOpen) {
        this.targetWidth = targetWidth;
        this.open = startOpen;
        this.currentWidth = startOpen ? targetWidth : 0;

        setLayout(null); // absolute layout — we control size ourselves
        setOpaque(false);
        add(content);

        // Keep content filling our current width at all times
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                content.setBounds(0, 0, getWidth(), getHeight());
            }
        });
    }

    public void setFullWidthMode(boolean fullWidthMode) {
        this.fullWidthMode = fullWidthMode;
        if (fullWidthMode) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
        }
        revalidate();
        repaint();
    }

    public boolean isFullWidthMode() {
        return fullWidthMode;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
        if (open && !fullWidthMode) {
            this.currentWidth = targetWidth;
            revalidate();
            repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (fullWidthMode) {
            return new Dimension(getParent() != null ? getParent().getWidth() : targetWidth, super.getPreferredSize().height);
        }
        return new Dimension(currentWidth, super.getPreferredSize().height);
    }

    @Override
    public Dimension getMinimumSize() {
        if (fullWidthMode) {
            return new Dimension(0, 0);
        }
        return new Dimension(currentWidth, 0);
    }

    @Override
    public Dimension getMaximumSize() {
        if (fullWidthMode) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        return new Dimension(currentWidth, Integer.MAX_VALUE);
    }

    /** Animate open to targetWidth */
    public void slideOpen() {
        if (open && currentWidth >= targetWidth) return;
        open = true;
        animateTo(targetWidth);
    }

    /** Animate closed to 0 */
    public void slideClose() {
        if (!open && currentWidth <= 0) return;
        open = false;
        animateTo(0);
    }

    /** Toggle open/close. Returns new open state. */
    public boolean toggle() {
        if (open) {
            slideClose();
        } else {
            slideOpen();
        }
        return open;
    }

    public boolean isOpen() {
        return open;
    }

    private void animateTo(int dest) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        int start = currentWidth;
        int[] step = {0};
        timer = new Timer(ANIM_DELAY_MS, null);
        timer.addActionListener(e -> {
            step[0]++;
            // Ease-out: progress = 1 - (1 - t)^2
            double t = (double) step[0] / ANIM_STEPS;
            double eased = 1.0 - Math.pow(1.0 - t, 2.0);
            currentWidth = (int) Math.round(start + (dest - start) * eased);
            if (step[0] >= ANIM_STEPS) {
                currentWidth = dest;
                timer.stop();
            }
            revalidate();
            if (getParent() != null) {
                getParent().revalidate();
                getParent().repaint();
            }
        });
        timer.start();
    }
}
