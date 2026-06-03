package ui;

import config.GameConfig;
import entities.Basket;
import entities.Egg;
import game.GameState;
import game.ScoreManager;

import java.awt.*;
import java.awt.geom.*;
import java.util.List;

public class GameRenderer {

    private final ScoreDisplay scoreDisplay;
    private final ScoreManager scoreManager;

    private GradientPaint skyGradient;
    private int           cachedW = -1;
    private int           cachedH = -1;

    private static final int[][] CLOUDS = {
        {  80, 60, 110, 45 },
        { 280, 35,  90, 38 },
        { 520, 75, 130, 50 },
        { 680, 40,  95, 40 }
    };

    public GameRenderer(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        this.scoreDisplay = new ScoreDisplay();
    }

public void render(Graphics2D g, Egg egg, List<Basket> baskets,
                       int groundScreenY, GameState gameState) {
        rebuildGradients();
        renderBackground(g);
        renderClouds(g);
        renderBaskets(g, egg, baskets);
        renderEgg(g, egg);
        renderOccupiedBasketOverlay(g, egg);
        renderGround(g, groundScreenY);
        scoreDisplay.render(g, scoreManager.getScore());
        renderControlHints(g, egg);

        if (gameState == GameState.GAME_OVER) {
            renderGameOver(g, scoreManager.getScore());
        }
    }

private void renderBackground(Graphics2D g) {
        g.setPaint(skyGradient);
        g.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    }

    private void renderClouds(Graphics2D g) {
        for (int[] c : CLOUDS) drawCloud(g, c[0], c[1], c[2], c[3]);
    }

    private void drawCloud(Graphics2D g, int cx, int cy, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 190));
        g2.fillOval(cx,          cy + h / 4, (int)(w * 0.55), (int)(h * 0.75));
        g2.fillOval(cx + w / 4,  cy,         (int)(w * 0.55), (int)(h * 0.85));
        g2.fillOval(cx + w / 2,  cy + h / 4, (int)(w * 0.50), (int)(h * 0.75));
        g2.dispose();
    }

    private void renderBaskets(Graphics2D g, Egg egg, List<Basket> baskets) {
        Basket occupied = egg.getCurrentBasket();
        for (Basket basket : baskets) {
            if (basket != occupied) basket.render(g); 
        }
    }

    private void renderEgg(Graphics2D g, Egg egg) {
        egg.render(g); 
    }

private void renderOccupiedBasketOverlay(Graphics2D g, Egg egg) {
        Basket occupied = egg.getCurrentBasket();
        if (occupied != null) {
            occupied.renderWalls(g);
        }
    }

    private void renderGround(Graphics2D g, int groundScreenY) {
        if (groundScreenY > GameConfig.WINDOW_HEIGHT) return; 

        int gx = 0;
        int gy = groundScreenY;
        int gw = GameConfig.WINDOW_WIDTH;
        int gh = GameConfig.WINDOW_HEIGHT - gy;

        Graphics2D g2 = (Graphics2D) g.create();

        GradientPaint grass = new GradientPaint(0, gy, new Color(72, 160, 55),
                                                0, gy + 14, new Color(55, 130, 40));
        g2.setPaint(grass);
        g2.fillRect(gx, gy, gw, 14);

        GradientPaint earth = new GradientPaint(0, gy + 14, new Color(110, 75, 40),
                                                0, gy + gh,  new Color( 80, 50, 25));
        g2.setPaint(earth);
        g2.fillRect(gx, gy + 14, gw, gh - 14);

        g2.setColor(new Color(120, 210, 80, 120));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(gx, gy + 1, gx + gw, gy + 1);

        g2.dispose();
    }

    private void renderControlHints(Graphics2D g, Egg egg) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(255, 255, 255, 100));
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        if (egg.isGrounded()) {
            g2.drawString("â† â†’ / A D  to move   |   SPACE / W / â†‘  to jump", 14, GameConfig.WINDOW_HEIGHT - 10);
        } else {
            g2.drawString("SPACE / W / â†‘  to jump", 14, GameConfig.WINDOW_HEIGHT - 10);
        }
        g2.dispose();
    }

private void renderGameOver(Graphics2D g, int score) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

g2.setColor(new Color(0, 0, 0, 165));
        g2.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);

        int cx = GameConfig.WINDOW_WIDTH  / 2;
        int cy = GameConfig.WINDOW_HEIGHT / 2;

int cardW = 360, cardH = 220;
        int cardX = cx - cardW / 2;
        int cardY = cy - cardH / 2;

        g2.setColor(new Color(20, 10, 5, 230));
        g2.fillRoundRect(cardX, cardY, cardW, cardH, 20, 20);

        g2.setColor(new Color(200, 80, 40, 180));
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(cardX, cardY, cardW, cardH, 20, 20);

g2.setFont(new Font("SansSerif", Font.BOLD, 46));
        g2.setColor(new Color(220, 60, 30));
        drawCentred(g2, "GAME OVER", cx, cardY + 60);

g2.setFont(new Font("SansSerif", Font.BOLD, 26));
        g2.setColor(new Color(255, 220, 80));
        drawCentred(g2, "Score: " + score, cx, cardY + 108);

g2.setColor(new Color(200, 80, 40, 100));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(cardX + 30, cardY + 128, cardX + cardW - 30, cardY + 128);

g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g2.setColor(new Color(200, 200, 200));
        drawCentred(g2, "Press  R  to play again", cx, cardY + 165);

int eggX = cx - GameConfig.EGG_WIDTH  / 2;
        int eggY = cardY + cardH + 12;
        
        drawCrackedEggIcon(g2, eggX, eggY);

        g2.dispose();
    }

    private void drawCentred(Graphics2D g, String text, int cx, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = cx - fm.stringWidth(text) / 2;
        g.drawString(text, x, y);
    }

private void drawCrackedEggIcon(Graphics2D g, int ix, int iy) {
        int iw = GameConfig.EGG_WIDTH;
        int ih = GameConfig.EGG_HEIGHT;

        RadialGradientPaint body = new RadialGradientPaint(
            new Point2D.Float(ix + iw * 0.38f, iy + ih * 0.30f),
            Math.max(iw, ih) * 0.70f,
            new float[]{ 0.0f, 1.0f },
            new Color[]{ new Color(215, 205, 175), new Color(175, 150, 90) }
        );
        g.setPaint(body);
        g.fillOval(ix, iy, iw, ih);
        g.setColor(new Color(100, 75, 25));
        g.setStroke(new BasicStroke(2f));
        g.drawOval(ix, iy, iw, ih);

        int cx = ix + iw / 2;
        int cy = iy + ih / 2;
        g.setColor(new Color(60, 35, 5));
        g.setStroke(new BasicStroke(1.5f));
        g.drawPolyline(new int[]{ cx, cx-2, cx-6, cx-4, cx-9 },
                       new int[]{ iy+7, cy-4, cy, cy+8, iy+ih-7 }, 5);
        g.drawPolyline(new int[]{ cx, cx+4, cx+6, cx+3 },
                       new int[]{ cy, cy+7, cy+15, iy+ih-6 }, 4);
        g.setColor(new Color(255, 190, 20, 200));
        g.fillOval(cx - 5, cy + ih / 4, 10, 8);
    }

private void rebuildGradients() {
        int w = GameConfig.WINDOW_WIDTH;
        int h = GameConfig.WINDOW_HEIGHT;
        if (w != cachedW || h != cachedH) {
            skyGradient = new GradientPaint(0, 0, new Color( 30, 100, 200),
                                            0, h, new Color(135, 195, 240));
            cachedW = w;
            cachedH = h;
        }
    }
}
