package com.av;

import java.awt.*;

/**
 * Created by av on 03.02.16.
 */
public class GlobalDimension {

    private Dimension frame;
    private Dimension tabsPanel;
    private Dimension rootPanel;
    private Dimension sideBarPanel;
    private Dimension textPanel;
    private Dimension listPanel;
    private Dimension textField;
    private Dimension searchPanel;
    private Dimension controlPanel;
    private int border;
    private static GlobalDimension instance = null;

    private GlobalDimension(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new Dimension((int)(screenSize.width * 0.6), (int)(screenSize.height * 0.6));
        border = (int)(frame.height * 0.07);
        tabsPanel = new Dimension(frame.width - border, border);
        rootPanel = new Dimension(frame.width, frame.height - border);
        sideBarPanel = new Dimension((int)(frame.width * 0.3), rootPanel.height);
        textPanel = new Dimension(frame.width - sideBarPanel.width, rootPanel.height);
        searchPanel = new Dimension(sideBarPanel.width, border);
        controlPanel = new Dimension(frame.width - sideBarPanel.width, border);
        listPanel = new Dimension(sideBarPanel.width, sideBarPanel.height - searchPanel.height);
        textField = new Dimension(textPanel.width, textPanel.height - controlPanel.height);
    }

    public static GlobalDimension getInstance(){
        if(instance == null)
            instance = new GlobalDimension();
        return instance;
    }

    public int getBorder(){
        return border;
    }

    public Dimension getRootPanel() {
        return new Dimension(rootPanel);
    }

    public Dimension getTextPanel() {
        return new Dimension(textPanel);
    }

    public Dimension getListPanel() {
        return new Dimension(listPanel);
    }

    public Dimension getTextField() {
        return new Dimension(textField);
    }

    public Dimension getControlPanel() {
        return new Dimension(controlPanel);
    }

    public Dimension getSearchPanel() {
        return new Dimension(searchPanel);
    }

    public Dimension getSideBarPanel() {
        return new Dimension(sideBarPanel);
    }

    public Dimension getFrame() {
        return new Dimension(frame);
    }

    public Dimension getTabsPanel() {
        return tabsPanel;
    }
}
