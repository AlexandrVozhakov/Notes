package com.av;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

/**
 * Created by av on 23.01.16.
 */
public class NewListRenderer implements ListCellRenderer{

    GlobalDimension dimension = GlobalDimension.getInstance();

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        Icon icon = new MyIcon();

        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                                                                                isSelected, cellHasFocus);

/*        if (isSelected) {
            renderer.setBackground(new Color(255, 232, 82));
        }*/

        renderer.setFont(Service.globalFont);
        renderer.setForeground(Color.DARK_GRAY);
        renderer.setPreferredSize(new Dimension(800, dimension.getSearchPanel().height + 2));
        renderer.setIcon(icon);
        renderer.setHorizontalTextPosition(JLabel.LEFT);
        renderer.setVerticalTextPosition(JLabel.NORTH);
        return renderer;
    }

    class MyIcon implements Icon {

        public int getIconHeight() {
            return 20;
        }
        public int getIconWidth() {
            return 300;
        }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.GRAY);
            for (int i = 2; i < dimension.getSearchPanel().width - 2; i += 5)
                g.fillOval(i, dimension.getSearchPanel().height , 2, 2);
        }
    }
}


