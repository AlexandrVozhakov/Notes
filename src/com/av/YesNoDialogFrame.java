package com.av;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by av on 11.02.16.
 */
public class YesNoDialogFrame extends JDialog {

    private boolean resultDialog;

    public boolean getResult(){
        return resultDialog;
    }

    public YesNoDialogFrame(String showText, Component component){
        //super((Window) component);
        this.setLocationRelativeTo(component);
        setSize(500, 200);
        //setLocationRelativeTo(null);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setUndecorated(true);

        setLayout(new BorderLayout());
        JLabel label = new JLabel(showText);
        label.setFont(new Font(null, Font.BOLD, 19));
        label.setPreferredSize(new Dimension(500, 100));

        JPanel ioPanel = new JPanel(new FlowLayout());
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        ioPanel.setPreferredSize(new Dimension(500, 100));
        ioPanel.add(label);

        JPanel butPanel = new JPanel(new FlowLayout());

        FlatButton okButton = new FlatButton("OkBut.png");
        FlatButton cancelButton = new FlatButton("NoBut.png");

        // OK button
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultDialog = true;
                YesNoDialogFrame.this.setVisible(false);
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    resultDialog = true;
                    YesNoDialogFrame.this.setVisible(false);
                }
                else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    resultDialog = false;
                    YesNoDialogFrame.this.setVisible(false);
                }
            }
        });

        // Cancel button
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultDialog = false;
                YesNoDialogFrame.this.setVisible(false);
            }
        });


        butPanel.add(okButton);
        butPanel.add(cancelButton);

        add(ioPanel, BorderLayout.CENTER);
        add(butPanel, BorderLayout.SOUTH);

        AWTUtilities.setWindowOpacity(this, 0.7f);
        //AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0, 0, 490, 190, 10, 10));

        setVisible(true);

    }

}
