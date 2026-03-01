public class FieldPlayer extends Player {

    // Key bindings
    public int keyUp, keyDown, keyLeft, keyRight, keyKick;

    // Movement state
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public double speed = 3.0;

    public FieldPlayer(int jerseyNumber, double startX, double startY,
            int keyUp, int keyDown, int keyLeft, int keyRight, int keyKick) {
        super(jerseyNumber, startX, startY);
        this.keyUp = keyUp;
        this.keyDown = keyDown;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyKick = keyKick;
    }

    public void onKeyPressed(int keyCode) {
        if (keyCode == keyUp)
            upPressed = true;
        if (keyCode == keyDown)
            downPressed = true;
        if (keyCode == keyLeft)
            leftPressed = true;
        if (keyCode == keyRight)
            rightPressed = true;
    }

    public void onKeyReleased(int keyCode) {
        if (keyCode == keyUp)
            upPressed = false;
        if (keyCode == keyDown)
            downPressed = false;
        if (keyCode == keyLeft)
            leftPressed = false;
        if (keyCode == keyRight)
            rightPressed = false;
    }

    public boolean isKickKey(int keyCode) {
        return keyCode == keyKick;
    }

    @Override
    public void update(int fieldX, int fieldY, int fieldW, int fieldH, SoccerBall ball) {
        double dx = 0;
        double dy = 0;

        if (upPressed)
            dy -= speed;
        if (downPressed)
            dy += speed;
        if (leftPressed)
            dx -= speed;
        if (rightPressed)
            dx += speed;

        // Facing updates when moving
        if (dx != 0 || dy != 0) {
            setFacingFromMove(dx, dy);
        }

        x += dx;
        y += dy;

        clampToField(fieldX, fieldY, fieldW, fieldH);

        // (Optional) FieldPlayer does NOT auto-kick. Kicking stays in
        // SoccerGame.keyPressed.
    }

    private void setFacingFromMove(double dx, double dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            facingLeft = dx < 0;
            facingRight = dx > 0;
            facingUp = false;
            facingDown = false;
        } else {
            facingUp = dy < 0;
            facingDown = dy > 0;
            facingLeft = false;
            facingRight = false;
        }
    }

    // Kick direction components: movement intent first, else facing
    public int getKickDirX() {
        if (leftPressed && !rightPressed)
            return -1;
        if (rightPressed && !leftPressed)
            return 1;
        if (facingLeft)
            return -1;
        if (facingRight)
            return 1;
        return 0;
    }

    public int getKickDirY() {
        if (upPressed && !downPressed)
            return -1;
        if (downPressed && !upPressed)
            return 1;
        if (facingUp)
            return -1;
        if (facingDown)
            return 1;
        return 0;
    }
}