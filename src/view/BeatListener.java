package view;
import controller.BeatController;

/**
 * A listener object that responds to beats sent by a BeatController
 */
public interface BeatListener {

    /**
     * Process the given beat, possibly adding or removing listeners from the generator.
     */
    void run(BeatController timer, int beat);

    /**
     * Temporarily stops this listener until it receives another call to run()
     */
    void stop();

}