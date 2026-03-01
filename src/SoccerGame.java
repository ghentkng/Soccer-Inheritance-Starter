import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SoccerGame implements ActionListener, KeyListener {

    // Window
    public static final int W = 900;
    public static final int H = 520;

    // Field (simple ints, no Rectangle)
    public int fieldX = 60;
    public int fieldY = 40;
    public int fieldW = 780;
    public int fieldH = 420;

    // Goals (mouth rectangles)
    public int goalDepth = 12;
    public int goalY = fieldY + 140; // 180
    public int goalH = 140; // 140
    public int leftGoalX = fieldX - goalDepth; // 48
    public int rightGoalX = fieldX + fieldW; // 840

    // Game objects
    public SoccerBall ball;
    public ArrayList<Player> players = new ArrayList<>();

    public boolean gameOver = false;
    public String gameOverText = "GOAL! Game Over";

    // Swing stuff
    private Timer timer;
    private GamePanel panel;

    public SoccerGame() {
        // Create game objects
        ball = new SoccerBall(fieldX + fieldW / 2.0, fieldY + fieldH / 2.0);

        FieldPlayer p1 = new FieldPlayer(7, fieldX + 140, fieldY + 180,
                KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
                KeyEvent.VK_SPACE);

        FieldPlayer p2 = new FieldPlayer(10, fieldX + 600, fieldY + 180,
                KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER);

        players.add(p1);
        players.add(p2);

        /*
         * TODO: Create goalies right in front of each goal mouth.
         * left goalie: x pos = fieldX + 4, y pos = goalY + 20
         * right goalie: x pos = fieldX + fieldW - 30, y pos = goalY + 20
         */

    }

    public void start() {
        JFrame frame = new JFrame("Inheritance Soccer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new GamePanel(this);
        frame.setContentPane(panel);

        // Key handling lives here (controller), not in GamePanel
        frame.addKeyListener(this);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Timer loop
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            panel.repaint();
            return;
        }

        // Update players (movement only)
        for (Player p : players) {
            p.update(fieldX, fieldY, fieldW, fieldH, ball);
        }

        // Update ball
        ball.update(fieldX, fieldY, fieldW, fieldH);

        // Goal check
        if (ballInGoal()) {
            gameOver = true;
            timer.stop();
        }

        panel.repaint();
    }

    private boolean ballInGoal() {
        // Ball circle vs goal mouth rectangle (simple check)
        double bx = ball.x;
        double by = ball.y;
        int r = ball.radius;

        // Left goal mouth rectangle: [leftGoalX, leftGoalX+goalDepth] x [goalY,
        // goalY+goalH]
        boolean inLeftX = (bx - r) <= (leftGoalX + goalDepth) && (bx + r) >= leftGoalX;
        boolean inLeftY = (by + r) >= goalY && (by - r) <= (goalY + goalH);

        // Right goal mouth rectangle: [rightGoalX, rightGoalX+goalDepth] x [goalY,
        // goalY+goalH]
        boolean inRightX = (bx + r) >= rightGoalX && (bx - r) <= (rightGoalX + goalDepth);
        boolean inRightY = inLeftY; // same vertical span

        return (inLeftX && inLeftY) || (inRightX && inRightY);
    }

    // ---------------- KeyListener ----------------

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        for (Player p : players) {
            if (p instanceof FieldPlayer fp) {
                fp.onKeyPressed(code);

                if (fp.isKickKey(code) && fp.isTouchingBall(ball)) {
                    ball.kick(fp.getKickDirX(), fp.getKickDirY());
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        for (Player p : players) {
            if (p instanceof FieldPlayer fp) {
                fp.onKeyReleased(code);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SoccerGame().start());
    }
}