package model;


import java.util.ArrayList;
import java.util.Arrays;

public final class NoteImpl implements Note {
    Pitch pitch;
    int octave;
    private int instrument;
    private int volume;
    ArrayList<Attribute> actions;

    /**
     * Constructs a new NoteImpl object
     * @param p the pitch of this note
     * @param octave the octave of this note
     */
    public NoteImpl(Pitch p, int octave, int instrument, int volume) throws IllegalArgumentException {
        if (octave < 0) {
            throw new IllegalArgumentException("Octaves cannot be less than 0.");
        }

        this.pitch = p;
        this.octave = octave;
        this.instrument = instrument;
        this.volume = volume;

        actions = new ArrayList<Attribute>();
        for (int i = 0; i < 32; i++) {
            actions.add(Attribute.Rest);
        }
    }

    public Pitch getPitch() {
        return this.pitch;
    }

    /**
     * @param o object to compare to this note.
     * @return true if this note is equal to the given note,
     *         returns false otherwise.
     */
    public boolean equals(Object o) {
        if (o instanceof NoteImpl) {
            return (this.pitch == ((NoteImpl) o).pitch) && (this.octave == ((NoteImpl) o).octave) &&
                    (this.instrument == ((NoteImpl) o).instrument);
        }
        return false;
    }

    /**
     * @return the hashCode for this NoteImpl.
     */
    public int hashCode() {
        return ((this.octave - 1) * 8) +
                this.pitch.getVal() + this.instrument * 1000;
    }

    /**
     * @return a string representation of this note
     */
    public String toString() {
        String out = this.pitch.toString() + this.octave;
        if (out.length() == 2) {
            return "  " + out + " ";
        }
        else {
            return " " + out + " ";
        }
    }

    /**
     * Finds the start beat of the note being played at the given beat.
     * @param beat any beat during a play of the given
     */
    public static int getStartOfNote(NoteImpl n, int beat) {
        if (n.actions.get(beat).equals(Attribute.Rest)) {
            throw new IllegalArgumentException("NoteImpl is not playing at the given beat.");
        }
        else if (n.actions.get(beat).equals(Attribute.Play)) {
            return beat;
        }
        int i = beat;
        while (!n.actions.get(i).equals(Attribute.Play)) {
            i--;
        }
        return i;
    }

    /**
     * Finds the end beat of the note being played at the given beat.
     * @param beat any beat during a play of the given
     */
    public static int getEndOfNote(NoteImpl n, int beat) {
        if (n.actions.get(beat).equals(Attribute.Rest)) {
            throw new IllegalArgumentException("NoteImpl is not playing at the given beat.");
        }
        int i = beat;
        while (n.actions.get(i).equals(Attribute.Sustain)) {
            i++;
        }
        return i - 1;
    }

    /**
     * Parses a note from the given value.
     * @param pitch [0,127] where 60 represents C4.
     * @param instrument the instrument of this note
     */
    public static NoteImpl parseNoteFromInt(int pitch, int instrument, int volume) {
        if (pitch < 0 || pitch > 127) {
            throw new IllegalArgumentException("Out of range.");
        }
        int pitchVal = pitch % 12;
        int octave = (pitch / 12) - 1;

        return new NoteImpl(Pitch.pitchFromVal(pitchVal), octave, instrument, volume);
    }

    /**
     * @return true if this note's actions have anything other than rests, returns
     * false otherwise.
     */
    public boolean plays() {
        for (Attribute a : actions) {
            if (!a.equals(Attribute.Rest)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the beat number where this note plays for the last time.
     */
    public int getPlayLength() {
        if (!this.plays()) {
            return 0;
        }
        int i = this.actions.size() - 1;
        while (this.actions.get(i).equals(Attribute.Rest) && i > 0) {
            i--;
        }
        return i + 1;
    }

    /**
     * @return a new note, a half step up from this one.
     */
    public NoteImpl getHalfStepUp() {
        int o = -1;
        if (this.pitch.halfStepUp().equals(Pitch.C)) {
            o = this.octave + 1;
        }
        else {
            o = this.octave;
        }
        return new NoteImpl(this.pitch.halfStepUp(), o, this.instrument, this.volume);
    }

    /**
     * @return the beat on which this note plays for the first time.
     */
    public int getFirstPlay() {
        if (!this.plays()) {
            return -1;
        }
        int i = 0;
        if (this.actions.get(i).equals(Attribute.Rest)) {
            i++;
        }
        return i;
    }

    /**
     * @return this note's list of actions
     */
    public ArrayList<Attribute> getActions() {
        return this.actions;
    }

    /**
     * @return an ArrayList of ArrayLists of size 2, where the 0th element represents
     * the start beat of a play of this note, and the 1st element represents the duration.
     * Every play of this note will be represented in its own ArrayList, held in the
     * larger ArrayList.
     */
    public ArrayList<ArrayList<Integer>> getPlays() {
        ArrayList<ArrayList<Integer>> notesPlayed = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).equals(Attribute.Play)) {
                try {
                    notesPlayed.add(new ArrayList<Integer>(Arrays.asList(i, noteDuration(i))));
                }
                catch (IllegalArgumentException e) {
                    notesPlayed.add(new ArrayList<Integer>(Arrays.asList(i, 1)));
                }
            }
        }
        return notesPlayed;
    }

    /**
     *
     * @param beat beat on which the played note starts
     * @return the duration of that note from the play.
     */
    public int noteDuration(int beat) {
        if (beat >= this.actions.size()) {
            throw new IllegalArgumentException("End of actions list.");
        }
        if (!this.actions.get(beat).equals(Attribute.Play)) {
            throw new IllegalArgumentException("NoteImpl does not play at this beat.");
        }
        int duration = 1;
        int index = beat + 1;
        while (index < this.actions.size()) {
            duration++;
            index++;
        }
        return duration;
    }

    /**
     * returns this notes instrument
     * @return; instrument field
     */
    public int getInstrument(){
        return this.instrument;
    }

    /**
     * returns this notes volume
     * @return: volume field
     */
    public int getVolume(){
        return this.volume;
    }

    /**
     * @return this note's octave field
     */
    public int getOctave() { return this.octave; }

    /**
     * @return this note's octave field
     */
    public int getPitchVal() { return this.pitch.getVal(); }

    /**
     * method to get midi value
     */
    public int midiValue(){
        return (this.octave + 1) * 12 + this.pitch.ordinal();
    }
}
