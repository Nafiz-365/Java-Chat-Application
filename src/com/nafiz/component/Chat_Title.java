package com.nafiz.component;

import com.nafiz.swing.InitialsAvatar;
import com.nafiz.swing.UiTheme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Chat_Title extends javax.swing.JPanel {

    private Runnable onToggleDetails;
    private Runnable onBack;
    private JButton cmdBack;
    private JButton cmdDetails;
    private boolean detailsOpen = false;

    public Chat_Title() {
        initComponents();
    }

    public void setOnToggleDetails(Runnable onToggleDetails) {
        this.onToggleDetails = onToggleDetails;
    }

    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    public void setBackVisible(boolean visible) {
        if (cmdBack != null) {
            cmdBack.setVisible(visible);
            revalidate();
            repaint();
        }
    }

    public void setUserName(String userName) {
        lbName.setText(userName);
        avatar.setInitials(userName);
    }

    public void statusActive() {
        lbStatus.setText("Active now");
        lbStatus.setForeground(UiTheme.SUCCESS);
    }

    public void setStatusText(String text) {
        lbStatus.setText(text);
        lbStatus.setForeground(UiTheme.MUTED);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        layer = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 0, 2));
        layer.setOpaque(false);
        lbName = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        avatar = new InitialsAvatar("YC");

        setBackground(UiTheme.SURFACE_WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.BORDER),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        setLayout(new BorderLayout(12, 0));

        avatar.setPreferredSize(new Dimension(40, 40));

        cmdBack = new JButton();
        cmdBack.setIcon(com.nafiz.swing.ModernIcons.back(UiTheme.PRIMARY, 18));
        cmdBack.setContentAreaFilled(false);
        cmdBack.setFocusPainted(false);
        cmdBack.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));
        cmdBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdBack.setToolTipText("Back to conversations");
        cmdBack.setVisible(false);
        cmdBack.addActionListener(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });

        javax.swing.JPanel leftGroup = new javax.swing.JPanel(new BorderLayout(8, 0));
        leftGroup.setOpaque(false);
        leftGroup.add(cmdBack, BorderLayout.WEST);
        leftGroup.add(avatar, BorderLayout.CENTER);
        add(leftGroup, BorderLayout.WEST);

        lbName.setFont(UiTheme.font(Font.BOLD, 15));
        lbName.setForeground(UiTheme.TEXT);
        lbName.setText("Your conversation");
        lbName.setMinimumSize(new Dimension(20, 18));
        layer.add(lbName);

        lbStatus.setFont(UiTheme.font(Font.PLAIN, 12));
        lbStatus.setForeground(UiTheme.MUTED);
        lbStatus.setText("Local workspace");
        lbStatus.setMinimumSize(new Dimension(20, 14));
        layer.add(lbStatus);
        layer.setMinimumSize(new Dimension(40, 32));
        add(layer, BorderLayout.CENTER);

        cmdDetails = new JButton("Details");
        cmdDetails.setFont(UiTheme.font(Font.BOLD, 12));
        cmdDetails.setForeground(UiTheme.PRIMARY);
        cmdDetails.setContentAreaFilled(false);
        cmdDetails.setFocusPainted(false);
        cmdDetails.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        cmdDetails.setIconTextGap(6);
        updateDetailsLabel();
        cmdDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdDetails.setToolTipText("Toggle details panel");
        cmdDetails.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        cmdDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                cmdDetails.setBackground(UiTheme.SELECTED);
                cmdDetails.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                cmdDetails.setContentAreaFilled(false);
            }
        });
        cmdDetails.addActionListener(e -> {
            detailsOpen = !detailsOpen;
            updateDetailsLabel();
            if (onToggleDetails != null) {
                onToggleDetails.run();
            }
        });

        java.awt.event.MouseAdapter headerClick = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (onToggleDetails != null) {
                    onToggleDetails.run();
                }
            }
        };
        avatar.addMouseListener(headerClick);
        avatar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        layer.addMouseListener(headerClick);
        layer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.JPanel rightControls = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 4));
        rightControls.setOpaque(false);
        rightControls.add(cmdDetails);
        add(rightControls, BorderLayout.EAST);
    }

    /** Sync button arrow indicator without firing the toggle callback */
    public void setDetailsOpen(boolean open) {
        this.detailsOpen = open;
        updateDetailsLabel();
    }

    private void updateDetailsLabel() {
        if (cmdDetails != null) {
            cmdDetails.setIcon(detailsOpen
                    ? com.nafiz.swing.ModernIcons.chevronLeft(UiTheme.PRIMARY, 14)
                    : com.nafiz.swing.ModernIcons.chevronRight(UiTheme.PRIMARY, 14));
        }
    }

    private InitialsAvatar avatar;
    private javax.swing.JPanel layer;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
}
