package weben;

import java.awt.Color;

/**
 * This <b>interface</b> allows me to have only one <b>GridPanel class</b>
 * and directs me about the required methods the <i>weaving grid classes</i>
 * (<b>WarpGrid, TieUpGrid, DesignGrid</b> and <b>WeftGrid classes</b>) should
 * have. The method bodies are different for all of these classes. It lets the
 * <b>GridPanel class</b> know how the weave 'behaves' and respond to a mouse
 * click.
 * <ul>
 * <li>(A) <b>WarpGrid</b> 1D array: lenght = "x-coordinate" = no. warp threads
 * used. Value = 0-(no. Shafts): value 0 = no schaft selected for this warp
 * thread; value 1 = first Schaft (upper cell) selected, etc.
 * <code>int[] arrayA = new int[warpThreads];</code></li>
 * <li>(B) <b>TieUpGrid</b> 2D array: lenght-x,height-y = no. treadles, no.
 * schafts. Value = 0 = not selected. Value = 1 = selected. Independent of other
 * TieUpGrid cell values.
 * <code>int[][] arrayB = new int[treadlingTreadles][threadingShafts];</code></li>
 * <li>(C) <b>DesignGrid</b> no array, but it'll read the other arrays to
 * decided how to respond with the getCellColor() method. As it is unresponsive
 * to mouseclicks on own grid, the clickCell() method will remain empty. Array
 * values depend on WarpGrid, TieUpGrid and WeftGrid! DETAILS TO BE
 * CONTINUED</li>
 * <li>(D) <b>WeftGrid</b> 1D array: height = "y-coordinate" = no. weft threads
 * used. Value = 0-(no. Treadles): value 0 = no treadle selected for this weft
 * thread; value 1 = first treadle (left cell), etc.
 * <code>int[] arrayD = new int[treadleThreads];</code> </li></ul>
 *
 */
interface Grid
{

    public abstract void updateObject();
    // update the grid's array  IF it requires an update
    // (N.A. for design grid)

    public abstract int getGridWidth();
    // return the grid width in index-format (dependend on the grid's array, or others in case of DesignGrid)

    public abstract int getGridHeight();
    // return required height in index-format (dependend on the grid's array, or others in case of DesignGrid)

    public abstract int getXCoordinate();
    // return the x-coordinate of the grid in index-format

    public abstract int getYCoordinate();
    // returns the y-coordinate of the grid in index-format

    public abstract Color getCellColor(int x, int y);
    // returns the color a cell should have at/corresponding to the x and y coordinates (parameters)

    public abstract void clickCell(int x, int y);
    // respond to a cell click (cell can be located using the x and y coordinate parameters)
    // (Design grid does not respond to a cell click in its GUI drawn-grid.

}
