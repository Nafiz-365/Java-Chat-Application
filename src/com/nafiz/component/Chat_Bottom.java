/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.component;

import com.nafiz.event.PublicEvent;
import com.nafiz.swing.JIMSendTextPane;
import com.nafiz.swing.RoundedPanel;
import com.nafiz.swing.ScrollBar;
import com.nafiz.swing.UiTheme;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Nafiz_365
 */
public class Chat_Bottom extends javax.swing.JPanel {

    /**
     * Creates new form Chat_Title
     */
    public Chat_Bottom() {
        initComponents();
        init();
    }
    
    private void init() {
        setLayout(new MigLayout("fillx, insets 10 12 10 12", "[shrink 0]6[grow, fill]6[shrink 0]", "[fill]"));
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.setOpaque(true);
        txt.setBackground(UiTheme.SURFACE);
        txt.setFont(UiTheme.font(java.awt.Font.PLAIN, 13));
        txt.setForeground(UiTheme.TEXT);
        JButton cmd = new JButton();

        // ── Attachment button (left) ──────────────────────────────────────
        JButton cmdAttach = new JButton();
        cmdAttach.setIcon(com.nafiz.swing.ModernIcons.attachment(UiTheme.MUTED, 20));
        cmdAttach.setContentAreaFilled(false);
        cmdAttach.setFocusPainted(false);
        cmdAttach.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        cmdAttach.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdAttach.setToolTipText("Attach file or image");
        cmdAttach.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                cmdAttach.setIcon(com.nafiz.swing.ModernIcons.attachment(UiTheme.PRIMARY, 20));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                cmdAttach.setIcon(com.nafiz.swing.ModernIcons.attachment(UiTheme.MUTED, 20));
            }
        });
        cmdAttach.addActionListener(e -> {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setDialogTitle("Select file to attach");
            int res = chooser.showOpenDialog(Chat_Bottom.this);
            if (res == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = chooser.getSelectedFile();
                if (file != null && file.exists()) {
                    long sizeKb = Math.max(1, file.length() / 1024);
                    String msg = "[Attached: " + file.getName() + " (" + sizeKb + " KB)]";
                    if (PublicEvent.getInstance().getEventChat() != null) {
                        PublicEvent.getInstance().getEventChat().sendMessage(msg);
                    }
                }
            }
        });

        txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent event) { refresh(); }
            @Override public void removeUpdate(DocumentEvent event) { refresh(); }
            @Override public void changedUpdate(DocumentEvent event) { refresh(); }
        });
        txt.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke("ENTER"), "send-message");
        txt.getActionMap().put("send-message", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent event) { cmd.doClick(); }
        });
        txt.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke("shift ENTER"), "insert-newline");
        txt.getActionMap().put("insert-newline", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent event) { insertNewline(txt); }
        });
        txt.setBorder(new EmptyBorder(8, 10, 8, 10));
        txt.setHintText("Write a message...");
        scroll.setViewportView(txt);
        ScrollBar sb = new ScrollBar();
        sb.setBackground(UiTheme.SURFACE);
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        RoundedPanel composer = new RoundedPanel(new BorderLayout(), 18);
        composer.setBackground(UiTheme.SURFACE);
        composer.add(scroll, BorderLayout.CENTER);

        // ── Send button (right) ───────────────────────────────────────────
        cmd.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 11, 9, 11));
        cmd.setContentAreaFilled(false);
        cmd.setOpaque(true);
        cmd.setBackground(UiTheme.PRIMARY);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setToolTipText("Send message (Enter)");
        cmd.setIcon(com.nafiz.swing.ModernIcons.send(java.awt.Color.WHITE, 18));
        cmd.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                cmd.setBackground(UiTheme.PRIMARY_HOVER);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                cmd.setBackground(UiTheme.PRIMARY);
            }
        });
        cmd.addActionListener(event -> sendMessage(txt));

        add(cmdAttach);
        add(composer, "growx");
        JPanel sendWrap = new JPanel(new MigLayout("filly", "0[]0", "0[bottom]0"));
        sendWrap.setOpaque(false);
        sendWrap.add(cmd);
        add(sendWrap);
        setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, UiTheme.BORDER),
                javax.swing.BorderFactory.createEmptyBorder(2, 0, 2, 0)));
    }

    private void sendMessage(JIMSendTextPane txt) {
        String text = txt.getText().trim();
        if (!text.isEmpty()) {
            if (PublicEvent.getInstance().getEventChat() != null) {
                PublicEvent.getInstance().getEventChat().sendMessage(text);
            }
            txt.setText("");
        }
        txt.grabFocus();
        refresh();
    }

    private void insertNewline(JIMSendTextPane txt) {
        int position = txt.getCaretPosition();
        try {
            txt.getDocument().insertString(position, "\n", null);
            int newPosition = Math.min(position + 1, txt.getDocument().getLength());
            txt.setCaretPosition(newPosition);
        } catch (javax.swing.text.BadLocationException exception) {
            throw new IllegalStateException("Unable to insert a new line.", exception);
        }
        txt.revalidate();
        txt.repaint();
    }
    
    private void refresh() {
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(UiTheme.SURFACE_WHITE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE)
        );

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
