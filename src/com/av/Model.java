package com.av;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by av on 15.02.16.
 */
public class Model extends Observable{


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

        ArrayList<String> sections = db.getSections();
        return sections;
    }

    private ArrayList<Note> downloadNotes(int section_id){

        ArrayList<Note> notes = db.getNotes(section_id);
        return notes;
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

    private void setNotes(int section_id){
        List<Note> notes = downloadNotes(section_id);
        for(Note note : notes)
            listModel.add(0, note);
    }

    public DefaultListModel<Note> getListModel(){
        return listModel;
    }

    public DefaultListModel<String> getTabsModel(){

        return tabsModel;
    }

    private void changeListModel(int section_id){

        listModel = new DefaultListModel<Note>();
        setNotes(section_id);

        setChanged();
        notifyObservers(listModel);
    }

    public void addSection(String name) {

        setSection(tabsModel.size() - 1 ,name);
    }

    public void setSection(int section_id, String name){

        tabsModel.add(section_id, name);
        db.addSection(name);
        changeListModel(section_id);
        setChanged();
        notifyObservers();
    }

    public void changeSection(int section_id) {

        changeListModel(section_id);
    }

    public void createNewNote(int section_id){

        listModel.add(0, new Note());
        db.insertNote(section_id, "New note");
        setChanged();
        notifyObservers(listModel);

    }

    public void editNote(int noteId, String text){
        
        //db.insertNote(pointerCurrentSection, text);
    }

    public void removeNote(int index){

        if(index < 0)
            return;
        listModel.remove(index);
    }

    public void selectNote(int index){
        selectedNote = index;
    }

    public Note getNote(int index){
        return listModel.get(index);
    }

}
