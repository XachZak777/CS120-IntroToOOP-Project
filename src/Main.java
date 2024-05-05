import javax.swing.SwingUtilities;
import ui.QuartoGUI;
/**
 * Main Class for execution
 */
public class Main {
    /**
     * // Execute the QuartoGUI constructor within the event dispatch thread
     * @param args Argument (Not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuartoGUI::new);
    }
}
