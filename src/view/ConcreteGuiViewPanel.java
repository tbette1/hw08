package view;


import model.MusicNote;
import model.Note;
import model.Pitch;
import util.NoteComparator;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that draws a viewModel on the screen
 */
class ConcreteGuiViewPanel extends JPanel {

    private final ViewModel viewModel;
    protected final static int GRIDSIZE = 18;
    protected final static int XOFFSET = 30;
    protected final static int YOFFSET = 15;


    /**
     * Construct a ConcreteGuiViewPanel
     *
     * @param viewModel the given viewModel
     */
    ConcreteGuiViewPanel(ViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.registerUpdates(t -> this.repaint(), this::repaint);

    }


    @Override
    public void paintComponent(Graphics g) {
        // Handle the default painting
        super.paintComponent(g);
        if (viewModel != null) {
            this.printAllNotes(g);
            this.printGrid(g);
            this.printBeatTimeLine(g);
            this.printPitchesLine(g);
            this.printCurrentBeatRedLine(g);
            this.printSelectionBox(g);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        if (viewModel != null)
            return new Dimension(viewModel.getLength() * GRIDSIZE + XOFFSET,
                    (viewModel.highestPitch().midiValue()
                            - viewModel.lowestPitch().midiValue()) * GRIDSIZE + YOFFSET);
        else return super.getPreferredSize();
    }


    /**
     * print the grid of the Gui View where the x coordinate is the beat and the y coordinate is the
     * pitch. columns are drawn every 4 beats.
     *
     * @param g the given graphics
     */
    private void printGrid(Graphics g) {
        int PitchRange = viewModel.highestPitch().midiValue() -
                viewModel.lowestPitch().midiValue() + 1;
        g.setColor(Color.black);
        //draw vertical lines
        for (int i = 0; i <= viewModel.getLength(); i++) {
            ((Graphics2D) g).setStroke(new BasicStroke(2.5f));
            if (i == viewModel.getLength() || i % 4 == 0) {
                //(int x1, int y1, int x2, int y2)
                int xPos = Math.max((i - (this.viewModel.getViewStart() % 4)) * GRIDSIZE, 0) + XOFFSET;
                g.drawLine(xPos, 0 + YOFFSET, xPos,
                        PitchRange * GRIDSIZE + YOFFSET);
            }
        }
        //draw horizontal lines
        for (int i = 0; i <= PitchRange; i++) {
            ((Graphics2D) g).setStroke(new BasicStroke(2.5f));

            // (int x1, int y1, int x2, int y2)
            g.drawLine(XOFFSET, GRIDSIZE * i + YOFFSET, viewModel.getLength() * GRIDSIZE + XOFFSET,
                    YOFFSET + i * GRIDSIZE);
        }
    }

    /**
     * print all visible notes in the viewModel to the screen
     *
     * @param g the given graphics
     */
    private void printAllNotes(Graphics g) {
        //TODO: only print visible notes (in y dim)
        for (int i = this.viewModel.getViewStart(); i < this.viewModel.getLength(); i++) {
            for (MusicNote n : viewModel.getNotesAt(i)) {
                NoteComparator nComp = new NoteComparator();
                if (nComp.compare(n.changePitch(this.viewModel.getViewTop()).getPitch(),
                        viewModel.highestPitch()) <= 0
                        && (n.getPitch().midiValue() + this.viewModel.getViewTop()
                        >= this.viewModel.lowestPitch().midiValue())) {
                    if (n.getStartTime() == i) {
                        this.printBlackNote(n, g);
                    } else {
                        this.printGreenNote(n, i, g);
                    }
                }
            }
        }

    }

    /**
     * Draws a note head in the appropriate grid space.
     *
     * @param note the note to draw
     * @param g    the graphics context of the screen
     */
    private void printBlackNote(MusicNote note, Graphics g) {
        //print note starting point
        g.setColor(Color.black.darker());
        //(int x, int y, int width, int height)
        g.fillRect((note.getStartTime() - this.getNoteOffset()) * GRIDSIZE + XOFFSET,
                (viewModel.highestPitch().midiValue()
                        - note.getPitch().midiValue()
                        - viewModel.getViewTop()
                ) * GRIDSIZE + YOFFSET,
                GRIDSIZE, GRIDSIZE);
    }


    /**
     * Draws a suspended note in the appropriate grid space on a specific beat.
     *
     * @param note the note to draw
     * @param beat the beat to draw the note at
     * @param g    the graphics context of the screen
     */
    private void printGreenNote(MusicNote note, int beat, Graphics g) {

        g.setColor(Color.green.darker());
        g.fillRect((beat - this.getNoteOffset()) * GRIDSIZE + XOFFSET,
                (viewModel.highestPitch().midiValue()
                        - note.getPitch().midiValue()
                        - viewModel.getViewTop()
                ) * GRIDSIZE + YOFFSET,
                GRIDSIZE, GRIDSIZE);

    }


    /**
     * print the pitches symbols in Gui View
     *
     * @param g the given graphics the pitches symbols vertically
     */
    private void printPitchesLine(Graphics g) {
        g.setColor(Color.black.darker());
        for (Note pitch : this.viewModel.getAllNotesInRange()) {
            try {
                String pitchname = Pitch.pitchFromVal(pitch.getPitch().getVal() -
                        this.viewModel.getViewTop()).name();
                g.drawString(pitchname, 0,
                        (viewModel.highestPitch().midiValue() - pitch.midiValue())
                                * GRIDSIZE + YOFFSET + 14);
            } catch (IllegalArgumentException e) {
                //do nothing, do not draw the note
            }

        }
    }


        /**
         * prints the beat timeline horizontally
         *
         * @param g the given graphics
         */
    private void printBeatTimeLine(Graphics g) {
        g.setColor(Color.black.darker());
        for (int i = this.getNoteOffset(); i <= viewModel.getLength(); i++) {
            if (i % 16 == 0) {
                //(String str, int x, int y)
                g.drawString(Integer.toString(i), (i - this.getNoteOffset()) * GRIDSIZE + XOFFSET, 10);
            }
        }
    }

    /**
     * prints the current red line based on current beat
     *
     * @param g the given graphics
     */
    private void printCurrentBeatRedLine(Graphics g) {
        int PitchRange = viewModel.highestPitch().midiValue() -
                viewModel.lowestPitch().midiValue() + 1;
        g.setColor(Color.RED.darker());
        g.drawLine(this.getRedLineOffset() * GRIDSIZE + XOFFSET, YOFFSET,
                this.getRedLineOffset() * GRIDSIZE + XOFFSET,
                PitchRange * GRIDSIZE + YOFFSET);
    }

    /**
     * Draws the selection box on the screen if a visible note is selected.
     *
     * @param g the graphics object for the screen
     */
    private void printSelectionBox(Graphics g) {
        g.setColor(Color.magenta);
        if (viewModel.getSelectedPoint() != null) {
            g.drawRect((viewModel.getSelectedPoint().x - viewModel.getViewStart()) * GRIDSIZE + XOFFSET,
                    (viewModel.highestPitch().midiValue()
                            - viewModel.getSelectedPoint().y
                            - viewModel.getViewTop()) * GRIDSIZE + YOFFSET,
                    GRIDSIZE, GRIDSIZE);
        }
    }

    /**
     * find the red line position
     *
     * @return the integer of red line offset
     */
    private int getRedLineOffset() {
        return this.viewModel.getCurrentBeat() - this.viewModel.getViewStart();
    }

    /**
     * find the  position of the view start
     *
     * @return the integer of note offset
     */
    private int getNoteOffset() {
        return this.viewModel.getViewStart();
    }

}
