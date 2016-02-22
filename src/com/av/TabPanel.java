package com.av;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by av on 22.02.16.
 */
public class TabPanel extends JPanel {

    private List<JLabel> tabs;
    private GlobalDimension dimension;
    private int selectedIndex = 0;
    private Font font;

    public TabPanel(){

        this.setLayout(new FlowLayout());
        tabs = new ArrayList<JLabel>();
        dimension = GlobalDimension.getInstance();
        font = new Font(null);
        JLabel add = createTab(" + ");
        tabs.add(add);
        this.add(add);
    }

    public void addTab(String name){

        JLabel label = createTab(name);
        tabs.add(tabs.size() - 1, label);
        this.add(label);
    }

    public void addTabs(List<String> tabs){
        for(String tab : tabs)
            addTab(tab);
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    private JLabel createTab(String name){

        final JLabel label = new JLabel(name);
        label.setFont(new Font(null, Font.BOLD, 19));

        FontMetrics fontMetrics = this.getFontMetrics(label.getFont());
        int text_width = fontMetrics.stringWidth(name);

        label.setPreferredSize(new Dimension(text_width + 10, dimension.getBorder()));

        label.setHorizontalAlignment(JLabel.CENTER);
        //label.setVerticalTextPosition(JLabel.TOP);
        label.setOpaque(true);
        setListener(label);

        return label;
    }

    public void addMouseListener(MouseAdapter ma){
        for(JLabel tab : tabs){
            tab.addMouseListener(ma);
        }

    }

    private void setListener(final JLabel label){

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetBackGroundColor();
                label.setBackground(Color.LIGHT_GRAY);
                selectedIndex = tabs.indexOf(label);
            }
        });
    }

    private void resetBackGroundColor(){

        for(JLabel label : tabs)
            label.setBackground(null);
    }


}
