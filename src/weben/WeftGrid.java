package weben;

import java.awt.Color;

/**
 * Class that implements the <b>Grid</b> interface and overrides all of its
 * methods. Additionally, these static variables are made and used:
 * <ul>
 * <li>1D array <code>arrayWeft</code>
 * <ul><li>-1 = default = no cell in column selected
 * <li> 0 = cell at index 0 (left) in column selected
 * <li> 1 = cells at index 1 in column selected
 * <li> (...)
 * <li> arrayWeft.length - 1 = cell at max. index (right) in column selected
 * </ul>
 * <li>Color-type <code>weftColor</code>
 * <ul><li>The <code>weftColor</code> is set to BLUE</ul>
 * <li>The no. treadles is saved, to be able to compare it to the updated no.
 * treadles and conclude whether <code>arrayWeft</code> should be altered or
 * entirely renewed (used and updated in <code>updateObject()</code>).</li>
 * </ul>
 */
public class WeftGrid implements Grid
{

    static int[] arrayWeft = new int[MainWindow.getInstance().getWeftThreads()];
    static Color weftColor = Color.BLUE;
    private int currentTreadles = MainWindow.getInstance().getTreadles();

    /**
     * When the WeftGrid is instantiated (in class <b>MainWindow</b>) all
     * <code>arrayWeft</code> values are set to the default <b>-1</b>.
     */
    public WeftGrid()
    {
        for (int x = 0; x < arrayWeft.length; x++)
        {
            arrayWeft[x] = -1;
        }
    }

    /**
     * Called when the user may have changed weave settings. Called in the
     * <code>gridBoundsUpdater()</code> in the <b>GridPanel class</b>, which is
     * called by <code>updateGridsAfterSettingWeaveSettings()</code>, called by
     * the weave-setting <i>setters</i>. <br>
     * When the number of <i>weft threads</i> is altered, <code>arrayWeft</code>
     * is adjusted here by making it shorter or longer (using a temporary array
     * to store the current array's values). Since the array indices increase
     * from left to right, and the <i>weft grid</i> increases or decreases in
     * size by extending/deducting on the bottom, array index 0 stays 0 (unlike
     * in <code>arrayWarp</code>).<br>
     * When the no. of <i>weft threads</i> is increased, the new array's new
     * positions are given default value -1. A change in treadle number gives
     * rise to a new empty array.<br>
     */
    @Override
    public void updateObject()
    {
        // save all arrayWeft values in a temporary array
        int[] arrayTemporary = new int[arrayWeft.length];
        for (int x = 0; x < arrayTemporary.length; x++)
        {
            arrayTemporary[x] = arrayWeft[x];
        }

        // if the updated no. WefpThreads has changed: update (not renew!)
        if (MainWindow.getInstance().getWeftThreads() != arrayWeft.length)
        {
            // Update the true array with the new updated size
            arrayWeft = new int[MainWindow.getInstance().getWeftThreads()];
            // Add all possible 'current' values (saved in the temporary array) to the true array
            if (arrayWeft.length > arrayTemporary.length)
            {
                // no. warp threads has been increased by:
                for (int x = 0; x < arrayTemporary.length; x++)     // max. index to min
                {
                    arrayWeft[x] = arrayTemporary[x];
                }
                // set default value -1 at the new indices 
                for (int x = arrayTemporary.length; x < arrayWeft.length; x++)
                {
                    arrayWeft[x] = -1;
                }
            }
            if (arrayWeft.length < arrayTemporary.length)
            {

                // no. warp threads has been decreased
                for (int x = 0; x < arrayWeft.length; x++)                    // max. index to min
                {
                    arrayWeft[x] = arrayTemporary[x];           // so left-side graph info is deleted
                }
            }
        }
        // if the updated no. shafts has changed, I want everything to refresh
        if (currentTreadles != MainWindow.getInstance().getTreadles())
        {
            arrayWeft = new int[MainWindow.getInstance().getWeftThreads()];
            for (int x = 0; x < arrayWeft.length; x++)
            {
                arrayWeft[x] = -1;
            }
            // update instance variable:
            currentTreadles = MainWindow.getInstance().getTreadles();
        }
    }

    /**
     * Returns the no. of <code>treadles</code> since it corresponds to the
     * width of the weft grid.
     *
     * @return int weftGridWidth
     */
    @Override
    public int getGridWidth()
    {
        int weftGridWidth = MainWindow.getInstance().getTreadles();
        return weftGridWidth;
    }

    /**
     * Returns the length of <code>arrayWeft</code> since it corresponds to the
     * height of the weft grid.
     *
     * @return int weftGridHeight
     */
    @Override
    public int getGridHeight()
    {
        int weftGridHeight = arrayWeft.length;
        return weftGridHeight;
    }

    /**
     * Returns the <code>gridSizeConstant</code> (defined in the <b>MainWindow
     * class</b>) + no. of <code>warpThreads*gridSizeConstant</code> +
     * <code>gridSizeConstant</code> since that corresponds to the 'pixel'
     * x-coordiante of the weft grid.
     *
     * @return int weftXcoor
     */
    @Override
    public int getXCoordinate()
    {
        int weftXcoor = MainWindow.gridSizeConstant + MainWindow.getInstance().getWarpThreads() * MainWindow.gridSizeConstant + MainWindow.gridSizeConstant;
        return weftXcoor;
    }

    /**
     * Returns the <code>gridSizeConstant</code> (defined in the <b>MainWindow
     * class</b>) + no. of <code>shafts*gridSizeConstant</code> +
     * <code>gridSizeConstant</code> since that corresponds to the 'pixel'
     * y-coordiante of the weft grid.
     *
     * @return int weftYcoor
     */
    @Override
    public int getYCoordinate()
    {
        int weftYcoor = MainWindow.gridSizeConstant + MainWindow.getInstance().getShafts() * MainWindow.gridSizeConstant + MainWindow.gridSizeConstant;
        return weftYcoor;
    }
    // ===========================================================

    /**
     * The parameters are used to check the corresponding value in the
     * <code>arrayWeft</code>. If <code>(arrayWeft[y] == x)</code> it means the
     * corresponding cell should indeed be colored, so the returned color is
     * <code>weftColor</code>, instead of null.<br>
     * It's y first, then x, because the Weft grid is perpendicular to its
     * array.
     *
     * @param x 'index coordinate' of drawn-grid that corresponds to an
     * <code>arrayWeft</code> value
     * @param y 'index coordinate' of drawn-grid that corresponds to an
     * <code>arrayWeft</code> value
     * @return Color: the color the cell should have
     */
    @Override
    public Color getCellColor(int x, int y)
    {
        if (arrayWeft[y] == x)
        {
            return weftColor;
        }
        return null;
    }

    /**
     * Sets the <code>arrayWeft</code> value on index <code>y</code> to value
     * <code>x</code>.
     *
     * @param x 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWeft</code> value
     * @param y 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWeft</code> value
     */
    @Override
    public void clickCell(int x, int y)
    {
        // Set the value at array index 'x' to value 'y':
        arrayWeft[y] = x;
    }

}
