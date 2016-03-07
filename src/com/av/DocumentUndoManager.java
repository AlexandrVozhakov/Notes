package com.av;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.undo.UndoManager;
import javax.swing.text.JTextComponent;
import javax.swing.text.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.awt.event.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.*;


public class DocumentUndoManager {

    private static DocumentUndoManager instance;
    private UndoActionListener listener;
    private HashMap items = new HashMap();
    private EditPopup popup;

    private DocumentUndoManager() {

        listener = new UndoActionListener();
        popup = new EditPopup();
    }

    public static DocumentUndoManager getInstance() {
        if (instance ==null) {
            instance = new DocumentUndoManager();
        }
        return instance;
    }


    public void registerDocumentHolder(JTextComponent documentHolder) {
        //создаем новый менеджер изменений документа
        UndoManager undo = new UndoManager();
        //запоминаем его для данного текстового поля
        items.put(documentHolder, undo);
        //получаем модель документа такстового поля
        Document doc = documentHolder.getDocument();
        //добавляем слушатель изменений документа
        doc.addUndoableEditListener(undo);
        //добавляем слушатель нажатий клавиш (он будет обрабатывать ctrl+z)
        documentHolder.addKeyListener(listener);
        //добавляем слушатель нажатий кнопок мыши (он обработает нажатие правой кнопки)
        documentHolder.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //если нажата правая кнопка мыши
                if (e.getButton()==MouseEvent.BUTTON3) {
                    //if (e.isPopupTrigger()) { // <-- в линуксе (Fedora Core 5, KDE 3.5.3) это не работает
                    //устанавливаем текстовый компонент для меню
                    popup.setDocumentHolder((JTextComponent) e.getSource());
                    //отображаем всплывающее меню
                    popup.show((JTextComponent)e.getSource(), e.getX(), e.getY());
                    //popup.show((JTextComponent)e.getSource(), 0, e.getY());
                }
            }
        });
    }

    /**
     * очищает историю изменений для текстовых компонент
     * @param documentHolders список компонент (из JTextComponent)
     */
    public void clearChangeHistory(List documentHolders) {
        for (Iterator iterator = documentHolders.iterator(); iterator.hasNext();) {
            JTextComponent textComponent = (JTextComponent) iterator.next();
            UndoManager manager = (UndoManager) items.get(textComponent);
            manager.discardAllEdits();
        }
    }


    class EditPopup extends JPopupMenu {


        private JMenuItem copy;
        private JMenuItem cut;
        private JMenuItem paste;
        private JMenuItem undo;
        private JTextComponent documentHolder = null;



        public EditPopup() {

            copy = new JMenuItem("Копировать");
            copy.setFont(new Font(null, Font.PLAIN, 19));
            copy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (documentHolder!=null) {
                        documentHolder.copy();
                    }
                }
            });

            cut = new JMenuItem("Вырезать");
            cut.setFont(new Font(null, Font.PLAIN, 19));
            cut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (documentHolder!=null) {
                        documentHolder.cut();
                    }
                }
            });

            paste = new JMenuItem("Вставить");
            paste.setFont(new Font(null, Font.PLAIN, 19));
            paste.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (documentHolder!=null) {
                        documentHolder.paste();
                    }
                }
            });

            undo = new JMenuItem("Отмена");
            undo.setFont(new Font(null, Font.PLAIN, 19));
            undo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (documentHolder!=null) {
                        UndoManager manager = (UndoManager) items.get(documentHolder);
                        if (manager.canUndo()) {
                            manager.undo();
                        }
                    }
                }
            });

            add(copy);
            add(cut);
            add(paste);
            addSeparator();
            add(undo);
            setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
            setPreferredSize(new Dimension(200, 170));
        }


        public void setDocumentHolder(JTextComponent documentHolder) {
            this.documentHolder = documentHolder;
            //устанавливаем доступность пунктов "копировать" и "вырезать"
            if (documentHolder.getSelectedText()!= null) {
                copy.setEnabled(true);
                cut.setEnabled(true);
            }
            else {
                copy.setEnabled(false);
                cut.setEnabled(false);
            }

            //устанавливаем доступность пункта "вставить"
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(this);
            DataFlavor flavor = DataFlavor.stringFlavor;
            if (contents!=null && contents.isDataFlavorSupported(flavor)) {
                paste.setEnabled(true);
            }
            else {
                paste.setEnabled(false);
            }

            //устанавливаем доступность пункта "отмена"
            UndoManager manager = (UndoManager) items.get(documentHolder);
            if (manager.canUndo()) {
                undo.setEnabled(true);
            }
            else {
                undo.setEnabled(false);
            }

        }

    }


    class UndoActionListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
                UndoManager manager = (UndoManager) items.get(e.getSource());
                if (manager.canUndo()) {
                    manager.undo();
                }
            }
        }
    }
}