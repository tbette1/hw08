package model;

import java.util.ArrayList;

public interface Model {

    /**
     * @return an ArrayList containing all Notes in this model.
     */
    ArrayList<Note> getAllNotes();

    /**
     * @return an ArrayList of Strings containing all Note names in this model.
     */
    ArrayList<String> getNoteNames();

    /**
     * @return the length of this model's song in beats.
     */
    int getLength();

    /**
     * @param n note to retrieve attributes from.
     * @return an ArrayList of integers (-1, 0, or 1s) representing all attributes
     *         of the given note by beat (the index of the value represents the beat
     *         on which this occurs).
     */
    ArrayList<Integer> getAttributesFromNote(Note n);

    /**
     * @return the tempo of this Model's song.
     */
    int getTempo();

    /**
     * Adds a note to this song.
     * @param n note to add.
     * @param duration the duration of this play.
     * @param beat the beat on which to start playing this note.
     */
    void addNote(Note n, int duration, int beat);

    /**
     * Deletes a note from this song.
     * @param n note to delete.
     * @param beats the beats at which to make this note rest.
     */
    void deleteNote(Note n, ArrayList<Integer> beats);

    /**
     * Lengthens the play of the given note by one sustain at the given beat.
     * @param n note to extend.
     * @param beat beat at which to add sustain.
     * @throws IllegalArgumentException if it is not possible to sustain a play at
     *         the given beat.
     */
    void addSustain(Note n, int beat);


}
