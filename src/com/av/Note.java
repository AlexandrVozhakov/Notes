package com.av;

/**
 * Created by av on 04.02.16.
 */
public class Note {

    private String name;
    private String date;
    private String text;

    public Note(){
        this("New note");
    }
    public Note(String text){
        this.createNote(text);
    }
    Controller controller = Model.getControl();

    private void createNote(String text){

        this.setName(text);
        this.setText("");
        this.setDate(controller.getDateNow("HH:mm"));
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }
}
