package com.av;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by av on 15.02.16.
 */
public class Model extends Observable{


    //private ArrayList<String> sections;
    private DefaultListModel<Note> listModel;
    private DefaultListModel<String> tabsModel;
    private int selectedNote = 0;

    private DataBase db;

    public Model(){

        listModel = new DefaultListModel<Note>();
        tabsModel = new DefaultListModel<String>();

        //sections = new ArrayList<String>();
        connectDataBase();
        setSections();
    }

    private void connectDataBase(){

        db = DataBase.getInstance();
        db.connect();
        db.createDB();
    }

    private ArrayList<String> downloadSections(){

        //setChanged();
        //notifyObservers();

        ArrayList<String> sections = db.getSections();
        return sections;
    }

    private void setSections(){

        List<String> tabs = downloadSections();
        for(String tab : tabs)
            tabsModel.addElement(tab);
        tabsModel.addElement("+");
    }

    public String getSection(int index){
        return tabsModel.get(index);
    }

    public DefaultListModel<Note> getListModel(){
        return listModel;
    }

    public DefaultListModel<String> getTabsModel(){

        return tabsModel;
    }

    private void changeListModel(){

        listModel = new DefaultListModel<Note>();
        setChanged();
        notifyObservers(listModel);
    }


    public void addSection(String name) {

        setSection(tabsModel.size() - 1 ,name);
    }

    public void setSection(int index, String name){

        tabsModel.add(index, name);
        //db.addSection(name);
        changeListModel();
        setChanged();
        notifyObservers();
    }

    public void changeSection(int index) {

        changeListModel();
    }

    public void createNewNote(){

        listModel.add(0, new Note());
        setChanged();
        notifyObservers(listModel);
        //db.insertNote(pointerCurrentSection, "New note");
    }

    public void editNote(int noteId, String text){
        //db.insertNote(pointerCurrentSection, text);
    }

    private void setNotes(ArrayList<Note> notes){

        //this.notes.addAll(notes);
    }

    public void removeNote(int index){

        if(index < 0)
            return;
        listModel.remove(index);
    }

    private ArrayList<Note> downloadNotes(String param){
        return db.getNotes(param);
    }

    public void selectNote(int index){
        selectedNote = index;
    }

    public Note getNote(int index){
        return listModel.get(index);
    }






}
