package view;


import model.ModelAdapter;
import model.MusicNote;

import java.awt.*;
/**
 * Create a Mutable View Model
 */
public interface MutableViewModel extends ViewModel{
    /**
     * set the currentBeat on the ViewModel
     * @param currentBeat
     */
    void setCurrentBeat(int currentBeat);

    /**
     * reset the view Start
     * @param viewStart the given view start that we want
     */
    void setViewStart(int viewStart);

    /**
     * reset the view top
     * @param value the given view top that we want
     */
    void setViewTop(int value);

    /**
     * Sets the point (in pixels) that is currently selected
     */
    void setSelectedPoint(Point point);

    /**
     * remove the given note from our piece
     * @param n the given note
     */
    void removeNote(MusicNote n);

    /**
     * change the given note duration
     * @param n the given note
     * @param i the amount of duration that you want to change
     */
    void changeNoteDuration(MusicNote n, int i);

    /**
     * add a new note to our piece
     * @param n the given note
     */
    void addNote(MusicNote n);

    /**
     * set the heldnote while dragging a note
     * @param n the given note
     */
    void setHeldNote(MusicNote n);

    /**
     * Creates a new mutable viewmodel wrapping the given model.
     * @param piece the model to wrap
     * @return the new viewmodel
     */
    static MutableViewModel newViewModel(ModelAdapter piece) {
        return new ViewModelImpl(piece);
    }

}
