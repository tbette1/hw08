package util;

import model.Attribute;
import model.Model;
import model.MusicPlayer;
import model.NoteImpl;

import java.util.ArrayList;

/**
 * Builder object for Tracks.
 */
public final class TrackBuilder implements CompositionBuilder<Model> {
    int tempo;
    ArrayList<NoteImpl> buildNotes;


    public TrackBuilder(){
        buildNotes = new ArrayList<NoteImpl>();
    }

    @Override
    public CompositionBuilder<Model> setTempo(int temp) {
        this.tempo = temp;
        return this;
    }

    @Override
    public CompositionBuilder<Model> addNote(int start, int end,
                                            int instrument, int pitch, int volume) {
        NoteImpl n = NoteImpl.parseNoteFromInt(pitch, instrument, volume);
        if (!buildNotes.contains(n)) {
            buildNotes.add(n);
        }
        NoteImpl toChange = buildNotes.get(buildNotes.indexOf(n));
        while (toChange.getActions().size() - 1 < start) {
            toChange.getActions().add(Attribute.Rest);
        }
        toChange.getActions().set(start, Attribute.Play);
        for (int i = start + 1; i < end; i++) {
            if (toChange.getActions().size() - 1 < i) {
                toChange.getActions().add(Attribute.Rest);
            }
            toChange.getActions().set(i, Attribute.Sustain);
        }

        return this;
    }

    @Override
    public Model build() {
        return new MusicPlayer(buildNotes, tempo);
    }
}