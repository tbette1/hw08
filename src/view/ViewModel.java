package view;

import controller.BeatController;
import model.MusicNote;
import model.Note;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
/**
 * A ViewModel holds all state that a view might request. This includes the piece
 * that the view is supposed to render. This indirection both serves to isolate
 * the view from any program state and prevents it from modifying the model.
 */
public interface ViewModel extends BeatListener {

    /**
     * Should delegate to an internal
     * @return the length of the viewModel
     */
    int getLength();

    /**
     * Should delegate to an internal
     * @return the highestPitch of the viewModel
     */
    Note highestPitch();

    /**
     * Should delegate to an internal
     * @return the lowestPitch of the viewModel
     */
    Note lowestPitch();

    /**
     * Should delegate to an internal
     * @return a list of all notes at the given beat, ordered by pitch and start time.
     */
    ArrayList<MusicNote> getNotesAt(int i);

    /**
     * @return the first beat that the view should render
     */
    int getViewStart();

    /**
     * Should delegate to an internal
     * @return the currentBeat of the inner model
     */
    int getCurrentBeat();

    /**
     * Adds update callbacks to the viewmodel so that it can request that
     * the view updates when data changes.
     * @param playUpdate a method to call when the current note changes
     * @param viewUpdate a method to call when a view property changes
     */
    void registerUpdates(Consumer<BeatController> playUpdate, Runnable viewUpdate);

    /**
     * Should delegate to an internal
     * @return the tempo of the model
     */
    int getTempo();

    /**
     * get the top of the view, the highest pitch that should be rendered
     * @return the top of the view
     */
    int getViewTop();

    /**
     * Gets the last point in the piece selected by the user.
     * The X coordinate is the beat and
     * the Y coordinate is the pitch
     * @return the selected point
     */
    Point getSelectedPoint();

    /**
     * Gets the note currently held by the controller for manipulation.
     * Used for dragging notes.
     * @return the note currently held by the controller
     */
    MusicNote getHeldNote();

    /**
     * Gets a list of all notes in this model.
     */
    ArrayList<Note> getAllNotesInRange();
}
