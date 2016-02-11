package com.av;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by av on 20.01.16.
 */
public class Model implements Controller {

    public static Model instance;
    public static File storage = new File("/home/av/Workspace/IDEA/Notes/src/com/company/Storage.txt");
    public static File index = new File("/home/av/Workspace/IDEA/Notes/src/com/company/index.txt");


    private Model(){

    }
    public static Model getControl(){
        if(instance == null)
            instance = new Model();
        return instance;
    }
/*
    @Override
    public boolean createNote(Note note) {

        String text = note.getText();//TextPanel.textArea.getText();
        String noteName = note.getName();

        if(text.matches("(\\s+)?\\S"))
            return false;

        try {

            RandomAccessFile indexFile = new RandomAccessFile (index, "rw");
            text += "*}}}*";
            indexFile.writeBytes(noteName + " " + text.length());
            indexFile.close();

            RandomAccessFile storageFile = new RandomAccessFile (storage, "rw");
            storageFile.writeBytes(noteName + " " + text);
            storageFile.close();

        } catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        //headders.reedHeaders();
        return true;
    }

    @Override
    public boolean createHeader(String name) {
        //Header header = new Header(name);

        return true;
    }*/


    @Override
    public boolean createNote() {

        return false;
    }

    @Override
    public boolean deletNote(Header header) {
        return false;
    }

    @Override
    public boolean safeNote(String note) {
        return false;
    }

    @Override
    public ArrayList<Header> searchHeaders(String regex) {

        return null;
    }

    @Override
    public ArrayList<Header> getHeaders() {
        return null;
    }

    @Override
    public void setHeaders(ArrayList<Header> headers) {

    }

    @Override
    public void setTextHeader(String text) {

    }

    @Override
    public String getNote(Header header) {
        return null;
    }

    public String getDateNow(String reg){

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(reg);
        return new String(format.format(date)); //"dd.MM.yyyy HH:mm:ss"
    }

    @Override
    public boolean documentChanged(String text) {

        //System.out.println(text);

        return false;
    }
}
