package weben;

import javax.swing.JFrame;

/**
 * Individualized <b>JFrame</b>.
 */
public class MainFrame extends JFrame
{

    /**
     * To set the edges, enums (collection of constants) are used.
     */
    public enum Edges
    {
        TOP, LEFT, BOTTOM, RIGHT
    }

    // --------------------------------------------------------------
    /**
     * with <code>this("");</code> it calls the
     * <code>MainFrame(String title)</code> constructor below. This way you can
     * use the <b>MainFrame</b> constructor without parameters, to call the one
     * with parameters, with an empty string.
     */
    public MainFrame()
    {
        this("");
    }

    // --------------------------------------------------------------
    /**
     * The edges of the personalized JFrame (= MainFrame) are made using the
     * enums for the four edges, made above. The method <code>getInsets()</code>
     * we set the tickness of the input edge side. <code>Insets</code> objects
     * (from java.lang.Object and java.awt.Insets) represnt container borders:
     * the space that a container must leave at each of its edges. This space
     * can be a border, black space, or title.
     *
     * @param edge = Sides of which their thickness should be determined
     * @return Thickness of the sides in pixels
     */
    public int thickness(Edges edge)
    {
        int back = 0;

        switch (edge)
        {
            case TOP:   // we called the enum TOP because...
                back = this.getInsets().top;    // Java knows it as the int 'top': the inset from the top.
                break;
            case LEFT:
                back = this.getInsets().left;
                break;
            case BOTTOM:
                back = this.getInsets().bottom;
                break;
            case RIGHT:
                back = this.getInsets().right;
                break;
        }
        return back;
    }

    // --------------------------------------------------------------
    /**
     * I use this constructor in the <b>MainWindow</b> class with a
     * <code>title</code>.
     *
     * @param title Title of the constructed Frame.
     */
    public MainFrame(String title)
    {
        super(title);
        this.setLayout(null);   // to prevent using the default layout
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // --------------------------------------------------------------
    /**
     *
     */
    public void showFrame()
    {
        this.setBounds(1000, 100, 800, 800);
        this.setVisible(true);
    }

    // --------------------------------------------------------------
}
