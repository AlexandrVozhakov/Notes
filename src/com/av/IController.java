package com.av;

/**
 * Created by av on 18.02.16.
 */
public interface IController {

    void changeTab(int index);
    void searchTextInsert(String text);
    void createNote(int index);
    void removeNote(int index);
    void openNote(int index);
    void textNoteChange(int note_id, int section_id, String text);
}
