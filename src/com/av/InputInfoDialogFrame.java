package com.av;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by av on 10.02.16.
 */
public class InputInfoDialogFrame extends JDialog {

    private String resultDialog;

    public String getString(){
        return resultDialog;
    }

    public InputInfoDialogFrame(String showText){
        //super(component);

        setSize(500, 200);
        setLocationRelativeTo(null);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        setUndecorated(true);

        setLayout(new BorderLayout());
        JLabel label = new JLabel(showText);
        label.setFont(new Font(null, Font.BOLD, 19));
        label.setPreferredSize(new Dimension(500, 100));

        final JTextField textField = new JTextField();
        textField.setFont(Program.globalFont);
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        textField.setHorizontalAlignment(JTextField.CENTER);

        JPanel ioPanel = new JPanel(new FlowLayout());
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        ioPanel.setPreferredSize(new Dimension(500, 100));
        ioPanel.add(label);
        ioPanel.add(textField);


        JPanel butPanel = new JPanel(new FlowLayout());

        FlatButton okButton = new FlatButton("OkBut.png");
        FlatButton cancelButton = new FlatButton("NoBut.png");

        // OK button
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultDialog = textField.getText();
                InputInfoDialogFrame.this.setVisible(false);
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    resultDialog = textField.getText();
                    InputInfoDialogFrame.this.setVisible(false);
                }
                else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    resultDialog = "";
                    InputInfoDialogFrame.this.setVisible(false);
                }
            }
        });

        // Cancel button
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultDialog = "";
                InputInfoDialogFrame.this.setVisible(false);
            }
        });


        butPanel.add(okButton);
        butPanel.add(cancelButton);

        add(ioPanel, BorderLayout.CENTER);
        add(butPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
