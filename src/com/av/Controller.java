package com.av;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by av on 15.02.16.
 */
public class Controller {

    Model model;
    View view;

    public Controller(){

        model = new Model();
        view = new View(this);
        view.createGUI();
        setTabs();
        view.setMainPanel();
        view.setTabChangeListener();
    }

    private void setTabs(){

        ArrayList<String> sections = model.downloadSections();
        for (String name : sections)
            view.addTab(name);
        view.addTab("+");
    }

    public void changeTab(int index){

        if(index == model.getSections().size())
            createNewTab();
        else
            view.setListModel(new DefaultListModel());
    }

    private void createNewTab(){

        InputInfoDialogFrame dialog = new InputInfoDialogFrame("name tab", view.createNote);

        if (model.createNewTab(dialog.getString()))
            view.setNewTab(model.getSections().size() - 1, model.getLastSection());
        else
            view.setSelectedTab(model.getSections().size() - 1);
    }

    public DefaultListModel<Header> getListModel(){
        return null;//model.getListModel();
    }

    public void noteTextChanged(String text){

    }
}
