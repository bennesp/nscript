/*
 * This source file is part of NScript, released under BSD-modern.
 *
 * Copyright (C) 2000-2001 Enrique Campos-Nanez
 * Copyright (C) 2012,2014 Stefano Sanfilippo
 *
 * See README.* at top level for copying, contacts, history and notes.
 */
package org.esseks.nscript;

import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * Implements the AbstractTableModel to display the index Arrays that are part
 * of the simulation.
 */
public class SArrayTableModel extends AbstractTableModel {

    static final long serialVersionUID = 42L;
    /**
     * A reference to the simulation model.
     */
    private NSWorld M;

    /**
     * Constructor the stores a reference to the simulation model.
     *
     * @param inM a reference to the current simulation model.
     */
    public SArrayTableModel(NSWorld inM) {
        this.M = inM;
    }

    /**
     * Obtains the number of rows in the table, which corresponds to number of
     * index Array elements.
     *
     * @return the number of arrays in the current model.
     */
    @Override
    public int getRowCount() {
        if (this.M == null) {
            return 0;
        } else {
            return this.M.getArrayCount();
        }
    }

    /**
     * Obtains the number of columns of the table (two in this case).
     *
     * @return 2 if the model has array elements, 0 otherwise.
     */
    @Override
    public int getColumnCount() {
        if (this.M == null) {
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * Obtains the value stored at a given position.
     *
     * @param row the row position in the table.
     * @param col the col position in the table.
     * @return a string with element.
     */
    @Override
    public Object getValueAt(int row, int col) {
        if (this.M == null) {
            return "";
        }

        if (col == 0) {
            return this.M.getArray(row).getName();
        } else {
            return Integer.toString(this.M.getArray(row).getSize());
        }
    }

    /**
     * Sets the value at a given position in the table.
     *
     * @param value the new value for the object, stored as a String.
     * @param row the row position of the table where the update applies.
     * @param column the column of the table where the update applies.
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
        if (this.M == null) {
            return;
        }

        if (column == 0) {
            this.M.getArray(row).setName(value.toString());
        } else {
            this.M.getArray(row).setSize(Integer.parseInt(value.toString()));
        }
    }

    /**
     * Returns the column headers. In this case, the first header will be 'Index
     * Name', and the second 'Size'.
     *
     * @param columnIndex the index of the column of interest.
     * @return
     */
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) {
            return Messages.tr("index_name");
        } else {
            return Messages.tr("size");
        }
    }

    /**
     * Establishes if a given cell is editable.
     *
     * @param rowIndex the row position of the cell.
     * @param columnIndex the column position of the cell.
     * @return true if the cell should be editable, false otherwise.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    private static final Logger LOG = Logger.getLogger(SArrayTableModel.class.getName());
}
