package ui;

import java.awt.*;

public class ScoreDisplay {

    private static final Font  LABEL_FONT = new Font("SansSerif", Font.BOLD,  16);
    private static final Font  SCORE_FONT = new Font("SansSerif", Font.BOLD,  42);
    private static final int   PAD_RIGHT  = 20;
    private static final int   PAD_TOP    = 20;

    public void render(Graphics2D g, int score) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

String scoreText = String.valueOf(score);
        g2.setFont(SCORE_FONT);
        FontMetrics sfm  = g2.getFontMetrics();
        int sw           = sfm.stringWidth(scoreText);

        g2.setFont(LABEL_FONT);
        FontMetrics lfm  = g2.getFontMetrics();
        String label     = "SCORE";
        int lw           = lfm.stringWidth(label);

        int boxW = Math.max(sw, lw) + 28;
        int boxH = 80;
        int bx   = 800 - boxW - PAD_RIGHT;   
        int by   = PAD_TOP;

        g2.setColor(new Color(0, 0, 0, 130));
        g2.fillRoundRect(bx, by, boxW, boxH, 16, 16);
        g2.setColor(new Color(255, 220, 70, 80));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(bx, by, boxW, boxH, 16, 16);

g2.setFont(LABEL_FONT);
        g2.setColor(new Color(255, 220, 70));
        int lx = bx + (boxW - lw) / 2;
        g2.drawString(label, lx, by + 22);

g2.setFont(SCORE_FONT);
        g2.setColor(Color.WHITE);
        sfm  = g2.getFontMetrics();
        sw   = sfm.stringWidth(scoreText);
        int sx = bx + (boxW - sw) / 2;
        g2.drawString(scoreText, sx, by + 68);

        g2.dispose();
    }
}
