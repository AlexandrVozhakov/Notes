package com.av;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by av on 15.02.16.
 */
public class Model extends Observable{


    private DefaultListModel<Note> headersModel;
    private DefaultListModel<Section> tabsModel;
    private int selectedTab;
    private int selectedNote;
    private boolean flag = true;
    private int i;
    private DataBase db;

    public Model(){

        headersModel = new DefaultListModel<Note>();
        tabsModel = new DefaultListModel<Section>();

        connectDataBase();

        selectedTab = 0;
        selectedNote = 0;

        setSections();
    }

    private void connectDataBase(){

        db = DataBase.getInstance();
        db.connect();
        db.createDB();
    }

    private void setSections(){

        List<Section> tabs = db.getSections();
        tabsModel.clear();// = new DefaultListModel<Section>();

        for(Section tab : tabs)
            tabsModel.addElement(tab);

        tabsModel.addElement(new Section("+"));

        setNotes(getSection(selectedTab).getId());
    }

    public void findNotes(String text){

        List<Note> notes = db.findNotes(text);
        headersModel = new DefaultListModel<Note>();
        for(Note note : notes)
            headersModel.add(0, note);

        notifyAllObservers();
    }

    private void setNotes(int section_id){

        //long time = System.currentTimeMillis();

        List<Note> notes = db.getNotes(section_id);
        headersModel = new DefaultListModel<Note>();

        for(Note note : notes)
            headersModel.add(0, note);

        //time = System.currentTimeMillis() - time;
        //System.out.println("time " + (double)time / 1000);

        notifyAllObservers();
    }

    private void notifyAllObservers(){

        setChanged();
        notifyObservers();
    }

    public DefaultListModel<Note> getHeadersModel(){
        return headersModel;
    }

    public DefaultListModel<Section> getTabsModel(){

        return tabsModel;
    }

    public void addSection(String name){

        if(name.equals(""))
            selectedTab = tabsModel.size() - 2;
        else {
            db.addSection(name);
            selectedTab = tabsModel.size() - 1;
        }
        setSections();
    }

    public void renameSection(int index, String name){

        int id = getSection(index).getId();
        db.updateSection(id, name);
        setSections();
    }

    public void deleteSection(int index){

        int id = getSection(index).getId();
        db.deleteSection(id);
        selectedTab--;
        setSections();
    }

    public void changeSection(int section_id) {

        headersModel = new DefaultListModel<Note>();
        selectedTab = section_id;
        int id = getSection(section_id).getId();
        setNotes(id);
    }

    public void createNewNote(int section_id, String text){

        if(headersModel.size() > 0 && getNote(0).getText().trim().equals(""))
            return;

        int id = getSection(section_id).getId();
        db.addNote(id, text);
        setNotes(id);
    }

    public void editNote(int note_id, String text){

        getNote(note_id).update(text, Service.date());
        saveNote(note_id);
    }

    public void deleteNote(int index){

        int id = getNote(index).getId();
        headersModel.remove(index);
        db.deleteNote(id);
        notifyAllObservers();
    }

    private void saveNote(final int id){

        i = 0; // TODO: rename this variable

        if(flag) {

            flag = false;

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    do {
                        try {
                            Thread.sleep(100);
                            i++;
                        } catch (InterruptedException e) {e.printStackTrace();}
                    } while (i < 5);
                    //TODO: подобрать время ожидания
                    db.updateNote(headersModel.get(id));
                    flag = true;
                }
            });
            thread.start();    //Запуск потока
        }
    }

    public Note getNote(int index){

        Note note = new Note();
        if (headersModel.size() > 0 && index >= 0) {
            note = headersModel.get(index);
        }
        return note;
    }

    public Section getSection(int index){

        return tabsModel.get(index);
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public int getSelectedNote() {
        return selectedNote;
    }

}
