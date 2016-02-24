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
    private boolean flag = true;
    private int i;

    private DataBase db;

    public Model(){

        listModel = new DefaultListModel<Note>();
        tabsModel = new DefaultListModel<String>();

        //sections = new ArrayList<String>();
        connectDataBase();
        setSections();
        setNotes(0);
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
        listModel.clear();

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

        db.addNote(section_id);
        setNotes(section_id);
        setChanged();
        notifyObservers(listModel);

    }

    public void editNote(int note_id, int section_id, String text){

        // string for header
        String t = text;
        if(text.contains("\n"))
            t = text.substring(0, text.indexOf("\n"));

        Note note = listModel.get(note_id);
        note.setHeader(t);
        note.setText(text);

        saveNote(note, section_id);
    }

    public void removeNote(int index){

        int id = listModel.get(index).getId();
        listModel.remove(index);
        db.deleteNote(id);
    }

    private void saveNote(Note note, int section_id){

        //if(pause())
            db.updateNote(note, section_id);

    }

    private boolean pause(){
        i = 0;

        if(flag) {

            flag = false;

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    do {
                        try {

                            Thread.sleep(500);
                            i++;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (i < 2);

                    flag = true;
                    System.out.println("save");
                }
            });
            thread.start();    //Запуск потока
        }
        return flag;
    }

    public void selectNote(int index){
        selectedNote = index;
    }

    public Note getNote(int index){
        return listModel.get(index);
    }

}
