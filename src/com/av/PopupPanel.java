package com.av;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by av on 26.02.16.
 */
public class PopupPanel extends JDialog {

    protected String resultDialog;

    public PopupPanel(String showText, Point location){

        setSize(200, 60);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setLocation(location);
        setUndecorated(true);

        resultDialog = "";

        JLabel rename = new JLabel("rename");
        rename.setFont(new Font(null, Font.BOLD, 19));
        rename.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PopupPanel.this.resultDialog = "rename";
                PopupPanel.this.setVisible(false);
            }
        });
        rename.setHorizontalAlignment(JLabel.CENTER);

        JLabel delete = new JLabel("delete");
        delete.setFont(new Font(null, Font.BOLD, 19));
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PopupPanel.this.resultDialog = "delete";
                PopupPanel.this.setVisible(false);
            }
        });
        delete.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(rename, BorderLayout.NORTH);
        panel.add(delete, BorderLayout.SOUTH);

        this.add(panel);
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                PopupPanel.this.setVisible(false);
            }
        });
        //ioPanel.setPreferredSize(new Dimension(dimension.width, dimension.height / 2));


        AWTUtilities.setWindowOpacity(this, 0.9f);
        //AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0, 0, 490, 190, 10, 10));
        setVisible(true);

    }

    public String getResult(){
        return resultDialog;
    }
}
