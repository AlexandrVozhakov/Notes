package com.av;

import java.util.ArrayList;

/**
 * Created by av on 20.01.16.
 */
public interface Controller {

    boolean createNote();
    //boolean createHeader(String name);
    boolean deletNote(Header header);
    boolean safeNote(String note);
    ArrayList<Header> searchHeaders(String regex);
    ArrayList<Header> getHeaders();
    void setHeaders(ArrayList<Header> headers);
    void setTextHeader(String text);
    String getNote(Header header);
    String getDateNow(String reg);
    boolean documentChanged(String text);
}
