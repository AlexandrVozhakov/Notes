package com.av;

/**
 * Created by av on 04.02.16.
 */
public class Note {

    private int id;
    private int section_id;
    private String header;
    private String date;
    private String text;

    public Note(){
        this(0);
    }

    public Note(int id){
        this(id, 0);
    }

    public Note(int id, int section_id){
        this(id, section_id, "");
    }

    public Note(int id, int section_id, String text){

        this.setId(id);
        this.setText(text);
        this.setSection_id(section_id);
        this.setDate(Service.date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public String getHeader() {

        return header;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return  date;
    }

    public String getTime() {

        return date.substring(date.lastIndexOf(" "));
    }

    public void setHeader(String text) {

        if(text.contains("\n"))
            this.header = text.substring(0, text.indexOf("\n"));
        else if(text.trim().equals(""))
            this.header = "New note";
        else
            this.header = text;
        //TODO: контролировать длинну заголовка
    }

    public void setDate(String date) {
        this.date =  date;
    }

    public void setText(String text) {

        this.text = text;
        setHeader(text);
    }

    public void update(String text, String date){

        setText(text);
        setDate(date);
    }

    public String toString(){

        return "<html><b> "+ getHeader() +"</b><br/><FONT color=\"#808080\"> " + getTime() + "</FONT></html>";
    }
}
