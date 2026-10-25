package com.nafiz.form;

import com.nafiz.event.PublicEvent;
import com.nafiz.swing.InitialsAvatar;
import com.nafiz.swing.RoundedPanel;
import com.nafiz.swing.UiTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu_Right extends javax.swing.JPanel {

    private final InitialsAvatar avatar = new InitialsAvatar("My profile");
    private final JLabel name = new JLabel("My profile");
    private Runnable onClose;

    public Menu_Right() {
        initComponents();
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    private void initComponents() {
        setBackground(UiTheme.SURFACE_WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, UiTheme.BORDER));
        setLayout(new BorderLayout());

        JPanel profile = new JPanel(new BorderLayout(12, 2));
        profile.setOpaque(false);
        profile.setBorder(BorderFactory.createEmptyBorder(22, 18, 12, 18));

        avatar.setPreferredSize(new Dimension(48, 48));

        JPanel details = new JPanel(new BorderLayout(0, 4));
        details.setOpaque(false);
        name.setFont(UiTheme.font(Font.BOLD, 15));
        name.setForeground(UiTheme.TEXT);
        JLabel status = new JLabel("Online");
        status.setFont(UiTheme.font(Font.PLAIN, 12));
        status.setForeground(UiTheme.SUCCESS);
        details.add(name, BorderLayout.NORTH);
        details.add(status, BorderLayout.SOUTH);

        JButton cmdClose = new JButton();
        cmdClose.setIcon(com.nafiz.swing.ModernIcons.close(UiTheme.MUTED, 18));
        cmdClose.setContentAreaFilled(false);
        cmdClose.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        cmdClose.setFocusPainted(false);
        cmdClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdClose.setToolTipText("Close details");
        cmdClose.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                cmdClose.setIcon(com.nafiz.swing.ModernIcons.close(UiTheme.TEXT, 18));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                cmdClose.setIcon(com.nafiz.swing.ModernIcons.close(UiTheme.MUTED, 18));
            }
        });
        cmdClose.addActionListener(e -> {
            if (onClose != null) {
                onClose.run();
            }
        });

        profile.add(avatar, BorderLayout.WEST);
        profile.add(details, BorderLayout.CENTER);
        profile.add(cmdClose, BorderLayout.EAST);
        add(profile, BorderLayout.NORTH);

        RoundedPanel detailsCard = new RoundedPanel(new BorderLayout(0, 8), 18);
        detailsCard.setBackground(UiTheme.SURFACE);
        detailsCard.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JPanel cardHolder = new JPanel(new BorderLayout());
        cardHolder.setOpaque(false);
        cardHolder.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        JLabel heading = new JLabel("Your space");
        heading.setFont(UiTheme.font(Font.BOLD, 13));
        heading.setForeground(UiTheme.TEXT);
        JLabel description = new JLabel("<html><div style='width:150px'>"
                + "A calm place for notes, drafts, and conversations that stay on this computer."
                + "</div></html>");
        description.setFont(UiTheme.font(Font.PLAIN, 12));
        description.setForeground(UiTheme.MUTED);
        detailsCard.add(heading, BorderLayout.NORTH);
        detailsCard.add(description, BorderLayout.CENTER);
        cardHolder.add(detailsCard, BorderLayout.NORTH);
        add(cardHolder, BorderLayout.CENTER);

        JButton logout = new JButton("Sign out");
        logout.setFont(UiTheme.font(Font.BOLD, 13));
        logout.setForeground(UiTheme.DANGER);
        logout.setBackground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.DANGER_BORDER, 1, true),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        logout.addActionListener(event -> {
            int result = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to sign out?", "Sign out",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE);
            if (result == javax.swing.JOptionPane.YES_OPTION) {
                PublicEvent.getInstance().getEventMain().logout();
            }
        });

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(18, 18, 22, 18));
        footer.add(logout, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    public void setUsername(String username) {
        String displayName = username == null || username.isBlank() ? "My profile" : username;
        name.setText(displayName);
        avatar.setInitials(displayName);
    }
}
