package com.av;


import java.awt.*;

/**
 * Created by av on 15.02.16.
 */
public class Controller implements IController{

    Model model;

    public Controller(Model model){

        this.model = model;
    }

    public void searchTextInsert(String text) {
        model.findNotes(text);
    }

    public void changeTab(int index){

        if (index == model.getTabsModel().size() - 1){
            addTab();
        } else
            model.changeSection(index);
    }

    private void addTab(){

        String name = getName();
        model.addSection(name);
    }

    private String getName(){

        InputInfoDialog dialog = new InputInfoDialog("Name tab", View.createNoteButton);
        return dialog.getString();
    }

    public void renameTab(int index){

        String name = getName();

        if(!name.equals("")){
            model.renameSection(index ,name);
        }
    }

    @Override
    public void tabSettings(int index, Point point) {

        PopupPanel d = new PopupPanel("test", point);

        if(d.getResult().equals("rename")){
            renameTab(index);

        } else if(d.getResult().equals("delete")){
            model.deleteSection(index);
        }
    }

    public void createNote(int section_id, String text) {
        model.createNewNote(section_id, text);
    }

    public void deleteNote(int index){

        if(index < 0)
            return;

        if(!model.getNote(index).getText().equals("")) {
            YesNoDialogFrame dialog = new YesNoDialogFrame("delete this note?", View.createNoteButton);
            if(dialog.getResult())
                model.deleteNote(index);
        } else
            model.deleteNote(index);
    }

    public void openNote(int index) {
        //model.selectNote(index);
    }

    public void textNoteChange(int note_id, int section_id, String text) {

        if (note_id < 0) {
            createNote(section_id, text);
            note_id = 0;
        }
        model.editNote(note_id, text);
    }
}
