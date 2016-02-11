package com.av;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by av on 20.01.16.
 */
public class Program {

    public static final String icons = "/home/av/Workspace/IDEA/Note/src/com/av/icons/";
    public static Font globalFont = new Font(null, Font.PLAIN, 16);

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

/*                javax.swing.plaf.metal.MetalLookAndFeel
                javax.swing.plaf.nimbus.NimbusLookAndFeel
                com.sun.java.swing.plaf.motif.MotifLookAndFeel
                com.sun.java.swing.plaf.gtk.GTKLookAndFeel*/

                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    //UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }

                new View();
            }
        }) ;
    }

    public static ImageIcon loadIcon(String iconName){

        Image image = null;
        try {
            image = ImageIO.read(new File(icons + iconName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);

        return icon;
    }
}
