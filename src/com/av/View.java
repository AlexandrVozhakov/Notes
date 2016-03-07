package com.av;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
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
    public static FlatButton createNoteButton;
    public static FlatButton deleteNoteButton;
    public JPanel mainPanel;
    private DocListener docListener;
    private JList<Note> headerList;
    private JList<Section> tabList;
    private Model model;
    IController controller;

    public View(Model model, IController controller) {

        this.model = model;
        this.controller = controller;
        //create frame size
        dimension = GlobalDimension.getInstance();
        docListener = new DocListener();
        headerList = new JList<Note>(model.getHeadersModel());
        headerList.setCellRenderer(new SideBarListRenderer());
        tabList = new JList<Section>(model.getTabsModel());

        createGUI();
        setListeners();
    }


    public void createGUI(){

        mainPanel = createMainPanel();

        JPanel tabsPanel = createPanel(dimension.getTabsPanel(), null, new BorderLayout());

        tabList.setCellRenderer(new TabsBarListRenderer());
        tabList.setLayoutOrientation (JList.HORIZONTAL_WRAP); // стиль размещения горизонтальный
        tabList.setVisibleRowCount(1);// управлением количества видимых строк в списке
        tabList.setSelectedIndex(0);
        tabList.setBackground(null);

        headerList.setSelectedIndex(0);

        JScrollPane sp = new JScrollPane(tabList);

        sp.setBorder(null);
        tabsPanel.add(sp, BorderLayout.CENTER);

        this.setPreferredSize(dimension.getFrame());
        this.add(tabsPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.SOUTH);
        showNote(model.getNote(headerList.getSelectedIndex()));
        //this.setVisible(true);
    }

    private JPanel createPanel(Dimension dimension, Color color, LayoutManager layout){

        JPanel panel = new JPanel(layout);
        panel.setPreferredSize(dimension);
        panel.setBackground(color);
        return panel;
    }

    private JPanel createMainPanel(){

        JPanel mainPanel = createPanel(dimension.getRootPanel(), null, new BorderLayout());
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
        createNoteButton = createAddNoteButton();
        createNoteButton.setName("Create");

        // create Delete button
        deleteNoteButton = createDeleteNoteButton();
        deleteNoteButton.setName("Delete");

        // create date label
        date = new JLabel(Service.date());
        date.setFont(new Font(null, Font.ITALIC, 19));

        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane.getVerticalScrollBar().setUI(new CustomScrollbarUI());
        listScrollPane.setBorder(null);

        listPanel.add(listScrollPane, BorderLayout.CENTER);
        searchPanel.add(search, BorderLayout.CENTER);

        createButtonPanel.add(createNoteButton);
        controlButtonPanel.add(deleteNoteButton, BorderLayout.EAST);
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

        mainPanel.add(sideBarPanel, BorderLayout.WEST);
        mainPanel.add(textPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JTextArea createTextArea(){

        textArea = new JTextArea();
        DocumentUndoManager undo = DocumentUndoManager.getInstance();
        undo.registerDocumentHolder(textArea);

        textArea.setFont(Service.globalFont);
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(null);
        textArea.getDocument().addDocumentListener(docListener);

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

            @Override
            public void keyReleased(KeyEvent e) {
                String text = search.getText();
                if(!text.trim().equals(""))
                    controller.searchTextInsert(text.trim());
            }
        });
        /*search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.searchTextInsert(search.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.searchTextInsert(search.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });*/
        search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                search.setText("Search");
            }
        });
        return search;
    }

    private FlatButton createAddNoteButton(){
        createNoteButton = new FlatButton("Create.png");
        createNoteButton.setLocation(dimension.getBorder(), 5);
        return createNoteButton;
    }

    private FlatButton createDeleteNoteButton(){

        deleteNoteButton = new FlatButton("Delete.png");
        deleteNoteButton.setLocation(deleteNoteButton.getWidth() * 3, 5);
        return deleteNoteButton;
    }

    private void setTextArea(final String text) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.getDocument().removeDocumentListener(docListener);

                textArea.setText(text);

                textArea.getDocument().addDocumentListener(docListener);
            }
        });
    }

    private void showNote(Note note){

        setTextArea(note.getText());
        date.setText(note.getDate());
    }


    @Override
    public void update(Observable o, Object arg) {

        tabList.setSelectedIndex(model.getSelectedTab());
        headerList.setModel(model.getHeadersModel());
        headerList.setSelectedIndex(0);
        headerList.ensureIndexIsVisible(0);
        showNote(model.getNote(headerList.getSelectedIndex()));
        //textArea.requestFocus();
    }

    public void setListeners(){

        createNoteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.createNote(tabList.getSelectedIndex(), "");

            }
        });
        deleteNoteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.deleteNote(headerList.getSelectedIndex());
            }
        });
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE)
                    controller.deleteNote(headerList.getSelectedIndex());
            }
        });

        tabList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //if(e.getButton() == MouseEvent.BUTTON3)
                    tabList.setSelectedIndex(tabList.locationToIndex(e.getPoint()));
                controller.changeTab(tabList.getSelectedIndex());
                if(e.getButton() == MouseEvent.BUTTON3) {
                    Point location = MouseInfo.getPointerInfo().getLocation();
                    controller.tabSettings(tabList.getSelectedIndex(), location);
                }
            }
        });

        headerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNote(model.getNote(headerList.getSelectedIndex()));
                textArea.requestFocus();
            }
        });

       /* search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {find();}
            @Override
            public void removeUpdate(DocumentEvent e) {find();}
            @Override
            public void changedUpdate(DocumentEvent e) {}
            private void find(){
                controller.searchTextInsert(search.getText().trim());
            }
        });*/

    }

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
            controller.textNoteChange(headerList.getSelectedIndex(), tabList.getSelectedIndex(), text);
            headerList.repaint();
        }
    }
}
