package view;


import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.util.Objects;
import controller.*;
import javax.swing.*;


/**
 * A visual view that displays a viewModel of music in a window
 */
class GuiViewFrame extends javax.swing.JFrame implements GuiView {

    private final ConcreteGuiViewPanel displayPanel;
    private final ViewModel viewModel;
    private final JScrollBar hbar;
    private final JScrollBar vbar;


    /**
     * Creates a new GuiView backed by the given viewModel.
     *
     * @param viewModel the model backing this view
     */
    GuiViewFrame(ViewModel viewModel) {
        //set viewmodel and initialize components
        this.viewModel = Objects.requireNonNull(viewModel);
        this.displayPanel = new ConcreteGuiViewPanel(this.viewModel);
        hbar = new JScrollBar(JScrollBar.HORIZONTAL, 0,
                this.getWidth(), 0, Math.max(1, viewModel.getLength()));
        vbar = new JScrollBar(JScrollBar.VERTICAL, 0,
                this.getHeight(), 0,
                Math.max(1, this.viewModel.highestPitch().getIntValue()
                        - this.viewModel.lowestPitch().getIntValue()));

        //register update methods for this view
        this.viewModel.registerUpdates(t -> {
            this.hbar.setValue(this.viewModel.getViewStart());
        }, () -> {
            this.hbar.setValue(this.viewModel.getViewStart());
            this.vbar.setValue(this.viewModel.getViewTop());
        });

        //setup the window
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.add(displayPanel, BorderLayout.CENTER);
        this.add(hbar, BorderLayout.SOUTH);
        this.add(vbar, BorderLayout.EAST);
        this.pack();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 550);
    }


    @Override
    public void renderModel(BeatController timer) throws IOException {
        timer.addBeatListener(this.viewModel, BeatController.Priority.LAST);
    }

    @Override

    public void initialize() {
        this.setVisible(true);
    }

    @Override
    public void addHorizAdjustmentListener(AdjustmentListener listener) {
        hbar.addAdjustmentListener(listener);
    }

    @Override
    public void removeHorizAdjustmentListener(AdjustmentListener listener) {
        hbar.removeAdjustmentListener(listener);
    }

    @Override
    public void addVertAdjustmentListener(AdjustmentListener listener) {
        vbar.addAdjustmentListener(listener);
    }

    @Override
    public void removeVertAdjustmentListener(AdjustmentListener listener) {
        vbar.removeAdjustmentListener(listener);
    }

    @Override
    public Point fromScreenPoint(Point screenPoint) {
        return new Point(viewModel.getViewStart() +
                (screenPoint.x - ConcreteGuiViewPanel.XOFFSET) / ConcreteGuiViewPanel.GRIDSIZE,
                viewModel.highestPitch().getIntValue() - viewModel.getViewTop() + 1 -
                        (screenPoint.y - ConcreteGuiViewPanel.YOFFSET) / ConcreteGuiViewPanel.GRIDSIZE);
    }

}