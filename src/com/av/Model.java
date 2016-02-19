package com.av;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by av on 15.02.16.
 */
public class Model extends Observable{


    private ArrayList<String> sections;
    private DefaultListModel<Note> listModel;

    private DataBase db;

    public Model(){

        listModel = new DefaultListModel<Note>();
        sections = new ArrayList<String>();
        connectDataBase();
        setSections();
    }

    private void connectDataBase(){

        db = DataBase.getInstance();
        db.connect();
        db.createDB();
    }

    public ArrayList<String> downloadSections(){

        //setChanged();
        //notifyObservers();

        ArrayList<String> sections = db.getSections();
        //this.sections.addAll(sections);
        return sections;
    }

    private void setSections(){
        sections.addAll(downloadSections());
        //sections
    }

    private void addSection(String name){

        //sections.add(name);
        //changeSection(sections.size() - 1);
        //db.addSection(name);
    }

    public String getSection(int index){
        return null;//sections.get(index);
    }

    public String getLastSection(){
        return null;//getSection(sections.size() - 1);
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

    public void changeSection(int sectionIndex){

        //pointerCurrentSection = sectionIndex;
        //System.out.println(sections.get(pointerCurrentSection));
    }

    public void createNewNote(){

        //notes.add(new Note());
        //db.insertNote(pointerCurrentSection, "New note");
    }

    public void editNote(int noteId, String text){
        //db.insertNote(pointerCurrentSection, text);
    }

    private void setNotes(ArrayList<Note> notes){

        //this.notes.addAll(notes);
    }

    private ArrayList<Note> downloadNotes(String param){
        return db.getNotes(param);
    }

    public Note getNote(int index){
        return null;//notes.get(index);
    }






}
