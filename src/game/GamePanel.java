package game;

import config.GameConfig;
import entities.Basket;
import entities.Egg;
import physics.CollisionDetector;
import physics.CollisionResult;
import physics.PhysicsEngine;
import ui.GameRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {

private final Egg          egg;
    private final List<Basket> baskets;
    private final ScoreManager scoreManager;
    private final InputHandler inputHandler;
    private final GameRenderer gameRenderer;
    private final Random       random;

private double groundScreenY;

private boolean hasLandedInBasket;

private GameState gameState;
    private GameLoop  gameLoop;

public GamePanel() {
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        random       = new Random();
        scoreManager = new ScoreManager();
        inputHandler = new InputHandler();
        gameRenderer = new GameRenderer(scoreManager);
        gameState    = GameState.PLAYING;
        groundScreenY = GameConfig.GROUND_Y;
        hasLandedInBasket = false;

        egg     = createEgg();
        baskets = createInitialBaskets();

        addKeyListener(inputHandler);
    }

private Egg createEgg() {
        return new Egg(GameConfig.EGG_START_X,
                       GameConfig.GROUND_Y - GameConfig.EGG_HEIGHT);
    }

private List<Basket> createInitialBaskets() {
        List<Basket> list = new ArrayList<>(GameConfig.BASKET_COUNT);
        
        list.add(new Basket( 55, 400, 115,  1));
        list.add(new Basket(490, 400, 148, -1));
        
        list.add(new Basket(210, 255, 143,  1));
        
        list.add(new Basket(440, 120, 172, -1));
        return list;
    }

public void tick(double deltaTime) {
        if (gameState == GameState.GAME_OVER) {
            if (inputHandler.consumeRestart()) restart();
            return;
        }

        handleInput(deltaTime);
        applyEggPhysics(deltaTime);
        applyScrolling();
        moveBaskets(deltaTime);
        updateEggRidingPosition(deltaTime);
        resolveCollisions();
        checkGameOver();
    }

private void handleInput(double deltaTime) {
        
        if (inputHandler.consumeJump()) {
            if (egg.isGrounded()) {
                egg.jump();
            } else if (egg.isInBasket()) {
                egg.jump();
            }
        }

if (egg.isGrounded()) {
            double dx = 0;
            if (inputHandler.isMovingLeft())  dx -= GameConfig.EGG_MOVE_SPEED;
            if (inputHandler.isMovingRight()) dx += GameConfig.EGG_MOVE_SPEED;
            if (dx != 0) egg.moveHorizontal(dx * deltaTime);
        }
    }

    private void applyEggPhysics(double deltaTime) {
        if (egg.isAirborne()) {
            PhysicsEngine.applyGravity(egg, deltaTime);
            PhysicsEngine.applyVerticalVelocity(egg, deltaTime);
        }
    }

private void applyScrolling() {
        if (!egg.isAirborne() || egg.getVelocityY() >= 0) return;
        if (egg.getY() >= GameConfig.SCROLL_THRESHOLD)    return;

        double shift = GameConfig.SCROLL_THRESHOLD - egg.getY();
        egg.setY(GameConfig.SCROLL_THRESHOLD);

for (Basket basket : baskets) {
            basket.setY(basket.getY() + shift);
        }

groundScreenY += shift;

baskets.removeIf(b -> b.getY() > GameConfig.WINDOW_HEIGHT + 80);

generateBaskets();
    }

private void generateBaskets() {
        double highestY = highestBasketY();
        while (highestY > GameConfig.BASKET_GEN_MARGIN) {
            highestY = spawnBasketAbove(highestY);
        }
    }

    private double highestBasketY() {
        return baskets.stream()
                      .mapToDouble(Basket::getY)
                      .min()
                      .orElse(GameConfig.WINDOW_HEIGHT);
    }

    private double spawnBasketAbove(double currentHighestY) {
        int    gap   = GameConfig.BASKET_GAP_MIN
                     + random.nextInt(GameConfig.BASKET_GAP_MAX - GameConfig.BASKET_GAP_MIN + 1);
        double newY  = currentHighestY - gap;
        double speed = GameConfig.BASKET_MIN_SPEED
                     + random.nextDouble() * (GameConfig.BASKET_MAX_SPEED - GameConfig.BASKET_MIN_SPEED);
        int    dir   = random.nextBoolean() ? 1 : -1;
        double startX = random.nextInt(GameConfig.WINDOW_WIDTH - GameConfig.BASKET_WIDTH);
        baskets.add(new Basket(startX, newY, speed, dir));
        return newY;
    }

    private void moveBaskets(double deltaTime) {
        for (Basket basket : baskets) {
            basket.update(deltaTime);
        }
    }

    private void updateEggRidingPosition(double deltaTime) {
        egg.update(deltaTime);
    }

    private void resolveCollisions() {
        if (!egg.isAirborne()) return;

        CollisionResult result = CollisionDetector.check(egg, baskets);
        if (result.isBasketLand()) {
            egg.landInBasket(result.getBasket());
            hasLandedInBasket = true;
            scoreManager.addLanding();
        }
        
    }

    private void checkGameOver() {
        if (!egg.isAirborne()) return;

if (groundScreenY <= GameConfig.WINDOW_HEIGHT
                && egg.getBottom() >= groundScreenY) {
            if (hasLandedInBasket) {
                triggerGameOver();
            } else {
                egg.landOnGround(groundScreenY);
            }
            return;
        }

if (egg.getY() > GameConfig.WINDOW_HEIGHT + 30) {
            triggerGameOver();
        }
    }

    private void triggerGameOver() {
        egg.crack();
        gameState = GameState.GAME_OVER;
    }

private void restart() {
        groundScreenY = GameConfig.GROUND_Y;
        hasLandedInBasket = false;
        gameState     = GameState.PLAYING;
        scoreManager.reset();

        egg.reset(GameConfig.EGG_START_X,
                  GameConfig.GROUND_Y - GameConfig.EGG_HEIGHT);

        baskets.clear();
        baskets.addAll(createInitialBaskets());
    }

@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);

        gameRenderer.render(g2, egg, baskets, (int) groundScreenY, gameState);
    }

public void startGame() {
        gameLoop = new GameLoop(this);
        gameLoop.start();
        requestFocusInWindow();
    }
}
