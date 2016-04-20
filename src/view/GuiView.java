package view;


import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Create a sub interface GuiView to control Keyboard and Mouse Handlers
 */
public interface GuiView extends MusicOutputDevice {

    /**
     * Add the given key listener to the GUI view
     *
     * @param key the given key listener
     */
    void addKeyListener(KeyListener key);

    /**
     * Remove the given key listener to the GUI view
     * @param key the given key listener
     */
    void removeKeyListener(KeyListener key);


    /**
     * Add the given Mouse listener to the GUI view
     *
     * @param mouse the given Mouse listener
     */
    void addMouseListener(MouseListener mouse);

    /**
     * Remove the given Mouse listener from the GUI view
     *
     * @param mouse the given Mouse listener
     */
    void removeMouseListener(MouseListener mouse);

    /**
     * Add the given Mouse listener to the GUI view
     *
     * @param mouse the given Mouse listener
     */
    void addMouseMotionListener(MouseMotionListener mouse);

    /**
     * Remove the given Mouse listener from the GUI view
     *
     * @param mouse the given Mouse listener
     */
    void removeMouseMotionListener(MouseMotionListener mouse);

    /**
     * Add the given Adjustment listener to the GUI view on horizontal adjustment events
     *
     * @param listener the given Adjustment listener
     */
    void addHorizAdjustmentListener(AdjustmentListener listener);

    /**
     * Remove the given Adjustment listener from the GUI view on horizontal adjustment events
     *
     * @param listener the given Adjustment listener
     */
    void removeHorizAdjustmentListener(AdjustmentListener listener);

    /**
     * Add the given Adjustment listener to the GUI view on vertical adjustment events
     *
     * @param listener the given Adjustment listener
     */
    void addVertAdjustmentListener(AdjustmentListener listener);

    /**
     * Remove the given Adjustment listener from the GUI view on vertical adjustment events
     *
     * @param listener the given Adjustment listener
     */
    void removeVertAdjustmentListener(AdjustmentListener listener);


    /**
     *  Converts from a screen point in pixels in this view
     *  to a grid point in beats/pitch in the piece
     * @param screenPoint
     * @return
     */
    Point fromScreenPoint(Point screenPoint);


}