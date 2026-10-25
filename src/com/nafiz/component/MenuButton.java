/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nafiz.component;

import com.nafiz.swing.UiTheme;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Nafiz_365
 */
public class MenuButton extends JButton {

    public Icon getIconSimple() {
        return iconSimple;
    }

    public void setIconSimple(Icon iconSimple) {
        this.iconSimple = iconSimple;
    }

    public Icon getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(Icon iconSelected) {
        this.iconSelected = iconSelected;
    }

    private Icon iconSimple;
    private Icon iconSelected;

    public MenuButton() {
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @Override
    public void setSelected(boolean bln) {
        super.setSelected(bln);
        if (bln) {
            setIcon(iconSelected);
        } else {
            setIcon(iconSimple);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        if (isSelected()) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(UiTheme.PRIMARY);
            g2.fillRoundRect(10, getHeight() - 5, getWidth() - 20, 3, 3, 3);
            g2.dispose();
        }
    }
}