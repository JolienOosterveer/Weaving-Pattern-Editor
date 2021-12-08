package weben;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

/**
 * The class with the <b>main method</b> which is excucuted when the program
 * runs: to start the program.
 */
public class MainClass
{

    // --------------------------------------------------------------
    /**
     * The <code>main-</code>method is static so the compiler can call it
     * without the creation of an object of <b>MainClass</b>.<br>
     * It starts the program by declaring a new <b>MainWindow class</b> object
     * <code>g</code> and instantiating it with default values of the object
     * <code>g</code>'s instance variables warpThreads, shafts, treadles and
     * weftThreads, nameley 10, 5, 5 and 10 respectively. By calling the
     * <code>los()</code> method on the <code>MainWindow</code> class object
     * <code>g</code>, the individualized graphical user interface (GUI)
     * <code>JFrame</code> is created and shown when this project is run.
     *
     * @param args ignored by this program (would only be used in the Command
     * Prompt)
     * @throws Exception because the <b>MainWindow class</b> constructor that is
     * used to instantiate object <code>g</code> in this
     * <code>main</code>-method may throw an Exception.
     */
    public static void main(String[] args) throws Exception
    {
        FlatArcOrangeIJTheme.setup();
        MainWindow g = new MainWindow(10, 5, 5, 15);
        g.los();

    }

    // --------------------------------------------------------------
}
