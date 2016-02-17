package com.av;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by av on 15.02.16.
 */
public class Model {

    //private DefaultListModel<Header> listModel;
    private ArrayList<String> sections;
    private ArrayList<Note> notes;
    private DataBase db;

    public Model(){

        //listModel = new DefaultListModel<Header>();
        sections = new ArrayList<String>();
        notes = new ArrayList<Note>();
        db = DataBase.getInstance();
        connectDataBase();
    }

    private void connectDataBase(){

        db = DataBase.getInstance();
        db.connect();
        db.createDB();
    }

    public ArrayList<String> downloadSections(){

        ArrayList<String> sections = db.getSections();
        this.sections.addAll(sections);
        return sections;
    }

    public void addSection(String name){

        sections.add(name);
        db.addSection(name);
    }

    public String getSection(int index){
        return sections.get(index);
    }

    public String getLastSection(){
        return getSection(sections.size() - 1);
    }

    public ArrayList<String> getSections(){

        return new ArrayList<String>(sections);
    }

    public boolean createNewTab(String name) {

        if (name.equals("")) {
            return false;
        }
        addSection(name);
        return true;
    }

    private void setNotes(ArrayList<Note> notes){

        this.notes.addAll(notes);
    }

    private ArrayList<Note> downloadNotes(String param){
        return db.getNotes(param);
    }

    public Note getNote(int index){
        return notes.get(index);
    }






}
