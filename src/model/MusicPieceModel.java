package model;

import java.util.ArrayList;

/**
 * An interface representing a viewModel of music. INVARIANT: there exists at most 1 note starting
 * any given pitch and beat INVARIANT: all durations are non-null and >= 1 This interface follows
 * the 'fluent interface' pattern.
 */
public interface MusicPieceModel {

    /**
     * Overlays the input over this viewModel and returns this The result is a composite viewModel
     * that will be equivalent to both played simultaneously. This is equivalent to insert(viewModel,
     * 0)
     *
     * @return this
     */
    void playSimultaneously(MusicPieceModel piece);

    /**
     * Appends the input to the end of this viewModel and returns this. This is equivalent to
     * insert(viewModel, this.getLength())
     *
     * @return this
     */
    void playConsecutively(MusicPieceModel piece);

    /**
     * Inserts the new viewModel at the given point. Conflicting notes are resolved in favor of the
     * longest note.
     *
     * @return this
     * @throws IllegalArgumentException if beat < 0
     */
    void insert(MusicPieceModel piece, int beat);

    /**
     * Adds a new note to this viewModel
     *
     * @param note the note to add
     * @return this
     */
    void addNote(MusicNote note);


    /**
     * Removes the given note from this viewModel
     *
     * @param note the note to remove
     * @return this
     * @throws IllegalArgumentException if the note is not in the viewModel
     */
    void removeNote(MusicNote note);

    /**
     * change the start time of a note
     *
     * @param note the given note
     * @param i    number of changing start time
     */
    void changeNoteStartTime(MusicNote note, int i);


    /**
     * change the note's location
     *
     * @param note      the given note
     * @param halfsteps number of changing the Note pitch location
     */
    void changePitch(MusicNote note, int halfsteps);

    /**
     * Transposes this viewModel by a given number of halfsteps
     *
     * @param halfsteps the number of half steps to changePitch the viewModel by
     * @return this
     */
    void changePitch(int halfsteps);


    /**
     * change the duration of a note
     *
     * @param note the given note
     * @param i    number of changing the duration
     */
    void changeNoteDuration(MusicNote note, int i);

    /**
     * @return the number of beats until the last note ends
     */
    int getLength();

    /**
     * @return the lowest pitch in the viewModel
     */
    Note lowestPitch();

    /**
     * @return the highest pitch in the viewModel
     */
    Note highestPitch();


    /**
     * Gets the notes at a given beat
     *
     * @return a mapping from pitches to the duration to play them
     */
    ArrayList<MusicNote> getNotesAt(int beat);

    /**
     * Gets a list containing all of the notes in this piece. Note: to get the notes in a particular
     * range of beats, {@link MusicPieceModel getNotesAt} should be used for greater efficiency.
     *
     * @return a list containing all of the notes in this piece
     */
    ArrayList<MusicNote> getNotes();


    /**
     * @return the number of microseconds per beat in this viewModel
     */
    int getTempo();

    /**
     * @param tempo The speed, in microseconds per beat
     * @return this
     */
    void setTempo(int tempo);

}
