package weben;

import java.awt.Color;

/**
 * Class that implements the <b>Grid</b> interface and overrides all of its
 * methods. Additionally, these static variables are made and used:
 * <ul>
 * <li>2D array <code>arrayTieUp</code>
 * <ul><li> <b>0</b> = cell not selected: which is default and can happen when a
 * cell with an array value of <b>1</b> is clicked again (deselected).
 * <li> <b>1</b> = cell selected
 * </ul>
 * <li>Color-type <code>tieUpColor</code>
 * <ul><li>The <code>tieUpColor</code> is set to BLACK</ul>
 * <li>The no. treadles and shafts are saved, to be able to compare it to the
 * updated numbers and conclude whether <code>arrayTieUp</code> should be
 * altered or entirely renewed (used and updated in
 * <code>updateObject()</code>).</li>
 * </ul>
 *
 */
public class TieUpGrid implements Grid
{

    static int[][] arrayTieUp = new int[MainWindow.getInstance().getTreadles()][MainWindow.getInstance().getShafts()];
    static Color tieUpColor = Color.BLACK;
    private int currentTreadles = MainWindow.getInstance().getTreadles();
    private int currentShafts = MainWindow.getInstance().getShafts();

    /**
     * When the TieUpGrid is instantiated (in class <b>MainWindow</b>) all
     * <code>arrayTieUp</code> values are by default set to <b>0</b>, which are
     * default values of new arrays, so this constructor's method body is empty.
     */
    public TieUpGrid()
    {
        // N.A.
    }

    /**
     * Called when the user may have changed weave settings. Called in the
     * <code>gridBoundsUpdater()</code> in the <b>GridPanel class</b>, which is
     * called by <code>updateGridsAfterSettingWeaveSettings()</code>, called by
     * the weave-setting <i>setters</i>. <br>
     * When the number of <code>shafts</code> or <code>treadles</code> is
     * altered, <code>arrayTieUp</code> is completely renewed (no old data
     * restored).
     */
    @Override
    public void updateObject()
    {
        // If no. shafts and/or treadles is changed, renew the TieUpGrid (fresh)
        if ((currentTreadles != MainWindow.getInstance().getTreadles()) || (currentShafts != MainWindow.getInstance().getShafts()))
        {
            arrayTieUp = new int[MainWindow.getInstance().getTreadles()][MainWindow.getInstance().getShafts()];

            // update the instance variables:
            currentTreadles = MainWindow.getInstance().getTreadles();
            currentShafts = MainWindow.getInstance().getShafts();
        }
        // else, do nothing with the TieUpGrid. 
    }

    /**
     * Returns the length of the first dimention of <code>arrayTieUp</code>
     * since it corresponds to the width of the tie up grid.
     *
     * @return int tieUpWidth
     */
    @Override
    public int getGridWidth()
    {
        int tieUpWidth = arrayTieUp.length;
        return tieUpWidth;
    }

    /**
     * Returns the length of the second dimension of <code>arrayTieUp</code>
     * since it corresponds to the height of the tie up grid.
     *
     * @return int tieUpGridHeight
     */
    @Override
    public int getGridHeight()
    {
        int tieUpGridHeight = arrayTieUp[0].length;
        return tieUpGridHeight;
    }

    /**
     * Returns the <code>gridSizeConstant</code> (defined in the <b>MainWindow
     * class</b>) + no. <code>warpThreads*gridSizeConstant</code> +
     * gridSizeConstant since it corresponds to the x-coordiante of the tie up
     * grid.
     *
     * @return int tieUpXcoor
     */
    @Override
    public int getXCoordinate()
    {
        int tieUpXcoor = MainWindow.gridSizeConstant + MainWindow.getInstance().getWarpThreads() * MainWindow.gridSizeConstant + MainWindow.gridSizeConstant;
        return tieUpXcoor;
    }

    /**
     * Returns the <code>gridSizeConstant</code> (defined in the <b>MainWindow
     * class</b>) since it corresponds to the y-coordiante of the tie up grid.
     *
     * @return int tieUpYcoor
     */
    @Override
    public int getYCoordinate()
    {
        int tieUpYcoor = MainWindow.gridSizeConstant;       // set value
        return tieUpYcoor;
    }

    /**
     * The parameters are used to check the corresponding value in the
     * <code>arrayTieUp</code>. If <code>(arrayTieUp[x][y] == 1)</code> it means
     * the corresponding cell should indeed be colored, so the returned color is
     * <code>tieUpColor</code>, instead of null.
     *
     * @param x 'index coordinate' in drawn-grid that corresponds to the first
     * dimension of <code>arrayTieUp</code> <code>arrayWarp</code> value
     * @param y 'index coordinate' in drawn-grid that corresponds to the second
     * dimension of <code>arrayTieUp</code>
     * @return Color: the color the cell should have
     */
    @Override
    public Color getCellColor(int x, int y)
    {
        if (arrayTieUp[x][y] == 1)
        {
            return tieUpColor;
        }
        return null;
    }

    /**
     * Sets the <code>arrayTieUp[x][y]</code> value to either 0 or 1 depending
     * on what value it should have. <i>For example: if it used to be 0, change
     * it to 1.</i>
     *
     * @param x 'index coordinate' in drawn-grid that corresponds to the first
     * dimension of <code>arrayTieUp</code> <code>arrayWarp</code> value
     * @param y 'index coordinate' in drawn-grid that corresponds to the second
     * dimension of <code>arrayTieUp</code>
     */
    @Override
    public void clickCell(int x, int y)
    {
        if (arrayTieUp[x][y] == 0)
        {
            arrayTieUp[x][y] = 1;
        } else
        {
            arrayTieUp[x][y] = 0;
        }
    }
}
