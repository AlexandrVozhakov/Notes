package com.av;

import java.awt.*;

/**
 * Created by av on 18.02.16.
 */
public interface IController {

    void changeTab(int index);
    void tabSettings(int index, Point point);
    void searchTextInsert(String text);
    void createNote(int index, String text);
    void deleteNote(int index);
    void openNote(int index);
    void textNoteChange(int note_id, int section_id, String text);
}
