package com.av;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by av on 20.01.16.
 */
public class Service {

    public static Font globalFont = new Font(null, Font.PLAIN, 16);

/*    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

*//*                javax.swing.plaf.metal.MetalLookAndFeel
                javax.swing.plaf.nimbus.NimbusLookAndFeel
                com.sun.java.swing.plaf.motif.MotifLookAndFeel
                com.sun.java.swing.plaf.gtk.GTKLookAndFeel*//*

                *//*try {
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
                }*//*
                *//*try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }*//*

                new Controller();
            }
        }) ;
    }*/

    public static ImageIcon loadIcon(String iconName){

        Image image = null;
        try {
            image = ImageIO.read(new File("icons/" + iconName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);

        return icon;
    }

    public static String date(String format){

        Date date = new Date();
        return new SimpleDateFormat(format).format(date);
    }

    public static String readFile(String path){

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str;
        StringBuffer sb = new StringBuffer();
        try {
            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
