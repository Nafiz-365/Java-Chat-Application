/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.form;

import com.nafiz.swing.SlidingPanel;
import com.nafiz.swing.UiTheme;
import net.miginfocom.swing.MigLayout;

public class Home extends javax.swing.JLayeredPane {
    public static final int COMPACT_BREAKPOINT = 560;
    public static final int WIDE_BREAKPOINT = 780;
    private static final int SIDEBAR_WIDTH = 240;
    private static final int RIGHT_WIDTH = 260;

    private Menu_Left menuLeft;
    private Menu_Right menuRight;
    private Chat chat;
    private SlidingPanel rightSlider;

    // View states
    private boolean isCompact = false;
    private boolean detailsOpen = false;
    private boolean compactInChat = true;
    private boolean compactInDetails = false;

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
            if (isCompact) {
                compactInChat = true;
                compactInDetails = false;
                applyLayoutState();
            }
        });

        chat.setOnBack(() -> {
            if (isCompact) {
                if (compactInDetails) {
                    compactInDetails = false;
                    compactInChat = true;
                } else {
                    compactInChat = false;
                }
                applyLayoutState();
            } else if (detailsOpen && getWidth() < WIDE_BREAKPOINT) {
                // In medium mode: back button closes details and restores sidebar
                closeRightMenu();
            }
        });

        // Initialize layout based on window width
        int initialWidth = getWidth();
        isCompact = initialWidth > 0 && initialWidth < COMPACT_BREAKPOINT;
        applyLayoutState();

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

        boolean nowCompact = width < COMPACT_BREAKPOINT;
        boolean compactStateChanged = nowCompact != isCompact;
        if (compactStateChanged) {
            isCompact = nowCompact;
        }
        applyLayoutState();
    }

    private void applyLayoutState() {
        int width = getWidth();
        removeAll();

        if (isCompact) {
            // ─── 1. COMPACT / MOBILE MODE (< 560px) ─────────────────────────
            // Single panel takes 100% full width, zero empty space
            setLayout(new MigLayout("fill, insets 0, hidemode 3", "0[grow, fill]0", "0[grow, fill]0"));
            add(menuLeft, "cell 0 0, grow, push");
            add(chat, "cell 0 0, grow, push");
            add(rightSlider, "cell 0 0, grow, push");
            rightSlider.setFullWidthMode(true);

            if (compactInDetails) {
                menuLeft.setVisible(false);
                chat.setVisible(false);
                rightSlider.setVisible(true);
                chat.setDetailsOpen(true);
            } else if (compactInChat) {
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
        } else {
            // ─── 2. DESKTOP / TABLET MODE (>= 560px) ────────────────────────
            boolean isWide = width >= WIDE_BREAKPOINT;
            rightSlider.setFullWidthMode(false);

            if (isWide) {
                // Wide mode (>= 780px): 3 columns possible simultaneously
                // Sidebar (240px) + Chat (grow, push) + Details slider (0-260px)
                setLayout(new MigLayout("fill, insets 0, hidemode 3",
                        "0[" + SIDEBAR_WIDTH + "!]0[grow, fill]0[]", "0[grow, fill]0"));
                add(menuLeft, "cell 0 0, width " + SIDEBAR_WIDTH + "!, growy");
                add(chat, "cell 1 0, grow, push");
                add(rightSlider, "cell 2 0, growy");

                menuLeft.setVisible(true);
                chat.setVisible(true);
                chat.setBackVisible(false);
                rightSlider.setVisible(true);
                chat.setDetailsOpen(detailsOpen);
            } else {
                // Medium mode (560px - 779px): 2 columns
                if (detailsOpen) {
                    // When Details is open, show Chat + Details side-by-side so chat isn't squished
                    setLayout(new MigLayout("fill, insets 0, hidemode 3",
                            "0[grow, fill]0[" + RIGHT_WIDTH + "!]0", "0[grow, fill]0"));
                    add(chat, "cell 0 0, grow, push");
                    add(rightSlider, "cell 1 0, width " + RIGHT_WIDTH + "!, growy");

                    menuLeft.setVisible(false);
                    chat.setVisible(true);
                    chat.setBackVisible(true); // Back button allows closing details to restore sidebar
                    rightSlider.setVisible(true);
                    rightSlider.slideOpen();
                    chat.setDetailsOpen(true);
                } else {
                    // Normal state: Sidebar + Chat side-by-side, nothing hidden
                    setLayout(new MigLayout("fill, insets 0, hidemode 3",
                            "0[" + SIDEBAR_WIDTH + "!]0[grow, fill]0", "0[grow, fill]0"));
                    add(menuLeft, "cell 0 0, width " + SIDEBAR_WIDTH + "!, growy");
                    add(chat, "cell 1 0, grow, push");

                    menuLeft.setVisible(true);
                    chat.setVisible(true);
                    chat.setBackVisible(false);
                    rightSlider.setVisible(false);
                    rightSlider.slideClose();
                    chat.setDetailsOpen(false);
                }
            }
        }

        revalidate();
        repaint();
    }

    public void toggleRightMenu() {
        if (isCompact) {
            compactInDetails = !compactInDetails;
            applyLayoutState();
        } else {
            detailsOpen = !detailsOpen;
            if (getWidth() >= WIDE_BREAKPOINT) {
                // Wide desktop: slide animate
                if (detailsOpen) {
                    rightSlider.slideOpen();
                } else {
                    rightSlider.slideClose();
                }
                chat.setDetailsOpen(detailsOpen);
            } else {
                // Medium desktop: switch to 2-column Chat + Details
                applyLayoutState();
            }
        }
    }

    public void closeRightMenu() {
        if (isCompact) {
            compactInDetails = false;
            applyLayoutState();
        } else {
            detailsOpen = false;
            if (getWidth() >= WIDE_BREAKPOINT) {
                rightSlider.slideClose();
                chat.setDetailsOpen(false);
            } else {
                applyLayoutState();
            }
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