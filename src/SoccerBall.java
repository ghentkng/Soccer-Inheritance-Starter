public class SoccerBall {
    public double x, y;
    public double vx, vy;
    public int radius = 10;
    public double friction = 0.92;
    public double stopThreshold = 0.15;

    public SoccerBall(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
    }

    public void update(int fieldX, int fieldY, int fieldW, int fieldH) {
        x += vx;
        y += vy;

        int left = fieldX;
        int right = fieldX + fieldW;
        int top = fieldY;
        int bottom = fieldY + fieldH;

        // Bounce off field
        if (x - radius < left) {
            x = left + radius;
            vx *= -0.6;
        } else if (x + radius > right) {
            x = right - radius;
            vx *= -0.6;
        }

        if (y - radius < top) {
            y = top + radius;
            vy *= -0.6;
        } else if (y + radius > bottom) {
            y = bottom - radius;
            vy *= -0.6;
        }

        // Friction
        vx *= friction;
        vy *= friction;

        if (Math.abs(vx) < stopThreshold)
            vx = 0;
        if (Math.abs(vy) < stopThreshold)
            vy = 0;
    }

    public void kick(int dx, int dy) {
        // If both are zero (shouldn't happen), default to right
        if (dx == 0 && dy == 0)
            dx = 1;

        vx = dx * 9;
        vy = dy * 9;
    }
}