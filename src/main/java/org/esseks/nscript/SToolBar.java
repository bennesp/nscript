/*
 * This source file is part of NScript, released under BSD-modern.
 *
 * Copyright (C) 2000-2001 Enrique Campos-Nanez
 * Copyright (C) 2012,2014 Stefano Sanfilippo
 *
 * See README.* at top level for copying, contacts, history and notes.
 */
package org.esseks.nscript;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Implements the "ToolBar" element, where the libraries and its elements are
 * displayed, and where the user can select an object to create.
 */
public class SToolBar extends JPanel implements ActionListener {

    static final long serialVersionUID = 42L;
    /**
     * A reference to a Library Manager which stores libraries and their
     * information.
     */
    private TclLibraryManager libManager;
    /**
     * A tabbed pane. This interface elements contains a "tab" for each library
     * that is opened.
     */
    private JTabbedPane tabPane;
    /**
     * A label with a description of the current action.
     */
    private JLabel action;
    /**
     * A pane that knows how to display a library element (or class).
     */
    private NSIconPane nip;

    /**
     * Constructor that takes the parent frame, and a reference to the library
     * manager. Creates and initializes the interface elements.
     *
     * @param inFrame a reference to the main application frame.
     * @param inLibManager a referemce to the library manager.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public SToolBar(NScript inFrame, TclLibraryManager inLibManager) {
        super(new BorderLayout());
        this.libManager = inLibManager;
        this.action = new JLabel(Messages.tr("selection_tool"));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.action, BorderLayout.NORTH);
        JPanel panel2 = new JPanel(new BorderLayout());
        JButton b = new JButton(new ImageIcon(SToolBar.class.getResource("/pixmaps/select.png")));
        b.setPreferredSize(new Dimension(22, 22));
        b.addActionListener(this);
        panel2.add(b, BorderLayout.NORTH);
        panel.add(panel2, BorderLayout.WEST);
        this.tabPane = new JTabbedPane(SwingConstants.BOTTOM);
        panel.add(this.tabPane, BorderLayout.CENTER);
        this.nip = new NSIconPane(0, "NO ICON");
        this.add(panel, BorderLayout.CENTER);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    /**
     * Adds a new library to the interface by creating a new tabbed pane, and
     * configuring it to store that libraries classes.
     *
     * @param lib a reference to the library.
     */
    public void addLibraryPane(TclLibrary lib) {
        JList<TclSnippet> c = new JList<TclSnippet>(new STBListModel(lib));
        c.setCellRenderer(this.nip);
        JScrollPane sp = new JScrollPane(c);
        this.tabPane.addTab(lib.getTBName(), sp);
        this.action.setText(Messages.tr("select_an_object"));
    }

    /**
     * Returns a reference to the class currently selected by the user
     * (TclSnippet).
     *
     * @return the selected class of the currently active pane.
     */
    public TclSnippet getSelectedSnippet() {
        int libIndex, snippetIndex;

        libIndex = this.tabPane.getSelectedIndex();
        if (libIndex < 0) {
            return null;
        }

        // TODO find more readable solution (here and below)
        snippetIndex = ((JList<?>) (((JScrollPane) this.tabPane.getSelectedComponent()).getViewport().getView())).getSelectedIndex();

        if (snippetIndex < 0) {
            return null;
        } else {
            return this.libManager.getSnippet(libIndex, snippetIndex);
        }
    }

    /**
     * Clears the selection in the current pane. This is a hack in response of
     * the user selecting the "Select" tool.
     */
    public void clearSelection() {
        ((JList<?>) (((JScrollPane) this.tabPane.getSelectedComponent()).getViewport().getView())).clearSelection();
    }

    /**
     * Implements the ActionListener interface to respond to the "Select"
     * button.
     *
     * @param ae an object containing the information about the current event.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.clearSelection();
    }

    /**
     * Class that implements the AbstractListModel behavior to render the
     * elements of a library as a list.
     */
    private static class STBListModel extends AbstractListModel<TclSnippet> {

        static final long serialVersionUID = 42L;
        /**
         * A reference to the library that corresponds to the list.
         */
        TclLibrary lib;

        /**
         * Constructor receives the reference to the library.
         *
         * @param inLib a reference to the library.
         */
        STBListModel(TclLibrary inLib) {
            this.lib = inLib;
        }

        /**
         * Obtains and passes the i-th element stored in the library.
         *
         * @param index the element of interest.
         * @return a reference to the object, in this case the TclSnippet object
         * that corresponds to the entry.
         */
        @Override
        public TclSnippet getElementAt(int index) {
            return this.lib.getSnippet(index);  //.getName();
        }

        /**
         * Obtains the size of a library in terms of number of classes
         * (TclSnippet).
         *
         * @return the number of classes in the library.
         */
        @Override
        public int getSize() {
            return this.lib.getSnippetCount();
        }
    }
    private static final Logger LOG = Logger.getLogger(SToolBar.class.getName());
}
