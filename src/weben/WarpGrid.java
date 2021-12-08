package weben;

import java.awt.Color;

/**
 * Class that implements the <b>Grid</b> interface and overrides all of its
 * methods. Additionally, these static variables are made and used:
 * <ul>
 * <li>1D array <code>arrayWarp</code>
 * <ul><li>-1 = default = no cell in column selected
 * <li> 0 = cell at index 0 (upper) in column selected
 * <li> 1 = cells at index 1 in column selected
 * <li> (...)
 * <li> arrayWarp.length - 1 = cell at max. index (bottom) in column selected
 * </ul>
 * <li>Color-type <code>warpColor</code>
 * <ul><li>The <code>warpColor</code> is set to ORANGE</ul>
 * <li>The no. shafts is saved, to be able to compare it to the updated no.
 * shafts and conclude whether <code>arrayWarp</code> should be altered or
 * entirely renewed (used and updated in <code>updateObject()</code>).</li>
 * </ul>
 */
public class WarpGrid implements Grid
{

    static int[] arrayWarp = new int[MainWindow.getInstance().getWarpThreads()];
    static Color warpColor = Color.ORANGE;
    private int currentShafts = MainWindow.getInstance().getShafts();

    /**
     * When the WarpGrid is instantiated (in class <b>MainWindow</b>) all
     * <code>arrayWarp</code> values are set to the default <b>-1</b>.
     */
    public WarpGrid()
    {
        for (int x = 0; x < arrayWarp.length; x++)
        {
            arrayWarp[x] = -1;
        }
    }

    /**
     * Called when the user may have changed weave settings. Called in the
     * <code>gridBoundsUpdater()</code> in the <b>GridPanel class</b>, which is
     * called by <code>updateGridsAfterSettingWeaveSettings()</code>, called by
     * the weave-setting <i>setters</i>. <br>
     * When the number of <i>warp threads</i> is altered, <code>arrayWarp</code>
     * is adjusted here by making it shorter or longer (using a temporary array
     * to store the current array's values). Since the array indices increase
     * from left to right, but the <i>warp grid</i> increases or decreases in
     * size by extending/deducting from the left side, array index 0 always
     * 'shifts'. Therefore, the calculations in this method are more tricky than
     * those of e.g. the WeftGrid. <br>
     * When the no. of <i>warp threads</i> is increased, the new array's new
     * positions are given default value -1. A change in shaft number gives rise
     * to a new empty array.<br>
     */
    @Override
    public void updateObject()
    {
        // save all arrayWarp values in a temporary array
        int[] arrayTemporary = new int[arrayWarp.length];
        for (int x = 0; x < arrayTemporary.length; x++)
        {
            arrayTemporary[x] = arrayWarp[x];
        }

        // if the updated no. WarpThreads has changed: update (not renew!)
        if (MainWindow.getInstance().getWarpThreads() != arrayWarp.length)
        {
            // Update the true array with the new updated size
            arrayWarp = new int[MainWindow.getInstance().getWarpThreads()];
            // Add all possible 'current' values (saved in the temporary array) to the true array
            if (arrayWarp.length > arrayTemporary.length)
            {
                // no. warp threads has been increased by:
                int indexDifference = arrayWarp.length - arrayTemporary.length;
                for (int x = indexDifference; x < arrayWarp.length; x++)     // max. index to min
                {
                    arrayWarp[x] = arrayTemporary[x - indexDifference];
                }
                // set default value -1 at the new indices 
                for (int x = 0; x < indexDifference; x++)
                {
                    arrayWarp[x] = -1;
                }
            }
            if (arrayWarp.length < arrayTemporary.length)
            {

                // no. warp threads has been decreased
                int indexDifference = arrayTemporary.length - arrayWarp.length;
                for (int x = arrayWarp.length - 1; x > -1; x--)                    // max. index to min
                {
                    arrayWarp[x] = arrayTemporary[x + indexDifference];           // so left-side graph info is deleted
                }
            }
        }
        // if the updated no. shafts has changed, I want everything to refresh
        if (currentShafts != MainWindow.getInstance().getShafts())
        {
            arrayWarp = new int[MainWindow.getInstance().getWarpThreads()];
            for (int x = 0; x < arrayWarp.length; x++)
            {
                arrayWarp[x] = -1;
            }
            // update instance variable:
            currentShafts = MainWindow.getInstance().getShafts();
        }
    }
// ===========================================================

    /**
     * Returns the length of <code>arrayWarp</code> since it corresponds to the
     * width of the warp grid.
     *
     * @return int warpGridWidth
     */
    @Override
    public int getGridWidth()
    {
        int warpGridWidth = arrayWarp.length;
        return warpGridWidth;

        // return arrayWarp.length;         // would do the same ;)
    }

    /**
     * Returns the no. of <code>shafts</code> since it corresponds to the height
     * of the warp grid.
     *
     * @return int warpGridHeight
     */
    @Override
    public int getGridHeight()
    {
        int warpGridHeight = MainWindow.getInstance().getShafts();
        return warpGridHeight;
    }

    /**
     * Returns the <code>gridSizeConstant</code> (defined in the <b>MainWindow
     * class</b>) since it corresponds to the x-coordiante of the warp grid.
     *
     * @return int warpXcoor
     */
    @Override
    public int getXCoordinate()
    {
        int warpXcoor = MainWindow.gridSizeConstant;
        return warpXcoor;
    }

    /**
     * Returns the <code>gridSizeConstant</code> (defined in the <b>MainWindow
     * class</b>) since it corresponds to the y-coordiante of the warp grid.
     *
     * @return int warpYcoor
     */
    @Override
    public int getYCoordinate()
    {
        int warpYcoor = MainWindow.gridSizeConstant;
        return warpYcoor;
    }

    /**
     * The parameters are used to check the corresponding value in the
     * <code>arrayWarp</code>. If <code>(arrayWarp[x] == y)</code> it means the
     * corresponding cell should indeed be colored, so the returned color is
     * <code>warpColor</code>, instead of null. <i>For example: corresponding
     * array's value at index 'x' is 3: if 'y'==3 then the cell at that position
     * should be painted indeed.</i>
     *
     * @param x 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWarp</code> value
     * @param y 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWarp</code> value
     * @return Color: the color the cell should have
     */
    @Override
    public Color getCellColor(int x, int y)
    {
        // if the y coordinate corresponds with the 'y-value' stored in the x-coordinate array index: we want to colour it.
        if (arrayWarp[x] == y)
        {
            return warpColor;
        }
        return null;
    }

    /**
     * Sets the <code>arrayWarp</code> value on index <code>x</code> to value
     * <code>y</code>.
     *
     * @param x 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWarp</code> value
     * @param y 'index coordinate' in drawn-grid that corresponds to an
     * <code>arrayWarp</code> value
     */
    @Override
    public void clickCell(int x, int y)
    {
        // Set the value at array index 'x' to value 'y':
        arrayWarp[x] = y;
    }
}
