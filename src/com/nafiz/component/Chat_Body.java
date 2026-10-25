/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.component;

import com.nafiz.chat.ChatMessage;
import com.nafiz.swing.ScrollBar;
import com.nafiz.swing.UiTheme;
import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Nafiz_365
 */
public class Chat_Body extends javax.swing.JPanel {
    private JLabel emptyState;

    /**
     * Creates new form Chat_Body
     */
    public Chat_Body() {
        initComponents();
        init();

    }

    private void init() {
        body.setLayout(new MigLayout("fillx, insets 18 22 18 22", "", "8[]8"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(UiTheme.SURFACE);
        showEmptyState();
    }

    private void showEmptyState() {
        if (emptyState == null) {
            emptyState = new JLabel("<html><div style='text-align:center;width:280px'>"
                    + "<div style='font-size:16px;color:#0F172A;font-weight:700;padding-bottom:8px'>Start this conversation</div>"
                    + "<div style='color:#64748B;font-size:12px'>Your messages stay on this computer. Write something below to begin.</div>"
                    + "</div></html>");
            emptyState.setForeground(UiTheme.MUTED);
            emptyState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            body.add(emptyState, "wrap, growx, pushy, align center, gapy 90");
        }
    }

    public void clearChat() {
        body.removeAll();
        emptyState = null;
        showEmptyState();
        body.repaint();
        body.revalidate();
    }

    private void removeEmptyState() {
        if (emptyState != null) {
            body.remove(emptyState);
            emptyState = null;
        }
    }

    public void addItemLeft(ChatMessage message) {
        String content = message.content();
        if (content != null && content.startsWith("[IMG:") && content.endsWith("]")) {
            String path = content.substring(5, content.length() - 1);
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(path);
                addItemLeft("", message.sender(), content, icon);
                return;
            } else {
                addItemFile("", message.sender(), content, file.getName(), "Image unavailable");
                return;
            }
        } else if (content != null && content.startsWith("[FILE:") && content.endsWith("]")) {
            String meta = content.substring(6, content.length() - 1);
            String[] parts = meta.split("\\|", 2);
            String fileName = parts[0];
            String fileSize = parts.length > 1 ? parts[1] : "";
            addItemFile("", message.sender(), content, fileName, fileSize);
            return;
        }

        removeEmptyState();
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(message.content());
        item.setRawContent(message.content());
        item.setTime(message.createdAt());
        item.setUserProfile(message.sender());
        body.add(item, "wrap, w 100:: 72%");
        body.repaint();
        body.revalidate();
        scrollToBottom();
    }

    public void addItemLeft(String text, String user, Icon... image) {
        addItemLeft(text, user, text, image);
    }

    public void addItemLeft(String text, String user, String rawContent, Icon... image) {
        removeEmptyState();
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setRawContent(rawContent);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100:: 72%");
        body.repaint();
        body.revalidate();
    }

    public void addItemLeft(String text, String user, String[] image) {
        removeEmptyState();
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100:: 72%");
        body.repaint();
        body.revalidate();
    }

    public void addItemFile(String text, String user, String fileName, String fileSize) {
        addItemFile(text, user, "[FILE:" + fileName + "|" + fileSize + "]", fileName, fileSize);
    }

    public void addItemFile(String text, String user, String rawContent, String fileName, String fileSize) {
        removeEmptyState();
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setRawContent(rawContent);
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100:: 72%");
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(String text, Icon... image) {
        addItemRight(text, text, image);
    }

    public void addItemRight(String text, String rawContent, Icon... image) {
        removeEmptyState();
        Chat_Right item = new Chat_Right();
        item.setText(text);
        item.setRawContent(rawContent);
        item.setImage(image);
        body.add(item, "wrap, al right, w 100:: 72%");
        body.repaint();
        body.revalidate();
        item.setTime();
        scrollToBottom();
    }

    public void addItemRight(ChatMessage message) {
        String content = message.content();
        if (content != null && content.startsWith("[IMG:") && content.endsWith("]")) {
            String path = content.substring(5, content.length() - 1);
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(path);
                addItemRight("", content, icon);
                return;
            } else {
                addItemFileRight("", content, file.getName(), "Image unavailable");
                return;
            }
        } else if (content != null && content.startsWith("[FILE:") && content.endsWith("]")) {
            String meta = content.substring(6, content.length() - 1);
            String[] parts = meta.split("\\|", 2);
            String fileName = parts[0];
            String fileSize = parts.length > 1 ? parts[1] : "";
            addItemFileRight("", content, fileName, fileSize);
            return;
        }

        removeEmptyState();
        Chat_Right item = new Chat_Right();
        item.setText(message.content());
        item.setRawContent(message.content());
        item.setTime(message.createdAt());
        body.add(item, "wrap, al right, w 100:: 72%");
        body.repaint();
        body.revalidate();
        scrollToBottom();
    }

    public void addItemFileRight(String text, String fileName, String fileSize) {
        addItemFileRight(text, "[FILE:" + fileName + "|" + fileSize + "]", fileName, fileSize);
    }

    public void addItemFileRight(String text, String rawContent, String fileName, String fileSize) {
        removeEmptyState();
        Chat_Right item = new Chat_Right();
        item.setText(text);
        item.setRawContent(rawContent);
        item.setFile(fileName, fileSize);
        body.add(item, "wrap, al right, w 100:: 72%");
        body.repaint();
        body.revalidate();
    }

    public void addDate(String date) {
        removeEmptyState();
        Chat_Date item = new Chat_Date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(UiTheme.SURFACE);

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sp)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
