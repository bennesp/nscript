/*
 * This source file is part of NScript, released under BSD-modern.
 *
 * Copyright (C) 2000-2001 Enrique Campos-Nanez
 * Copyright (C) 2012 Stefano Sanfilippo
 *
 * See README.* at top level for copying, contacts, history and notes.
 */
package edu.virginia.patek.nscript;

import java.util.*;
import java.util.logging.Logger;

/**
 * Defines storage for the elements in a simulation. Stores objects, as well as
 * arrays (index definitions).
 */
public class NSWorld extends Object {

    /**
     * The objects that are part of a simulation script are stored here.
     */
    ArrayList<NSObject> objects;
    /**
     * The arrays that are part of the simulation script are stored here.
     */
    ArrayList<NSArray> arrays;
//   /** Iterates over the list of objects for purposes of searching, saving, etc. */
//   Iterator objIter;

    /**
     * Only constructor requires the 'environment' object, which contains the
     * default values for any simulation script.
     *
     * @param inEnvironment the ns environment object. See the
     * bin/settings/environment for the exact definition of this object.
     */
    public NSWorld(NSObject inEnvironment) {
        // For convenience, the environment is always at index 0, and cannot be deleted.
        objects = new ArrayList<NSObject>();
        addObject(inEnvironment);
        arrays = new ArrayList<NSArray>();
    }

    /**
     * Returns the ns environment for the simulation.
     *
     * @return the ns environment object.
     */
    public NSObject getEnvironment() {
        return objects.get(0);
    }

    /**
     * Adds a new index array. Remember an array is defined by a name (string),
     * and a number.
     *
     * @param inArrayName a string containing the name of the new array.
     * @param inNumberOfElements the number of elements of the array.
     */
    public void addArray(String inArrayName, int inNumberOfElements) {
        arrays.add(new NSArray(inArrayName, inNumberOfElements));
    }

    /**
     * Removes the array stored at a specified position. Notice that if the
     * position is incorrect no notification is done.
     *
     * @param inIndex the 0-based position of the array to remove.
     */
    public void removeArray(int inIndex) {
        if (inIndex >= 0 && inIndex < arrays.size()) {
            arrays.remove(inIndex);
        }
    }

    /**
     * Removes all the arrays stored in the current model. It is used when the
     * user selects 'New' option of the 'File' menuu.
     */
    public void removeAllArrays() {
        while (arrays.size() > 0) {
            arrays.remove(0);
        }
    }

    /**
     * Returns the array stored at specific position. Notice that if the
     * requested index is incorrect, an error message will be generated at the
     * standard output, and a null object will be returned.
     *
     * @param inIndex the 0-based position of the array.
     * @return the requested array if 0<=inIndex<=NoOfArrays-1, Null otherwise.
     */
    public NSArray getArray(int inIndex) {
        if (inIndex >= 0 && inIndex < arrays.size()) {
            return arrays.get(inIndex);
        } else {
            LOG.severe(Messages.tr("array_out_of_bonds"));
            return null;
        }
    }

    /**
     * Returns the number of arrays stored in the model.
     *
     * @return the number of arrays.
     */
    public int getArrayCount() {
        return arrays.size();
    }

    /**
     * Verifies that a given name is stil valid (not used by any other object.
     *
     * @param theName the name to be verified.
     * @return true if the name is not taken by any of the existing object,
     * false otherwise.
     */
    public boolean isValidName(String theName) {
        NSObject o;
        Iterator<NSObject> i = objects.iterator();
        while (i.hasNext()) {
            o = i.next();
            if (o.getName().equals(theName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a new object to the model.
     *
     * @param newObject the object to be stored.
     * @return true if the object was correctly stored, false otherwise.
     */
    public boolean addObject(NSObject newObject) {
        return (objects.add(newObject));
    }

    /**
     * Counts the number of objects in the current simulation script.
     *
     * @return the number of simulation objects.
     */
    public int getObjectCount() {
        return objects.size();
    }

    /**
     * Obtains an object using its name, Null if the name is not found.
     *
     * @param theName a string with the name of the object being looked.
     * @return the object if the string was found, NULL otherwise.
     */
    public NSObject getObject(String theName) {
        NSObject o;
        Iterator<NSObject> i = objects.iterator();

        while (i.hasNext()) {
            o = i.next();
            if (o.getName().equals(theName)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Returns the i-th object in the simulation script. Notice that a bad
     * position returns a Null object with no warnings.
     *
     * @param index the 0-based position of the object.
     * @return the NSObject if the index was appropiate, null otherwise.
     */
    public NSObject getObject(int index) {
        if (index >= 0 && index < objects.size()) {
            return objects.get(index);
        } else {
            return null;
        }
    }

    /**
     * Returns the name of the environment variable.
     *
     * @return the name of the model's environment. (Unused).
     */
    public String toString(int dos) {
        return getEnvironment().getName();
    }
    private static final Logger LOG = Logger.getLogger(NSWorld.class.getName());
}
