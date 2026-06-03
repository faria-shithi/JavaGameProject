package entities;

import config.GameConfig;
import java.awt.*;
import java.awt.geom.*;

public class Basket extends GameObject {

private double  speed;       
    private int     direction;   

public Basket(double x, double y, double speed, int direction) {
        super(x, y, GameConfig.BASKET_WIDTH, GameConfig.BASKET_HEIGHT);
        this.speed     = speed;
        this.direction = direction;
    }

@Override
    public void update(double deltaTime) {
        double nx = getX() + speed * direction * deltaTime;

        if (nx <= 0) {
            nx        = 0;
            direction = 1;
        } else if (nx + getWidth() >= GameConfig.WINDOW_WIDTH) {
            nx        = GameConfig.WINDOW_WIDTH - getWidth();
            direction = -1;
        }
        setX(nx);
    }

public double getInnerLeft()  { return getX() + GameConfig.BASKET_WALL; }
    
    public double getInnerRight() { return getRight() - GameConfig.BASKET_WALL; }

@Override
    public void render(Graphics2D g) {
        renderBackground(g);
        renderWalls(g);
    }

public void renderBackground(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int ix = (int) Math.round(getX());
        int iy = (int) Math.round(getY());
        int iw = getWidth();
        int ih = getHeight();
        int wt = GameConfig.BASKET_WALL;

        g2.setColor(new Color(55, 28, 8, 140));
        g2.fillRect(ix + wt, iy, iw - 2 * wt, ih);
        g2.dispose();
    }

public void renderWalls(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ix  = (int) Math.round(getX());
        int iy  = (int) Math.round(getY());
        int iw  = getWidth();
        int ih  = getHeight();
        int wt  = GameConfig.BASKET_WALL;

        Color darkBrown  = new Color(101, 67,  33);
        Color midBrown   = new Color(140, 95,  45);
        Color lightBrown = new Color(175, 120, 55);
        Color rimColor   = new Color(210, 155, 65);

GradientPaint lw = new GradientPaint(ix, iy, lightBrown, ix + wt, iy, darkBrown);
        g2.setPaint(lw);
        g2.fillRect(ix, iy, wt, ih);

GradientPaint rw = new GradientPaint(ix + iw - wt, iy, darkBrown, ix + iw, iy, lightBrown);
        g2.setPaint(rw);
        g2.fillRect(ix + iw - wt, iy, wt, ih);

GradientPaint bt = new GradientPaint(ix, iy + ih - wt, darkBrown, ix, iy + ih, midBrown);
        g2.setPaint(bt);
        g2.fillRect(ix, iy + ih - wt, iw, wt);

g2.setColor(rimColor);
        g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(ix,            iy, ix + wt,       iy);
        g2.drawLine(ix + iw - wt, iy, ix + iw,       iy);

g2.setColor(new Color(70, 42, 15, 160));
        g2.setStroke(new BasicStroke(1.0f));
        for (int ly = iy + 7; ly < iy + ih - wt; ly += 7) {
            g2.drawLine(ix + 1,       ly, ix + wt - 1,       ly);
            g2.drawLine(ix + iw - wt + 1, ly, ix + iw - 1,  ly);
        }

g2.setColor(new Color(70, 40, 10, 200));
        g2.setStroke(new BasicStroke(1.5f));
        
        g2.drawRect(ix, iy, wt - 1, ih - 1);
        
        g2.drawRect(ix + iw - wt, iy, wt - 1, ih - 1);
        
        g2.drawRect(ix, iy + ih - wt, iw - 1, wt - 1);

        g2.dispose();
    }

public double getSpeed()     { return speed; }
    public int    getDirection() { return direction; }
}
