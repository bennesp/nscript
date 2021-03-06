/*
 * This source file is part of NScript, released under BSD-modern.
 *
 * Copyright (C) 2000-2001 Enrique Campos-Nanez
 * Copyright (C) 2012,2014 Stefano Sanfilippo
 *
 * See README.* at top level for copying, contacts, history and notes.
 */
package org.esseks.nscript;

import java.awt.Component;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * A view that holds a list of the objects involved in a simulation. Not yet
 * implemented.
 */
public class NSWorldPane extends JLabel implements ListCellRenderer<String> {

    static final long serialVersionUID = 42L;

    /**
     * Default constructor.
     */
    public NSWorldPane() {
        super(Messages.tr("empty"));
        this.setOpaque(true);
    }

    /**
     * Class that implements the rendering of the cells (rows) in the world
     * view.
     *
     * @param list
     * @param cellHasFocus
     * @param index
     * @param value
     * @param isSelected
     * @return
     */
    @Override
    public Component getListCellRendererComponent(
            JList<? extends String> list,
            String value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        this.setText(value);

        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }

        return this;
    }
    private static final Logger LOG = Logger.getLogger(NSWorldPane.class.getName());
}
