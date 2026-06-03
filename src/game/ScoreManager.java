package game;

import config.GameConfig;

public class ScoreManager {

    private int score;

    public ScoreManager() {
        this.score = 0;
    }

    public void addLanding() {
        score += GameConfig.SCORE_PER_LAND;
    }

    public int  getScore() { return score; }
    public void reset()    { score = 0;    }
}
