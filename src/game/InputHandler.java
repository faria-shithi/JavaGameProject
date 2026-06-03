package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputHandler extends KeyAdapter {

    private final Set<Integer> heldKeys   = new HashSet<>();
    private volatile boolean   jumpPending    = false;
    private volatile boolean   restartPending = false;

@Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!heldKeys.contains(code)) {
            heldKeys.add(code);
            if (isJumpKey(code))    jumpPending    = true;
            if (code == KeyEvent.VK_R) restartPending = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        heldKeys.remove(e.getKeyCode());
    }

public boolean consumeJump() {
        if (jumpPending) { jumpPending = false; return true; }
        return false;
    }

    public boolean consumeRestart() {
        if (restartPending) { restartPending = false; return true; }
        return false;
    }

public boolean isMovingLeft() {
        return heldKeys.contains(KeyEvent.VK_LEFT) || heldKeys.contains(KeyEvent.VK_A);
    }

    public boolean isMovingRight() {
        return heldKeys.contains(KeyEvent.VK_RIGHT) || heldKeys.contains(KeyEvent.VK_D);
    }

private boolean isJumpKey(int code) {
        return code == KeyEvent.VK_SPACE
            || code == KeyEvent.VK_W
            || code == KeyEvent.VK_UP;
    }
}
