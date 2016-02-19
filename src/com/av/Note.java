package com.av;

/**
 * Created by av on 04.02.16.
 */
public class Note {

    private String header;
    private String date;
    private String time;
    private String text;

    public Note(){
        this("New note");
    }
    public Note(String text){
        this.createNote(text);
    }

    private void createNote(String text){


        this.setHeader(text);
        this.setText("");
        this.setTime(Service.date("HH:mm"));
        this.setDate(Service.date("dd  MMMM  yyyy"));
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {

        return time;
    }

    public void setHeader(String text) {

        String date = Service.date("HH:mm");
        this.header = "<html><b>"+ text +"</b><br/><FONT color=\"#808080\">" + date + "</FONT></html>";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString(){
        return getHeader();
    }
}
