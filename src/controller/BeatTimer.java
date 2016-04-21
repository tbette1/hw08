package controller;

import view.BeatListener;

import javax.swing.Timer;

public class BeatTimer implements BeatController {
    Timer t;
    BeatListener bl;

    public BeatTimer(Timer t) {
        this.t = t;
    }

    /**
     * Adds a listener that responds whenever this generator
     * produces a new beat.
     * @param l the listener to add
     * @param priority the priority of this listener
     */
    public void addBeatListener(BeatListener l, Priority priority) {
        bl = l;
    }

    /**
     * Removes a listener from this generator
     * so that it is no longer notified when this
     * porduces a new beat.
     * @param l the listener to remove
     */
    public void removeBeatListener(BeatListener l, Priority priority) {

    }

}
