import game.Game;
import javax.swing.SwingUtilities;

public class BasketEgg {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
