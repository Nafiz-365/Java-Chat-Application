package com.nafiz.form;

import com.nafiz.auth.AuthService;
import com.nafiz.auth.Session;
import com.nafiz.event.EventLogin;
import com.nafiz.event.PublicEvent;
import com.nafiz.swing.RoundedPanel;
import com.nafiz.swing.UiTheme;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login extends javax.swing.JPanel {

    private final CardLayout cards = new CardLayout();
    private final JPanel forms = new JPanel(cards);
    private final AuthService authService = new AuthService();
    private P_Login loginForm;
    private P_Register registerForm;
    private volatile boolean authenticating;
    private volatile Thread authenticationThread;
    private volatile long authenticationGeneration;

    public Login() {
        initComponents();
        init();
    }

    private void init() {
        PublicEvent.getInstance().addEventLogin(new EventLogin() {
            @Override
            public void login(String username, char[] password) {
                if (authenticating) {
                    return;
                }
                authenticating = true;
                long generation = ++authenticationGeneration;
                authenticationThread = new Thread(() -> {
                    javax.swing.SwingUtilities.invokeLater(
                            () -> {
                                if (generation == authenticationGeneration) {
                                    PublicEvent.getInstance().getEventMain().showLoading(true);
                                }
                            });
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            if (generation == authenticationGeneration) {
                                authenticating = false;
                                PublicEvent.getInstance().getEventMain().showLoading(false);
                            }
                        });
                        return;
                    }
                    AuthService.AuthResult result;
                    try {
                        result = authService.authenticate(username, password);
                    } catch (IllegalArgumentException ex) {
                        result = AuthService.AuthResult.failure(ex.getMessage());
                    } finally {
                        java.util.Arrays.fill(password, '\0');
                    }
                    AuthService.AuthResult finalResult = result;
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (generation != authenticationGeneration) {
                            return;
                        }
                        authenticating = false;
                        authenticationThread = null;
                        PublicEvent.getInstance().getEventMain().showLoading(false);
                        if (finalResult.successful()) {
                            Session.start(username);
                            PublicEvent.getInstance().getEventMain().initChat();
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(Login.this,
                                    finalResult.message(), "Sign in failed",
                                    javax.swing.JOptionPane.WARNING_MESSAGE);
                        }
                    });
                }, "chat-authentication");
                authenticationThread.start();
            }

            @Override
            public void register(String username, char[] password) {
                new Thread(() -> {
                    AuthService.AuthResult result;
                    try {
                        result = authService.register(username, password);
                    } catch (IllegalArgumentException ex) {
                        result = AuthService.AuthResult.failure(ex.getMessage());
                    } finally {
                        java.util.Arrays.fill(password, '\0');
                    }
                    AuthService.AuthResult finalResult = result;
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        javax.swing.JOptionPane.showMessageDialog(Login.this,
                                finalResult.message(), "Create account",
                                finalResult.successful()
                                        ? javax.swing.JOptionPane.INFORMATION_MESSAGE
                                        : javax.swing.JOptionPane.WARNING_MESSAGE);
                        if (finalResult.successful()) {
                            registerForm.resetForm();
                            cards.show(forms, "login");
                        }
                    });
                }).start();
            }

            @Override
            public void goRegister() {
                cards.show(forms, "register");
            }

            @Override
            public void goLogin() {
                cards.show(forms, "login");
            }
        });
        loginForm = new P_Login();
        registerForm = new P_Register();
        forms.add(loginForm, "login");
        forms.add(registerForm, "register");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel page = new BackgroundPanel();
        page.setLayout(new GridBagLayout());

        RoundedPanel card = new RoundedPanel(new BorderLayout(0, 10), 28);
        card.setBackground(new Color(255, 255, 255, 246));
        card.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32));
        card.setPreferredSize(new Dimension(400, 490));

        JPanel heading = new JPanel(new BorderLayout(0, 4));
        heading.setOpaque(false);
        JLabel brand = new JLabel("FURY CHAT");
        brand.setForeground(UiTheme.PRIMARY);
        brand.setFont(UiTheme.font(Font.BOLD, 12));
        JLabel title = new JLabel("Messages that stay with you");
        title.setForeground(UiTheme.TEXT);
        title.setFont(UiTheme.font(Font.BOLD, 22));
        JLabel subtitle = new JLabel("Sign in or create a local account to begin.");
        subtitle.setForeground(UiTheme.MUTED);
        subtitle.setFont(UiTheme.font(Font.PLAIN, 13));
        heading.add(brand, BorderLayout.NORTH);
        heading.add(title, BorderLayout.CENTER);
        heading.add(subtitle, BorderLayout.SOUTH);
        card.add(heading, BorderLayout.NORTH);

        forms.setOpaque(false);
        card.add(forms, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(24, 24, 24, 24);
        page.add(card, constraints);
        page.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent event) {
                int width = Math.min(400, Math.max(320, page.getWidth() - 48));
                int height = Math.min(500, Math.max(440, page.getHeight() - 48));
                card.setPreferredSize(new Dimension(width, height));
                page.revalidate();
            }
        });
        add(page, BorderLayout.CENTER);
    }

    public void cancelAuthentication() {
        authenticationGeneration++;
        authenticating = false;
        Thread thread = authenticationThread;
        authenticationThread = null;
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void resetForms() {
        cancelAuthentication();
        loginForm.resetForm();
        registerForm.resetForm();
        cards.show(forms, "login");
    }

    private static final class BackgroundPanel extends JPanel {
        private final Image background;

        private BackgroundPanel() {
            setOpaque(true);
            java.net.URL resource = Login.class.getResource("/com/nafiz/icon/auth-background.png");
            if (resource == null) {
                resource = Login.class.getResource("/com/nafiz/icon/chat-login-background.png");
            }
            if (resource != null) {
                background = new ImageIcon(resource).getImage();
            } else {
                background = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            int width = getWidth();
            int height = getHeight();
            double scale = Math.max((double) width / background.getWidth(null),
                    (double) height / background.getHeight(null));
            int imageWidth = (int) Math.ceil(background.getWidth(null) * scale);
            int imageHeight = (int) Math.ceil(background.getHeight(null) * scale);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            g2.drawImage(background, x, y, imageWidth, imageHeight, this);
            g2.setColor(new Color(8, 15, 28, 88));
            g2.fillRect(0, 0, width, height);
            g2.dispose();
        }
    }
}
