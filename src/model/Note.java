package model;

/**
 * Created by torybettencourt on 4/16/16.
 */
public interface Note {

    /**
     * @return the Pitch of this note.
     */
    Pitch getPitch();

    /**
     * @return the octave of this note.
     */
    int getOctave();

    /**
     * @return the instrument of this note (integer).
     */
    int getInstrument();

    /**
     * @return the volume of this note.
     */
    int getVolume();

    /**
     * @return a string value to display this note.
     */
    String toString();

    /**
     * @return a midiValue for this note.
     */
    int midiValue();

    /**
     * @return the length of the play at the given beat.
     */
    int noteDuration(int beat);


}
