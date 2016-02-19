package com.av;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by av on 15.02.16.
 */
public class Controller implements ActionListener{

    Model model;

    public Controller(Model model){

        this.model = model;

/*        model = new Model();
        view = new View(this);
        view.createGUI();
        setTabs();
        view.setMainPanel();
        view.setTabChangeListener();*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }



    public void searchTextInsert(String text) {

    }


    public void changeTab(int index){

        /*if(index == model.getSections().size()) {
            createNewTab();
        }
        else {
            //view.setListModel(new DefaultListModel());
            model.changeSection(index);
        }*/
    }


    public void createNote() {

    }


    public void removeNote(int index){

    }


    public void openNote(int index) {

    }


    public void textNoteChange(int index, String text) {

    }





    private void setTabs(){

        //ArrayList<String> sections = model.downloadSections();
        /*for (String name : sections)
            view.addTab(name);
        view.addTab("+");*/
    }

    private void createNewTab(){

        /*InputInfoDialogFrame dialog = new InputInfoDialogFrame("name tab", view.createNote);
        int index = model.getSections().size();

        // if user set name tab not empty
        if (model.createNewTab(dialog.getString()))
            view.setNewTab(index, model.getLastSection());
        else {
            view.setSelectedTab(index - 1);
            //model.changeSection(index);
        }*/
    }

    public void createNewNote(){
        //view.getListModel().add(0, new Header());
        //model.createNewNote();
    }





    public DefaultListModel<Header> getListModel(){
        return null;//model.getListModel();
    }

    public void noteTextChanged(int inde, String text){

    }


}
