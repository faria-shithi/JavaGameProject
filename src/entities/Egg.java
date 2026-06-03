package entities;

import config.GameConfig;
import java.awt.*;
import java.awt.geom.*;

public class Egg extends GameObject {

    private double   velocityY;
    private EggState state;
    private Basket   currentBasket;

    public Egg(double x, double y) {
        super(x, y, GameConfig.EGG_WIDTH, GameConfig.EGG_HEIGHT);
        this.velocityY     = 0.0;
        this.state         = EggState.GROUNDED;
        this.currentBasket = null;
    }

@Override
    public void update(double deltaTime) {
        if (state == EggState.IN_BASKET && currentBasket != null) {
            double visibleHeight = getHeight() * (1.0 - GameConfig.EGG_SINK_RATIO);
            setX(currentBasket.getCenterX() - getWidth() / 2.0);
            setY(currentBasket.getY() - visibleHeight);
        }
    }

public void jump() {
        if (state == EggState.GROUNDED || state == EggState.IN_BASKET) {
            velocityY     = GameConfig.JUMP_FORCE;
            currentBasket = null;
            state         = EggState.AIRBORNE;
        }
    }

public void moveHorizontal(double dx) {
        if (state != EggState.GROUNDED) return;
        double nx = getX() + dx;
        nx = Math.max(0, Math.min(nx, GameConfig.WINDOW_WIDTH - getWidth()));
        setX(nx);
    }

    public void landOnGround() {
        landOnGround(GameConfig.GROUND_Y);
    }

    public void landOnGround(double groundY) {
        setY(groundY - getHeight());
        velocityY     = 0.0;
        currentBasket = null;
        state         = EggState.GROUNDED;
    }

    public void landInBasket(Basket basket) {
        this.currentBasket = basket;
        this.velocityY     = 0.0;
        this.state         = EggState.IN_BASKET;
    }

public void crack() {
        velocityY     = 0.0;
        currentBasket = null;
        state         = EggState.CRACKED;
    }

public void reset(double x, double y) {
        setX(x);
        setY(y);
        velocityY     = 0.0;
        currentBasket = null;
        state         = EggState.GROUNDED;
    }

@Override
    public void render(Graphics2D g) {
        if (state == EggState.CRACKED) {
            renderCracked(g);
        } else {
            renderNormal(g);
        }
    }

    private void renderNormal(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);

        int ix = (int) Math.round(getX());
        int iy = (int) Math.round(getY());
        int iw = getWidth();
        int ih = getHeight();

        RadialGradientPaint body = new RadialGradientPaint(
            new Point2D.Float(ix + iw * 0.38f, iy + ih * 0.30f),
            Math.max(iw, ih) * 0.70f,
            new float[]{ 0.0f, 1.0f },
            new Color[]{ new Color(255, 253, 220), new Color(238, 200, 100) }
        );
        g2.setPaint(body);
        g2.fillOval(ix, iy, iw, ih);

        g2.setColor(new Color(180, 135, 50));
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawOval(ix, iy, iw, ih);

g2.setColor(new Color(255, 255, 255, 110));
        g2.fillOval(ix + iw / 4, iy + ih / 10, iw / 3, ih / 5);

        g2.dispose();
    }

    private void renderCracked(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ix = (int) Math.round(getX());
        int iy = (int) Math.round(getY());
        int iw = getWidth();
        int ih = getHeight();

RadialGradientPaint body = new RadialGradientPaint(
            new Point2D.Float(ix + iw * 0.38f, iy + ih * 0.30f),
            Math.max(iw, ih) * 0.70f,
            new float[]{ 0.0f, 1.0f },
            new Color[]{ new Color(215, 205, 175), new Color(175, 150, 90) }
        );
        g2.setPaint(body);
        g2.fillOval(ix, iy, iw, ih);

        g2.setColor(new Color(100, 75, 25));
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawOval(ix, iy, iw, ih);

g2.setColor(new Color(60, 35, 5));
        g2.setStroke(new BasicStroke(1.5f));
        int cx = ix + iw / 2;
        int cy = iy + ih / 2;

g2.drawPolyline(
            new int[]{ cx,     cx - 2, cx - 6, cx - 4, cx - 9 },
            new int[]{ iy + 7, cy - 4, cy,     cy + 8, iy + ih - 7 }, 5);

g2.drawPolyline(
            new int[]{ cx,     cx + 4, cx + 6, cx + 3 },
            new int[]{ cy,     cy + 7, cy + 15, iy + ih - 6 }, 4);

g2.drawPolyline(
            new int[]{ cx + 6, cx + 2, cx - 1 },
            new int[]{ iy + 9, cy - 7, cy - 2  }, 3);

g2.setColor(new Color(255, 190, 20, 200));
        g2.fillOval(cx - 5, cy + ih / 4, 10, 8);

        g2.dispose();
    }

public double   getVelocityY()     { return velocityY; }
    public EggState getState()         { return state;     }
    public Basket   getCurrentBasket() { return currentBasket; }

    public boolean isAirborne() { return state == EggState.AIRBORNE;  }
    public boolean isGrounded() { return state == EggState.GROUNDED;  }
    public boolean isInBasket() { return state == EggState.IN_BASKET; }
    public boolean isCracked()  { return state == EggState.CRACKED;   }

    public void setVelocityY(double v) { this.velocityY = v; }
}
