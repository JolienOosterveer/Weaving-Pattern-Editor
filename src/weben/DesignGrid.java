package weben;

import java.awt.Color;

/**
 * Class that implements the <b>Grid</b> interface and overrides all of its
 * methods. There are no static- or instance variables, nor do
 * <code>clickCell(x, y)</code> and <code>updateObject()</code> have a method
 * body.
 * <br>
 * <code>getCellColor(x, y)</code> analyzes the array's of the other <b>Grid
 * interface</b>-implementing classes and returns the color of the right class.
 */
public class DesignGrid implements Grid
{

    /**
     * There are no instance variables to be set so this constructor is empty.
     */
    public DesignGrid()
    {
        // N.A.
    }

    /**
     * There are no instance variables to be updated so this method body is
     * empty.
     */
    @Override
    public void updateObject()
    {
        // N.A.
    }

    /**
     * Returns the no. of <code>warpThreads</code> since it corresponds to the
     * width of the design grid.
     *
     * @return int designGridWidth
     */
    @Override
    public int getGridWidth()
    {
        int designGridWidth = MainWindow.getInstance().getWarpThreads();
        return designGridWidth;
    }

    /**
     * Returns the no. of <code>weftThreads</code> since it corresponds to the
     * height of the design grid.
     *
     * @return int designGridHeight
     */
    @Override
    public int getGridHeight()
    {
        int designGridHeight = MainWindow.getInstance().getWeftThreads();
        return designGridHeight;
    }

    /**
     * Returns the <code>gridSizeConstant</code> since it corresponds to the
     * x-coordinate of the design grid.
     *
     * @return int designXcoor
     */
    @Override
    public int getXCoordinate()
    {
        int designXcoor = MainWindow.gridSizeConstant;
        return designXcoor;
    }

    /**
     * Returns the <code>gridSizeConstant</code> + no.
     * <code>shafts * gridSizeConstant</code> + gridSizeConstant, since it
     * corresponds to the y-coordinate of the design grid.
     *
     * @return int designYcoor
     */
    @Override
    public int getYCoordinate()
    {
        int designYcoor = MainWindow.gridSizeConstant + MainWindow.getInstance().getShafts() * MainWindow.gridSizeConstant + MainWindow.gridSizeConstant;
        return designYcoor;
    }

    /**
     * The parameters are used to check the corresponding design grid value and
     * the to that corresponding <code>arrayWarp, arrayWeft</code> and
     * <code>arrayTieUp</code> values.<br>
     * Several local variables are made, including:
     * <ul>
     * <li>Color-type <code>designColor</code>: returned by this method</li>
     * <li>int-type x- and y- coordinates for the warp, tie up and weft grids,
     * calculated using the method's parameters and values at certain arrays:
     * <ul>
     * <li>the x-coordinate of the design grid == x-coordinate of the warp grid
     * <li>the y-coordinate of the design grid == y-coordinate of the weft grid
     * <li>the x-coordinate of the weft grid (calculated with
     * <code>arrayWeft[y]</code> == x-coordinate of the tie up grid
     * <li>the y-coordinate of the warp grid (calcualted with
     * <code>arrayWarp[x]</code> == y-coordinate of the tie up grid
     * </ul>
     * <li>With all corresponding coordinated calculated, if the corresponding
     * <code>arrayWeft[y]</code> and <code>arrayWarp[x]</code> values are for
     * neither default -1: <code>designColor</code> can be set to:
     * <ul>
     * <li><code>weftColor</code> if the corresponding <code>arrayTieUp</code>
     * == 0
     * <li><code>warpColor</code> if the corresponding <code>arrayTieUp</code>
     * == 1
     * </ul><li>else, 'null' is returned.</li>
     * </ul>
     *
     *
     * @param x 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWarp</code> value
     * @param y 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWeft</code> value
     * @return Color designColor the color the cell should have
     */
    @Override
    public Color getCellColor(int x, int y)
    {
        Color designColor = null;
        int arrayA_x = x;
        int arrayA_y = WarpGrid.arrayWarp[arrayA_x];
        int arrayD_y = y;
        int arrayD_x = WeftGrid.arrayWeft[arrayD_y];
        int arrayB_x = arrayD_x;
        int arrayB_y = arrayA_y;

        // Change designColor only if both corresponding warp and weft array values are not default -1:
        if ((arrayB_x != -1) && (arrayB_y != -1))
        {
            // Read out the other 3 arrays using the input coordinates:
            if (TieUpGrid.arrayTieUp[arrayB_x][arrayB_y] == 0)
            {
                designColor = WeftGrid.weftColor;
            }
            if (TieUpGrid.arrayTieUp[arrayB_x][arrayB_y] == 1)
            {
                designColor = WarpGrid.warpColor;
            }
        }
        return designColor;
    }

    /**
     * There are no instance variables to be updated so this method body is
     * empty.
     *
     * @param x N.A.
     * @param y N.A.
     */
    @Override
    public void clickCell(int x, int y)
    {
        // N.A.
    }
}
