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
import java.awt.Point;

/**
 * Defines an interface to be implemented by objects that want to be controlled
 * by the DMControl object for visual editing.
 */
public interface DMModel {

    /**
     * Defines the edition mode of the model to be of selection.
     */
    public static final int SELECT_MODE = 0;
    /**
     * Defines the edition mode of the model to be the creation of icons
     * (entities).
     */
    public static final int ICON_MODE = 1;
    /**
     * Defines the edition mode of the model to be the creation of relations.
     */
    public static final int RELATION_MODE = 2;

    /**
     * Obtain the current edition mode. Should return one of SELECT_MODE,
     * ICON_MODE, and RELATION_MODE
     *
     * @return int the edition mode.
     */
    public int getEditionMode();

    /**
     * Adds a 'node' object to its collection.
     *
     * @param r the dimension of the current drawing pane.
     * @param p the position of the new object.
     * @param unselectOthers a flag that tells if the model should unselect
     * other objects after adding the new one.
     */
    public void addSimpleObject(Dimension r, Point p, boolean unselectOthers);

    /**
     * Adds a 'relation' node.
     *
     * @param oFrom a reference to a 'node' object that marks the origin of the
     * relation.
     * @param oTo a reference to a 'node' object that marks the destination of
     * the relation.
     * @param unselectOthers
     */
    public void addRelationObject(DMObject oFrom, DMObject oTo, boolean unselectOthers);

    /**
     * To be called after modifying the model, so that all views dependent on it
     * render the model correctly.
     *
     * @param onTheFly if true, do not update properties table
     *                 (for progressive table update).
     */
    public void updateAllViews(boolean onTheFly);

    /**
     * Get the size of the model in number of objects stored.
     *
     * @return the number of objects in the model.
     */
    public int getSize();

    /**
     * Get the objects stored at a given position, assuming an array or list
     * order.
     *
     * @param index the position at which the desired object is stored.
     * @return the desired object if index is in the correct range, null
     * otherwise.
     */
    public DMObject getObjectAt(int index);

    /**
     * Remove currently selected objects.
     */
    public void removeSelected();

    /**
     * Raise a flag if the model has been modified and requires saving.
     *
     * @param dirtyState
     */
    public void setDirty(boolean dirtyState);
}
