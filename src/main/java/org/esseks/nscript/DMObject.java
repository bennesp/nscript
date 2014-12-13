/*
 * This source file is part of NScript, released under BSD-modern.
 *
 * Copyright (C) 2000-2001 Enrique Campos-Nanez
 * Copyright (C) 2012,2014 Stefano Sanfilippo
 *
 * See README.* at top level for copying, contacts, history and notes.
 */
package org.esseks.nscript;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Defines an interface to be implemented by any objects that wants to act as a
 * part of a model (stored in an object through the interface DMModel).
 */
public interface DMObject {

    /**
     * Function called when the objects needs to be draw in the screen.
     *
     * @param g environment for painting.
     * @param r dimension of the actual canvas.
     */
    public void drawSelf(Graphics g, Dimension r);

    /**
     * A function that needs to bee implemented so a model that will own an
     * object can query it to see if it is currently selected.
     *
     * @return <b>true</b> if the object is currently selected, <b>false</b>
     * otherwise.
     */
    public boolean isSelected();

    /**
     * Function to be implemented to set the state of the object as selected. If
     * isSelected() function is called after a <b>select()</b> statement a
     * <b>true</b> value is obtained.
     */
    public void select();

    /**
     * Function to be implemented to set the state of the object as unselected.
     * If isSelected() function is called after a <b>unselect()</b> statement a
     * <b>false</b> value is obtained.
     */
    public void unselect();

    /**
     * Function to be implemented to change the state of the object to the
     * opposite of the current value.
     */
    public void toggleSelect();

    /**
     * This function should be implemented by any object that requires editing.
     *
     * @param r the dimension of the canvas in which the object is draw.
     * @param p position of the mouse.
     * @return true if the object was 'hit' by the mouse.
     */
    public boolean isHit(Dimension r, Point p);

    /**
     * Similar to isHit, but in this case we are interested if the object is
     * contained in a rectangle with corners p1, p2.
     *
     * @param r
     * @param p1
     * @param p2
     * @return
     */
    public boolean isContained(Dimension r, Point p1, Point p2);

    /**
     * What exactly are you try
     *
     * @param r
     * @param byWhat
     */
    public void moveBy(Dimension r, Dimension byWhat);
}
