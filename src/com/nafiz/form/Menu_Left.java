/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nafiz.form;

import com.nafiz.component.Item_People;
import com.nafiz.swing.HintTextField;
import com.nafiz.swing.ScrollBar;
import com.nafiz.swing.UiTheme;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Nafiz_365
 */
public class Menu_Left extends javax.swing.JPanel {

    public Menu_Left() {
        initComponents();
        init();
    }

    private void init() {
        removeAll();
        setLayout(new MigLayout("fill, insets 0, wrap 1", "0[fill]0", "0[]4[]8[]8[fill, grow]0"));
        sp.setVerticalScrollBar(new ScrollBar());
        menuList.setLayout(new MigLayout("fillx, wrap 1", "0[grow, fill]0", "2[]2"));
        add(menu, "growx, h 52!, wrap");
        // Section heading
        javax.swing.JPanel headingBar = new javax.swing.JPanel(new java.awt.BorderLayout());
        headingBar.setOpaque(false);
        headingBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 14, 2, 14));
        JLabel heading = new JLabel("Conversations");
        heading.setFont(UiTheme.font(Font.BOLD, 13));
        heading.setForeground(UiTheme.TEXT);
        headingBar.add(heading, java.awt.BorderLayout.WEST);
        add(headingBar, "growx, wrap");
        // Search field with icon
        javax.swing.JPanel searchRow = new javax.swing.JPanel(new java.awt.BorderLayout(6, 0));
        searchRow.setOpaque(false);
        searchRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        javax.swing.JLabel searchIcon = new javax.swing.JLabel();
        searchIcon.setIcon(com.nafiz.swing.ModernIcons.search(UiTheme.MUTED, 16));
        searchIcon.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 4));
        HintTextField search = new HintTextField("Search conversations");
        UiTheme.styleField(search);
        search.setToolTipText("Search conversations");
        javax.swing.JPanel searchWrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
        UiTheme.styleField(search);
        searchWrapper.setBorder(search.getBorder());
        search.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 10));
        searchWrapper.setBackground(UiTheme.SURFACE);
        searchWrapper.add(searchIcon, java.awt.BorderLayout.WEST);
        searchWrapper.add(search, java.awt.BorderLayout.CENTER);
        searchWrapper.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(UiTheme.BORDER, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        searchRow.add(searchWrapper, java.awt.BorderLayout.CENTER);
        add(searchRow, "growx, h 40!, wrap");
        add(sp, "grow, push, gapx 4 4");
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                filter(search.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                filter(search.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                filter(search.getText());
            }
        });
        showMessage();
    }

    private void showMessage() {
        showItems("Start a new conversation", "Saved messages");
    }

    private void showItems(String... names) {
        menuList.removeAll();
        for (String name : names) {
            menuList.add(new Item_People(name), "wrap, growx");
        }
        refreshMenuList();
    }

    private void showGroup() {
        showItems("Create your first group");
    }

    private void showBox() {
        showItems("No archived chats yet");
    }

    private void filter(String query) {
        String normalized = query == null ? "" : query.trim().toLowerCase();
        if (normalized.isEmpty()) {
            showMessage();
        } else if ("saved".contains(normalized)) {
            showItems("Saved messages");
        } else if ("new".contains(normalized) || "conversation".contains(normalized)) {
            showItems("Start a new conversation");
        } else {
            showItems("No conversations found");
        }
    }

    private void refreshMenuList() {
        menuList.repaint();
        menuList.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JLayeredPane();
        menuMessage = new com.nafiz.component.MenuButton();
        menuGroup = new com.nafiz.component.MenuButton();
        menuBox = new com.nafiz.component.MenuButton();
        sp = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();

        setBackground(UiTheme.SIDEBAR);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UiTheme.BORDER));

        menu.setBackground(new java.awt.Color(226, 232, 240));
        menu.setOpaque(true);
        menu.setLayout(new java.awt.GridLayout(1, 3));

        menuMessage.setIconSelected(com.nafiz.swing.ModernIcons.chat(UiTheme.PRIMARY, true, 22));
        menuMessage.setIconSimple(com.nafiz.swing.ModernIcons.chat(UiTheme.MUTED, false, 22));
        menuMessage.setIcon(menuMessage.getIconSelected());
        menuMessage.setToolTipText("Messages");
        menuMessage.setSelected(true);
        menuMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMessageActionPerformed(evt);
            }
        });
        menu.add(menuMessage);

        menuGroup.setIconSelected(com.nafiz.swing.ModernIcons.group(UiTheme.PRIMARY, true, 22));
        menuGroup.setIconSimple(com.nafiz.swing.ModernIcons.group(UiTheme.MUTED, false, 22));
        menuGroup.setIcon(menuGroup.getIconSimple());
        menuGroup.setToolTipText("Groups");
        menuGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGroupActionPerformed(evt);
            }
        });
        menu.add(menuGroup);

        menuBox.setIconSelected(com.nafiz.swing.ModernIcons.archive(UiTheme.PRIMARY, true, 22));
        menuBox.setIconSimple(com.nafiz.swing.ModernIcons.archive(UiTheme.MUTED, false, 22));
        menuBox.setIcon(menuBox.getIconSimple());
        menuBox.setToolTipText("Archive");
        menuBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBoxActionPerformed(evt);
            }
        });
        menu.add(menuBox);

        sp.setBackground(UiTheme.SIDEBAR);
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(UiTheme.SIDEBAR);
        menuList.setOpaque(true);

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
                menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
                menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 576, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sp)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menuMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMessageActionPerformed
        if (!menuMessage.isSelected()) {
            menuMessage.setSelected(true);
            menuGroup.setSelected(false);
            menuBox.setSelected(false);
            showMessage();
        }
    }//GEN-LAST:event_menuMessageActionPerformed

    private void menuGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGroupActionPerformed
        if (!menuGroup.isSelected()) {
            menuMessage.setSelected(false);
            menuGroup.setSelected(true);
            menuBox.setSelected(false);
            showGroup();
        }
    }//GEN-LAST:event_menuGroupActionPerformed

    private void menuBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBoxActionPerformed
        if (!menuBox.isSelected()) {
            menuMessage.setSelected(false);
            menuGroup.setSelected(false);
            menuBox.setSelected(true);
            showBox();
        }
    }//GEN-LAST:event_menuBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane menu;
    private com.nafiz.component.MenuButton menuBox;
    private com.nafiz.component.MenuButton menuGroup;
    private javax.swing.JLayeredPane menuList;
    private com.nafiz.component.MenuButton menuMessage;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}