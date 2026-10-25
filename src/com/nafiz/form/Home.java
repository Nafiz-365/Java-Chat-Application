/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.form;

import com.nafiz.swing.SlidingPanel;
import com.nafiz.swing.UiTheme;
import net.miginfocom.swing.MigLayout;

public class Home extends javax.swing.JLayeredPane {
    public static final int NARROW_BREAKPOINT = 700;
    private static final int RIGHT_WIDTH = 280;

    private Menu_Left menuLeft;
    private Menu_Right menuRight;
    private Chat chat;
    private SlidingPanel rightSlider;
    private boolean isNarrow = false;
    private boolean inChatView = true;
    private boolean showingDetailsInNarrow = false;

    public Home() {
        initComponents();
        init();
    }

    private void init() {
        setOpaque(true);
        setBackground(UiTheme.SURFACE);

        menuLeft = new Menu_Left();
        chat = new Chat();
        menuRight = new Menu_Right();
        menuRight.setOnClose(this::closeRightMenu);

        // Wrap right panel in SlidingPanel for smooth animation
        rightSlider = new SlidingPanel(menuRight, RIGHT_WIDTH, false);

        chat.setOnToggleDetails(this::toggleRightMenu);

        chat.setOnConversationSelected(() -> {
            if (isNarrow) {
                inChatView = true;
                showingDetailsInNarrow = false;
                applyNarrowState();
            }
        });

        chat.setOnBack(() -> {
            if (isNarrow) {
                inChatView = false;
                showingDetailsInNarrow = false;
                applyNarrowState();
            }
        });

        // Initial setup
        isNarrow = getWidth() > 0 && getWidth() < NARROW_BREAKPOINT;
        applyLayoutConstraints();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                handleResize();
            }
        });
    }

    private void handleResize() {
        int width = getWidth();
        if (width <= 0) return;

        boolean nowNarrow = width < NARROW_BREAKPOINT;
        if (nowNarrow != isNarrow) {
            isNarrow = nowNarrow;
            applyLayoutConstraints();
        }
    }

    private void applyLayoutConstraints() {
        removeAll();
        if (isNarrow) {
            // Narrow mode (mobile single column): active panel fills 100% width with 0 empty gaps
            setLayout(new MigLayout("fill, insets 0, hidemode 3", "0[grow, fill]0", "0[grow, fill]0"));
            add(menuLeft, "cell 0 0, grow, push");
            add(chat, "cell 0 0, grow, push");
            add(rightSlider, "cell 0 0, grow, push");
            rightSlider.setFullWidthMode(true);
            applyNarrowState();
        } else {
            // Desktop mode (multi-column): sidebar 260px, chat fills remainder, rightSlider slides 0-280px
            setLayout(new MigLayout("fill, insets 0, hidemode 3", "0[260!]0[grow, fill]0[]", "0[grow, fill]0"));
            add(menuLeft, "cell 0 0, width 260!, growy");
            add(chat, "cell 1 0, grow, push");
            add(rightSlider, "cell 2 0, growy");
            rightSlider.setFullWidthMode(false);
            menuLeft.setVisible(true);
            chat.setVisible(true);
            chat.setBackVisible(false);
            rightSlider.setVisible(true);
            chat.setDetailsOpen(rightSlider.isOpen());
            revalidate();
            repaint();
        }
    }

    private void applyNarrowState() {
        if (!isNarrow) return;

        if (showingDetailsInNarrow) {
            menuLeft.setVisible(false);
            chat.setVisible(false);
            rightSlider.setVisible(true);
            chat.setDetailsOpen(true);
        } else if (inChatView) {
            rightSlider.setVisible(false);
            menuLeft.setVisible(false);
            chat.setVisible(true);
            chat.setBackVisible(true);
            chat.setDetailsOpen(false);
        } else {
            rightSlider.setVisible(false);
            chat.setVisible(false);
            menuLeft.setVisible(true);
            chat.setDetailsOpen(false);
        }
        revalidate();
        repaint();
    }

    public void toggleRightMenu() {
        if (isNarrow) {
            showingDetailsInNarrow = !showingDetailsInNarrow;
            applyNarrowState();
        } else {
            // Desktop: animate slide in/out
            boolean nowOpen = rightSlider.toggle();
            chat.setDetailsOpen(nowOpen);
        }
    }

    public void closeRightMenu() {
        if (isNarrow) {
            showingDetailsInNarrow = false;
            applyNarrowState();
        } else {
            rightSlider.slideClose();
            chat.setDetailsOpen(false);
        }
    }

    public void refreshSession(String username) {
        if (menuRight != null) {
            menuRight.setUsername(username);
        }
    }

    public void connectChat() {
        if (chat != null) {
            chat.connect();
        }
    }

    public void disconnectChat() {
        if (chat != null) {
            chat.disconnect();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1007, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 551, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}