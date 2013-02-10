/*
 * This source file is part of NScript, released under BSD-modern.
 *
 * Copyright (C) 2000-2001 Enrique Campos-Nanez
 * Copyright (C) 2012 Stefano Sanfilippo
 *
 * See README.* at top level for copying, contacts, history and notes.
 */
package org.esseks.nscript;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Creates a panel that allows the user to modify the properties of an object.
 */
public class SObjectBrowser extends JPanel implements ActionListener {

    static final long serialVersionUID = 42L;
    /**
     * A reference to the simulation model.
     */
    private NSModel M;
    /**
     * The default button from the interface.
     */
    private JButton defaultBtn;
    /**
     * A label that describes the current object. Typically its class.
     */
    private JLabel description;
    /**
     * The name field, where the user sets and edits the name of the selected
     * object.
     */
    private JTextField nameField;
    /**
     * The name label. "Name: "
     */
    private JLabel name;
    /**
     * A drop down index containing the available indices + the 'None' entry.
     * Through this element, the user can specify if a given object is indexed
     * by a given array.
     */
    private JComboBox<NSArray> arrayIndex;
    /**
     * The attribute table, where the object properties are listed, and the user
     * can modify.
     */
    private JTable attrTable;
    /**
     * A reference to the current object being edited.
     */
    private NSObject o = null;

    /**
     * The default constructor receives a reference to the current simulation
     * model.
     *
     * @param inModel
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public SObjectBrowser(NSModel inModel) {
        super();
        M = inModel;
        name = new JLabel(Messages.tr("name"));
        nameField = new JTextField();
        nameField.addActionListener(this);
        description = new JLabel(Messages.tr("no_selection"));
        JPanel p = new JPanel(new GridLayout(1, 2));
        JPanel aiPanel = new JPanel(new BorderLayout());
        arrayIndex = new JComboBox<NSArray>();
        arrayIndex.addActionListener(this);
        // arrayIndex.addItemListener(this);
        aiPanel.add(arrayIndex, BorderLayout.CENTER);
        aiPanel.add(new JLabel(Messages.tr("indexed_by")), BorderLayout.WEST);
        p.add(name);
        p.add(nameField);
        JPanel p2 = new JPanel(new GridLayout(3, 1));
        p2.add(description);
        p2.add(p);
        p2.add(aiPanel);

        setLayout(new BorderLayout());
        add(p2, BorderLayout.NORTH);

        defaultBtn = new JButton(Messages.tr("use_defaults"));
        defaultBtn.addActionListener(this);

        JPanel p3 = new JPanel(new GridLayout(1, 2));
        p3.add(defaultBtn);

        add(p3, BorderLayout.SOUTH);

        // OK, the table
        attrTable = new JTable(new SObjectTableModel(null));
        attrTable.getSelectionModel().addListSelectionListener(new PropertiesUpdater());
        JScrollPane sp_at = new JScrollPane(attrTable);
        add(sp_at, BorderLayout.CENTER);
        setBorder(new EmptyBorder(2, 2, 2, 2));
    }

    /**
     * Provides an interface through which the program can advertise changes in
     * the simulation model.
     */
    public void selectionChanged() {
        NSRelation or;
        int i, oc;

        SObjectTableModel tm;
        oc = 0;
        for (i = 0; i < M.getSize(); i++) {
            if (M.getObjectAt(i).isSelected()) {
                oc++;
                o = (NSObject) M.getObjectAt(i);
            }
        }
        if (oc == 1) {
            nameField.setText(o.getName());
            if (o.getSnippet().isRelation()) {
                or = (NSRelation) o;
                description.setText(Messages.tr("relates") + " (" + or.getFrom().getName() + ", " + or.getTo().getName() + ")");
            } else {
                description.setText(Messages.tr("entity_class") + ": " + o.getSnippet().getName() + " : " + o.getSnippet().getBase());
            }
            // Prepare array list
            arrayIndex.removeAllItems();
            arrayIndex.addItem(new NSArray(Messages.tr("no_index"), 0));
            for (i = 0; i < M.getArrayCount(); i++) {
                arrayIndex.addItem(M.getArray(i));
            }
            if (o.getArrayIndex() >= 0) {
                arrayIndex.setSelectedIndex(o.getArrayIndex() + 1);
            }
        }
        if (oc > 1) {
            nameField.setText("");
            description.setText(Messages.tr("multiple_selection"));
            arrayIndex.setSelectedIndex(-1);
            o = null;
        }
        if (oc == 0) {
            nameField.setText("");
            description.setText(Messages.tr("no_selection"));
            arrayIndex.setSelectedIndex(-1);
            o = null;
        }
        tm = new SObjectTableModel(o);
        attrTable.setModel(tm);
        if (oc == 1) {
            attrTable.getColumnModel().getColumn(1).setCellEditor(new SObjectCellEditor(o));
        }
    }

    /**
     * Implements the ActionListener interface by responding to the different
     * actions the interface elements generate when manipulated by the user.
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (o == null) {
            return;
        }

        if (ae.getSource() == defaultBtn) {
            o.getSnippet().instantiateNSObject(o);
        }
        else if (ae.getSource() == nameField) {
            o.setName(nameField.getText());
        }

        M.updateAllViews(false);
        M.setDirty(true);
    }

    class PropertiesUpdater implements ListSelectionListener {
        /** Responds to changes in the selection of the list. */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && (o != null)) {
                for (int i = e.getFirstIndex(); i <= e.getLastIndex(); ++i) {
                    o.setAttribute(i, (String) attrTable.getValueAt(i, 1));
                }
                M.updateAllViews(true);
                M.setDirty(true);
            }
        }
    }

    private static final Logger LOG = Logger.getLogger(SObjectBrowser.class.getName());
}
