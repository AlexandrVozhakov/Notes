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
    private FlatButton createNote;
    private FlatButton deleteNote;
    private JLabel date;
    private GlobalDimension dimension;
    private JTextArea textArea;
    public static JPanel mainPanel;
    private DocListener docListener;
    HeaderList headerList;
    private JTabbedPane tabbedPanel;
    Controller controller = Model.getControl();

    public View(){

        //create frame size
        dimension = GlobalDimension.getInstance();
        docListener = new DocListener();
        headerList = new HeaderList(new DefaultListModel<Header>());
        headerList.addMouseListener(headerListListener);
        createGUI();
    }

    private void createGUI(){

        // settings main frame program
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(dimension.getFrame());
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        //sideBarScrollPane.getVerticalScrollBar().setUI(new CustomScrollbarUI());

        mainPanel = createNotePanel();
        tabbedPanel = new JTabbedPane();
        tabbedPanel.setPreferredSize(dimension.getFrame());
        tabbedPanel.setBackground(null);
        tabbedPanel.setFont(new Font(null, Font.BOLD, 18));
        tabbedPanel.add(mainPanel, "Scribe", 0);
        tabbedPanel.addTab("+", null);
        tabbedPanel.addChangeListener(tabChangeListener);

        this.add(tabbedPanel, BorderLayout.CENTER);
        this.setVisible(true);

        setListeners();
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

        textArea = new JTextArea();
        textArea.setFont(Program.globalFont);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // create search text field
        search = new JTextField("Search");
        search.setFont(Program.globalFont);
        search.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        search.setHorizontalAlignment(JTextField.CENTER);

        // create Create button
        createNote = new FlatButton("Create.png");
        createNote.setLocation(dimension.getBorder(), 5);

        // create Delete button
        deleteNote = new FlatButton("Delete.png");
        deleteNote.setLocation(deleteNote.getWidth() * 3, 5);
        // create date label
        date = new JLabel(controller.getDateNow("d  MMMM  yyyy"));
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
        textArea.setBackground(null);
        tp.add(textScroll);

        textPanel.add(tp, BorderLayout.SOUTH);
        sideBarPanel.add(searchPanel, BorderLayout.NORTH);
        sideBarPanel.add(listPanel, BorderLayout.SOUTH);

        rootPanel.add(sideBarPanel, BorderLayout.WEST);
        rootPanel.add(textPanel, BorderLayout.EAST);

        return rootPanel;
    }

    public void changeTab(){

        int index = tabbedPanel.getSelectedIndex();

        // if click not tab "+"
        if ((tabbedPanel.getTabCount() - 1) != index){
            headerList.changeListModel(index);
            setTextArea(headerList.getSelectedNoteText());
        }
        else {
            addNewTab();
        }
    }

    public void addNewTab() {

        InputInfoDialogFrame dialogFrame = new InputInfoDialogFrame("name tab", createNote);
        if(dialogFrame.getString().equals("")) {
            // set select on tab before tab "+"
            tabbedPanel.setSelectedIndex(tabbedPanel.getTabCount() - 2);
            return;
        }
        int index = tabbedPanel.getSelectedIndex();
        tabbedPanel.removeChangeListener(tabChangeListener);
        tabbedPanel.add(null, dialogFrame.getString(), index);
        tabbedPanel.setSelectedIndex(index);
        setTextArea("");
        headerList.setNewListModel();
        tabbedPanel.addChangeListener(tabChangeListener);
    }

    public void setTextArea(String text){

        textArea.getDocument().removeDocumentListener(docListener);
        textArea.setText(text);
        textArea.getDocument().addDocumentListener(docListener);
    }

    private void setListeners(){

        // SEARCH
        {
            search.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    search.setText("");
                    //cancel.setVisible(true);
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
        }
        // CREATE
        {
            createNote.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if (headerList.addNote()) {
                        setTextArea("");
                        textArea.requestFocus();
                    }
                }
            });
        }
        // DELETE
        {
            deleteNote.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    headerList.deleteNote();
                    setTextArea(headerList.getSelectedNoteText());
                }
            });
        }
        //TEXT
        {
            textArea.getDocument().addDocumentListener(docListener);

            //Side Bar

            //Delete note key TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE){
                    headerList.deleteNote();
                    setTextArea(headerList.getSelectedNoteText());
                }
            }
        });
/*        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(sideBar.getSelectedNote().equals("")){
                    sideBar.delNote();
                    setTextArea(sideBar.getSelectedNote());
                }
            }
        });*/
        }
    }

    ChangeListener tabChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            View.this.changeTab();
        }
    };

    MouseAdapter headerListListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            headerList.deleteEmptyNote();
            setTextArea(headerList.getSelectedNoteText());
            textArea.requestFocus();
        }
    };


    class DocListener implements DocumentListener {

        private String text;

        public void removeUpdate(DocumentEvent event) {

            //System.out.println("removeUpdate");
            textChanged();
        }

        public void insertUpdate(DocumentEvent event) {

            //System.out.println("insertUpdate");
            textChanged();
        }
        public void changedUpdate(DocumentEvent event) {}

        private void textChanged() {

            text = textArea.getText().trim();
            headerList.textChanged(text);
            headerList.repaint();
        }
    }
}
