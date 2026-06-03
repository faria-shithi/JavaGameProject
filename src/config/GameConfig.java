package config;

public final class GameConfig {

    private GameConfig() {  }

public static final int    WINDOW_WIDTH    = 800;
    public static final int    WINDOW_HEIGHT   = 600;
    public static final String WINDOW_TITLE    = "Basket Egg";
    public static final int    TARGET_FPS      = 60;

public static final double GRAVITY         = 1400.0;  
    public static final double JUMP_FORCE      = -750.0;  
    public static final double MAX_FALL_SPEED  = 900.0;   

public static final int    EGG_WIDTH       = 36;
    public static final int    EGG_HEIGHT      = 46;
    public static final int    EGG_START_X     = 120;
    
    public static final double EGG_MOVE_SPEED  = 220.0;

public static final int    BASKET_WIDTH    = 84;
    public static final int    BASKET_HEIGHT   = 48;
    public static final int    BASKET_WALL     = 10;
    public static final int    BASKET_COUNT    = 4;       
    public static final double BASKET_MIN_SPEED = 90.0;
    public static final double BASKET_MAX_SPEED = 200.0;

public static final int    GROUND_Y        = WINDOW_HEIGHT - 60;
    public static final int    GROUND_HEIGHT   = 60;

public static final double EGG_SINK_RATIO  = 0.45;
    public static final double LAND_TOLERANCE  = 20.0;

public static final int    SCROLL_THRESHOLD     = 220;

public static final int    BASKET_GEN_MARGIN    = 160;
    
    public static final int    BASKET_GAP_MIN       = 130;
    
    public static final int    BASKET_GAP_MAX       = 165;

public static final int    SCORE_PER_LAND  = 1;

public static final double GAME_TIME_LIMIT = Double.POSITIVE_INFINITY;
}
