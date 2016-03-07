package com.av;

import javax.swing.*;
import java.awt.*;

/**
 * Created by av on 22.02.16.
 */
public class TabsBarListRenderer implements ListCellRenderer {

    GlobalDimension dimension = GlobalDimension.getInstance();

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {


        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        if (isSelected) {
            renderer.setBackground(Color.lightGray);
        }

        renderer.setBorder(null);
        renderer.setFont(new Font(null, Font.BOLD, 19));
        renderer.setForeground(Color.DARK_GRAY);

        FontMetrics fontMetrics = renderer.getFontMetrics(renderer.getFont());
        int text_width = fontMetrics.stringWidth(renderer.getText());
        renderer.setPreferredSize(new Dimension(text_width, dimension.getBorder() - 5));

        renderer.setHorizontalAlignment(JLabel.CENTER);
        return renderer;
    }
}