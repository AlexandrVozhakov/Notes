package com.av;


/**
 * Created by av on 15.02.16.
 */
public class Controller implements IController{

    Model model;

    public Controller(Model model){

        this.model = model;
    }

    public void searchTextInsert(String text) {

    }


    public void changeTab(int index){

        if (index == model.getTabsModel().size() -1){
            InputInfoDialog dialog = new InputInfoDialog("Name tab", View.createNote);
            if(!dialog.getString().equals("")){
                model.addSection(dialog.getString());
            } //else

        } else
            model.changeSection(index);
    }


    public void createNote(int section_id) {
        model.createNewNote(section_id);
    }


    public void removeNote(int index){

        if(index < 0)
            return;

        if(!model.getNote(index).getText().equals("")) {
            YesNoDialogFrame dialog = new YesNoDialogFrame("delete this note?", View.createNote);
            if(dialog.getResult())
                model.removeNote(index);
        } else
            model.removeNote(index);
    }


    public void openNote(int index) {
        model.selectNote(index);
    }


    public void textNoteChange(int note_id, int section_id, String text) {
        model.editNote(note_id, section_id, text);
    }
}
