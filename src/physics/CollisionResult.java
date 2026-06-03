package physics;

import entities.Basket;

public final class CollisionResult {

    public enum Type { NONE, LAND_IN_BASKET, HIT_GROUND }

    private final Type   type;
    private final Basket basket;

    private CollisionResult(Type type, Basket basket) {
        this.type   = type;
        this.basket = basket;
    }

public static CollisionResult none()           { return new CollisionResult(Type.NONE,           null); }
    public static CollisionResult ground()         { return new CollisionResult(Type.HIT_GROUND,     null); }
    public static CollisionResult basket(Basket b) { return new CollisionResult(Type.LAND_IN_BASKET, b);   }

public boolean isNone()       { return type == Type.NONE;           }
    public boolean isGround()     { return type == Type.HIT_GROUND;     }
    public boolean isBasketLand() { return type == Type.LAND_IN_BASKET; }

    public Basket getBasket() { return basket; }
    public Type   getType()   { return type;   }
}
