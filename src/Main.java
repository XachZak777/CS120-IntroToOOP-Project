import javax.swing.SwingUtilities;
import ui.QuartoGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuartoGUI::new);
    }
}
