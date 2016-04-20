package controller;

import cs3500.music.model.NoteImpl;

import java.awt.event.InputEvent;

/**
 * Created by Elijah on 4/3/2016.
 */
public interface IGuiController extends Controller {
//
//    /**
//     * Assign the current view the mouse handler
//     */
//    void setMouseHandler(MouseListener ml);
//
//    /**
//     * Assign the current view the key handler
//     */
//    void setKeyHandler(KeyListener kl);


    /**
     * Returns the key that was pressed down (-1 if no key was selected)
     *
     * @return key unicode for key
     */
    int getPressed();



    /**
     * Adds a new note play to the model. NOTE: only adds a Play of one
     * count. To extend the note, call changeNoteEnd on the added note.
     * @param n the note to be added
     * @param startBeat the beat at which to add a play.
     */
    void addNote(NoteImpl n, int startBeat);

    /**
     * Removes a note play from the model.
     * @param n the note to be altered
     * @param beat a beat during the play to be removed
     */
    void removeNote(NoteImpl n, int beat);

    /**
     * Changes the start beat of the currently selected note to the given startpoint.
     * @param n the note to be altered
     * @param prevStart the original start of the play on this note
     * @param newStart the new desired start of the same play on this note
     */
    void changeNoteStart(NoteImpl n, int prevStart, int newStart);

    /**
     * Changes the end beat of the currently selected note to the given endpoint.
     * @param n the note to be altered
     * @param prevEnd the previous end of the play
     * @param newEnd the new desired end of the same play
     */
    void changeNoteEnd(NoteImpl n, int prevEnd, int newEnd);

    /**
     * Moves a note.
     * @param n the original note
     * @param prevStart original start of note play
     * @param newNote the new note to be played
     * @param newStart new start of note play
     */
    void moveNote(NoteImpl n, int prevStart, NoteImpl newNote, int newStart);

    /**
     * Sends a mock event to the InputHandler; for testing purposes.
     * @param type  helps delegate whether it's a key or mouse input
     * @param e     the key or mouse event to be passed
     */
    void mockEvent(String type, InputEvent e);

}
