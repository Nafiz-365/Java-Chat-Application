package com.nafiz.form;

import com.nafiz.event.PublicEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu_Right extends javax.swing.JPanel {

    public Menu_Right() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(248, 250, 252));
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(226, 232, 240)));
        setLayout(new BorderLayout());

        JPanel profile = new JPanel(new BorderLayout(10, 2));
        profile.setOpaque(false);
        profile.setBorder(BorderFactory.createEmptyBorder(24, 18, 18, 18));

        JLabel avatar = new JLabel("FC", JLabel.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(37, 99, 235));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        avatar.setPreferredSize(new Dimension(40, 40));

        JPanel details = new JPanel(new BorderLayout(0, 3));
        details.setOpaque(false);
        JLabel name = new JLabel("My profile");
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        name.setForeground(new Color(15, 23, 42));
        JLabel status = new JLabel("Online");
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        status.setForeground(new Color(22, 163, 74));
        details.add(name, BorderLayout.NORTH);
        details.add(status, BorderLayout.SOUTH);

        profile.add(avatar, BorderLayout.WEST);
        profile.add(details, BorderLayout.CENTER);
        add(profile, BorderLayout.NORTH);

        JButton logout = new JButton("Sign out");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logout.setForeground(new Color(185, 28, 28));
        logout.setBackground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(254, 202, 202)),
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
        JPanel profile = (JPanel) getComponent(0);
        JPanel details = (JPanel) profile.getComponent(1);
        JLabel name = (JLabel) details.getComponent(0);
        JLabel avatar = (JLabel) profile.getComponent(0);
        name.setText(displayName);
        avatar.setText(displayName.substring(0, 1).toUpperCase());
    }
}
