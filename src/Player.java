public class Player {

    public double x, y; // x and y position

    public int width = 26;
    public int height = 26;

    public int jerseyNumber;

    // Facing flags
    public boolean facingUp = false;
    public boolean facingDown = false;
    public boolean facingLeft = false;
    public boolean facingRight = true;

    public Player(int jerseyNumber, double startX, double startY) {
        this.jerseyNumber = jerseyNumber;
        this.x = startX;
        this.y = startY;
    }

    // Default update: does nothing (subclasses decide how they move)
    public void update(int fieldX, int fieldY, int fieldW, int fieldH, SoccerBall ball) {
        // Intentionally blank
    }

    // Shared helper: clamp inside field
    public void clampToField(int fx, int fy, int fw, int fh) {
        if (x < fx)
            x = fx;
        if (y < fy)
            y = fy;
        if (x + width > fx + fw)
            x = fx + fw - width;
        if (y + height > fy + fh)
            y = fy + fh - height;
    }

    /**
     * Shared collision helper: true if this player is touching the ball.
     * Rectangle (player) vs circle (ball), pure math.
     */
    public boolean isTouchingBall(SoccerBall ball) {
        double closestX = clampValue(ball.x, x, x + width);
        double closestY = clampValue(ball.y, y, y + height);

        double dx = ball.x - closestX;
        double dy = ball.y - closestY;

        return (dx * dx + dy * dy) <= (ball.radius * ball.radius);
    }

    private double clampValue(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}