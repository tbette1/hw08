package view;

import controller.BeatController;

import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * Combines the GUI and MIDI views
 */
class CompositeView implements GuiView {
    private final GuiView gui;
    private final MusicOutputDevice midi;

    /**
     * Constructor for a {@link CompositeView}
     *
     * @param gui  the {@link GuiView} to render
     * @param midi the {@link MusicOutputDevice} to render
     */
    CompositeView(GuiView gui, MusicOutputDevice midi) {
        this.gui = Objects.requireNonNull(gui);
        this.midi = Objects.requireNonNull(midi);
    }


    @Override
    public void addKeyListener(KeyListener key) {
        this.gui.addKeyListener(key);
    }

    @Override
    public void removeKeyListener(KeyListener key) {
        this.gui.removeKeyListener(key);
    }

    @Override
    public void addMouseListener(MouseListener mouse) {
        this.gui.addMouseListener(mouse);
    }

    @Override
    public void removeMouseListener(MouseListener mouse) {
        gui.removeMouseListener(mouse);
    }

    @Override
    public void addMouseMotionListener(MouseMotionListener mouse) {
        this.gui.addMouseMotionListener(mouse);
    }

    @Override
    public void removeMouseMotionListener(MouseMotionListener mouse) {
        this.gui.removeMouseMotionListener(mouse);
    }

    @Override
    public void addHorizAdjustmentListener(AdjustmentListener listener) {
        this.gui.addHorizAdjustmentListener(listener);
    }

    @Override
    public void removeHorizAdjustmentListener(AdjustmentListener listener) {
        this.gui.removeHorizAdjustmentListener(listener);
    }

    @Override
    public void addVertAdjustmentListener(AdjustmentListener listener) {
        this.gui.addVertAdjustmentListener(listener);
    }

    @Override
    public void removeVertAdjustmentListener(AdjustmentListener listener) {
        this.gui.removeVertAdjustmentListener(listener);
    }

    @Override
    public Point fromScreenPoint(Point screenPoint) {
        return this.gui.fromScreenPoint(screenPoint);
    }

    @Override
    public void initialize() {
        midi.initialize();
        gui.initialize();
    }


    @Override
    public void renderModel(BeatController timer) throws IOException {
        midi.renderModel(timer);
        gui.renderModel(timer);
    }

}