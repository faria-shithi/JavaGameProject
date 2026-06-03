package entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class GameObject implements Renderable, Updatable, Collidable {

private double  x;
    private double  y;
    private final int width;
    private final int height;
    private boolean active;

    protected GameObject(double x, double y, int width, int height) {
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
        this.active = true;
    }

@Override public abstract void update(double deltaTime);
    @Override public abstract void render(Graphics2D g);

@Override
    public Rectangle2D getCollisionBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

public double getCenterX() { return x + width  / 2.0; }
    public double getCenterY() { return y + height / 2.0; }
    public double getBottom()  { return y + height; }
    public double getRight()   { return x + width;  }

public double  getX()      { return x; }
    public double  getY()      { return y; }
    public int     getWidth()  { return width; }
    public int     getHeight() { return height; }
    public boolean isActive()  { return active; }

public void setX(double x)            { this.x      = x; }
    public void setY(double y)            { this.y      = y; }
    public void setActive(boolean active) { this.active = active; }
}
