package weben;

/**
 * <h2>Projektbeschreibung </h2>
 *
 * <b>Projekt Name:</b> Webentwurfprogramm<br>
 * <b>Entwickler:</b> Jolien Oosterveer<br>
 * <b>Datum Anfang:</b> 22. November 2021<br>
 * <b>Datum Anfang:</b> 03. Dezember 2021<br>
 * <b>Dozent: André Großkopf</b><br><br>
 *
 * <h1>Projektbeschreibung</h1>
 * Programm für Webentwürfe für Webstühle mit <i>Grafischem User Interface</i>.
 * Der User kann selbst die <i>Webstuhleinstellungen</i> ändern: individuell die
 * Anzahl an
 * <i>Garnen</i> die eingefädelt werden durch die <i>Schäfte</i> (warp threads,
 * shafts). Außerdem kann mann die Anzahl an Schäfte und <i>Tritten</i> ändern,
 * und die <i>Tie up</i>
 * Knoten ändern.
 * <br>
 * Wenn das Program läuft, reagiert es auf User input mit hilfe von
 * <code>ActionListener(ActionEvent ae)</code>, <code>JButton</code> und
 * Mausklicks (<b>MouseListener interface</b>).<br>
 * Der User kann jederzeit den Entwurf inklusive den Webeinstellungen als
 * <b>png datei exportieren</b>.
 *
 * <br><code>
 * Warp Grid - - - - - - - - - - Tie Up Grid<br>
 * _ ___ ___ ___ ___ - - ___ ___ ___ ___  <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * Design Grid - - - - - - - - - Weft Grid<br>
 * _ ___ ___ ___ ___ - - ___ ___ ___ ___  <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br>
 * ||___|___|___|___|| ||___|___|___|___|| <br></code>
 * <br>
 * <ul>
 * <li><b>Warp Grid: </b>(links oben)
 * <ul>
 * <li> x: Anzahl an warp threads (garn durch die Schäfte)
 * <li> y: Anzahl an Schäften ("shafts")
 * <li> <i>Nur eine einzige Zelle pro Spalte kann selektiert werden, weil die
 * warp threads Garne nur durch einen einzelnen Schaft gefädelt werden
 * können.</i>
 * </ul>
 * <li><b>Tie Up Grid: </b>(rechts oben)
 * <ul>
 * <li> x: Anzahl an Tritten ("treadles")
 * <li> y: Anzahl an Schäften
 * <li> <i>Jede Zelle kann (de)selektiert werden.</i>
 * </ul>
 * <li><b>Design Grid (Muster): </b>(links unten)
 * <ul>
 * <li> x: Anzahl an warp threads
 * <li> y: Anzahl an weft threads (Garn durch die Tritte)
 * <li> <i>Jede Zelle ist nur dann gefärbt, wenn die dazugehörigen warp- und
 * weft Zellen gefärbt sind. Wenn dann auch noch die dazugehörige Tie Up Zelle
 * gefärbt ist, ist die Farbe der Muster-Zelle die der warp grid Farbe; sonnst
 * die der weft grid Farbe.</i>
 * </ul>
 * <li><b>Weft Grid: </b>(rechts unten)
 * <ul>
 * <li> x: Anzahl an Tritten
 * <li> y: Anzahl an weft threads
 * <li> <i>Nur eine einzige Zelle pro Zeile kann selektiert werden, weil die
 * weft threads Garne nur durch einen einzelnen Tritt gefädelt werden
 * können.</i>
 * </ul>
 * </ul>
 *
 */
public class Notes
{
    // N.A.
}
