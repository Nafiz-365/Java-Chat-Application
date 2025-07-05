/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nafiz.swing;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nafiz_365
 */
public class ComponentResizer extends MouseAdapter {

    private static final Dimension MINIMUM_SIZE = new Dimension(10, 10);
    private static final Dimension MAXIMUM_SIZE = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private static final Map<Integer, Integer> cursors = new HashMap<>();
    static {
        cursors.put(1, Cursor.N_RESIZE_CURSOR);
        cursors.put(2, Cursor.W_RESIZE_CURSOR);
        cursors.put(4, Cursor.S_RESIZE_CURSOR);
        cursors.put(8, Cursor.E_RESIZE_CURSOR);
        cursors.put(3, Cursor.NW_RESIZE_CURSOR);
        cursors.put(9, Cursor.NE_RESIZE_CURSOR);
        cursors.put(6, Cursor.SW_RESIZE_CURSOR);
        cursors.put(12, Cursor.SE_RESIZE_CURSOR);
    }

    private Insets dragInsets;
    private Dimension snapSize;

    private int direction;
    protected static final int NORTH = 1;
    protected static final int WEST = 2;
    protected static final int SOUTH = 4;
    protected static final int EAST = 8;

    private Cursor sourceCursor;
    private boolean resizing;
    private Rectangle bounds;
    private Point pressed;
    private boolean autoscrolls;
    private boolean autoLayout;

    private Dimension minimumSize = MINIMUM_SIZE;
    private Dimension maximumSize = MAXIMUM_SIZE;

    public ComponentResizer() {
        this(new Insets(5, 5, 5, 5), new Dimension(1, 1));
    }

    public ComponentResizer(Component... components) {
        this(new Insets(5, 5, 5, 5), new Dimension(1, 1), components);
    }

    public ComponentResizer(Insets dragInsets, Component... components) {
        this(dragInsets, new Dimension(1, 1), components);
    }

    public ComponentResizer(Insets dragInsets, Dimension snapSize, Component... components) {
        setDragInsets(dragInsets);
        setSnapSize(snapSize);
        registerComponent(components);
    }

    public boolean isAutoLayout() {
        return autoLayout;
    }

    public void setAutoLayout(boolean autoLayout) {
        this.autoLayout = autoLayout;
    }

    public Insets getDragInsets() {
        return dragInsets;
    }

    public void setDragInsets(Insets dragInsets) {
        validateMinimumAndInsets(minimumSize, dragInsets);
        this.dragInsets = dragInsets;
    }

    public Dimension getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Dimension maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Dimension getMinimumSize() {
        return minimumSize;
    }

    public void setMinimumSize(Dimension minimumSize) {
        validateMinimumAndInsets(minimumSize, dragInsets);
        this.minimumSize = minimumSize;
    }

    public void deregisterComponent(Component... components) {
        for (Component component : components) {
            component.removeMouseListener(this);
            component.removeMouseMotionListener(this);
        }
    }

    public void registerComponent(Component... components) {
        for (Component component : components) {
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }
    }

    public Dimension getSnapSize() {
        return snapSize;
    }

    public void setSnapSize(Dimension snapSize) {
        this.snapSize = snapSize;
    }

    private void validateMinimumAndInsets(Dimension minimum, Insets drag) {
        int minimumWidth = drag.left + drag.right;
        int minimumHeight = drag.top + drag.bottom;

        if (minimum.width < minimumWidth || minimum.height < minimumHeight) {
            throw new IllegalArgumentException("Minimum size cannot be less than drag insets");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component source = e.getComponent();
        Point location = e.getPoint();
        direction = 0;

        if (location.x < dragInsets.left) direction += WEST;
        if (location.x > source.getWidth() - dragInsets.right - 1) direction += EAST;
        if (location.y < dragInsets.top) direction += NORTH;
        if (location.y > source.getHeight() - dragInsets.bottom - 1) direction += SOUTH;

        if (direction == 0) {
            source.setCursor(sourceCursor);
        } else {
            int cursorType = cursors.get(direction);
            source.setCursor(Cursor.getPredefinedCursor(cursorType));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!resizing) {
            sourceCursor = e.getComponent().getCursor();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!resizing) {
            e.getComponent().setCursor(sourceCursor);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (direction == 0) return;

        resizing = true;

        Component source = e.getComponent();
        pressed = e.getPoint();
        SwingUtilities.convertPointToScreen(pressed, source);
        bounds = source.getBounds();

        if (source instanceof JComponent jc) {
            autoscrolls = jc.getAutoscrolls();
            jc.setAutoscrolls(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        resizing = false;

        Component source = e.getComponent();
        source.setCursor(sourceCursor);

        if (source instanceof JComponent jc) {
            jc.setAutoscrolls(autoscrolls);
        }

        if (autoLayout) {
            Component parent = source.getParent();
            if (parent instanceof JComponent jcp) {
                jcp.revalidate();
            } else if (parent != null) {
                parent.validate();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!resizing) return;

        Component source = e.getComponent();
        Point dragged = e.getPoint();
        SwingUtilities.convertPointToScreen(dragged, source);
        changeBounds(source, direction, bounds, pressed, dragged);
    }

    protected void changeBounds(Component source, int direction, Rectangle bounds, Point pressed, Point current) {
        int x = bounds.x;
        int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;

        if ((direction & WEST) == WEST) {
            int drag = getDragDistance(pressed.x, current.x, snapSize.width);
            int max = Math.min(width + x - 10, maximumSize.width);
            drag = getDragBounded(drag, snapSize.width, width, minimumSize.width, max);
            x -= drag;
            width += drag;
        }

        if ((direction & NORTH) == NORTH) {
            int drag = getDragDistance(pressed.y, current.y, snapSize.height);
            int max = Math.min(height + y - 10, maximumSize.height);
            drag = getDragBounded(drag, snapSize.height, height, minimumSize.height, max);
            y -= drag;
            height += drag;
        }

        if ((direction & EAST) == EAST) {
            int drag = getDragDistance(current.x, pressed.x, snapSize.width);
            Dimension boundSize = getBoundingSize(source);
            int max = Math.min(boundSize.width - x, maximumSize.width);
            drag = getDragBounded(drag, snapSize.width, width, minimumSize.width, max);
            width += drag;
        }

        if ((direction & SOUTH) == SOUTH) {
            int drag = getDragDistance(current.y, pressed.y, snapSize.height);
            Dimension boundSize = getBoundingSize(source);
            int max = Math.min(boundSize.height - y, maximumSize.height);
            drag = getDragBounded(drag, snapSize.height, height, minimumSize.height, max);
            height += drag;
        }

        source.setBounds(x, y, width, height);
        source.validate();
    }

    private int getDragDistance(int larger, int smaller, int snap) {
        int halfway = snap / 2;
        int drag = larger - smaller;
        drag += (drag < 0) ? -halfway : halfway;
        return (drag / snap) * snap;
    }

    private int getDragBounded(int drag, int snap, int dim, int min, int max) {
        while (dim + drag < min) drag += snap;
        while (dim + drag > max) drag -= snap;
        return drag;
    }

    private Dimension getBoundingSize(Component source) {
        if (source instanceof Window) {
            Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            return new Dimension(bounds.width, bounds.height);
        } else {
            Dimension d = source.getParent().getSize();
            d.width -= 10;
            d.height -= 10;
            return d;
        }
    }
}