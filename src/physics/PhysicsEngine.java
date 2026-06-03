package physics;

import config.GameConfig;
import entities.Egg;

public final class PhysicsEngine {

    private PhysicsEngine() {  }

public static void applyGravity(Egg egg, double deltaTime) {
        double newVY = egg.getVelocityY() + GameConfig.GRAVITY * deltaTime;
        egg.setVelocityY(Math.min(newVY, GameConfig.MAX_FALL_SPEED));
    }

public static void applyVerticalVelocity(Egg egg, double deltaTime) {
        egg.setY(egg.getY() + egg.getVelocityY() * deltaTime);
    }
}
