package com.av;

import javax.swing.*;
import java.util.*;

/**
 * Created by av on 10.02.16.
 */
public class HeaderList extends JList<Header> {

    private ArrayList<DefaultListModel<Header>> listModels;
    private Map<Header, Note> notes;

    public HeaderList(DefaultListModel model) {

        super(model);
        notes = new LinkedHashMap<Header, Note>();
        listModels = new ArrayList<DefaultListModel<Header>>();
        listModels.add(model);
        this.setCellRenderer(new NewListRenderer());
    }

    public void setNewListModel(){

        DefaultListModel<Header> listModel = new DefaultListModel<Header>();
        listModels.add(listModel);
        this.setModel(listModel);
    }

    public void changeListModel(int indexListModel){

        this.setModel(listModels.get(indexListModel));
        this.setSelectedIndex(0);
        this.ensureIndexIsVisible(0);
    }

    public boolean addNote() {

        //do not create note if in begin note no text
        if (!getSelectedListModel().isEmpty() && getNote(0).getText().equals("")) {
            return false;
        }
        //System.out.println(getNote(0));
        Header header = new Header();
        this.getSelectedListModel().add(0, header);
        notes.put(header, new Note());

        this.setSelectedIndex(0);
        this.ensureIndexIsVisible(0);
        return true;
    }

    public void setNoteText(String text) {
        getSelectedNote().setText(text);
    }

    public void setTextHeader(String text) {

        final Header element = getSelectedListModel().getElementAt(getSelectedIndex());
        element.setHeaderName(text);
    }

    public void textChanged(String text) {

        // create new note if note list is empty in this tab
        if (getSelectedListModel().isEmpty())
            addNote();

        String t = text;

        // cat first string in note for header
        if (text.contains("\n"))
            t = text.substring(0, text.indexOf("\n"));

        setTextHeader(t);
        setNoteText(text);
    }

    public boolean deleteNote() {

        int index = getSelectedIndex();
        if (index < 0) // if list not is empty in this tab
            return false;

        // if in selected tab no text
        if(getSelectedNoteText().equals("")) {
            removeNote(index);
            this.setSelectedIndex(0);
            this.ensureIndexIsVisible(0);
            return true;
        }
        // else
        YesNoDialogFrame dialog = new YesNoDialogFrame("Remove this note?", this);

        if(dialog.getResult()) {
            removeNote(index);
            this.setSelectedIndex(0);
            this.ensureIndexIsVisible(0);
            return true;
        }
        return false;
    }

    private void removeNote(int index){

        notes.remove(index);
        getSelectedListModel().removeElementAt(index);
    }

    public void deleteEmptyNote(){

        if(this.getNote(0).getText().equals("")) {
            removeNote(0);
        }
    }

    public String getSelectedNoteText() {
        return notes.size() > 0 ? getSelectedNote().getText() : "";
    }

    public Note getSelectedNote(){
        return notes.get(getSelectedListModel().get(getSelectedIndex()));
    }

    public Note getNote(int index){
        return notes.get(getSelectedListModel().get(index));
    }

    private DefaultListModel<Header> getSelectedListModel(){
        return  (DefaultListModel) this.getModel();
    }

}
