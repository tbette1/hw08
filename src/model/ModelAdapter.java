package model;

import java.util.ArrayList;

public class ModelAdapter implements MusicPieceModel {
    MusicPlayer m;

    public ModelAdapter(MusicPlayer model) {
        this.m = model;
    }

    /**
     * Returns this ModelAdapter's MusicPlayer.
     */
    public MusicPlayer getMusicPlayer() {
        return this.m;
    }

    /**
     * Copies this viewModel
     *
     * @return an identical copy of this viewModel
     */
    public MusicPieceModel copy() {
        return new ModelAdapter(this.m);
    }

    /**
     * Overlays the input over this viewModel and returns this The result is a composite viewModel
     * that will be equivalent to both played simultaneously. This is equivalent to insert(viewModel,
     * 0)
     *
     * @return this
     */
    public void playSimultaneously(MusicPieceModel piece) {

    }

    /**
     * Appends the input to the end of this viewModel and returns this. This is equivalent to
     * insert(viewModel, this.getLength())
     *
     * @return this
     */
    public void playConsecutively(MusicPieceModel piece) {

    }

    /**
     * Inserts the new viewModel at the given point. Conflicting notes are resolved in favor of the
     * longest note.
     *
     * @return this
     * @throws IllegalArgumentException if beat < 0
     */
    public void insert(MusicPieceModel piece, int beat) {

    }

    /**
     * Adds a new note to this viewModel
     *
     * @param note the note to add
     * @return this
     */
    public void addNote(MusicNote note) {
        m.addNote(note.getPitch(), note.getDuration(), note.getStartTime());
    }


    /**
     * Removes the given note from this viewModel
     *
     * @param note the note to remove
     * @return this
     * @throws IllegalArgumentException if the note is not in the viewModel
     */
    public void removeNote(MusicNote note) {
        ArrayList<Integer> beats = new ArrayList<Integer>();
        int end = note.getStartTime() + note.getDuration();
        for (int i = note.getStartTime(); i < end; i++) {
            beats.add(i);
        }

        m.deleteNote(note.getPitch(), beats);
    }

    /**
     * change the start time of a note
     *
     * @param note the given note
     * @param i    number of changing start time
     */
    public void changeNoteStartTime(MusicNote note, int i) {
        int newStart = note.getStartTime() + i;
        if (newStart < 0) {
            throw new IllegalArgumentException("Cannot start a note " +
                    "before the beginning of the piece.");
        }
        removeNote(note);
        note.addStartTime(i);
        addNote(note);
    }


    /**
     * change the note's location
     *
     * @param note      the given note
     * @param halfsteps number of changing the Note pitch location
     */
    public void changePitch(MusicNote note, int halfsteps) {
        removeNote(note);
        note.changePitch(halfsteps);
        addNote(note);
    }

    /**
     * Transposes this viewModel by a given number of halfsteps
     *
     * @param halfsteps the number of half steps to changePitch the viewModel by
     * @return this
     */
    public void changePitch(int halfsteps) {
        for (MusicNote n : this.getNotes()) {
            changePitch(n, halfsteps);
        }
    }


    /**
     * change the duration of a note
     *
     * @param note the given note
     * @param i    number of changing the duration
     */
    public void changeNoteDuration(MusicNote note, int i) {
        if (note.getDuration() + i <= 0) {
            throw new IllegalArgumentException("Cannot have a note " +
                    "duration of less than or equal to zero.");
        }
        removeNote(note);
        note.addNoteDuration(i);
        addNote(note);
    }

    /**
     * @return the number of beats until the last note ends
     */
    public int getLength() {
        return m.getLength();
    }

    /**
     * @return the lowest pitch in the viewModel
     */
    public Note lowestPitch() {
        return m.getAllNotes().get(m.getAllNotes().size() - 1);
    }

    /**
     * @return the highest pitch in the viewModel
     */
    public Note highestPitch() {
        return m.getAllNotes().get(0);
    }


    /**
     * Gets the notes at a given beat
     *
     * @return a mapping from pitches to the duration to play them
     */
    public ArrayList<MusicNote> getNotesAt(int beat) {
        return null;
    }

    /**
     * Gets a list containing all of the notes in this piece. Note: to get the notes in a particular
     * range of beats, {@link MusicPieceModel getNotesAt} should be used for greater efficiency.
     *
     * @return a list containing all of the notes in this piece
     */
    public ArrayList<MusicNote> getNotes() {
        ArrayList<MusicNote> notes = new ArrayList<MusicNote>();
        for (NoteImpl n : this.m.getNotes()) {
            notes.addAll(getNotesFrom(n));
        }
        return notes;
    }

    /**
     * Gets a list containing all of the notes with the given absolute pitch as a NoteImpl.
     * @param n to represent absolute pitch of the notes desired.
     * @return the list of all MusicNotes at that pitch.
     */
    private ArrayList<MusicNote> getNotesFrom(NoteImpl n) {
        ArrayList<MusicNote> notes = new ArrayList<MusicNote>();
        int duration = -1;
        int start = -1;
        for (int i = 0; i < n.actions.size(); i++) {
            if (n.actions.get(i).equals(Attribute.Play)) {
                start = i;
                duration = 1;
            }
            else if (n.actions.get(i).equals(Attribute.Sustain) && start != -1) {

                duration ++;
            }
            else if (n.actions.get(i).equals(Attribute.Rest) && duration != -1) {
                notes.add(new MusicNoteImpl(n.getPitch(), n.getOctave(), start,
                        duration, n.getInstrument(), n.getVolume()));
                duration = -1;
                start = -1;
            }
        }
        return notes;
    }


    /**
     * @return the number of microseconds per beat in this viewModel
     */
    public int getTempo() {
        return m.getTempo();
    }

    /**
     * @param tempo The speed, in microseconds per beat
     * @return this
     */
    @Override
    public void setTempo(int tempo) {
        m.setTempo(tempo);
    }
}
