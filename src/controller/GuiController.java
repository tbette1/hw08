package controller;

import cs3500.music.model.MusicPlayer;
import cs3500.music.model.NoteImpl;
import cs3500.music.view.GuiView;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Controller for the music editor console UI. This takes user inputs and
 * acts on the view and then takes information from the view and shows it
 * to the user.
 */
public class GuiController implements IGuiController {
    //Data for GuiController class
    private MusicPlayer mp;  //get data from model
    private GuiView view;

    //State of view trackers
    int currBeat = 0;
    javax.swing.Timer timer;
    //Input
    private KeyboardHandler kh;
    private MouseHandler mh;
    //mouseInput mi;
    private int pressedKey = 0;
    // last click of mouse in absolute coordinates
    private Point lastClick;
    //boolean to start/stop music
    private boolean isitPaused;

    /**
     * Constructs a controller for playing the given game model, with the given input and
     * output for communicating with the user.
     * @param mp: the music to play
     * @param view: the view to draw
     * @param mode: the controllers current mode
     */

    public GuiController(MusicPlayer mp, GuiView view, String mode){
        this.mp = java.util.Objects.requireNonNull(mp);
        this.view = java.util.Objects.requireNonNull(view);
        this.timer = new javax.swing.Timer(this.view.getTempo() / 10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!view.tick()) {
                        isitPaused = true;
                        timer.stop();
                        System.out.println("Done!");
                    }
                } catch (InvalidMidiDataException ie) {
                    ie.printStackTrace();
                }

            }
        });
        //initial state
        this.isitPaused = true;
        this.lastClick = new Point(-1, -1);
        if(mode.equals("run")){
            kh = new KeyboardHandler(this);
            mh = new MouseHandler(this);
        }
        else{
            kh = new KeyboardHandler(this, new StringBuilder());
            mh = new MouseHandler (this, new StringBuilder());
        }
        // the actions dealing with the view//

        //make view go to start//
        //kh.addPressedEvent(KeyEvent.VK_HOME, view:: goToStart);
        // make view go to the end//
        //kh.addPressedEvent(KeyEvent.VK_END, view:://how ever we get to end in view );
        //scroll up with up arrow //
        // kh.addPressedEvent(KeyEvent.VK_UP, view:://how ever we scroll up in view );
        //scroll down with down arrow//
        // kh.addPressedEvent(KeyEvent.VK_DOWN, view:://how ever we scroll down in view );
        //scroll left with left arrow//
//        kh.addPressedEvent(KeyEvent.VK_LEFT, view:: windowScroll);
        //scroll right with right arrow//
        // kh.addPressedEvent(KeyEvent.VK_RIGHT, view:://how ever we scroll right in view );
        //Pause/Play the music ...."space"

        kh.addPressedEvent(KeyEvent.VK_SPACE, (Integer k) ->{
            if (this.isitPaused) {
                this.isitPaused = false;
                timer.start();
            }
            else if(!this.isitPaused){
                this.isitPaused = true;
                timer.stop();
            }

        });

        /**
         *  interacting with the model
         */

        //allow clicking to add note when this key is pressed//
        //if(kh.addPressedEvent(KeyEvent.VK_A
        kh.addPressedEvent(KeyEvent.VK_A, this :: pressChange);
        //allow clicking to delete note when this key is pressed//
        kh.addPressedEvent(KeyEvent.VK_D, this:: pressChange);
        /**interactions with mouse clicks. **/
        //if a is being pressed w/ click add note//
        mh.addClickedEvent(KeyEvent.VK_A, this::mouseAction);
        //if d is being pressed w/ click delete note
        mh.addClickedEvent(KeyEvent.VK_D,this::mouseAction);
        //if M is being pressed w/ click, move this note
        mh.addClickedEvent(KeyEvent.VK_M, this::mouseActions);
    }


    /**
     * Action peformed on mouse clicks with keys pressed
     * @param e: Mouse Event data
     */
    private void mouseAction(MouseEvent e){
        //performs proper action according to the key pressed.
        this.lastClick = e.getPoint();
        Point p = this.view.getGridCoors(lastClick);
        switch(pressedKey){
            case KeyEvent.VK_A:
                //this.addNote(this.view.getNoteFromIndex(p.y + 1), p.x);
                this.view.update();
                break;
            case KeyEvent.VK_D:
                System.out.println("D");
                //this.removeNote(this.view.getNoteFromIndex(p.y), p.x);
                this.view.update();
                break;
            default:
                break;
        }
        //returns selected note data to the default value
    }

    /**
     * Actions that require more than one step (ex modifying data)
     * @param e: mouse event data
     */

    private void mouseActions(MouseEvent e){
        Point firstClick = this.lastClick;
        this.lastClick = e.getPoint();
        Point secondClick = e.getPoint();
        //performs the proper action according to the key pressed
        switch (pressedKey){
            //do the case event for however you made movenote
            case KeyEvent.VK_M:
                //NoteImpl n1 = this.view.getNoteFromIndex(this.view.getGridCoors(firstClick).y);
                //NoteImpl n2 = this.view.getNoteFromIndex(this.view.getGridCoors(secondClick).y);
                int beat1 = this.view.getGridCoors(firstClick).x;
                int beat2 = this.view.getGridCoors(secondClick).x;
                //this.moveNote(n1, beat1, n2, beat2);
        }
    }


//    @Override
//    public void setMouseHandler(MouseListener ml) {
//        this.view.setMouseListener(ml);
//    }
//
//
//    @Override
//    public void setKeyHandler(KeyListener kl) {
//        this.view.setKeyListener(kl);
//    }

    @Override
    public int getPressed() {
        return this.pressedKey;
    }

    /**
     * Changes the current pressed key to a new value(if its the same, reset pressed key to 0)
     * @param
     */
    private void pressChange(Integer i) {
        if (this.pressedKey == i) {
            System.out.println("same");
            this.pressedKey = 0;
        } else {
            System.out.println("notsame");
            this.pressedKey = i;
            System.out.println(pressedKey);
        }
    }



    @Override
    public void addNote(NoteImpl n, int beat) {
        mp.addNote(n,beat,1);
    }

    @Override
    public void removeNote(NoteImpl n, int beat) {
        int start = NoteImpl.getStartOfNote(mp.getNote(n.getPitch(), n.getOctave(), n.getInstrument(), n.getVolume()), beat);
        int end = NoteImpl.getEndOfNote(mp.getNote(n.getPitch(), n.getOctave(), n.getInstrument(), n.getVolume()), beat);
        ArrayList<Integer> beats = new ArrayList<Integer>();
        for (int i = start; i <= end; i++) {
            beats.add(i);
        }

        mp.deleteNote(n, beats);
    }

    @Override
    public void changeNoteStart(NoteImpl n, int prevStart, int newStart) {
        if (NoteImpl.getEndOfNote(n, prevStart) != prevStart) {
            throw new IllegalArgumentException("NoteImpl does not start on the given beat!");
        }
        if (prevStart < newStart) {
            ArrayList<Integer> beats = new ArrayList<Integer>();
            for (int i = prevStart; i < newStart; i++) {
                beats.add(i);
            }
            mp.deleteNote(n, beats);
        }
        else if (prevStart > newStart) {
            int duration = NoteImpl.getEndOfNote(n, prevStart) - newStart;
            mp.addNote(n, duration, newStart);
        }
    }

    @Override
    public void changeNoteEnd(NoteImpl n, int prevEnd, int newEnd) {
        if (prevEnd < newEnd) {
            int i = prevEnd + 1;
            while (i <= newEnd) {
                this.mp.addSustain(n, i);
            }
        }
    }


    @Override
    public void moveNote(NoteImpl n, int prevStart, NoteImpl newNote, int newStart) {
        if(this.isitPaused){
            int start = NoteImpl.getStartOfNote(mp.getNote(n.getPitch(), n.getOctave(), n.getInstrument(), n.getVolume()), prevStart);
            int end = NoteImpl.getEndOfNote(mp.getNote(n.getPitch(), n.getOctave(), n.getInstrument(), n.getVolume()), prevStart);
            ArrayList<Integer> beats = new ArrayList<Integer>();
            for (int i = start; i <= end; i++) {
                beats.add(i);
            }
            mp.addNote(newNote, newStart, beats.size());
            mp.deleteNote(n, beats);
        }
    }

    @Override
    public void mockEvent(String type, InputEvent e) {
        if(type.equals("Key")){

        }

    }


    @Override
    public void run() {
        view.setKeyListener(kh);
        view.setMouseListener(mh);
        view.display();
        timer.start();
    }
}