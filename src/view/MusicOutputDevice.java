package view;


import controller.BeatController;

import java.io.IOException;

/**
 * Represents a way to output music. The view component of a music application.
 */
public interface MusicOutputDevice {

    /**
     * Renders the given viewModel in this view
     *
     * @param timer the beat controller
     */
    void renderModel(BeatController timer) throws IOException;


    /**
     * Perform any necessary setup that this view requires
     */
    void initialize();

}