package com.av;

import javax.swing.*;

/**
 * Created by av on 19.02.16.
 */
public class MainFrame extends JFrame {

    public MainFrame(){

        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model);
        view.addActionListener(controller);
        model.addObserver(view);
        this.getContentPane().add(view);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                final MainFrame mf = new MainFrame();
                mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mf.pack();
                mf.setLocationRelativeTo(null);
                mf.setVisible(true);
            }
        });
    }
}
