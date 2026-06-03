package game;

import config.GameConfig;

public class GameLoop implements Runnable {

    private static final double MAX_DELTA   = 0.050;          
    private static final long   FRAME_NANOS =
            1_000_000_000L / GameConfig.TARGET_FPS;

    private final GamePanel     panel;
    private volatile boolean    running;

    public GameLoop(GamePanel panel) {
        this.panel   = panel;
        this.running = false;
    }

public void start() {
        running = true;
        Thread t = new Thread(this, "BasketEgg-GameLoop");
        t.setDaemon(true);
        t.start();
    }

    public void stop() {
        running = false;
    }

@Override
    public void run() {
        long lastNanos = System.nanoTime();

        while (running) {
            long   nowNanos   = System.nanoTime();
            double deltaTime  = (nowNanos - lastNanos) / 1_000_000_000.0;
            lastNanos         = nowNanos;

if (deltaTime > MAX_DELTA) deltaTime = MAX_DELTA;

            panel.tick(deltaTime);
            panel.repaint();

long elapsed  = System.nanoTime() - nowNanos;
            long sleepNs  = FRAME_NANOS - elapsed;
            if (sleepNs > 0) {
                try {
                    Thread.sleep(sleepNs / 1_000_000, (int) (sleepNs % 1_000_000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
