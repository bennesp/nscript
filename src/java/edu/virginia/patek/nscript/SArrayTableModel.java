/* ----------------------------------------------------------------------------
 * NSCRIPT
 * version 1.0.2
 * Author:              Enrique Campos-Nanez
 * Contact information: Dr. Stephen D. Patek
 *                      Department of Systems and Information Engineering
 *                      101-C Olsson Hall
 *                      Charlottesville, VA 22904
 *                      University of Virginia
 *                      e-mail: patek@virginia.edu
 * ------------------------------------------------------------------------- */

package edu.virginia.patek.nscript;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

import edu.virginia.patek.nscript.NSWorld;

/** Implements the AbstractTableModel to display the index Arrays that
 *  are part of the simulation.
 */
public class SArrayTableModel extends AbstractTableModel {
  /** A reference to the simulation model. */
  NSWorld M;

  /** Constructor the stores a reference to the simulation model.
   *  @param inM a reference to the current simulation model. */
  public SArrayTableModel( NSWorld inM )
  {
    M = inM;
  }

  /** Obtains the number of rows in the table, which corresponds to
   *  number of index Array elements.
   *  @return the number of arrays in the current model. */
  public int getRowCount()
  {
    if (M==null)
      return 0;
    else
      return M . getArrayCount();
  }

  /** Obtains the number of columns of the table (two in this case).
   *  @return 2 if the model has array elements, 0 otherwise. */
  public int getColumnCount()
  {
    if (M==null)
      return 0;
    else
      return 2;
  }

  /** Obtains the value stored at a given position.
   *  @param row the row position in the table.
   *  @param col the col position in the table.
   *  @return a string with element. */
  public Object getValueAt( int row, int col )
  {
    if (M == null)
      return new String("");

    if (col == 0) {
      return M . getArray( row ) . name;
    } else {
      return Integer.toString(M . getArray( row ) . elements);
    }
  }

  /** Sets the value at a given position in the table.
   *  @param value the new value for the object, stored as a String.
   *  @param row the row position of the table where the update applies.
   *  @param column the column of the table where the update applies. */
  public void setValueAt( Object value, int row, int column )
  {
    if ( M == null)
      return;

    if (column==0)
      M . getArray( row ) . name = value . toString();
    else
      M . getArray( row ) . elements = Integer.parseInt( value.toString() );
  }

  /** Returns the column headers. In this case, the first header will
   *  be 'Index Name', and the second 'Size'.
   *
   *  @param columnIndex the index of the column of interest. */
  public String getColumnName( int columnIndex )
  {
    if (columnIndex==0)
      return "Index Name";
    else
      return "Size";
  }

  /** Establishes if a given cell is editable.
   *  @param rowIndex the row position of the cell.
   *  @param columnIndex the column position of the cell.
   *  @return true if the cell should be editable, false otherwise. */
  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return true;
  }
}
