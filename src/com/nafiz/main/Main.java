/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.nafiz.main;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.nafiz.auth.Session;
import com.nafiz.event.EventImageView;
import com.nafiz.event.EventMain;
import com.nafiz.event.PublicEvent;
import com.nafiz.swing.ComponentResizer;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.JFrame;

/**
 *
 * @author Nafiz_365
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        init();
    }

    private void init() {
        initWindow();
        ComponentResizer com = new ComponentResizer();
        com.registerComponent(this);
        Dimension minimumSize = new Dimension(500, 500);
        setMinimumSize(minimumSize);
        com.setMinimumSize(minimumSize);
        com.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        com.setSnapSize(new Dimension(10, 10));
        login.setVisible(true);
        loading1.setVisible(false);
        view_Image.setVisible(false);
        home.setVisible(false);
        initEvent();
    }

    private void initWindow() {
        setTitle("Fury Chat");
        try {
            java.net.URL iconUrl = getClass().getResource("/com/nafiz/icon/icon.png");
            if (iconUrl != null) {
                java.awt.Image baseIcon = new javax.swing.ImageIcon(iconUrl).getImage();
                java.util.List<java.awt.Image> icons = new java.util.ArrayList<>();
                int[] sizes = {16, 20, 24, 32, 40, 48, 64, 128, 256};
                for (int s : sizes) {
                    java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(s, s, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                    java.awt.Graphics2D g2 = bi.createGraphics();
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.drawImage(baseIcon, 0, 0, s, s, null);
                    g2.dispose();
                    icons.add(bi);
                }
                icons.add(baseIcon);
                setIconImages(icons);
                setIconImage(baseIcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEvent() {
        PublicEvent.getInstance().addEventMain(new EventMain() {
            @Override
            public void showLoading(boolean show) {
                loading1.setVisible(show);
            }

            @Override
            public void initChat() {
                home.connectChat();
                home.refreshSession(Session.getUsername());
                home.setVisible(true);
                login.setVisible(false);
            }

            @Override
            public void logout() {
                home.disconnectChat();
                login.resetForms();
                loading1.setVisible(false);
                Session.clear();
                home.setVisible(false);
                login.setVisible(true);
            }
        });
        PublicEvent.getInstance().addEventImageView(new EventImageView() {
            @Override
            public void viewImage(Icon image) {
                view_Image.viewImage(image);
            }

            @Override
            public void saveImage(Icon image) {
                if (image == null) {
                    return;
                }
                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                chooser.setDialogTitle("Save Image");
                chooser.setSelectedFile(new java.io.File("chat-image.png"));
                chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Image (*.png)", "png"));
                int userSelection = chooser.showSaveDialog(Main.this);
                if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
                    java.io.File fileToSave = chooser.getSelectedFile();
                    if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                        fileToSave = new java.io.File(fileToSave.getAbsolutePath() + ".png");
                    }
                    try {
                        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(
                                image.getIconWidth(), image.getIconHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
                        java.awt.Graphics2D g2 = bi.createGraphics();
                        image.paintIcon(null, g2, 0, 0);
                        g2.dispose();
                        javax.imageio.ImageIO.write(bi, "png", fileToSave);
                        javax.swing.JOptionPane.showMessageDialog(Main.this,
                                "Image saved successfully.", "Save Image", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        javax.swing.JOptionPane.showMessageDialog(Main.this,
                                "Failed to save image: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        border = new javax.swing.JPanel();
        background = new javax.swing.JPanel();
        title = new javax.swing.JPanel();
        cmdMinimize = new javax.swing.JButton();
        cmdMaximize = new javax.swing.JButton();
        cmdClose = new javax.swing.JButton();
        body = new javax.swing.JLayeredPane();
        loading1 = new com.nafiz.form.Loading();
        login = new com.nafiz.form.Login();
        view_Image = new com.nafiz.form.View_Image();
        home = new com.nafiz.form.Home();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fury Chat");
        setUndecorated(true);

        border.setBackground(new java.awt.Color(15, 23, 42));

        background.setBackground(new java.awt.Color(248, 250, 252));
        title.setBackground(new java.awt.Color(15, 23, 42));

        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 16, 0, 8));
        title.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                titleMouseDragged(evt);
            }
        });
        title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                titleMousePressed(evt);
            }
        });

        styleWindowButton(cmdMinimize, "−", new java.awt.Color(51, 65, 85), new java.awt.Color(71, 85, 105));
        cmdMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMinimizeActionPerformed(evt);
            }
        });

        styleWindowButton(cmdMaximize, "□", new java.awt.Color(51, 65, 85), new java.awt.Color(71, 85, 105));
        cmdMaximize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMaximizeActionPerformed(evt);
            }
        });

        styleWindowButton(cmdClose, "×", new java.awt.Color(51, 65, 85), new java.awt.Color(220, 38, 38));
        cmdClose.setForeground(new java.awt.Color(248, 113, 113));
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        javax.swing.JLabel appTitle = new javax.swing.JLabel("  Fury Chat");
        appTitle.setForeground(new java.awt.Color(226, 232, 240));
        appTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        try {
            java.net.URL titleIconUrl = getClass().getResource("/com/nafiz/icon/icon.png");
            if (titleIconUrl != null) {
                java.awt.Image img = new javax.swing.ImageIcon(titleIconUrl).getImage();
                appTitle.setIcon(new javax.swing.ImageIcon(img.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH)));
            }
        } catch (Exception ignored) {
        }
        appTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                titleMousePressed(evt);
            }
        });
        appTitle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                titleMouseDragged(evt);
            }
        });

        javax.swing.JPanel windowButtons = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 4, 4));
        windowButtons.setOpaque(false);
        windowButtons.add(cmdMinimize);
        windowButtons.add(cmdMaximize);
        windowButtons.add(cmdClose);
        title.setLayout(new java.awt.BorderLayout());
        title.add(appTitle, java.awt.BorderLayout.WEST);
        title.add(windowButtons, java.awt.BorderLayout.EAST);

        body.setLayout(new java.awt.CardLayout());
        body.add(loading1, "card5");
        body.add(login, "card4");
        body.setLayer(view_Image, javax.swing.JLayeredPane.POPUP_LAYER);
        body.add(view_Image, "card3");
        body.add(home, "card2");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout borderLayout = new javax.swing.GroupLayout(border);
        border.setLayout(borderLayout);
        borderLayout.setHorizontalGroup(
            borderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        borderLayout.setVerticalGroup(
            borderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borderLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(border, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(border, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private int pX;
    private int pY;

    private void styleWindowButton(javax.swing.JButton button, String text,
            java.awt.Color normalColor, java.awt.Color hoverColor) {
        button.setText(text);
        button.setFont(new java.awt.Font("Segoe UI Symbol", java.awt.Font.PLAIN, text.equals("×") ? 18 : 15));
        button.setForeground(new java.awt.Color(226, 232, 240));
        button.setBackground(normalColor);
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setRolloverEnabled(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setPreferredSize(new java.awt.Dimension(42, 30));
        button.setMinimumSize(new java.awt.Dimension(42, 30));
        button.setMaximumSize(new java.awt.Dimension(42, 30));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                button.setBackground(normalColor);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent event) {
                button.setBackground(hoverColor.darker());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent event) {
                button.setBackground(button.contains(event.getPoint()) ? hoverColor : normalColor);
            }
        });
    }

    private void titleMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleMouseDragged
        if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
            return;
        }
        this.setLocation(this.getLocation().x + evt.getX() - pX, this.getLocation().y + evt.getY() - pY);
    }//GEN-LAST:event_titleMouseDragged

    private void titleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleMousePressed
        if (evt.getClickCount() == 2 && javax.swing.SwingUtilities.isLeftMouseButton(evt)) {
            cmdMaximizeActionPerformed(null);
            return;
        }
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_titleMousePressed

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        login.cancelAuthentication();
        home.disconnectChat();
        Session.clear();
        System.exit(0);
    }//GEN-LAST:event_cmdCloseActionPerformed

    private void cmdMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMinimizeActionPerformed
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_cmdMinimizeActionPerformed

    private void cmdMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMaximizeActionPerformed
        if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
            setExtendedState(JFrame.NORMAL);
            cmdMaximize.setText("□");
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            cmdMaximize.setText("❐");
        }
    }//GEN-LAST:event_cmdMaximizeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlatArcIJTheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JLayeredPane body;
    private javax.swing.JPanel border;
    private javax.swing.JButton cmdClose;
    private javax.swing.JButton cmdMaximize;
    private javax.swing.JButton cmdMinimize;
    private com.nafiz.form.Home home;
    private com.nafiz.form.Loading loading1;
    private com.nafiz.form.Login login;
    private javax.swing.JPanel title;
    private com.nafiz.form.View_Image view_Image;
    // End of variables declaration//GEN-END:variables
}
