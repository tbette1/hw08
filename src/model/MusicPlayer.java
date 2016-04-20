package model;
import util.NoteComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class MusicPlayer implements Model {
    private ArrayList<NoteImpl> notes;
    private int tempo;
    /**
     * Constructs a new MusicPlayer object with no track.
     */
    public MusicPlayer() {
        notes = new ArrayList<NoteImpl>();
        this.tempo = 0;
    }

    /**
     * Convenience constructor.
     */
    public MusicPlayer(ArrayList<NoteImpl> notes, int tempo) {
        this.notes = notes;
        this.tempo = tempo;
    }

    /**
     * Adds the given song to this MusicPlayer to start at the
     * given beat. If another song exists at that beat, the song
     * to be added is layered over it.
     * @param m song to be added
     * @param beat beat at which to start adding this song
     */
    public void addSong(MusicPlayer m, int beat) {
        ArrayList<NoteImpl> notesToAdd = m.getNotes();

        for (NoteImpl n : notesToAdd) {
            this.addNoteHelp(n, n.actions, beat);
        }

    }

    /**
     * Adds the given single note to this MusicPlayer.
     * @param n NoteImpl to be added
     * @param duration duration of note
     * @param beat beat on which to start playing this note
     */
    public void addNote(Note n, int duration, int beat) {
        ArrayList<Attribute> toAdd = new ArrayList<>();
        toAdd.add(Attribute.Play);
        for (int i = 0; i < duration - 1; i++) {
            toAdd.add(Attribute.Sustain);
        }
        addNoteHelp((NoteImpl) n, toAdd, beat);
    }


    /**
     * Helper for adding notes to this song.
     * @param n
     * @param attributes
     * @param startBeat
     */
    private void addNoteHelp(NoteImpl n, List<Attribute> attributes, int startBeat) {
        int ind = notes.indexOf(n);
        if (ind == -1) {
            ArrayList<Attribute> toAddActions = new ArrayList<Attribute>();
            for (int i = 0; i < startBeat; i++) {
                toAddActions.add(Attribute.Rest);
            }
            for (int i = 0; i < attributes.size(); i++) {
                toAddActions.add(startBeat + i, attributes.get(i));
            }
            n.actions = toAddActions;
            notes.add(n);
        }
        else {
            NoteImpl temp = notes.get(ind);
            int diff = startBeat - temp.getPlayLength();
            if (diff > 0) {
                for (int i = 0; i < diff; i++) {
                    temp.actions.add(Attribute.Rest);
                }
                for (int i = 0; i < attributes.size(); i++) {
                    temp.actions.add(attributes.get(i));
                }
            }
            else {
                for (int i = 0; i < attributes.size() - diff; i++) {
                    temp.actions.add(Attribute.Rest);
                }
                int i = startBeat;
                for (Attribute a : attributes) {
                    if (!a.equals(Attribute.Rest)) {
                        temp.actions.set(i, a);
                        i++;
                    }
                }
            }
        }
        Collections.sort(this.notes, new NoteComparator());
    }

    /**
     * Adds a sustain to the given beat iff the given beat is the beat directly
     * after a sustain or play of the same note. (If there is already a sustain at
     * that beat, does nothing.)
     * @param n note to extend
     * @param beat beat on which to add this sustain.
     */
    public void addSustain(Note n, int beat) {
        if (beat == 0) {
            throw new IllegalArgumentException("Cannot add sustain where there is no note head.");
        }
        if (n instanceof NoteImpl) {
            throw new IllegalArgumentException("requires NoteImpl type");
        }
        else if (beat >= ((NoteImpl) n).actions.size()) {
            while (((NoteImpl) n).actions.size() < beat) {
                ((NoteImpl) n).actions.add(Attribute.Rest);
            }
        }

        if (((NoteImpl) n).actions.get(beat - 1).equals(Attribute.Rest)) {
            throw new IllegalArgumentException("Cannot add sustain where there is no note head.");
        }
        else {
            ((NoteImpl) n).actions.add(beat, Attribute.Sustain);
        }
    }

    /**
     * @return this MusicPlayer's list of notes.
     */
    public ArrayList<NoteImpl> getNotes() {
        return this.notes;
    }

    /**
     * @return the note that in this track that is equivalent to the given note.
     */
    public NoteImpl getNote(Pitch p, int octave, int instrument, int volume) {
        NoteImpl n = new NoteImpl(p, octave, instrument, volume);
        int index = this.notes.indexOf(n);
        return this.notes.get(index);
    }

    /**
     * Deletes an instruction for this note.
     * If there is no instruction at this index,
     * throws an IllegalArgumentException.
     * @param beats beats on which this note is playing that
     *              are to be deleted.
     */
    @Override
    public void deleteNote(Note n, ArrayList<Integer> beats) {
        if (!(n instanceof NoteImpl)) {
            throw new IllegalArgumentException("Must use proper implementation of Note!");
        }

        int ind = notes.indexOf(n);
        if (ind == -1) {
            throw new IllegalArgumentException("The given note is not contained in this track.");
        }
        else {
            for (int i : beats) {
                notes.get(ind).actions.set(i, Attribute.Rest);
            }
            for (int i = 1; i < ((NoteImpl) n).actions.size(); i++) {
                if (((NoteImpl) n).actions.get(i).equals(Attribute.Sustain) &&
                        (((NoteImpl) n).actions.get(i - 1).equals(Attribute.Rest))) {
                    ((NoteImpl) n).actions.set(i, Attribute.Play);
                }
            }
            if (!((NoteImpl) n).plays()) {
                notes.remove(n);
            }
        }
    }

    /**
     * Returns all notes in range played by this Model's song.
     */
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> fullList = new ArrayList<Note>();
        if (this.notes.size() == 0) {
            return fullList;
        }

        for (int i = 0; i < this.notes.size() - 1; i++) {
            fullList.add(this.notes.get(i));
            int notesAway = new NoteComparator().compare(this.notes.get(i + 1), this.notes.get(i));
            NoteImpl last = this.notes.get(i);
            for (int j = 0; j < notesAway - 1; j++) {
                NoteImpl next = last.getHalfStepUp();
                fullList.add(next);
                last = next;
            }
        }
        fullList.add(this.notes.get(this.notes.size() - 1));
        return reverseList(fullList);
    }

    /**
     * @param notes list of notes.
     * @return a reversed copy of the given list.
     */
    private ArrayList<Note> reverseList(ArrayList<Note> notes) {
        ArrayList<Note> reversed = new ArrayList<Note>();
        for (Note n : notes) {
            reversed.add(0, n);
        }
        return reversed;
    }

    public ArrayList<String> getNoteNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (Note n : this.getAllNotes()) {
            names.add(n.toString());
        }
        return names;
    }

    public ArrayList<Integer> getAttributesFromNote(Note n) {
        ArrayList<Attribute> actions = ((NoteImpl) n).actions;
        ArrayList<Integer> newActions = new ArrayList<Integer>();
        for (Attribute a : actions) {
            if (a.equals(Attribute.Rest)) {
                newActions.add(0);
            }
            else if (a.equals(Attribute.Play)) {
                newActions.add(1);
            }
            else {
                newActions.add(-1);
            }
        }
        return newActions;
    }

    public int getNoteAttribute(NoteImpl n, int beat) {
        Attribute a = n.actions.get(beat);
        switch(a.name()) {
            case "Play":
                return 1;
            case "Rest":
                return 0;
            default :
                return -1;
        }
    }

    /**
     * gets the tempo of the song
     * @return: the tempo as an int
     */
    public int getTempo(){
        return this.tempo;
    }

    /**
     * Sets the tempo of this MusicPlayer to the given value.
     * @param tempo the new tempo
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    /**
     * @return the length of this song.
     */
    public int getLength() {
        int longest = 0;
        for (NoteImpl n : this.notes) {
            if (n.getPlayLength() > longest) {
                longest = n.getPlayLength();
            }
        }
        return longest;
    }

}