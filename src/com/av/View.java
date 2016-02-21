package com.av;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by av on 20.01.16.
 */
public class View extends JPanel implements Observer{

    private JTextField search;
    private JLabel date;
    private GlobalDimension dimension;
    private JTextArea textArea;
    public static FlatButton createNote;
    public static FlatButton deleteNote;
    public JPanel mainPanel;
    private DocListener docListener;
    private JList<Note> headerList;
    private JTabbedPane tabbedPanel;
    private Model model;
    IController controller;

    public View(Model model, IController controller) {

        this.model = model;
        this.controller = controller;
        //create frame size
        dimension = GlobalDimension.getInstance();
        docListener = new DocListener();
        headerList = new JList<Note>(model.getListModel());
        headerList.setCellRenderer(new NewListRenderer());
        createGUI();
        setListeners();
    }


    public void createGUI(){

        //sideBarScrollPane.getVerticalScrollBar().setUI(new CustomScrollbarUI());
        mainPanel = createNotePanel();
        tabbedPanel = createTabbedPanel();

        this.add(tabbedPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JTabbedPane createTabbedPanel(){

        tabbedPanel = new JTabbedPane();
        tabbedPanel.setPreferredSize(dimension.getFrame());
        tabbedPanel.setBackground(null);
        tabbedPanel.setBorder(null);
        tabbedPanel.setFont(new Font(null, Font.BOLD, 18));
        tabbedPanel.addTab("+", mainPanel);
        tabbedPanel.addChangeListener(tabChangeListener);
        this.setTabs(model.getSections());
        return tabbedPanel;
    }

    private JPanel createPanel(Dimension dimension, Color color, LayoutManager layout){

        JPanel panel = new JPanel(layout);
        panel.setPreferredSize(dimension);
        panel.setBackground(color);
        return panel;
    }

    private JPanel createNotePanel(){

        JPanel rootPanel = createPanel(dimension.getRootPanel(), null, new BorderLayout());
        JPanel sideBarPanel = createPanel(dimension.getSideBarPanel(), Color.WHITE, new BorderLayout());
        JPanel searchPanel = createPanel(dimension.getSearchPanel(), Color.WHITE, new BorderLayout());
        JScrollPane listScrollPane = new JScrollPane(headerList);

        JPanel listPanel = createPanel(dimension.getListPanel(), Color.WHITE, new BorderLayout());

        JPanel createButtonPanel = createPanel(new Dimension(dimension.getControlPanel().width / 3,
                dimension.getControlPanel().height), null, null);
        createButtonPanel.setLocation(dimension.getBorder(), 0);

        JPanel controlButtonPanel = createPanel(new Dimension(dimension.getControlPanel().width / 3,
                dimension.getControlPanel().height), null, null);

        JPanel textPanel = createPanel(dimension.getTextPanel(), null, new BorderLayout());

        JPanel controlPanel = createPanel(dimension.getControlPanel(), null, new BorderLayout());

        textArea = createTextArea();

        // create search text field
        search = createSearchField();
        //search.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        // create Create button
        createNote = createAddNoteButton();
        createNote.setName("Create");

        // create Delete button
        deleteNote = createDeleteNoteButton();
        deleteNote.setName("Delete");

        // create date label
        date = new JLabel(Service.date("d  MMMM  yyyy"));
        date.setFont(new Font(null, Font.ITALIC, 19));

        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane.setBorder(null);

        listPanel.add(listScrollPane, BorderLayout.CENTER);
        searchPanel.add(search, BorderLayout.CENTER);

        createButtonPanel.add(createNote);
        controlButtonPanel.add(deleteNote, BorderLayout.EAST);
        controlPanel.add(createButtonPanel, BorderLayout.WEST);
        controlPanel.add(date, BorderLayout.CENTER);
        controlPanel.add(controlButtonPanel, BorderLayout.EAST);

        textPanel.add(controlPanel, BorderLayout.NORTH);

        JPanel tp = createPanel(new Dimension(dimension.getTextPanel().width,
                dimension.getTextPanel().height - dimension.getBorder()), null, null);

        JScrollPane textScroll = new JScrollPane(textArea);
        textScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setBorder(null);
        textScroll.setBounds(dimension.getBorder(), 0, dimension.getTextPanel().width -
                        dimension.getBorder(),
                dimension.getTextPanel().height - dimension.getBorder());

        tp.add(textScroll);

        textPanel.add(tp, BorderLayout.SOUTH);
        sideBarPanel.add(searchPanel, BorderLayout.NORTH);
        sideBarPanel.add(listPanel, BorderLayout.SOUTH);

        rootPanel.add(sideBarPanel, BorderLayout.WEST);
        rootPanel.add(textPanel, BorderLayout.EAST);

        return rootPanel;
    }

    private JTextArea createTextArea(){

        textArea = new JTextArea();
        textArea.setFont(Service.globalFont);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(null);
        textArea.getDocument().addDocumentListener(docListener);
        /*textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE){
                    //headerList.deleteNote();
                    //setTextArea(headerList.getSelectedNoteText());
                }
            }
        });*/
        return textArea;
    }

    private JTextField createSearchField(){

        search = new JTextField();
        search = new JTextField("Search");
        search.setFont(Service.globalFont);
        search.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        search.setHorizontalAlignment(JTextField.CENTER);

        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                search.setText("");
            }
        });
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    search.setText("Search");
                    textArea.requestFocus();
                }
            }
        });
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //controller.searchTextInsert(search.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                //controller.searchTextInsert(search.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                search.setText("Search");
            }
        });
        return search;
    }

    private FlatButton createAddNoteButton(){
        createNote = new FlatButton("Create.png");
        createNote.setLocation(dimension.getBorder(), 5);
        return createNote;
    }

    private FlatButton createDeleteNoteButton(){

        deleteNote = new FlatButton("Delete.png");
        deleteNote.setLocation(deleteNote.getWidth() * 3, 5);
        deleteNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //controller.removeNote(headerList.getSelectedIndex());
            }
        });
        return deleteNote;
    }

    private void setTabs(ArrayList<String> tabs){

        for(String t : tabs)
            setNewTab(t);
    }

    private void setNewTab(String name) {

        tabbedPanel.removeChangeListener(tabChangeListener);
        tabbedPanel.add(null, name, tabbedPanel.getTabCount() - 1);
        tabbedPanel.setSelectedIndex(tabbedPanel.getTabCount() - 2);
        tabbedPanel.addChangeListener(tabChangeListener);
    }

    private void setSelectedTab(int index){
        tabbedPanel.setSelectedIndex(index);
    }

    private void setMainPanel(){
        tabbedPanel.setComponentAt(0, mainPanel);
        //tabbedPanel.addTab(name, component);
    }

    private void setTextArea(String text){

        textArea.getDocument().removeDocumentListener(docListener);
        textArea.setText(text);
        textArea.getDocument().addDocumentListener(docListener);
    }

    private void showNote(Note note){
        setTextArea(note.getText());
        date.setText(note.getDate());
    }


    @Override
    public void update(Observable o, Object arg) {
        //updateTabs();
        if(arg instanceof ArrayList)
            setNewTab(model.getSection(tabbedPanel.getTabCount() - 1));

        if(arg instanceof DefaultListModel)
            headerList.setModel(model.getListModel());
    }

    public void setListeners(){

        createNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.createNote();
            }
        });
        deleteNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.removeNote(headerList.getSelectedIndex());
            }
        });
        headerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNote(model.getNote(headerList.getSelectedIndex()));
            }
        });

    }

    ChangeListener tabChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            controller.changeTab(tabbedPanel.getSelectedIndex());
        }
    };

    class DocListener implements DocumentListener {

        private String text;

        public void removeUpdate(DocumentEvent event) {
            textChanged();
        }
        public void insertUpdate(DocumentEvent event) {
            textChanged();
        }
        public void changedUpdate(DocumentEvent event) {}

        private void textChanged() {

            text = textArea.getText().trim();
            //controller.textNoteChange(headerList.getSelectedIndex(), text);
            headerList.repaint();
        }
    }
}
