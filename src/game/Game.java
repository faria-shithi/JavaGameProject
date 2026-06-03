package game;

import config.GameConfig;
import javax.swing.*;
import java.awt.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }

    public Game() {
        JFrame frame = new JFrame(GameConfig.WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);   
        frame.setVisible(true);

panel.startGame();
    }
}
