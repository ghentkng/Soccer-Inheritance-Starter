import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    public SoccerGame game;

    public GamePanel(SoccerGame game) {
        this.game = game;
        setPreferredSize(new Dimension(SoccerGame.W, SoccerGame.H));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(new Color(30, 120, 50));
        g2.fillRect(0, 0, SoccerGame.W, SoccerGame.H);

        drawField(g2);
        drawBall(g2);
        drawPlayers(g2);

        if (game.gameOver) {
            drawGameOver(g2);
        }

        g2.dispose();
    }

    private void drawField(Graphics2D g2) {
        // Field
        g2.setColor(new Color(40, 160, 70));
        g2.fillRect(game.fieldX, game.fieldY, game.fieldW, game.fieldH);

        // Lines
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(game.fieldX, game.fieldY, game.fieldW, game.fieldH);

        // Midline + center circle
        int midX = game.fieldX + game.fieldW / 2;
        int midY = game.fieldY + game.fieldH / 2;
        g2.drawLine(midX, game.fieldY, midX, game.fieldY + game.fieldH);
        g2.drawOval(midX - 60, midY - 60, 120, 120);

        // Goals
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(game.leftGoalX, game.goalY, game.goalDepth, game.goalH);
        g2.drawRect(game.rightGoalX, game.goalY, game.goalDepth, game.goalH);

        // Instructions
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.drawString("P1: WASD + SPACE to kick", 60, 20);
        g2.drawString("P2: Arrows + ENTER to kick", 260, 20);
        g2.drawString("Kick direction = movement direction, else facing direction", 520, 20);
    }

    private void drawBall(Graphics2D g2) {
        int r = game.ball.radius;
        int bx = (int) (game.ball.x - r);
        int by = (int) (game.ball.y - r);

        g2.setColor(Color.WHITE);
        g2.fillOval(bx, by, r * 2, r * 2);
        g2.setColor(Color.BLACK);
        g2.drawOval(bx, by, r * 2, r * 2);
    }

    private void drawPlayers(Graphics2D g2) {
        for (Player p : game.players) {
            int px = (int) p.x;
            int py = (int) p.y;

            // Body
            g2.setColor(new Color(20, 40, 200));
            g2.fillOval(px, py, p.width, p.height);

            // Jersey number
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString("" + p.jerseyNumber, px + 8, py + 17);

            // Facing indicator (booleans, no enum)
            // We'll draw a small line in the facing direction.
            int cx = px + p.width / 2;
            int cy = py + p.height / 2;

            int fx = cx;
            int fy = cy;

            int len = 14;

            if (p.facingLeft)
                fx -= len;
            if (p.facingRight)
                fx += len;
            if (p.facingUp)
                fy -= len;
            if (p.facingDown)
                fy += len;

            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(cx, cy, fx, fy);
        }
    }

    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, SoccerGame.W, SoccerGame.H);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 48));
        FontMetrics fm = g2.getFontMetrics();

        int textW = fm.stringWidth(game.gameOverText);
        g2.drawString(game.gameOverText, (SoccerGame.W - textW) / 2, SoccerGame.H / 2);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g2.drawString("Close the window to exit.", (SoccerGame.W - 240) / 2, SoccerGame.H / 2 + 40);
    }
}