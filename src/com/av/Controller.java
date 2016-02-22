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


    public void createNote() {
        model.createNewNote();
    }


    public void removeNote(int index){
        model.removeNote(index);
    }


    public void openNote(int index) {
        model.selectNote(index);
    }


    public void textNoteChange(int index, String text) {

    }
}
