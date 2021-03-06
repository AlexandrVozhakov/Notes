package com.av;

import javafx.scene.*;
import javafx.scene.Cursor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by av on 02.02.16.
 */
public class FlatButton extends JLabel {

    FlatButton(String iconName) {

        ImageIcon icon = loadIcon(iconName);
        this.setIcon(icon);
        this.setSize(icon.getIconWidth(), icon.getIconHeight());
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }
    public ImageIcon loadIcon(String iconName){

        Image image = null;
        try {
            image = ImageIO.read(new File(Program.icons + iconName));
        } catch (IOException e) {

            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);

        return icon;
    }
}
