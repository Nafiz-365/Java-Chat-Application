/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.component;

import com.nafiz.swing.ScrollBar;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.Icon;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Nafiz_365
 */
public class Chat_Body extends javax.swing.JPanel {

    /**
     * Creates new form Chat_Body
     */
    public Chat_Body() {
        initComponents();
        init();

//      addItemLeft("does not word warp it at all showing all the text in one line only instead. It would be interesting to support word warp on jTextPane1 resize too...", "Nafiz");
//        addItemRight("Hello \ntewffedgg\nygeygd");
//        addItemLeft("does not word warp it at all showing all the text in one line only instead. It would be interesting to support word warp on jTextPane1 resize too...", "Kalam", new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/dog.jpg")), new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/pic.jpg")));
        //      addItemRight("does not word warp it at all showing all the text in one line only instead. It would be interesting to support word warp on jTextPane1 resize too...", new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/cat.png")), new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/pic.jpg")));
//        addDate("05/06/2021");
//        String img[] = {"LRMj,K-:?G9G_JIon}WqD~ITRPs,", "LOI}-B004pI;~qROIoW=4:jYxtxu"};
//        addItemLeft("Hello \ntewffedgg\nygeygd", "Oli", img);
        addItemRight("does not word warp it at all showing all the text in one line only instead. It would be interesting to support word warp on jTextPane1 resize too...");
//        addItemLeft("Hello \ntewffedgg\nygeygd", "Proshanto", new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/dog.jpg")), new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/dog.jpg")));
//        addItemRight("Hello \ntewffedgg\nygeygd", new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/pic.jpg")));
//        addDate("Today");
//        addItemLeft("Hello \ntewffedgg\nygeygd", "Rayhan");
//        addItemLeft("", "Talha", new ImageIcon(getClass().getResource("/com/nafiz/icon/testing/pic.jpg")));
//        addItemFile("my file", "Proshanto", "my doc.pdf", "1 MB");
//        addItemFileRight("","myfile.rar", "15 MB");
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(String text, String user, Icon... image) {
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100:: 50%");
        //  ::50% set max  with 50%
        body.repaint();
        body.revalidate();
    }

    public void addItemLeft(String text, String user, String[] image) {
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100:: 50%");
        //  ::50% set max  with 50%
        body.repaint();
        body.revalidate();
    }

    public void addItemFile(String text, String user, String fileName, String fileSize) {
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100:: 50%");
        //  ::50% set max  with 50%
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(String text, Icon... image) {
        Chat_Right item = new Chat_Right();
        item.setText(text);
        item.setImage(image);
        body.add(item, "wrap, al right, w 100:: 50%");
        //  ::50% set max  with 50%
        body.repaint();
        body.revalidate();
        item.setTime();
        scrollToBottom();

    }

    public void addItemFileRight(String text, String fileName, String fileSize) {
        Chat_Right item = new Chat_Right();
        item.setText(text);
        item.setFile(fileName, fileSize);
        body.add(item, "wrap, al right, w 100:: 50%");
        //  ::50% set max  with 50%
        body.repaint();
        body.revalidate();

    }

    public void addDate(String date) {
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

        body.setBackground(new java.awt.Color(255, 255, 255));

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
