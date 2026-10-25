package com.nafiz.component;

import com.nafiz.swing.ModernIcons;
import com.nafiz.swing.UiTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Chat_File extends javax.swing.JPanel {

    private JLabel lbFileName;
    private JLabel lbFileSize;
    private JLabel iconLabel;

    public Chat_File() {
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setLayout(new BorderLayout(10, 0));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 12)));

        iconLabel = new JLabel();
        iconLabel.setIcon(ModernIcons.file(UiTheme.PRIMARY, 28));
        iconLabel.setPreferredSize(new Dimension(32, 32));
        add(iconLabel, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(2, 1, 0, 2));
        info.setOpaque(false);

        lbFileName = new JLabel("Document.file");
        lbFileName.setFont(UiTheme.font(Font.BOLD, 13));
        lbFileName.setForeground(UiTheme.TEXT);
        info.add(lbFileName);

        lbFileSize = new JLabel("0 KB");
        lbFileSize.setFont(UiTheme.font(Font.PLAIN, 11));
        lbFileSize.setForeground(UiTheme.MUTED);
        info.add(lbFileSize);

        add(info, BorderLayout.CENTER);
    }

    public void setFile(String fileName, String size) {
        lbFileName.setText(fileName);
        lbFileSize.setText(size);
    }

    public void applyBubbleStyle(boolean isOutgoing) {
        if (isOutgoing) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 12)));
            iconLabel.setIcon(ModernIcons.file(Color.WHITE, 28));
            lbFileName.setForeground(Color.WHITE);
            lbFileSize.setForeground(new Color(224, 231, 255));
        } else {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiTheme.BORDER, 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 12)));
            iconLabel.setIcon(ModernIcons.file(UiTheme.PRIMARY, 28));
            lbFileName.setForeground(UiTheme.TEXT);
            lbFileSize.setForeground(UiTheme.MUTED);
        }
    }
}
