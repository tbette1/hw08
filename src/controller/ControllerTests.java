
package controller;

import cs3500.music.model.Attribute;
import cs3500.music.model.MusicPlayer;
import cs3500.music.model.NoteImpl;
import cs3500.music.model.Pitch;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GraphicView;
import cs3500.music.view.MidiView;
import org.junit.Test;

import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ControllerTests {

    @Test (expected = NullPointerException.class)
    public void testConstructNonNull1() {
        MusicPlayer m = new MusicPlayer();
        GuiController g = new GuiController(m, null, "run");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructNonNull2() {
        GuiController g = new GuiController(null, null, "run");
    }


    @Test
    public void testGuiControllerAddNote() throws IOException{
        MusicPlayer m = new MusicPlayer();
        CompositeView cv = new CompositeView(null, null);
        try {
            cv = new CompositeView(new MidiView(m), new GraphicView(m));
        }
        catch (MidiUnavailableException e) {

        }

        GuiController g = new GuiController(m, cv, "run");
        NoteImpl midC = new NoteImpl(Pitch.C, 4, 0, 0);
        NoteImpl e4 = new NoteImpl(Pitch.E, 4, 0, 0);
        g.addNote(midC, 0);
        g.addNote(midC, 1);
        g.addNote(e4, 2);

        assertEquals(m.track.getLength(), 3);
        assertEquals(midC.plays(), true);
        assertEquals(e4.plays(), true);
        assertEquals(m.track.getNotes().size(), 2);
    }

    @Test
    public void testGuiControllerRemoveNote() {
        MusicPlayer m = new MusicPlayer();
        CompositeView cv = new CompositeView(null, null);
        try {
            cv = new CompositeView(new MidiView(m), new GraphicView(m));
        }
        catch (MidiUnavailableException e) {

        }

        GuiController g = new GuiController(m, cv, "run");
        NoteImpl midC = new NoteImpl(Pitch.C, 4, 0, 0);
        NoteImpl e4 = new NoteImpl(Pitch.E, 4, 0, 0);
        g.addNote(midC, 0);
        g.addNote(midC, 1);
        g.addNote(e4, 2);

        g.removeNote(e4, 2);

        assertEquals(m.track.getLength(), 2);
        assertEquals(midC.plays(), true);
        assertEquals(e4.plays(), false);
        assertEquals(m.track.getNotes().size(), 1);

    }

    @Test
    public void testGuiControllerChangeNoteStart() {
        MusicPlayer m = new MusicPlayer();
        CompositeView cv = new CompositeView(null, null);
        try {
            cv = new CompositeView(new MidiView(m), new GraphicView(m));
        }
        catch (MidiUnavailableException e) {

        }

        GuiController g = new GuiController(m, cv, "run");
        NoteImpl midC = new NoteImpl(Pitch.C, 4, 0, 0);
        NoteImpl e4 = new NoteImpl(Pitch.E, 4, 0, 0);
        g.addNote(midC, 2);
        g.addNote(midC, 3);
        g.addNote(e4, 4);

        g.changeNoteStart(midC, 2, 0);

        assertEquals(midC.getActions().get(0), Attribute.Play);
        assertEquals(midC.getActions().get(1), Attribute.Sustain);
        assertEquals(midC.getActions().get(2), Attribute.Sustain);
        assertEquals(midC.getActions().get(3), Attribute.Play);
        assertEquals(m.track.getLength(), 5);
        assertEquals(midC.plays(), true);
        assertEquals(e4.plays(), true);
        assertEquals(m.track.getNotes().size(), 2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testChangeStartOfNoteException() {
        MusicPlayer m = new MusicPlayer();
        CompositeView cv = new CompositeView(null, null);
        try {
            cv = new CompositeView(new MidiView(m), new GraphicView(m));
        }
        catch (MidiUnavailableException e) {

        }

        GuiController g = new GuiController(m, cv, "run");
        NoteImpl midC = new NoteImpl(Pitch.C, 4, 0, 0);
        NoteImpl e4 = new NoteImpl(Pitch.E, 4, 0, 0);
        g.addNote(midC, 2);
        g.addNote(midC, 3);
        g.addNote(e4, 4);

        g.changeNoteStart(midC, 1, 0);
    }

    @Test
    public void testGuiControllerChangeNoteEnd() {
        MusicPlayer m = new MusicPlayer();
        CompositeView cv = new CompositeView(null, null);
        try {
            cv = new CompositeView(new MidiView(m), new GraphicView(m));
        }
        catch (MidiUnavailableException e) {

        }

        GuiController g = new GuiController(m, cv, "run");
        NoteImpl midC = new NoteImpl(Pitch.C, 4, 0, 0);
        NoteImpl e4 = new NoteImpl(Pitch.E, 4, 0, 0);
        g.addNote(midC, 0);
        g.addNote(midC, 1);
        g.addNote(e4, 2);

        g.changeNoteEnd(e4, 2, 4);

        assertEquals(m.track.getLength(), 5);
        assertEquals(midC.plays(), true);
        assertEquals(e4.plays(), true);
        assertEquals(m.track.getNotes().size(), 2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testChangeEndOfNoteException() {
        MusicPlayer m = new MusicPlayer();
        CompositeView cv = new CompositeView(null, null);
        try {
            cv = new CompositeView(new MidiView(m), new GraphicView(m));
        }
        catch (MidiUnavailableException e) {

        }

        GuiController g = new GuiController(m, cv, "run");
        NoteImpl midC = new NoteImpl(Pitch.C, 4, 0, 0);
        NoteImpl e4 = new NoteImpl(Pitch.E, 4, 0, 0);
        g.addNote(midC, 2);
        g.addNote(midC, 3);
        g.addNote(e4, 4);

        g.changeNoteEnd(e4, 6, 8);
    }



    @Test
    public void testGuiControllerMoveNote() {

    }
}
