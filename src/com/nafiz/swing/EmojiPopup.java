package com.nafiz.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class EmojiPopup extends JPopupMenu {

    private static final String[] EMOJIS = {
        "😀", "😂", "🤣", "😍", "🥰", "😎", "😊", "🥳",
        "🤔", "🤫", "😭", "🥺", "😡", "😱", "😴", "😇",
        "👍", "👎", "👏", "🙌", "🤝", "✌️", "🙏", "💪",
        "❤️", "💖", "🔥", "✨", "🎉", "💯", "🚀", "💡"
    };

    public EmojiPopup(Consumer<String> onSelectEmoji) {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)));

        JPanel grid = new JPanel(new GridLayout(4, 8, 4, 4));
        grid.setOpaque(false);

        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 18);

        for (String emoji : EMOJIS) {
            JButton btn = new JButton(emoji);
            btn.setFont(emojiFont);
            btn.setPreferredSize(new Dimension(32, 32));
            btn.setContentAreaFilled(false);
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    btn.setContentAreaFilled(true);
                    btn.setBackground(UiTheme.HOVER);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    btn.setContentAreaFilled(false);
                }
            });

            btn.addActionListener(e -> {
                if (onSelectEmoji != null) {
                    onSelectEmoji.accept(emoji);
                }
                setVisible(false);
            });

            grid.add(btn);
        }

        add(grid);
    }
}
