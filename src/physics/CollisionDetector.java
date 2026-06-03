package physics;

import config.GameConfig;
import entities.Basket;
import entities.Egg;
import java.util.List;

public final class CollisionDetector {

    private CollisionDetector() {  }

public static CollisionResult check(Egg egg, List<Basket> baskets) {
        if (!egg.isAirborne()) return CollisionResult.none();

        for (Basket basket : baskets) {
            CollisionResult r = checkBasket(egg, basket);
            if (!r.isNone()) return r;
        }

        if (isOnGround(egg)) return CollisionResult.ground();

        return CollisionResult.none();
    }

private static CollisionResult checkBasket(Egg egg, Basket basket) {
        
        if (egg.getVelocityY() <= 0) return CollisionResult.none();

        double eggBottom  = egg.getBottom();
        double eggCenterX = egg.getCenterX();
        double basketTop  = basket.getY();
        double innerLeft  = basket.getInnerLeft();
        double innerRight = basket.getInnerRight();

        boolean horizontallyAligned = eggCenterX >= innerLeft && eggCenterX <= innerRight;
        boolean nearRim             = eggBottom   >= basketTop &&
                                      eggBottom   <= basketTop + GameConfig.LAND_TOLERANCE;

        if (horizontallyAligned && nearRim) {
            return CollisionResult.basket(basket);
        }
        return CollisionResult.none();
    }

    private static boolean isOnGround(Egg egg) {
        return egg.getBottom() >= GameConfig.GROUND_Y;
    }
}
