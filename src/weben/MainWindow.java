package weben;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Class that brings everything together, the whole code.<br>
 * <code>MainFrame</code> is initialized and all <i>panels</i> are set and shown
 * as <i>drawn-grids</i>.
 * <br>
 * In this class, the <code>JFrame</code> is made with some alterations using
 * <code>MainFrame</code> (in the MainFrame class) and all four graphical boxes
 * are made here, using the four inputs that determine how many shafts and
 * treadles are used, and how many warp and weft threads will be used to weave.
 * <br>
 * no. shafts, treadles and threads DEFAULT is set in <code>main()</code>
 * <i>..and is able to change according to user input</i>. There is also a user
 * response to a certain button click that allows the user to export the design
 * as a png-file, at a desired directory. The method <code>los()</code> adds the
 * new panels (graphics) and activates the frame itself with the method
 * <code>showFrame()</code>.
 *
 */
public class MainWindow
{

    // SINGLETON: this static variable contains the only instance of MainWindow: because it is static it can be accessed from all other Classes in this Project
    private static MainWindow instance = null;

    public static MainWindow getInstance()
    {
        return instance;
    }

    // instance variables set in MainWindow constructor with parameters from Startklasse main()
    private int warpThreads;
    private int shafts;
    private int treadles;
    private int weftThreads;

    // instance and static variables for GUI:
    private MainFrame frame;

    private GridPanel panelWarp;          // left top 
    private GridPanel panelTieUp;         // right top
    private GridPanel panelDesign;        // left bottom  
    private GridPanel panelWeft;          // right bottom

    private final JLabel labelWarpGrid, labelTieUpGrid, labelDesignGrid, labelWeftGrid;
    private final JLabel labelQuestionShafts, labelQuestionWarpThreads, labelQuestionTreadles, labelQuestionWeftThreads;
    private final JTextField textShafts, textWarpThreads, textTreadles, textWeftThreads;
    private final JButton buttonAnswerShafts, buttonAnswerWarpThreads, buttonAnswerTreadles, buttonAnswerWeftThreads;
    private final JButton buttonExportPNG;
    private final JPanel container; // = new JPanel();
    private final JScrollPane scrPane; // = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // static final constants declared and initialized:
    static final int gridSizeConstant = 20;
    static final int gridBorderThickness = 1;

    // getters and setters of the instance variables warpThreads, shafts, treadles and weftThreads
    public void setWarpThreads(int warpThreads)
    {
        this.warpThreads = warpThreads;
        updateGridsAfterSettingWeaveSettings();
    }

    public int getWarpThreads()
    {
        return warpThreads;
    }

    public void setShafts(int shafts)
    {
        this.shafts = shafts;
        updateGridsAfterSettingWeaveSettings();
    }

    public int getShafts()
    {
        return shafts;
    }

    public void setTreadles(int treadles)
    {
        this.treadles = treadles;
        updateGridsAfterSettingWeaveSettings();
    }

    public int getTreadles()
    {
        return treadles;
    }

    public void setWeftThreads(int weftThreads)
    {
        this.weftThreads = weftThreads;
        updateGridsAfterSettingWeaveSettings();
    }

    public int getWeftThreads()
    {
        return weftThreads;
    }

    /**
     * Method to update the grids internally and on the GUI after one of the
     * weave settings (no. <code>warpThreads, shafts, treadles</code> and/or
     * <code>weftThreads</code>) has changed. It:
     * <ol>
     * <li>updates all GUI-grid bounds by calling
     * <code>gridBoundsUpdater()</code> of the <b>GridPanel class</b> on the
     * GridPanel-type instance variables of MainWindow-object
     * <code>g</code></li>
     * <li>updates all GUI-grid labels with <code>gridLabelUpdater()</code>
     * </li>
     * <li>repaints all GUI-grids by calling <code>repaint()</code> of the
     * <b>GridPanel class</b> on the GridPanel-type instance variables of
     * MainWindow-object <code>g</code></li>
     * <li>updates the JPanel which houses the GUI-grids and their labels</li>
     * </ol>
     */
    public void updateGridsAfterSettingWeaveSettings()
    {
        // update all grid bounds
        panelWarp.gridBoundsUpdater();
        panelTieUp.gridBoundsUpdater();
        panelDesign.gridBoundsUpdater();
        panelWeft.gridBoundsUpdater();

        // update all grid texts
        gridLabelUpdater();

        // repaint all grids
        panelWarp.repaint();
        panelTieUp.repaint();
        panelDesign.repaint();
        panelWeft.repaint();

        // update JPanel
        Rectangle rectanglepanelweft = panelWeft.getBounds();
        container.setPreferredSize(new Dimension((int) rectanglepanelweft.getMaxX() + gridSizeConstant, (int) rectanglepanelweft.getMaxY() + gridSizeConstant));
        container.revalidate();
    }

    /**
     * Method to update all GUI-grid labels (e.g. "Warp grid" above the warp
     * grid), called when one of the weave settings has changed and the
     * <code>updateGridsAfterSettingWeaveSettings()</code> was called. Resets
     * the bounds of the grid labels using <code>setBounds(...)</code> for all
     * grid labels except the <code>labelWarpGrid</code> since that one never
     * moves.
     */
    public void gridLabelUpdater()
    {
        int heightUI = 30;
        int heightWeft = this.weftThreads * gridSizeConstant + gridBorderThickness;
        int heightDesign = heightWeft;

        labelWarpGrid.setBounds(panelWarp.grid.getXCoordinate(), panelWarp.grid.getYCoordinate() - heightUI, 100, heightUI);
        labelTieUpGrid.setBounds(panelTieUp.grid.getXCoordinate(), panelTieUp.grid.getYCoordinate() - heightUI, 100, heightUI);
        labelDesignGrid.setBounds(panelDesign.grid.getXCoordinate(), panelDesign.grid.getYCoordinate() + heightDesign, 100, heightUI);
        labelWeftGrid.setBounds(panelWeft.grid.getXCoordinate(), panelWeft.grid.getYCoordinate() + heightWeft, 100, heightUI);
    }

    /**
     * Method to excecute <code>panelDesign.repaint();</code> from outside this
     * (MainWindow) class. Required since a mouse click in another grid vs the
     * Design grid, may cause a direct change in the Design grid appearance:
     * called in the <code>mouseClicked()</code> method in the <b>GridPanel
     * class</b>.
     */
    public void repaintPanelDesign()
    {
        panelDesign.repaint();
    }

    // ===========================================================
    /**
     * Constructor with the weaving parameters: warpThreads, shafts, treadles
     * and weftThreads. It creates many connections in between the classes of
     * this program, creates GUI elements and includes
     * <code>ActionListener</code>s to respond to clicks on the JButtons, which
     * allows the user to change the weave settings!<br>
     * <i>For detailed information:</i>
     * <ul>
     * <li>Since only one <b>MainWindow class</b> object should excist during a
     * run, the first thing the constructor checks is whether that is the case
     * -with help of <b><i>Singleton</i></b> (by allowing to call the current
     * object <code>g</code> with "<code>instance</code>"). </li>
     * <li>Weave settings are instantiated with the constructor's
     * parameters</li>
     * <li>the GUI <b>MainFrame</b> object is instantiated incl. title</li>
     * <li>all corresponding <code>Jlabels, Jbuttons, JtextFields, JPanel and
     * JScrollPane</code> are instantiated, labels and buttons incl. text.
     * <i>A JPanel is made with a corresponding JScrollPane to later, in
     * <code>los()</code>, add the GUI panel-grids and their labels to group
     * together in this JPanel "<code>container</code>" so the user does not
     * have to enlarge the JFrame or buy a huge screen, but can scroll over the
     * enlarged grids after increase the weave-settings.</i></li>
     * <li>the GridPanel-type instance variables of MainWindow-object
     * <code>g</code> (e.g. <code>panelWarp</code>) are now instantiated by the
     * <b>GridPanel class</b>: so they hold a reference to a new GridPanel
     * object (with e.g. <code>panelWarp = new GridPanel();</code></li>
     * <li><b>Grid interface</b>-implementing objects are declared and
     * instantiated with their corresponding class (e.g.
     * <code>Grid warpGrid = new WarpGrid();</code>) so they store a reference
     * to an object of that class</li>
     * <li>Now, the <code>grid</code> instance variable value of the GridPanel
     * object the GridPanel-type MainWindow instance variable (e.g.
     * <code>panelWarp</code>) references to, is set to the previously made
     * Grid-type variable (e.g. <code>warpGrid</code>) which has that reference
     * to an object of the grid class (e.g. <b>WarpGrid</b>)</li>
     * <li>all visual GUI settings are set: the bounds for all
     * <b>GridPanel</b>-type panels (e.g. <code>panelWarp</code> and all
     * JLabels, JTextFields, JButtons and the JScrollPane. Also the color of the
     * Export png file button</li>
     * <li><code>ActionListener</code>s for each of the five JButtons: those for
     * changing the weave settings check the JTextField user inserted value and
     * respond accordingly by calling the corresponding setter method on the
     * weave setting! And the Export-Button ActionListener generates an .jpg
     * image and saves it with a certain name in a certain folder the user can
     * choose.</li>
     * </ul>
     *
     * @param warpThreads number of threads that go through the schafts
     * @param shafts number of shafts (Schaefte)
     * @param treadles number of thrads that go through the treadles
     * @param weftThreads number of treadles (Tritte)
     * @throws Exception an Exception may be thrown
     */
    public MainWindow(int warpThreads, int shafts, int treadles, int weftThreads) throws Exception
    {
        // SINGLETON
        if (instance != null)
        {
            throw new Exception("Instance cannot be made >1");
        }
        instance = this;        // this = the current object 

        // weave setting instance variables instantiated by the constructor's parameters (default):
        this.warpThreads = warpThreads;
        this.shafts = shafts;
        this.treadles = treadles;
        this.weftThreads = weftThreads;

        // instantiate frame: 
        frame = new MainFrame("Weaving pattern maker");

        // instantiate MainFrame-related elements: JLabels, JButtons, JTextFields, JPanel, JScrollPane
        labelWarpGrid = new JLabel("Warp grid");
        labelTieUpGrid = new JLabel("Tie up grid");
        labelDesignGrid = new JLabel("Design grid");
        labelWeftGrid = new JLabel("Weft grid");
        labelQuestionShafts = new JLabel("Number of shafts (2-24):");
        labelQuestionWarpThreads = new JLabel("Number of warp threads (2-50):");
        labelQuestionTreadles = new JLabel("Number of treadles (2-10):");
        labelQuestionWeftThreads = new JLabel("Number of weft threads (2-50):");

        buttonAnswerShafts = new JButton("Submit");
        buttonAnswerWarpThreads = new JButton("Submit");
        buttonAnswerTreadles = new JButton("Submit");
        buttonAnswerWeftThreads = new JButton("Submit");
        buttonExportPNG = new JButton("Export image [png]");

        textShafts = new JTextField();
        textWarpThreads = new JTextField();
        textTreadles = new JTextField();
        textWeftThreads = new JTextField();

        container = new JPanel();
        scrPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // the GridPanel-type instance variables are instantiated WITH the GridPanel class so they get a reference of the GridPanel class assigned (reference to a new GridPanel object)
        panelWarp = new GridPanel();      // assigns to INSTANCE VARIABLE OF MainWindow "panelWarp" a GridPanel reference 
        panelTieUp = new GridPanel();
        panelDesign = new GridPanel();
        panelWeft = new GridPanel();

        // Declaration and instantiation of Grid-objects of specific Grid-implementing-class types:
        Grid warpGrid = new WarpGrid();         // local variable warpGrid it is of type grid and stores a reference to a WarpGrid() object.
        Grid tieUpGrid = new TieUpGrid();
        Grid designGrid = new DesignGrid();
        Grid weftGrid = new WeftGrid();

        // set the instance variable 'grid' of the GridPanel object that 'panelWarp' references to, to the local variable 'warpGrid'. 
        // so now: the INSTANCE VARIABLE OF MainWindow WITH A GridPanel REFERENCE "panelWarp" which has a certain 'grid' value ('grid' = AN INSTANCE VARIABLE OF GridPanel) namely 'warpGrid' (of type Grid that references to an object of the WarpGrid class.
        panelWarp.grid = warpGrid;
        panelTieUp.grid = tieUpGrid;
        panelDesign.grid = designGrid;
        panelWeft.grid = weftGrid;

        // Set visual GUI settings:
        // Calculate the width and height of the 'panels': 
        int widthTieUp = this.treadles * gridSizeConstant + gridBorderThickness;
        int heightTieUp = this.shafts * gridSizeConstant + gridBorderThickness;
        int widthWarp = this.warpThreads * gridSizeConstant + gridBorderThickness;
        int heightWarp = heightTieUp;
        int widthWeft = widthTieUp;
        int heightWeft = this.weftThreads * gridSizeConstant + gridBorderThickness;
        int widthDesign = widthWarp;
        int heightDesign = heightWeft;

        // Calculate the coordinates of the 'panels':
        int xCoordinateWarp = gridSizeConstant;     // a set value
        int yCoordinateWarp = gridSizeConstant;     // a set value (NEW due to added 'scanner' labels)
        int xCoordinateTieUp = xCoordinateWarp + widthWarp + gridSizeConstant;
        int yCoordinateTieUp = gridSizeConstant;     // a set value (NEW due to added 'scanner' labels)
        int xCoordinateDesign = gridSizeConstant;     // a set value 
        int yCoordinateDesign = yCoordinateWarp + heightWarp + gridSizeConstant;
        int xCoordinateWeft = xCoordinateDesign + widthWarp + gridSizeConstant;
        int yCoordinateWeft = yCoordinateTieUp + heightWarp + gridSizeConstant;

        // Set the bounds of the 'panels': 
        panelWarp.setBounds(xCoordinateWarp, yCoordinateWarp, widthWarp, heightWarp);
        panelTieUp.setBounds(xCoordinateTieUp, yCoordinateTieUp, widthTieUp, heightTieUp);
        panelDesign.setBounds(xCoordinateDesign, yCoordinateDesign, widthDesign, heightDesign);
        panelWeft.setBounds(xCoordinateWeft, yCoordinateWeft, widthWeft, heightWeft);

        // ===========================================================
        int xCoorUI = 50;
        int heightUI = 30;
        // ===========================================================
        gridLabelUpdater();

        // Set the bounds of the UI question labels
        int qWidth = 200;
        labelQuestionShafts.setBounds(xCoorUI, 10 + 0 * heightUI, qWidth, heightUI);
        labelQuestionWarpThreads.setBounds(xCoorUI, 10 + 1 * heightUI, qWidth, heightUI);
        labelQuestionTreadles.setBounds(xCoorUI, 10 + 2 * heightUI, qWidth, heightUI);
        labelQuestionWeftThreads.setBounds(xCoorUI, 10 + 3 * heightUI, qWidth, heightUI);

        // Set the bounds of the UI questions TextFields
        int textWidth = 30;
        textShafts.setBounds(xCoorUI + qWidth, 10 + 0 * heightUI, textWidth, heightUI);
        textWarpThreads.setBounds(xCoorUI + qWidth, 10 + 1 * heightUI, textWidth, heightUI);
        textTreadles.setBounds(xCoorUI + qWidth, 10 + 2 * heightUI, textWidth, heightUI);
        textWeftThreads.setBounds(xCoorUI + qWidth, 10 + 3 * heightUI, textWidth, heightUI);

        // Set the bounds of the UI question buttons
        buttonAnswerShafts.setBounds(xCoorUI + qWidth + textWidth, 10 + 0 * heightUI, 100, heightUI);
        buttonAnswerWarpThreads.setBounds(xCoorUI + qWidth + textWidth, 10 + 1 * heightUI, 100, heightUI);
        buttonAnswerTreadles.setBounds(xCoorUI + qWidth + textWidth, 10 + 2 * heightUI, 100, heightUI);
        buttonAnswerWeftThreads.setBounds(xCoorUI + qWidth + textWidth, 10 + 3 * heightUI, 100, heightUI);

        // Set the bounds and color of the Export png file button
        buttonExportPNG.setBounds(50, 700, 200, 30);
        buttonExportPNG.setForeground(Color.MAGENTA);

        // Set the JScrollPane bounds
        scrPane.setBounds(50, 160, 700, 500);

        // Set GUI button responses:
        buttonAnswerShafts.addActionListener((ActionEvent ae) ->
        {
            System.out.println("Shaft button clicked");
            String inputText = textShafts.getText();

            // call method with common value checks
            if (commonActions(inputText, 2, 24))
            {
                int inputTextToInt = Integer.parseInt(inputText);
                setShafts(inputTextToInt);
                System.out.println("Given shaft value: " + inputText);
            }
        });
        buttonAnswerWarpThreads.addActionListener((ActionEvent ae) ->
        {
            System.out.println("Warp thread button clicked");
            String inputText = textWarpThreads.getText();

            // call method with common value checks
            if (commonActions(inputText, 2, 50))
            {
                int inputTextToInt = Integer.parseInt(inputText);
                setWarpThreads(inputTextToInt);
                System.out.println("Given warp thread value: " + inputText);
            }
        });
        buttonAnswerTreadles.addActionListener((ActionEvent ae) ->
        {
            System.out.println("Treadle button clicked");
            String inputText = textTreadles.getText();

            // call method with common value checks
            if (commonActions(inputText, 2, 10))
            {
                int inputTextToInt = Integer.parseInt(inputText);
                setTreadles(inputTextToInt);
                System.out.println("Given treadle value: " + inputText);
            }
        });
        buttonAnswerWeftThreads.addActionListener((ActionEvent ae) ->
        {
            System.out.println("Weft thread button clicked");
            String inputText = textWeftThreads.getText();

            // call method with common value checks
            if (commonActions(inputText, 2, 50))
            {
                int inputTextToInt = Integer.parseInt(inputText);
                setWeftThreads(inputTextToInt);
                System.out.println("Given weft thread value: " + inputText);
            }
        });

        buttonExportPNG.addActionListener((ActionEvent ae) ->
        {
            System.out.println("Export png button clicked");
            container.setSize(container.getPreferredSize());
            BufferedImage image = new BufferedImage(container.getWidth(), container.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();      // swing components can paint themselves onto this Graphics2D image
            container.paint(g);
            g.dispose();
            try
            {
                JFileChooser dialog = new JFileChooser();
                // let user choose a file name and directory:
                int result = dialog.showSaveDialog(frame);
                // set the file name and directory if it was choosen:
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File file = dialog.getSelectedFile();
                    // convert the path of the file to a string so .png can be added and the user does not have to
                    String filepathString = file.getAbsolutePath();
                    ImageIO.write(image, "png", new File(filepathString + ".png"));     // may throw IOException
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    // ===========================================================
    /**
     * Method called by all weave-settings <code>ActionListener</code>s in the
     * <b>MainWindow class</b> constructor to check for all potential false user
     * inputs that all weave-settings have in common. When the user input was
     * not a int-number value, or negative, too small or large (weave-setting
     * dependent: use of method parameters), a message will appear in the
     * NetBeans Output and the weave-setting is not updated.
     *
     * @param textInput characters the user typed in the JTextField
     * @param min minimum value the variable may have
     * @param max maximum value the variable may have
     * @return boolean true if value is acceptable
     */
    private boolean commonActions(String textInput, int min, int max)
    {
        // 
        boolean OK = true;
        try
        {
            {
                int inputTextToInt = Integer.parseInt(textInput);
                if (textInput == "")
                {
                    System.out.println("Empty. Retry");
                    OK = false;
                }
                if (inputTextToInt <= 0)
                {
                    System.out.println("Zero or negative. Retry.");
                    OK = false;
                }
                if ((inputTextToInt > 0) && (inputTextToInt < min))
                {
                    System.out.println("Too little! Retry.");
                    OK = false;
                }
                if (inputTextToInt > max)
                {
                    System.out.println("Too much! Retry.");
                    OK = false;
                }
            }
        } catch (NumberFormatException nfe)
        {
            System.out.println("Invalid int. Retry.");
            OK = false;
        }
        return OK;
    }

    /**
     * Method called on the <b>MainWindow class</b> object <code>g</code> in
     * <code>main()</code> to do last JPanel and JScrollPane settings, add the
     * items to the JPanel ("<code>container</code>"), then add this JPanel to
     * the MainFrame, along with all other MainFrame elements. Last but not
     * least: call the method <code>showFrame()</code> on the
     * <b>MainFrame</b>-object <code>frame</code> to activate the JFrame-related
     * java libraries to work behind the scenes; set everything in motion(cannot
     * track the steps anymore with the debugger). <br><br>
     * <i>Extra infos:</i> <br>
     * The <code>container</code> dimensions are updated when
     * <code>updateGridsAfterSettingWeaveSettings()</code> is called (which
     * happens each time a weave setting setter is called, after a
     * weave-setting-button click), by reading out the bounds of the Weft grid
     * panel <code>panelWeft</code> with <code>getBounds()</code>. The four
     * bound values this method returns are stored in a <code>Rectangle</code>
     * object. The <code>getMaxX()</code> and <code>getMaxY()</code> of it give
     * the x and y coordinate of the right-bottom-position of the GridPanel
     * object: used to set the <code>container</code> size.<br>
     * The <code>container</code> layout is set to null to avoid unwelcome
     * automatic layout settings.<br>
     * The JScrollPane <code>scrPane</code> borders are deleted.
     */
    public void los()
    {
        // Avoid unwelcome automatic layout settings:
        container.setLayout(null);

        // Calculate and set JPanel size:
        Rectangle rectanglepanelweft = panelWeft.getBounds();
        container.setPreferredSize(new Dimension((int) rectanglepanelweft.getMaxX(), (int) rectanglepanelweft.getMaxY()));

        // Get rid of the JScrollPane border:
        scrPane.setBorder(null);

        // Add the grids and their titels to the JPanel 'container' that can add a scrollbar (both vertical as horizontal)
        container.add(panelWarp);
        container.add(panelTieUp);
        container.add(panelDesign);
        container.add(panelWeft);
        container.add(labelWarpGrid);
        container.add(labelTieUpGrid);
        container.add(labelDesignGrid);
        container.add(labelWeftGrid);

        // Add the JPanel container to the frame by adding the JScrollPane scrPane:
        frame.add(scrPane);

        // Add all other GUI elements to the frame:
        frame.add(labelQuestionShafts);
        frame.add(labelQuestionWarpThreads);
        frame.add(labelQuestionTreadles);
        frame.add(labelQuestionWeftThreads);
        frame.add(textShafts);
        frame.add(textWarpThreads);
        frame.add(textTreadles);
        frame.add(textWeftThreads);
        frame.add(buttonAnswerShafts);
        frame.add(buttonAnswerWarpThreads);
        frame.add(buttonAnswerTreadles);
        frame.add(buttonAnswerWeftThreads);
        frame.add(buttonExportPNG);

        // Make the GUI appear to the user:
        frame.showFrame();
    }

}
