package controller;

import view.BeatListener;

/**
 * A BeatGenerator is some object that over time will repeatedly
 * call its beat listeners with itself and the current beat.
 */
public interface BeatController {


    /**
     * Represents the priority of a BeatListener to a BeatController.
     * ALl listeners with {@link Priority FIRST} priority are called before
     * all listeners with {@link Priority LAST} priority.
     */
    enum Priority {
        FIRST, LAST
    }

    /**
     * Adds a listener that responds whenever this generator
     * produces a new beat.
     * @param l the listener to add
     * @param priority the priority of this listener
     */
    void addBeatListener(BeatListener l, Priority priority);

    /**
     * Removes a listener from this generator
     * so that it is no longer notified when this
     * porduces a new beat.
     * @param l the listener to remove
     */
    void removeBeatListener(BeatListener l, Priority priority);


}
