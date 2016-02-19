package com.av;

import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by av on 22.01.16.
 */
public class SideBar extends JPanel {

    private JList<Header> headers;
    private ArrayList<String> notes;
    private JScrollPane scroll;
    final DefaultListModel listModel;

    public SideBar() {

        listModel = new DefaultListModel();
        notes = new ArrayList<String>();
        headers = new JList<Header>(listModel);
        headers.setCellRenderer(new NewListRenderer());
        headers.setSelectedIndex(0);
        headers.setFocusable(false);

        scroll = new JScrollPane(headers);
        createGUI();

    }

    private void createGUI() {

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
    }

    public void addNote() {

        //do not create note if in begin note no text
        if (notes.size() != 0 && notes.get(0).equals(""))
            return;

        listModel.add(0, new Header());
        notes.add(0, "");
        headers.setSelectedIndex(0);
        headers.ensureIndexIsVisible(0);
    }

    public void setNote(String text) {

        int index = headers.getSelectedIndex();
        notes.set(index, text);
    }

    public void setTextHeader(String text) {

        int index = headers.getSelectedIndex();
        final Header element = (Header) (listModel.getElementAt(index));
        element.setHeaderName(text);
        headers.repaint();
    }

    public void textChanged(String text) {

        if (notes.size() == 0)
            addNote();
        String t = text;

        if (text.contains("\n"))
            t = text.substring(0, text.indexOf("\n"));

        setTextHeader(t);
        setNote(text);
    }

    public boolean delNote() {

        int index = headers.getSelectedIndex();
        if (index < 0)
            return false;

        if(notes.get(index).equals("")) {
            removeNote(index);
            return true;
        }

        DeleteNoteDialog dialog = new DeleteNoteDialog();

        if(dialog.isOK()) {
            removeNote(index);
            return true;
        }
        return false;
    }

    private void removeNote(int index){

        notes.remove(index);
        listModel.removeElementAt(index);
        headers.setSelectedIndex(0);
        headers.ensureIndexIsVisible(0);
    }

    public String getSelectedNote() {
        return notes.size() > 0 ? notes.get(headers.getSelectedIndex()) : "";
    }

    public void setListListener(MouseAdapter ma) {
        headers.addMouseListener(ma);
    }


    class DeleteNoteDialog extends JDialog{

        private boolean resultDialog;

        public boolean isOK(){
            return resultDialog;
        }

        DeleteNoteDialog(){
            //super(component);
            setSize(500, 200);
            setLocationRelativeTo(null);
            setModalityType(ModalityType.TOOLKIT_MODAL);
            setUndecorated(true);

            setLayout(new BorderLayout());
            JLabel label = new JLabel("Delete this note?");
            label.setFont(Service.globalFont);
            label.setPreferredSize(new Dimension(500, 100));

            JPanel labelPanel = new JPanel(new FlowLayout());
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.setPreferredSize(new Dimension(500, 100));
            labelPanel.add(label);

            FlatButton ok = new FlatButton("OkBut.png");
            FlatButton no = new FlatButton("NoBut.png");

            ok.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    resultDialog = true;
                    DeleteNoteDialog.this.setVisible(false);
                }
            });

            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        resultDialog = true;
                        DeleteNoteDialog.this.setVisible(false);
                    }
                    else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                        resultDialog = false;
                        DeleteNoteDialog.this.setVisible(false);
                    }
                }
            });

            no.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    resultDialog = false;
                    DeleteNoteDialog.this.setVisible(false);
                }
            });

            JPanel butPanel = new JPanel(new FlowLayout());

            butPanel.add(ok);
            butPanel.add(no);

            add(labelPanel, BorderLayout.CENTER);
            add(butPanel, BorderLayout.SOUTH);
            setVisible(true);
        }
    }
}

class MyScrollbarUI extends MetalScrollBarUI {

    private JButton b = new JButton() {

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }

    };

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle tb ) {

        g.setColor( Color.LIGHT_GRAY );
        g.fillRect( tb.x +10 , tb.y, tb.width + 10, tb.height );
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle tb) {
        g.setColor( Color.WHITE );
        g.fillRect(tb.x, tb.y, tb.width, tb.height);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return b;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return b;
    }
}
