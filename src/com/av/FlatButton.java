package com.av;

import javax.swing.*;

/**
 * Created by av on 02.02.16.
 */
public class FlatButton extends JLabel {

    FlatButton(String iconName) {

        ImageIcon icon = Service.loadIcon(iconName);
        this.setIcon(icon);
        this.setSize(icon.getIconWidth(), icon.getIconHeight());
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }
}
