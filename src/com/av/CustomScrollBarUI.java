package com.av;

import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;

/**
 * Created by av on 03.02.16.
 */
class CustomScrollbarUI extends MetalScrollBarUI {

    private JButton b = new JButton() {

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }

    };

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle tb ) {

        g.setColor( Color.LIGHT_GRAY );
        g.fillRect( tb.x +10 , tb.y, tb.width + 10, tb.height );
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle tb) {
        g.setColor( Color.WHITE );
        g.fillRect(tb.x, tb.y, tb.width, tb.height);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return b;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return b;
    }
}