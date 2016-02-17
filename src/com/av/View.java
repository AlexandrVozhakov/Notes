package com.av;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by av on 20.01.16.
 */
public class View extends JFrame {

    private JTextField search;
    private JLabel date;
    private GlobalDimension dimension;
    private JTextArea textArea;
    public static FlatButton createNote;
    public static FlatButton deleteNote;
    public JPanel mainPanel;
    private DocListener docListener;
    private JList<Header> headerList;
    private DefaultListModel<Header> listModel;
    private JTabbedPane tabbedPanel;
    private Controller controller;

    public View(Controller controller) {

        //create frame size
        this.controller = controller;
        dimension = GlobalDimension.getInstance();
        docListener = new DocListener();
        listModel = new DefaultListModel<Header>();
        headerList = new JList<Header>(listModel);
    }


    public void createGUI(){

        // settings main frame program
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(dimension.getFrame());
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

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
        tabbedPanel.setFont(new Font(null, Font.BOLD, 18));
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
        JTextField search = createSearchField();

        // create Create button
        FlatButton createNote = createAddNoteButton();

        // create Delete button
        FlatButton deleteNote = createDeleteNoteButton();

        // create date label
        date = new JLabel(Program.date("d  MMMM  yyyy"));
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
        textArea.setFont(Program.globalFont);
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
        search.setFont(Program.globalFont);
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
        createNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*if (headerList.addNote()) {
                    setTextArea("");
                    textArea.requestFocus();
                }*/
            }
        });
        return createNote;
    }

    private FlatButton createDeleteNoteButton(){

        deleteNote = new FlatButton("Delete.png");
        deleteNote.setLocation(deleteNote.getWidth() * 3, 5);
        deleteNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //headerList.deleteNote();
                //setTextArea(headerList.getSelectedNoteText());
            }
        });
        return deleteNote;
    }


    public void setTabChangeListener(){
        tabbedPanel.addChangeListener(tabChangeListener);
    }

    public void addTab(String name){
        tabbedPanel.add(name, null);
    }

    public void setNewTab(int index, String name) {

        tabbedPanel.removeChangeListener(tabChangeListener);
        tabbedPanel.add(null, name, index);
        tabbedPanel.setSelectedIndex(index);
        tabbedPanel.addChangeListener(tabChangeListener);
    }

    public void setSelectedTab(int index){
        tabbedPanel.setSelectedIndex(index);
    }

    public void setListModel(DefaultListModel listModel){
        headerList.setModel(listModel);
    }

    public void setMainPanel(){
        tabbedPanel.setComponentAt(0, mainPanel);
        //tabbedPanel.addTab(name, component);
    }

    public void setTextArea(String text){

        textArea.getDocument().removeDocumentListener(docListener);
        textArea.setText(text);
        textArea.getDocument().addDocumentListener(docListener);
    }

/*    public void changeTab(){

        int index = tabbedPanel.getSelectedIndex();

        // if click not tab "+"
        if ((tabbedPanel.getTabCount() - 1) != index){
            headerList.changeListModel(index);
            setTextArea(headerList.getSelectedNoteText());
        }
        else {
            addNewTab();
        }
    }*/

/*    public void createTab(int index, String name) {

        tabbedPanel.removeChangeListener(tabChangeListener);
        tabbedPanel.add(null, name, index);
        tabbedPanel.setSelectedIndex(index);
        headerList.setNewListModel();
        tabbedPanel.addChangeListener(tabChangeListener);
    }*/

/*    public void addNewTab() {

        InputInfoDialogFrame dialogFrame = new InputInfoDialogFrame("name tab", createNote);
        String name = dialogFrame.getString();
        if(name.equals("")) {
            // set select on tab before tab "+"
            tabbedPanel.setSelectedIndex(tabbedPanel.getTabCount() - 2);
            return;
        }
        int index = tabbedPanel.getSelectedIndex();
        createTab(index, name);
        //dataBase.addSection(name);
        setTextArea("");
    }*/


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
            controller.noteTextChanged(text);
            headerList.repaint();
        }
    }
}
