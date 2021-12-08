package weben;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * A class that is used to initially paint the drawn-grids with the default
 * weave setting. Next, it can respond to mouse clicks in those drawn-grids by
 * commanding the corresponding arrays to update and say which cells should be
 * colored.<br> It uses the <b>Grid interface</b>-type instance variable
 * <code>grid</code> to <i>connect</i> the <b>MainWindow class</b> object to the
 * right <b>Grid interface</b>-implementing class (as discussed in the
 * <b>MainWindow class</b>). <br>
 * <ul>
 * <li><code>GridPanel()</code> constructor communicates with the implemented
 * <b>MouseListener interface</b> to copy </li>
 * <li><code>gridBoundsUpdater()</code> updates the corresponding array and
 * bounds of the drawn grids</li>
 * <li><code>paintComponent(Graphics g)</code> is overridden from the
 * <b>MouseListener interface</b> and used to (re)paint all drawn-grids and
 * cells.</li>
 * <li><code>mouseClicked(MouseEvent e)</code> is also overridden from the
 * <b>MouseListener interface</b> and used to respond to a mouse click in a
 * drawn-grid.</li>
 * <li><i>all other <b>MouseListener interface</b> methods are not
 * overridden</i></li>
 * </ul>
 * This class does not know which weaving-grid classes exist. Using the <b>Grid
 * interface</b> I only need one <b>GridPanel class</b>: do not need a unique
 * one for each of the four weaving-grid classes.
 */
public class GridPanel extends JPanel implements MouseListener
{

    // instance variable of type Grid (interface)
    Grid grid;

    /**
     * Constructor tells to use the implemented <b>MouseListener</b> interface
     * with <code>addMouseListener(this);</code> (<code>this</code> = current
     * object of this class: e.g. <code>panelWarp</code>).
     */
    public GridPanel()
    {
        addMouseListener(this);        // this.addMouseListener(this);
    }

    // ===========================================================
    /**
     * Method to update the drawn grids. Used in <b>MainWindow class</b>
     * <code>updateGridsAfterSettingWeaveSettings()</code> on e.g.
     * <code>panelWarp</code>.<br>
     * It calls <code>updateObject()</code> -a <b>Grid interface</b> method that
     * is overridden by all of its implementing Grid-Classes- on the current
     * <b>GridPanel</b> instance variable of type <b>Grid interface</b>
     * "<code>grid</code>" --which can update the corresponding array.<br>
     * The updated width, height, x- and y-coordinates of the grid are
     * calculated and set.
     */
    public void gridBoundsUpdater()
    {
        // Update the corresponding array
        grid.updateObject();

        // Calculate and set the new grid bounds:
        int gridheigth = grid.getGridHeight() * MainWindow.gridSizeConstant + MainWindow.gridBorderThickness;
        int gridwidth = grid.getGridWidth() * MainWindow.gridSizeConstant + MainWindow.gridBorderThickness;
        int xcoor = grid.getXCoordinate();
        int ycoor = grid.getYCoordinate();
        setBounds(xcoor, ycoor, gridwidth, gridheigth);
    }

    // ===========================================================
    /**
     * Method that overrides the <b>MouseListener interface</b> static method
     * <code>paintComponent(Graphics g)</code>. This method is called with
     * <code>repaint()</code> (behind the scenes) and it <i>repaints</i>
     * everything from scratch, e.g. the <code>grid</code> corresponding to
     * <code>warpGrid</code>. First, the drawn-grid is painted, in black
     * (dimensions are set in <b>MainWindow class</b>. Next, the coordinates of the
     * drawn-grid is used to allocate the corresponding
     * <i>array</i> value and either color or not-color the cell (by adding a
     * colored filled square shape at the right drawn-grid position).
     * <br><br><br>
     * With <code>super.paintComponent(g);</code> the method is called in the
     * <b>MouseListener interface</b> to import it, and be able to add code
     * afterwards.<br>
     *
     * @param g reference to an object of the <b>Graphics</b> class: imput from
     * Graphics class where <code>showFrame();</code> sets magic behind the
     * scenes in motion and calls this <code>paintComponent()</code> method.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        // call the method that is overridden to call that code, before new code is added below
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Get current grid dimensions (in index size):
        int gridWidthIndex = grid.getGridWidth();        // this.grid.getGridWidth() <-- where this. is redundant
        int gridHeightIndex = grid.getGridHeight();

        // Convert grid dimensions to pixel size:
        int gridWidthPixel = gridWidthIndex * MainWindow.gridSizeConstant + MainWindow.gridBorderThickness;
        int gridHeightPixel = gridHeightIndex * MainWindow.gridSizeConstant + MainWindow.gridBorderThickness;

        // Draw the grids:
        for (int y = 0; y <= this.getHeight(); y += MainWindow.gridSizeConstant)
        {
            g.drawLine(0, y, this.getWidth(), y);
        }
        for (int x = 0; x <= this.getWidth(); x += MainWindow.gridSizeConstant)
        {
            g.drawLine(x, 0, x, this.getHeight());
        }

        // Fill the drawn-grid cells:
        if (grid != null)                 // is redundant since all grid's are set
        {
            for (int x = 0; x < (this.getWidth() / MainWindow.gridSizeConstant); x++)
            {
                for (int y = 0; y < (this.getHeight() / MainWindow.gridSizeConstant); y++)
                {
                    // 'index coordinate' to 'pixel coordinate' conversion:
                    int xCoordinate = x * MainWindow.gridSizeConstant + MainWindow.gridBorderThickness;
                    int yCoordinate = y * MainWindow.gridSizeConstant + MainWindow.gridBorderThickness;

                    // Read out what color the cell should have:
                    Color newColor = grid.getCellColor(x, y);

                    // Color the cell if it should be colored:
                    if (newColor != null)
                    {
                        g.setColor(newColor);
                        g.fillRect(xCoordinate, yCoordinate, MainWindow.gridSizeConstant - MainWindow.gridBorderThickness, MainWindow.gridSizeConstant - MainWindow.gridBorderThickness);
                    }
                }
            }
        }
    }

    /**
     * Method that overrides the <b>MouseListener interface</b> static method
     * <code>mouseClicked(MouseEvent e)</code>. It is called whenever a user
     * excecutes a mouse click (press + release) within the
     * <i>component</i> (defined by <code>paintComponent()</code>: the drawn
     * grid(s)). Using the automatically registered <i>pixel coordinates</i> of
     * the mouse click (and a quick conversion to <i>index coordinates</i>,
     * <code>clickCell()</code> is called in the correct Grid class; and the
     * array values are updated (unless the user clicked in the <i>Design
     * Grid</i> since that does not have an array). Then a
     * <code>repaint()</code> calls <code>paintComponent()</code> again, and the
     * array-less <i>Design Grid</i> is repainted by calling
     * <code>repaintPanelDesign()</code> on the current, one-and-only
     * <b>MainWindow class</b> instance <code>g</code> aka <code>instance</code>.
     *
     * @param e reference to an object of the <b>MouseEvent</b> class.
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("Mouse clicked " + e.getClickCount() + "x at " + e.getPoint());

        int indexCoordinateX = (e.getPoint().x - MainWindow.gridBorderThickness) / MainWindow.gridSizeConstant;
        int indexCoordinateY = (e.getPoint().y - MainWindow.gridBorderThickness) / MainWindow.gridSizeConstant;
        grid.clickCell(indexCoordinateX, indexCoordinateY);

        repaint();  // repaints only itself: the specific grid e.g. WarpGrid-GridPanel
        MainWindow.getInstance().repaintPanelDesign();
    }

    // ===========================================================
    /**
     * <b>Unused method</b>
     *
     * @param me reference to an object of the <b>MouseEvent</b> class.
     */
    @Override
    public void mousePressed(MouseEvent me)
    {
    }

    /**
     * <b>Unused method</b>
     *
     * @param me reference to an object of the <b>MouseEvent</b> class.
     */
    @Override
    public void mouseReleased(MouseEvent me)
    {
    }

    /**
     * <b>Unused method</b>
     *
     * @param me reference to an object of the <b>MouseEvent</b> class.
     */
    @Override
    public void mouseEntered(MouseEvent me)
    {
    }

    /**
     * <b>Unused method</b>
     *
     * @param me reference to an object of the <b>MouseEvent</b> class.
     */
    @Override
    public void mouseExited(MouseEvent me)
    {
    }

}
