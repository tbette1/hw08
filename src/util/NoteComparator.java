package util;

import model.Note;

import java.util.Comparator;
/**
 * To  compare two notes.
 */
public final class NoteComparator implements Comparator<Note> {

    /*
     * Compares two notes.
     */
    public int compare(Note n1, Note n2) {
        if (n1.midiValue() - n2.midiValue() == 0) {
            return n1.getInstrument() - n2.getInstrument();
        }
        else {
            return n1.midiValue() - n2.midiValue();
        }
    }
}
